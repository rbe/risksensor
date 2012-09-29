--
-- Convert data from external table
--
CREATE OR REPLACE VIEW v_rs_import
AS
SELECT
        i.recordnum
        , TRIM(i.sdcardid) sdcardid
        , TRIM(i.deviceid) deviceid
        , CASE
            WHEN LENGTH(i.theyear) > 2 OR LENGTH(i.thehour) > 2
                THEN NULL
            WHEN i.theyear > 0 AND TO_NUMBER(i.theyear) = TO_CHAR(SYSDATE, 'YY') AND i.themonth > 0 AND i.themonth <= 12 AND i.theday > 0 AND i.theday <= 31 AND i.thehour >= 0 AND i.thehour <= 23 AND i.themin >= 0 AND i.themin <= 59 AND i.thesec >= 0 AND i.thesec <= 59
                THEN TO_TIMESTAMP('20' || i.theyear || '-' || i.themonth || '-' || i.theday || ' ' || i.thehour || ':' || i.themin || ':' || i.thesec, 'YYYY-MM-DD HH24:MI:SS')
            ELSE NULL
          END createdon
        , CASE -- Battery defect?
            WHEN LENGTH(i.theyear) > 2
                THEN NULL
            WHEN i.theyear > 0 AND TO_NUMBER(i.theyear) = TO_CHAR(SYSDATE, 'YY') AND i.themonth > 0 AND i.themonth <= 12 AND i.theday > 0 AND i.theday <= 31
                THEN TRUNC(TO_DATE('20' || i.theyear || '-' || i.themonth || '-' || i.theday, 'YYYY-MM-DD'))
            ELSE NULL
          END createddate
        , CASE -- Battery defect, debug data found?
            WHEN SUBSTR(i.theday, 1, 3) = 'EVT'
                THEN 'DEBUG DATA FOUND: ' || i.theday
            WHEN i.theyear <= 0 OR i.theyear IS NULL OR i.themonth > 12 OR i.themonth <= 0 OR i.themonth IS NULL OR i.theday > 31 OR i.theday <= 0 OR i.theday IS NULL
                THEN 'DATE OUT OF BOUNDS: Y=' || i.theyear || ' M=' || i.themonth || ' D=' || i.theday
            WHEN LENGTH(i.theyear) > 0 AND LENGTH(i.theyear) < 3 AND TO_NUMBER(i.theyear) <> TO_CHAR(SYSDATE, 'YY')
                THEN 'DATE OUT OF BOUNDS: Y=' || i.theyear || ' M=' || i.themonth || ' D=' || i.theday
            ELSE NULL
          END datewarning
        , CASE
            WHEN LENGTH(i.thehour) > 2
                THEN NULL
            WHEN i.thehour >= 0 AND i.thehour <= 23 AND i.themin >= 0 AND i.themin <= 59 AND i.thesec >= 0 AND i.thesec <= 59
                THEN TO_DATE(i.thehour || ':' || i.themin || ':' || i.thesec, 'HH24:MI:SS')
            ELSE NULL
          END createdtime
        , CASE -- Battery defect, debug data found?
            WHEN SUBSTR(i.theday, 1, 3) = 'EVT'
                THEN 'DEBUG DATA FOUND: ' || i.theday
            WHEN i.thehour < 0 OR i.thehour > 23 OR i.thehour IS NULL OR i.themin < 0 OR i.themin > 59 OR i.themin IS NULL OR i.thesec < 0 OR i.thesec > 59 OR i.thesec IS NULL
                THEN 'TIME OUT OF BOUNDS: H=' || i.thehour || ' M=' || i.themin || ' S=' || i.thesec
            ELSE NULL
          END timewarning
        , CASE
            WHEN LENGTH(i.eventid) <= 2
                THEN TO_NUMBER(i.eventid)
            ELSE NULL
          END eventid
        , i.xmg -- Don't convert to number: may contain configuration commands
        , CASE
            WHEN LENGTH(i.ymg) = 0
                THEN NULL
            ELSE TO_NUMBER(i.ymg)
          END ymg
        , CASE
            WHEN LENGTH(i.zmg) = 0
                THEN NULL
            ELSE TO_NUMBER(i.zmg)
          END zmg
        , CASE
            WHEN LENGTH(i.durationms) = 0
                THEN NULL
            ELSE TO_NUMBER(i.durationms)
          END durationms
  FROM
        xt_rs_import i
;

--
--
--
CREATE OR REPLACE VIEW v_rs_import_threshold
AS
SELECT DISTINCT
        i.recordnum
        , i.deviceid
        , i.createdon
        /*, i.createddate
        , i.createdtime*/
        , i.eventid
        , SUBSTR(i.xmg, 1, 3) cmdprefix -- first three characters = command
        , TO_NUMBER(SUBSTR(i.xmg, 4)) cmdvalue -- rest is configuration value
  FROM
        v_rs_import i
 WHERE
        i.eventid IN (33, 34)
        AND SUBSTR(i.xmg, 1, 3) IN (SELECT cmdprefix FROM t_rs_cmd) -- commands we're interested in
        AND i.datewarning IS NULL
        AND i.timewarning IS NULL
 ORDER BY
        i.createdon
        , i.recordnum
;

--
-- Import sensor data
--
CREATE OR REPLACE VIEW v_rs_import_data
AS
SELECT DISTINCT
        i.recordnum
        , i.sdcardid
        , i.deviceid
        , i.createdon
        , i.createddate
        , i.createdtime
        , i.eventid
        , i.datewarning
        , i.timewarning
        , TO_NUMBER(i.xmg) xmg
        , i.ymg
        , i.zmg
        , i.durationms
  FROM
        v_rs_import i
 WHERE
        i.eventid NOT IN (33, 34)
        AND i.datewarning IS NULL -- TODO DO NOT IGNORE
        AND i.timewarning IS NULL -- TODO DO NOT IGNORE
 ORDER BY
        i.createdon
        , i.recordnum
;

--
-- Resolve configured thresholds for events
--
CREATE OR REPLACE VIEW v_rs_threshold
AS
SELECT
        t.recordnum
        , t.deviceid
        , n.lpn
        , t.validfrom
        , t.validto
        , c.foreventid -- Value configured "for" event id
        , e.eventname
        , t.cmdprefix
        , t.cmdvalue
  FROM
        t_rs_threshold t
        INNER JOIN t_rs_cmd c
            ON c.cmdprefix = t.cmdprefix
        INNER JOIN t_rs_event e
            ON c.foreventid = e.id
        LEFT JOIN t_rs_lpn n
            ON t.deviceid = n.deviceid
 ORDER BY
        t.recordnum
;

--
-- Show risk sensor events
--
CREATE OR REPLACE VIEW v_rs_event
AS
SELECT
        d.recordnum
        , CASE
            WHEN d.sdcardid = '0'
                THEN 'NO SDCARD'
            ELSE d.sdcardid
          END sdcardid
        , d.deviceid
        , n.lpn
        , CASE
            WHEN d.datewarning IS NULL
                THEN d.createddate
            ELSE NULL
          END createddate
        , d.datewarning
        , CASE
            WHEN d.timewarning IS NULL
                THEN d.createdtime
            ELSE NULL
          END createdtime
        , d.timewarning
        , d.createdon
        , d.eventid
        , e.eventname
        , te.cmdvalue eventthreshold
        , CASE
            WHEN d.eventid IN (7, 8, 9, 11, 12, 13)
                THEN 'TRUE'
            ELSE 'FALSE'
          END threshold_violated
        , CASE
            -- x
            WHEN d.eventid IN (7, 11)
                THEN d.xmg
            -- y
            WHEN d.eventid IN (8, 12)
                THEN d.ymg
            -- z
            WHEN d.eventid = 9
                THEN d.zmg
            WHEN d.eventid = 13
                THEN d.zmg + 981
            ELSE NULL
          END absolutemg
        , CASE
            -- x
            WHEN d.eventid IN (7, 11)
                THEN d.xmg - te.cmdvalue
            -- y
            WHEN d.eventid IN (8, 12)
                THEN d.ymg - te.cmdvalue
            -- z
            WHEN d.eventid = 9
                THEN d.zmg - te.cmdvalue
            WHEN d.eventid = 13
                THEN d.zmg + 981 - te.cmdvalue
            ELSE NULL
          END relativemg
        , ef.factor
        , ef.valuefactor
        , CASE
            -- x
            WHEN d.eventid IN (7, 11)
                THEN d.xmg - te.cmdvalue * NVL(ef.factor, 1) * NVL(ef.valuefactor, 1)
            -- y
            WHEN d.eventid IN (8, 12)
                THEN d.ymg - te.cmdvalue * NVL(ef.factor, 1) * NVL(ef.valuefactor, 1)
            -- z
            WHEN d.eventid = 9
                THEN d.zmg - te.cmdvalue * NVL(ef.factor, 1) * NVL(ef.valuefactor, 1)
            WHEN d.eventid = 13
                THEN d.zmg + 981 - te.cmdvalue * NVL(ef.factor, 1) * NVL(ef.valuefactor, 1)
            ELSE NULL
          END vmg
        , td.cmdvalue durationthreshold
        , d.durationms durationms
        , d.durationms - td.cmdvalue relativedurationms
        , ef.timefactor
        , d.durationms - td.cmdvalue * NVL(ef.factor, 1) * NVL(ef.timefactor, 1) vduration
  FROM
        t_rs_data d
        INNER JOIN t_rs_event e
            ON d.eventid = e.id
        LEFT JOIN t_rs_lpn n
            ON d.deviceid = n.deviceid
        LEFT JOIN v_rs_threshold te
            ON te.deviceid = d.deviceid
            AND d.eventid = te.foreventid
            AND te.validfrom <= d.createdon
            AND (te.validto >= d.createdon OR te.validto IS NULL)
            AND SUBSTR(te.cmdprefix, 3, 1) = 'L' -- threshold configuration commands
        LEFT JOIN v_rs_threshold td
            ON td.deviceid = d.deviceid
            AND d.eventid = td.foreventid
            AND td.validfrom <= d.createdon
            AND (td.validto >= d.createdon OR td.validto IS NULL)
            AND SUBSTR(td.cmdprefix, 3, 1) = 'D' -- duration configuration commands
        LEFT JOIN t_rs_eventfactor ef
            ON ef.deviceid = d.deviceid
            AND d.eventid = ef.eventid
            AND ef.validfrom <= d.createdon
            AND (ef.validto >= d.createdon OR ef.validto IS NULL)
  WHERE
        d.eventid IN (1, 2, 7, 8, 9, 11, 12, 13)
 ORDER BY
        createdon
        , d.recordnum
;
--TO_TIMESTAMP(TO_CHAR(d.createdon, 'DD.MM.YYYY') || ' ' || TO_CHAR(d.createdtime, 'HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS')

--
-- Show speedup events
-- 
CREATE OR REPLACE VIEW v_rs_event_speedup
AS
SELECT
        e.recordnum
        , e.sdcardid
        , e.deviceid
        , e.lpn
        , e.createdon
        , e.eventname
        , e.eventthreshold
        , e.absolutemg
        , e.relativemg
        , e.durationms
  FROM
        v_rs_event e
 WHERE
        e.eventid = 7
;

--
-- Show brake events
--
CREATE OR REPLACE VIEW v_rs_event_brake
AS
SELECT
        e.recordnum
        , e.sdcardid
        , e.deviceid
        , e.lpn
        , e.createdon
        , e.eventname
        , e.eventthreshold
        , e.absolutemg
        , e.relativemg
        , e.durationms
  FROM
        v_rs_event e
 WHERE
        e.eventid = 11
;

--
-- Show events 7, 11
--
CREATE OR REPLACE VIEW v_rs_event_x
AS
SELECT
        *
  FROM
        v_rs_speedup
UNION ALL
SELECT
        *
  FROM
        v_rs_brakehard
;

--
-- Show events 8, 12
--
/*
CREATE OR REPLACE VIEW v_rs_event_y
AS
SELECT
        *
  FROM
        v_rs_speedup
UNION ALL
SELECT
        *
  FROM
        v_rs_brakehard
;
*/

--
-- Show events for sharp turn
--
CREATE OR REPLACE VIEW v_rs_sharpturn
AS
SELECT
        e.recordnum
        , e.sdcardid
        , e.deviceid
        , e.lpn
        , e.createdon
        , e.eventname
        , e.eventthreshold
        , e.absolutemg
        , e.relativemg
        , e.durationms
  FROM
        v_rs_event e
 WHERE
        e.eventid IN (8, 12)
;

--
--
--
CREATE OR REPLACE VIEW v_device_sdcard
AS
SELECT
        td.deviceid
        , l.lpn
        , td.sdcardid
        , s.lastname
  FROM
        t_rs_tour_data td
        LEFT JOIN t_rs_lpn l ON td.deviceid = l.deviceid
        LEFT JOIN t_rs_sdcard s ON td.sdcardid = s.sdcardid
 GROUP BY
        td.deviceid
        , l.lpn
        , td.sdcardid
        , s.lastname
 ORDER BY
        td.deviceid
        , td.sdcardid
;

--
--
--
CREATE OR REPLACE VIEW v_device_data
AS
SELECT
        deviceid
        , MAX(createdon) lastcreatedon
  FROM
        t_rs_tour_data
 GROUP BY
        deviceid
;

--
-- View tours
--
CREATE OR REPLACE VIEW v_rs_tour
AS
SELECT
        t.id
        , t.deviceid
        , t.sdcardid
        , t.started
        , t.ended
        , t.durationts
        , t.exceedcount / ((TRUNC(SYSDATE) + t.durationts - TRUNC(SYSDATE)) * 60 * 24) eventspermin
        , ((TRUNC(SYSDATE) + t.durationts - TRUNC(SYSDATE)) * 60 * 60 * 24) durationsec
        , ((TRUNC(SYSDATE) + t.durationts - TRUNC(SYSDATE)) * 60 * 24) durationmin
        , ((TRUNC(SYSDATE) + t.durationts - TRUNC(SYSDATE)) * 24) durationhr
        , t.exceedcount
        , (SELECT COUNT(*) FROM t_rs_tour_data WHERE tourid = id AND eventid IN (7, 11) AND points > 0) exceedcount7_11
        , (SELECT COUNT(*) FROM t_rs_tour_data WHERE tourid = id AND eventid IN (8, 12) AND points > 0) exceedcount8_12
        , (SELECT COUNT(*) FROM t_rs_tour_data WHERE tourid = id AND eventid IN (9, 13) AND points > 0) exceedcount9_13
        , NVL(t.points, 0) points
        , CASE
            WHEN t.points > 0 AND t.exceedcount > 0
                THEN ROUND(NVL(t.points, 0) / NVL(t.exceedcount, 1), 2)
            ELSE 0
          END avgpoints
        , NVL((SELECT SUM(points) FROM t_rs_tour_data WHERE tourid = id AND eventid IN (7, 11)), 0) points7_11
        , NVL((SELECT SUM(points) FROM t_rs_tour_data WHERE tourid = id AND eventid IN (8, 12)), 0) points8_12
        , NVL((SELECT SUM(points) FROM t_rs_tour_data WHERE tourid = id AND eventid IN (9, 13)), 0) points9_13
  FROM
        t_rs_tour t
 ORDER BY
        t.deviceid
        , t.id
        , t.started
;

--
-- Tour without ended date
--
CREATE OR REPLACE VIEW v_rs_tour_wo_end
AS
SELECT
        t.deviceid
        , COUNT(*) num
  FROM
        v_rs_tour t
 WHERE
        t.ended IS NULL
 GROUP BY
        t.deviceid
;

--
--
--
CREATE OR REPLACE VIEW v_rs_tour_1
AS
SELECT
        x.*
        , t.started
        , t.ended
        , t.durationts
        , t.exceedcount totalexceedcount
  FROM
        (
            SELECT
                    td.tourid
                    , td.deviceid
                    , td.sdcardid
                    , COUNT(*) hourexceedcount
                    , SUM(td.points) points
              FROM
                    v_rs_tour_data td
             GROUP BY
                    td.deviceid
                    , td.sdcardid
                    , td.tourid
        ) x
        INNER JOIN v_rs_tour t
            ON x.tourid = t.id
;

--
--
--
CREATE OR REPLACE VIEW v_rs_tour_2
AS
SELECT
        deviceid
        , sdcardid
        , COUNT(*) tour#
        , sum_interval(durationts) duration
        , MAX(exceedcount) exceedcount
        , SUM(exceedcount7_11) exceedcount7_11
        , SUM(exceedcount8_12) exceedcount8_12
        , SUM(exceedcount9_13) exceedcount9_13
        , SUM(points) points
        , SUM(points7_11) points7_11
        , SUM(points8_12) points8_12
        , SUM(points9_13) points9_13
  FROM
        v_rs_tour t
 GROUP BY
        deviceid
        , sdcardid
;

--
-- View tour data
--
CREATE OR REPLACE VIEW v_rs_tour_data
AS
SELECT
        d.tourid
        , d.recordnum
        , t.deviceid
        , t.sdcardid
        , t.started
        , t.ended
        , d.createdon
        , d.relativetourtime
        , d.driventime
        , d.eventid
        , d.eventthreshold
        , d.absolutemg
        , d.relativemg
        , d.pctthrexceeded
        , d.factor
        , d.valuefactor
        , DECODE(ABS(e.vmg), NULL, 0, ABS(e.vmg)) vmg
        , d.durationthreshold
        , DECODE(d.durationms, NULL, 0, d.durationms) durationms
        , d.relativedurationms
        , d.pctdurexceeded
        , d.timefactor
        , e.vduration
        , DECODE(d.points, NULL, 0, d.points) points
        , d.eventlastoccured
  FROM
        t_rs_tour_data d
        INNER JOIN v_rs_tour t
            ON d.tourid = t.id
        INNER JOIN v_rs_event e
            ON t.deviceid = e.deviceid
            AND t.sdcardid = e.sdcardid
            AND d.createdon = e.createdon
            AND d.recordnum = e.recordnum
            AND d.eventid = e.eventid
 ORDER BY
        d.createdon
        , d.recordnum
;

--
-- View tour data by min
--
CREATE OR REPLACE VIEW v_rs_tour_data_bymin
AS
SELECT
        deviceid
        , sdcardid
        , TO_TIMESTAMP(TO_CHAR(createdon, 'DD.MM.YYYY HH24:MI'), 'DD.MM.YYYY HH24:MI') driventime
        , eventid
        , SUM(vmg) vmg
        , SUM(vduration) vduration
        , SUM(points) points
  FROM
        v_rs_tour_data d
 GROUP BY
        deviceid
        , sdcardid
        , eventid
        , TO_CHAR(createdon, 'DD.MM.YYYY HH24:MI')
 ORDER BY
        deviceid
        , sdcardid
        , eventid
        , TO_CHAR(createdon, 'DD.MM.YYYY HH24:MI')
;

--
-- View tour data by hour
--
CREATE OR REPLACE VIEW v_rs_tour_data_byhr
AS
SELECT
        deviceid
        , sdcardid
        , TO_TIMESTAMP(TO_CHAR(createdon, 'DD.MM.YYYY HH24'), 'DD.MM.YYYY HH24') driventime
        , eventid
        , SUM(vmg) vmg
        , SUM(vduration) vduration
        , SUM(points) points
  FROM
        v_rs_tour_data d
 GROUP BY
        deviceid
        , sdcardid
        , eventid
        , TO_CHAR(createdon, 'DD.MM.YYYY HH24')
 ORDER BY
        deviceid
        , sdcardid
        , eventid 
        , TO_CHAR(createdon, 'DD.MM.YYYY HH24')
;

--
--
--
CREATE OR REPLACE VIEW v_tour_log
AS
SELECT
        t.deviceid
        , t.sdcardid
        , t.tourcount
        , TO_CHAR(t.createdon, 'DD.MM.YYYY HH24:MI:SS') createdon
        , t.sensorevent
        , t.drivenseconds
        , t.drivenseconds / 60 drivenminutes
        , t.drivenseconds / 60 / 60 drivenhours
        , t.speeduppointssecond
        , t.speeduppointssum
        , t.brakepointssecond
        , t.brakepointssum
        , t.leftcurvepointssecond
        , t.leftcurvepointssum
        , t.rightcurvepointssecond
        , t.rightcurvepointssum
        , t.bumppointssecond
        , t.bumppointssum
        , t.potholepointssecond
        , t.potholepointssum
        , t.pointssecond
        , t.pointssum
  FROM
        t_rs_tour_log t
 ORDER BY
        t.deviceid
        , t.sdcardid
        , t.createdon
;

--
--
--
CREATE OR REPLACE VIEW v_tour_log_bymin
AS
SELECT
        t.deviceid
        , t.sdcardid
        , t.tourcount
        , t.drivenseconds
        , t.drivenseconds / 60 drivenminutes
        , t.drivenseconds / 60 / 60 drivenhours
        , t.pointssum
        , t.speeduppointssum
        , t.brakepointssum
        , t.leftcurvepointssum
        , t.rightcurvepointssum
        , t.bumppointssum
        , t.potholepointssum
  FROM
        t_rs_tour_log t
 WHERE
        MOD(t.drivenseconds, 60) = 0
 ORDER BY
        t.deviceid ASC
        , t.sdcardid ASC
        , t.drivenseconds / 60 ASC
;

--
--
--
CREATE OR REPLACE VIEW v_tour_log_byhour
AS
SELECT
        t.deviceid
        , t.sdcardid
        , t.tourcount
        , t.drivenseconds
        , t.drivenseconds / 60 drivenminutes
        , t.drivenseconds / 60 / 60 drivenhours
        , t.pointssum
        , t.speeduppointssum
        , t.brakepointssum
        , t.leftcurvepointssum
        , t.rightcurvepointssum
        , t.bumppointssum
        , t.potholepointssum
  FROM
        t_rs_tour_log t
 WHERE
        MOD(t.drivenseconds / 60 / 60, 1) = 0
 ORDER BY
        t.deviceid ASC
        , t.sdcardid ASC
        , t.drivenseconds / 60 / 60 ASC
;
