--
-- Procedure for importing risk sensor threshold values from CSV data
--
DROP PROCEDURE import_threshold;
CREATE OR REPLACE PROCEDURE import_threshold
AS
    CURSOR v_cur IS
        SELECT
                i.recordnum
                , i.deviceid
                , i.createdon
                /*, i.createddate
                , i.createdtime*/
                , i.eventid
                , cmdprefix
                , cmdvalue
          FROM
                v_rs_import_threshold i
        ;
    v_rec       v_cur%ROWTYPE;
    v_ts        TIMESTAMP;
    v_sqlerrm   CLOB;
BEGIN
    FOR v_rec IN v_cur LOOP
        BEGIN
            -- Create timestamp
            /*
            v_ts := TO_TIMESTAMP(
                        TO_CHAR(v_rec.createddate, 'DD.MM.YYYY')
                        || ' '
                        || TO_CHAR(v_rec.createdtime, 'HH24:MI:SS')
                        , 'DD.MM.YYYY HH24:MI:SS'
                    );
            */
            v_ts := v_rec.createdon;
            --
            UPDATE
                    t_rs_threshold
               SET
                    validto = v_ts
             WHERE
                    deviceid = v_rec.deviceid
                    AND cmdprefix = v_rec.cmdprefix
                    AND eventid = v_rec.eventid
                    AND validfrom < v_ts
                    AND validto IS NULL
            ;
            --
            INSERT INTO t_rs_threshold
                    (recordnum, deviceid, validfrom, validto, eventid, cmdprefix, cmdvalue)
            VALUES
                    (v_rec.recordnum, v_rec.deviceid, v_ts, NULL, v_rec.eventid, v_rec.cmdprefix, v_rec.cmdvalue);
        EXCEPTION
            WHEN OTHERS THEN
                v_sqlerrm := TO_CLOB(SQLERRM);
                INSERT INTO t_rs_threshold_warning
                        (recordnum, deviceid, validfrom, validto, eventid, cmdprefix, cmdvalue, message)
                VALUES
                        (v_rec.recordnum, v_rec.deviceid, v_ts, NULL, v_rec.eventid, v_rec.cmdprefix, v_rec.cmdvalue, v_sqlerrm);
                --DBMS_OUTPUT.PUT_LINE(SQLERRM || ' record#' || v_rec.recordnum);
                -- TODO Exception?
        END;
   END LOOP;
END;
/
SHOW ERRORS;

--
-- Procedure for importing risk sensor data from CSV data
--
DROP PROCEDURE import_data;
CREATE OR REPLACE PROCEDURE import_data
AS
    CURSOR v_cur IS
        SELECT
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
                v_rs_import_data i
        ;
    v_rec       v_cur%ROWTYPE;
    v_ts        TIMESTAMP;
    v_sqlerrm   CLOB;
BEGIN
    FOR v_rec IN v_cur LOOP
        BEGIN
            -- Create timestamp
            /*
            v_ts := TO_TIMESTAMP(
                        TO_CHAR(v_rec.createddate, 'DD.MM.YYYY')
                        || ' '
                        || TO_CHAR(v_rec.createdtime, 'HH24:MI:SS')
                        , 'DD.MM.YYYY HH24:MI:SS'
                    );
            */
            v_ts := v_rec.createdon;
            --
            INSERT INTO t_rs_data
                    (recordnum, sdcardid, deviceid, createddate, createdtime, createdon, datewarning, timewarning, eventid, xmg, ymg, zmg, durationms)
            VALUES
                    (v_rec.recordnum, v_rec.sdcardid, v_rec.deviceid, v_rec.createddate, v_rec.createdtime, v_ts, v_rec.datewarning, v_rec.timewarning, v_rec.eventid, v_rec.xmg, v_rec.ymg, v_rec.zmg, v_rec.durationms);
        EXCEPTION
            WHEN OTHERS THEN
                v_sqlerrm := TO_CLOB(SQLERRM);
                INSERT INTO t_rs_data_warning
                        (recordnum, sdcardid, deviceid, createddate, createdtime, createdon, datewarning, timewarning, eventid, xmg, ymg, zmg, durationms, message)
                VALUES
                        (v_rec.recordnum, v_rec.sdcardid, v_rec.deviceid, v_rec.createddate, v_rec.createdtime, v_ts, v_rec.datewarning, v_rec.timewarning, v_rec.eventid, v_rec.xmg, v_rec.ymg, v_rec.zmg, v_rec.durationms, v_sqlerrm);
        END;
    END LOOP;
END;
/
SHOW ERRORS;

--
-- Start a tour
--
DROP FUNCTION start_tour;
CREATE OR REPLACE FUNCTION start_tour(
    p_sdcardid t_rs_tour.sdcardid%TYPE
    , p_deviceid t_rs_tour.deviceid%TYPE
    , p_started t_rs_tour.started%TYPE
)
RETURN NUMBER
IS
    v_tourstmt  VARCHAR2(4000);
    v_tourid    NUMBER;
BEGIN
    v_tourstmt := 'INSERT INTO t_rs_tour (id, sdcardid, deviceid, started) VALUES (:id, :sdcardid, :deviceid, :started)';
    -- Get tour ID
    SELECT t_rs_tour_seq.nextval INTO v_tourid FROM dual;
    EXECUTE IMMEDIATE v_tourstmt USING v_tourid, p_sdcardid, p_deviceid, p_started;
    --
    RETURN v_tourid;
END;
/
SHOW ERRORS;

--
-- End tour
--
DROP PROCEDURE end_tour;
CREATE OR REPLACE PROCEDURE end_tour(
    p_tourid t_rs_tour.id%TYPE
    , p_ended t_rs_tour.ended%TYPE
)
AS
    v_started   t_rs_tour.started%TYPE;
    v_tsdiff    t_rs_tour.durationts%TYPE;
    -- debug
    x_start         TIMESTAMP;
    x_stop          TIMESTAMP;
BEGIN
    x_start := SYSDATE;
    --
    SELECT started INTO v_started FROM t_rs_tour WHERE id = p_tourid;
    SELECT p_ended - v_started INTO v_tsdiff FROM dual;
    -- Check duration
    IF p_ended > v_started
    THEN
        -- OK; save tour
        UPDATE
                t_rs_tour
           SET
                ended = p_ended
                , durationts = v_tsdiff
                , exceedcount = (SELECT COUNT(*) FROM t_rs_tour_data WHERE tourid = p_tourid AND points > 0)
                , points = (SELECT SUM(points) FROM t_rs_tour_data WHERE tourid = p_tourid)
         WHERE
                id = p_tourid
        ;
    ELSIF p_ended < v_started
    THEN
        -- Time corrupt; delete tour
        -- TODO Move into 'trash'table?
        DELETE FROM t_rs_tour_data WHERE tourid = p_tourid;
        DELETE FROM t_rs_tour WHERE id = p_tourid;
    END IF;
    --
    x_stop := SYSDATE;
--    DBMS_OUTPUT.PUT_LINE('end_tour took ' || (x_stop - x_start));
END;
/
SHOW ERRORS;

--
-- Create tour data
--
DROP PROCEDURE create_tour_data;
CREATE OR REPLACE PROCEDURE create_tour_data(
    p_tourid t_rs_tour_data.tourid%TYPE
    , p_recordnum t_rs_tour_data.recordnum%TYPE
    , p_sdcardid t_rs_tour_data.sdcardid%TYPE
    , p_deviceid t_rs_tour_data.deviceid%TYPE
    , p_ts t_rs_tour_data.createdon%TYPE
    , p_eventid t_rs_tour_data.eventid%TYPE
    , p_absolutemg t_rs_tour_data.absolutemg%TYPE
    , p_eventthreshold t_rs_tour_data.eventthreshold%TYPE
    , p_relativemg t_rs_tour_data.relativemg%TYPE
    , p_vmg t_rs_tour_data.vmg%TYPE
    , p_durationms t_rs_tour_data.durationms%TYPE
    , p_durationthreshold t_rs_tour_data.durationthreshold%TYPE
    , p_relativedurationms t_rs_tour_data.relativedurationms%TYPE
    , p_vduration t_rs_tour_data.vduration%TYPE
    , p_factor t_rs_eventfactor.factor%TYPE
    , p_valuefactor t_rs_eventfactor.valuefactor%TYPE
    , p_timefactor t_rs_eventfactor.valuefactor%TYPE
)
AS
    v_datastmt              VARCHAR2(4000);
    v_datastmt_warning      VARCHAR2(4000);
    v_pctthrexceeded        NUMBER(10, 2);
    v_pctdurexceeded        NUMBER(10, 2);
    v_driventime            INTERVAL DAY(9) TO SECOND;
    v_relativetourtime      INTERVAL DAY(9) TO SECOND;
    v_eventlastoccured      INTERVAL DAY(9) TO SECOND;
    v_thr                   NUMBER(10, 2);
    v_abs                   NUMBER(4);
    v_duration              NUMBER;
    v_factor                NUMBER(10, 2);
    v_valuefactor           NUMBER(10, 2);
    v_timefactor            NUMBER(10, 2);
    v_points                NUMBER(10, 2);
    v_sqlerrm               CLOB;
    -- debug
    x_start         TIMESTAMP;
    x_stop          TIMESTAMP;
BEGIN
    x_start := SYSDATE;
    --
    v_relativetourtime := INTERVAL '0 0:0:0.0' DAY TO SECOND;
    v_eventlastoccured := NULL;
    --
    v_datastmt := 'INSERT INTO t_rs_tour_data (';
    v_datastmt := v_datastmt || 'tourid, createdon, recordnum, deviceid, sdcardid, driventime, relativetourtime';
    v_datastmt := v_datastmt || ', eventid, absolutemg, eventthreshold, relativemg, pctthrexceeded, vmg';
    v_datastmt := v_datastmt || ', durationms, durationthreshold, relativedurationms, pctdurexceeded, vduration';
    v_datastmt := v_datastmt || ', factor, valuefactor, timefactor';
    v_datastmt := v_datastmt || ', points';
    v_datastmt := v_datastmt || ', eventlastoccured';
    v_datastmt := v_datastmt || ') VALUES (';
    v_datastmt := v_datastmt || ':tourid, :createdon, :recordnum, :deviceid, :sdcardid, :driventime, :relativetourtime';
    v_datastmt := v_datastmt || ', :eventid, :absolutemg, :eventthreshold, :relativemg, :pctthrexceeded, :vmg';
    v_datastmt := v_datastmt || ', :durationms, :durationthreshold, :relativedurationms, :pctdurexceeded, :vduration';
    v_datastmt := v_datastmt || ', :factor, :valuefactor, :timefactor';
    v_datastmt := v_datastmt || ', :points';
    v_datastmt := v_datastmt || ', :eventlastoccured';
    v_datastmt := v_datastmt || ')';
    --
    v_datastmt_warning := 'INSERT INTO t_rs_tour_data_warning (';
    v_datastmt_warning := v_datastmt_warning || 'tourid, createdon, recordnum, deviceid, sdcardid, driventime, relativetourtime';
    v_datastmt_warning := v_datastmt_warning || ', eventid, absolutemg, eventthreshold, relativemg, pctthrexceeded, vmg';
    v_datastmt_warning := v_datastmt_warning || ', durationms, durationthreshold, relativedurationms, pctdurexceeded, vduration';
    v_datastmt_warning := v_datastmt_warning || ', factor, valuefactor, timefactor';
    v_datastmt_warning := v_datastmt_warning || ', points';
    v_datastmt_warning := v_datastmt_warning || ', eventlastoccured';
    v_datastmt_warning := v_datastmt_warning || ', message';
    v_datastmt_warning := v_datastmt_warning || ') VALUES (';
    v_datastmt_warning := v_datastmt_warning || ':tourid, :createdon, :recordnum, :deviceid, :sdcardid, :driventime, :relativetourtime';
    v_datastmt_warning := v_datastmt_warning || ', :eventid, :absolutemg, :eventthreshold, :relativemg, :pctthrexceeded, :vmg';
    v_datastmt_warning := v_datastmt_warning || ', :durationms, :durationthreshold, :relativedurationms, :pctdurexceeded, :vduration';
    v_datastmt_warning := v_datastmt_warning || ', :points';
    v_datastmt_warning := v_datastmt_warning || ', :factor, :valuefactor, :timefactor';
    v_datastmt_warning := v_datastmt_warning || ', :eventlastoccured';
    v_datastmt_warning := v_datastmt_warning || ', :message';
    v_datastmt_warning := v_datastmt_warning || ')';
    -- Now process tour data: events 1, 2, 7 + 11, 8 + 12, 9 + 13
    IF p_eventid IN (1, 2, 7, 8, 9, 11, 12, 13)
    THEN
        -- Compute pct
        v_thr := ABS(p_eventthreshold);
        v_abs := ABS(p_absolutemg);
        IF NVL(v_thr, -1) > 0 AND NVL(v_abs, -1) > 0
        THEN
            v_pctthrexceeded := ROUND((v_abs / v_thr) * 100 - 100, 2);
            IF v_pctthrexceeded < 0
            THEN
                v_pctthrexceeded := 0.0;
            END IF;
        ELSE
            v_pctthrexceeded := 0.0;
        END IF;
        -- Compute pct
        IF NVL(p_durationthreshold, -1) > 0 AND NVL(p_durationms, -1) > 0
        THEN
            v_pctdurexceeded := ROUND((p_durationms / p_durationthreshold) * 100 - 100, 2);
            IF v_pctdurexceeded < 0
            THEN
                v_pctdurexceeded := 0.0;
            END IF;
        ELSE
            v_pctdurexceeded := 0.0;
        END IF;
        -- Compute absolute tour time
        BEGIN
            SELECT
                    p_ts - t.started INTO v_driventime
              FROM
                    t_rs_tour t
             WHERE
                    t.id = p_tourid
            ;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                v_driventime := INTERVAL '0 0:0:0.0' DAY TO SECOND;
        END;
        -- Compute tour time relative to last event
        BEGIN
            SELECT
                    p_ts - d.createdon INTO v_relativetourtime
              FROM
                    t_rs_tour_data d
             WHERE
                    ROWNUM = 1
                    AND d.tourid = p_tourid
             ORDER BY
                    createdon DESC
                    , recordnum DESC
            ;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                v_relativetourtime := INTERVAL '0 0:0:0.0' DAY TO SECOND;
        END;
        /*
        -- Find last occurence of event
        BEGIN
            SELECT
                    t.x INTO v_eventlastoccured
              FROM (
                    SELECT
                            p_ts - d.createdon x
                      FROM
                            v_rs_tour_data d
                     WHERE
                            d.deviceid = (SELECT deviceid FROM t_rs_tour WHERE id = p_tourid)
                            AND d.sdcardid = (SELECT sdcardid FROM t_rs_tour WHERE id = p_tourid)
                            AND d.eventid = p_eventid
                            AND d.createdon < p_ts
                     ORDER BY
                            createdon DESC
                            , recordnum DESC
                  ) t
             WHERE
                    ROWNUM = 1
            ;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                v_eventlastoccured := NULL;
        END;
        */
        v_eventlastoccured := NULL;
        -- Compute points and multiply with factors
        IF p_factor IS NULL
        THEN
            v_factor := 1.0;
        ELSE
            v_factor := p_factor;
        END IF;
        IF p_valuefactor IS NULL
        THEN
            v_valuefactor := 1.0;
        ELSE
            v_valuefactor := p_valuefactor;
        END IF;
        IF p_timefactor IS NULL
        THEN
            v_timefactor := 1.0;
        ELSE
            v_timefactor := p_timefactor;
        END IF;
        v_points := ROUND((v_pctthrexceeded * v_valuefactor + v_pctdurexceeded  * v_timefactor) / 100, 2) * v_factor;
        --
        BEGIN
            IF p_vduration IS NULL
            THEN
                v_duration := 0;
            ELSE
                v_duration := p_vduration;
            END IF;
            EXECUTE IMMEDIATE v_datastmt USING
                p_tourid, p_ts, p_recordnum, p_deviceid, p_sdcardid, v_driventime, v_relativetourtime
                , p_eventid, p_absolutemg, p_eventthreshold, p_relativemg, v_pctthrexceeded, p_vmg
                , p_durationms, p_durationthreshold, p_relativedurationms, v_pctdurexceeded, v_duration
                , p_factor, p_valuefactor, p_timefactor
                , v_points
                , v_eventlastoccured
            ;
        EXCEPTION
            WHEN OTHERS THEN
                --DBMS_OUTPUT.PUT_LINE('create_tour_data: ' || SQLERRM);
                v_sqlerrm := TO_CLOB(SQLERRM);
                BEGIN
                    EXECUTE IMMEDIATE v_datastmt_warning USING
                        p_tourid, p_ts, p_recordnum, p_deviceid, p_sdcardid, v_driventime, v_relativetourtime
                        , p_eventid, p_absolutemg, p_eventthreshold, p_relativemg, v_pctthrexceeded, p_vmg
                        , p_durationms, p_durationthreshold, p_relativedurationms, v_pctdurexceeded, v_duration
                        , p_factor, p_valuefactor, p_timefactor
                        , v_points
                        , v_eventlastoccured
                        , v_sqlerrm
                    ;
                EXCEPTION
                    WHEN OTHERS THEN
                        DBMS_OUTPUT.PUT_LINE('create_tour_data warning: ' || SQLERRM);
                END;
        END;
    END IF;
    --
    x_stop := SYSDATE;
--    DBMS_OUTPUT.PUT_LINE('create_tour_data took ' || (x_stop - x_start));
END;
/
SHOW ERRORS;

--
-- Procedure for analyzing risk sensor data
-- Filter tours: everything between event 1 and event 2
--
DROP PROCEDURE analyze_tour;
CREATE OR REPLACE PROCEDURE analyze_tour
AS
    CURSOR v_cur IS
        SELECT
                i.sdcardid
                , i.deviceid
                , i.recordnum
                , i.createddate
                , i.createdtime
                , i.createdon
                , i.datewarning
                , i.timewarning
                , i.eventid
                , i.eventthreshold
                , i.absolutemg
                , i.relativemg
                , i.factor
                , i.valuefactor
                , i.vmg
                , i.durationthreshold
                , i.durationms
                , i.relativedurationms
                , i.timefactor
                , i.vduration
          FROM
                /*tmp_rs_event*/v_rs_event i
        ;
    v_rec           v_cur%ROWTYPE;
    v_eventid       NUMBER;
    v_ts            TIMESTAMP;
    v_tourstarted   CHAR(1); -- Y or N
    v_tourid        NUMBER;
    v_lastcreatedon TIMESTAMP; -- For simulating event 2
    -- debug
    x_start         TIMESTAMP;
    x_stop          TIMESTAMP;
BEGIN
    x_start := SYSDATE;
    -- Initialize
    v_tourstarted := 'N';
    --
    FOR v_rec IN v_cur LOOP
        -- Create timestamp
        IF v_rec.datewarning IS NULL AND v_rec.timewarning IS NULL
        THEN
            v_ts := v_rec.createdon; --TO_DATE(v_rec.createdon || ' ' || v_rec.createdtime, 'DD.MM.YYYY HH24:MI:SS');
        ELSE
            v_ts := NULL;
        END IF;
        -- Save actual event id
        v_eventid := v_rec.eventid;
        -- Check for event 1 EVT_PWR_ON, 2 EVT_PWR_OFF
        IF v_eventid = 1 -- EVT_PWR_ON
        THEN
            IF v_tourstarted = 'N'
            THEN
                -- Start tour
                v_tourstarted := 'Y';
                -- Create tour and get tour id
                v_tourid := start_tour(
                    p_sdcardid => v_rec.sdcardid
                    , p_deviceid => v_rec.deviceid
                    , p_started => v_ts
                );
            ELSIF v_tourstarted = 'Y'
            THEN
                -- Simulate event 2 EVT_PWR_OFF: one second after last event
                BEGIN
                    SELECT
                            createdon + 1 / 86400 INTO v_lastcreatedon
                      FROM
                            t_rs_tour_data
                     WHERE
                            tourid = v_tourid
                            AND ROWNUM = 1
                     ORDER BY
                            createdon DESC
                            , recordnum DESC
                    ;
                    DBMS_OUTPUT.PUT_LINE('tour id=' || v_tourid || ': event#1 and tourstarted=Y -> tour ended');
                    -- End tour
                    v_tourstarted := 'N';
                    end_tour(
                        p_tourid => v_tourid
                        , p_ended => v_lastcreatedon
                    );
                    -- Start new tour
                    v_tourstarted := 'Y';
                    -- Create tour and get tour id
                    v_tourid := start_tour(
                        p_sdcardid => v_rec.sdcardid
                        , p_deviceid => v_rec.deviceid
                        , p_started => v_ts
                    );
                    DBMS_OUTPUT.PUT_LINE('--> started new tour id=' || v_tourid);
                EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                        DBMS_OUTPUT.PUT_LINE('tour id=' || v_tourid || ': ' || SQLERRM || ': could not simulate EVT_PWR_OFF');
                END;
            END IF;
        ELSIF v_eventid = 2
        THEN
            IF v_tourstarted = 'Y'
            THEN
                -- End tour
                v_tourstarted := 'N';
                --
                end_tour(
                    p_tourid => v_tourid
                    , p_ended => v_ts
                );
            END IF;
        END IF;
        -- Now process tour data
        create_tour_data(
            p_tourid => v_tourid
            , p_recordnum => v_rec.recordnum
            , p_deviceid => v_rec.deviceid
            , p_sdcardid => v_rec.sdcardid
            , p_ts => v_ts
            , p_eventid => v_eventid
            , p_absolutemg => v_rec.absolutemg
            , p_eventthreshold => v_rec.eventthreshold
            , p_relativemg => v_rec.relativemg
            , p_vmg => v_rec.vmg
            , p_durationms => v_rec.durationms
            , p_durationthreshold => v_rec.durationthreshold
            , p_relativedurationms => v_rec.relativedurationms
            , p_vduration => v_rec.vduration
            , p_factor => v_rec.factor
            , p_valuefactor => v_rec.valuefactor
            , p_timefactor => v_rec.timefactor
        );
    END LOOP;
    --
    x_stop := SYSDATE;
--    DBMS_OUTPUT.PUT_LINE('analyze_tour took ' || (x_stop - x_start));
END;
/
SHOW ERRORS;

/*
DROP PROCEDURE sum_tourtime;
CREATE OR REPLACE PROCEDURE sum_tourtime
AS
    CURSOR v_cur IS
        SELECT
                t.*
          FROM
                v_rs_tour t
         ORDER BY
                deviceid
                , started
        ;
    v_rec               v_cur%ROWTYPE;
    v_i                 NUMBER;
    v_actualtourid      NUMBER;
    v_actualtourtime    INTERVAL DAY(5) TO SECOND;
    v_sumtourtime       INTERVAL DAY(5) TO SECOND;
BEGIN
    v_i := 1;
    v_actualtourid := 0;
    v_actualtourtime := INTERVAL '000 00:00:00' DAY TO SECOND;
    v_sumtourtime := INTERVAL '000 00:00:00' DAY TO SECOND;
    FOR v_rec IN v_cur LOOP
        -- We need a duration
        IF v_rec.durationts IS NULL
        THEN
            CONTINUE;
        END IF;
        --
        IF v_actualtourid <> v_rec.id
        THEN
            v_i := 1;
            v_actualtourid := v_rec.id;
            v_sumtourtime := v_sumtourtime + v_actualtourtime;
        END IF;
        v_actualtourtime := v_rec.durationts;
        dbms_output.put_line(v_i || ': dev#' || v_rec.deviceid || ' tour#' || v_actualtourid);
        v_i := v_i + 1;
    END LOOP;
    v_sumtourtime := v_sumtourtime + v_actualtourtime;
    dbms_output.put_line('sum tourtime=' || v_sumtourtime);
END;
/
SHOW ERRORS;
*/
