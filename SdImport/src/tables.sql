--
-- Risk sensor events
--
DROP TABLE t_rs_event;
CREATE TABLE t_rs_event (
    id NUMBER(2) NOT NULL
    , eventname VARCHAR2(30)
    , description VARCHAR2(255) NOT NULL
);
ALTER TABLE t_rs_event ADD CONSTRAINT t_rs_event_pk PRIMARY KEY (id);
INSERT INTO t_rs_event VALUES (1, 'Zündung ein', 'Modul (Zündung) eingeschaltet');
INSERT INTO t_rs_event VALUES (2, 'Zündung aus', 'Modul (Zündung) ausgeschaltet');
INSERT INTO t_rs_event VALUES (3, NULL, 'Überschreitung des Betrages der eingestellten zulässigen Längsbeschleunigung. (Beschleunigung sowie Verzögerung in Fahrtrichtung)');
INSERT INTO t_rs_event VALUES (4, NULL, 'Überschreitung des Betrages der eingestellten zulässigen Querbeschleunigung. (Beschleunigungen quer zur Fahrtrichtung wie in Links- sowie Rechtskurven)');
INSERT INTO t_rs_event VALUES (5, NULL, 'Überschreitung des Betrages der eingestellten zulässigen Vertikalbeschleunigung. (Durch Bodenwellen und Schlaglöcher verursachte Vertikalbeschleunigungen)');
INSERT INTO t_rs_event VALUES (6, NULL, 'Überschreitung des Betrages der eingestellten Resultierenden Beschleunigung. (Vektorsumme)');
INSERT INTO t_rs_event VALUES (7, 'Beschleunigung', 'Überschreitung der eingestellten zulässigen Längsbeschleunigung in Fahrtrichtung. (Beschleunigung? positive Werte der X-Achse)');
INSERT INTO t_rs_event VALUES (8, 'Linkskurve', 'Überschreitung der eingestellten zulässigen Querbeschleunigung in Linkskurven. (Positive Werte der Y-Achse)');
INSERT INTO t_rs_event VALUES (9, 'Bodenwelle', 'Überschreitung der eingestellten zulässigen Vertikalbeschleunigung nach Oben. (Bodenwelle? Positive Werte der Z-Achse)');
INSERT INTO t_rs_event VALUES (10, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (11, 'Bremsen', 'Überschreitung der eingestellten zulässigen Längsverzögerung entgegen der Fahrtrichtung. (Bremsung? negative Werte derX-Achse)');
INSERT INTO t_rs_event VALUES (12, 'Rechtskurve', 'Überschreitung der eingestellten zulässigen Querbeschleunigung in Rechtskurven. (Negative Werte der Y-Achse)');
INSERT INTO t_rs_event VALUES (13, 'Schlagloch', 'Überschreitung der eingestellten zulässigen Vertikalbeschleunigung nach Unten. (Schlagloch ? kleinere positive bis negative Werte der Z-Achse, wegen 981 mg Offset)');
INSERT INTO t_rs_event VALUES (14, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (15, NULL, 'Speicherkarte eingesetzt');
INSERT INTO t_rs_event VALUES (16, NULL, 'Speicherkarte entnommen');
INSERT INTO t_rs_event VALUES (17, NULL, 'Sicherheitsschlüssel für Datenaufzeichnungen korrekt erkannt');
INSERT INTO t_rs_event VALUES (18, NULL, 'Sicherheitsschlüssel für Einstellungänderungen korrekt erkannt');
INSERT INTO t_rs_event VALUES (19, NULL, 'Sicherheitsschlüssel für Softwareupdates korrekt erkannt');
INSERT INTO t_rs_event VALUES (20, NULL, 'Sicherheitsschlüssel für Datenaufzeichnung nicht korrekt. (Schreibt Daten fortan intern)');
INSERT INTO t_rs_event VALUES (21, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (22, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (23, NULL, 'Kalibriertaster betätigt');
INSERT INTO t_rs_event VALUES (24, 'Akustisches Signal aus', 'Akustische Signalisierung am Schalter deaktiviert');
INSERT INTO t_rs_event VALUES (25, 'Akustisches Signal ein', 'Akustische Signalisierung am Schalter aktiviert');
INSERT INTO t_rs_event VALUES (26, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (27, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (28, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (29, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (30, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (31, NULL, 'Z.Zt nicht verfügbar');
INSERT INTO t_rs_event VALUES (32, NULL, 'Kalibrierung softwareseitig vorgenommen (Differenz zur Voreinstellung dokumentiert für x, y und z-Achse)');
INSERT INTO t_rs_event VALUES (33, 'Konfiguration', 'Konfigurationsbefehl via Programmierschnittstelle verarbeitet');
INSERT INTO t_rs_event VALUES (34, 'Konfiguration', 'Konfigurationsbefehl via Konfigurationskarte verarbeitet');

--
-- Factors for device and/or sdcard
--
DROP TABLE t_rs_eventfactor;
CREATE TABLE t_rs_eventfactor (
    id NUMBER NOT NULL
    , validfrom TIMESTAMP NOT NULL
    , validto TIMESTAMP
    , deviceid VARCHAR2(16)
    , sdcardid VARCHAR2(16)
    , eventid NUMBER(2) NOT NULL
    , factor NUMBER(10, 2) NOT NULL -- factor for event itself
    , valuefactor NUMBER(10, 2) NOT NULL -- factor for value
    , timefactor NUMBER(10, 2) NOT NULL -- factor for duration of violation
);
DROP SEQUENCE t_rs_eventfactor_seq;
CREATE SEQUENCE t_rs_eventfactor_seq START WITH 1 INCREMENT BY 1 NOCACHE;
ALTER TABLE t_rs_eventfactor ADD CONSTRAINT t_rs_eventfactor_pk PRIMARY KEY (id);
CREATE INDEX t_rs_eventfactor_idx1 ON t_rs_eventfactor (sdcardid);
CREATE INDEX t_rs_eventfactor_idx2 ON t_rs_eventfactor (deviceid);

--
-- Risk sensor configuration command
--
DROP TABLE t_rs_cmd;
CREATE TABLE t_rs_cmd (
    id NUMBER NOT NULL
    , eventid NUMBER(2) NOT NULL
    , cmdprefix VARCHAR2(8) NOT NULL
    , foreventid NUMBER(2) NOT NULL -- This command is "for" another event id
    , description VARCHAR2(200)
);
ALTER TABLE t_rs_cmd ADD CONSTRAINT t_rs_cmd_pk PRIMARY KEY (id);
CREATE UNIQUE INDEX t_rs_cmd_uq ON t_rs_cmd (cmdprefix);
DROP SEQUENCE t_rs_cmd_seq;
CREATE SEQUENCE t_rs_cmd_seq START WITH 1 INCREMENT BY 1 NOCACHE;
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'AVG', 33, 'Anzahl Messungen für Durchschnitt');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C1L', 7, 'Level+ Beschleunigung');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C2L', 11, 'Level- Bremsempfindlichkeit');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C3L', 8, 'Level+ Linkskurve');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C4L', 12, 'Level- Rechtskurve');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C5L', 9, 'Level+ Bodenwelle');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C6L', 13, 'Level- Schlagloch');
-- Dauer, die ein übertretener Schwellwert als Mittelwert vorliegen muss
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C1D', 7, 'Dauer Beschleunigung');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C2D', 11, 'Dauer Bremsen');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C3D', 8, 'Dauer Linkskurve');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C4D', 12, 'Dauer Rechtskurve');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C5D', 9, 'Dauer Bodenwelle');
INSERT INTO t_rs_cmd VALUES (t_rs_cmd_seq.nextval, 33, 'C6D', 13, 'Dauer Schlagloch');

--
-- Map device ID to license plate number
--
DROP TABLE t_rs_lpn;
CREATE TABLE t_rs_lpn (
    deviceid VARCHAR2(16) NOT NULL
    , lpn VARCHAR2(11) NOT NULL
);
-- deviceid is unique
ALTER TABLE t_rs_lpn ADD CONSTRAINT t_rs_lpn_pk PRIMARY KEY (deviceid);
CREATE INDEX t_rs_lpn_idx ON t_rs_lpn (lpn);

--
-- Map SD card ID to human
--
DROP TABLE t_rs_sdcard;
CREATE TABLE t_rs_sdcard (
    sdcardid VARCHAR2(16) NOT NULL
    , lastname VARCHAR2(50) NOT NULL
);
-- sdcardid is unique
ALTER TABLE t_rs_sdcard ADD CONSTRAINT t_rs_sdcard_pk PRIMARY KEY (sdcardid);
CREATE INDEX t_rs_sdcard_idx ON t_rs_sdcard (lastname);

--
-- External table for import of CSV
--
DROP TABLE xt_rs_import;
CREATE TABLE xt_rs_import (
    sdcardid VARCHAR2(16)
    , deviceid VARCHAR2(16)
    , recordnum NUMBER
    , theyear VARCHAR(10) -- Can be a complete date (debug data)
    , themonth VARCHAR(8) -- Can be time (debug data)
    , theday VARCHAR(20)  -- Can be a EVT_ string (debug data)
    , thehour VARCHAR(10) -- Can be a mg value or configuration command (debug data)
    , themin VARCHAR(5) -- Can be a mg value (debug data)
    , thesec VARCHAR(5) -- Can be a mg value (debug data)
    , eventid VARCHAR(5) -- Can be a mg value (debug data)
    , xmg VARCHAR2(20) -- could be a configuration command
    , ymg NUMBER(4)
    , zmg NUMBER(4)
    , durationms NUMBER(5)
)
ORGANIZATION EXTERNAL (
    TYPE oracle_loader
    DEFAULT DIRECTORY rsimportdir
    ACCESS PARAMETERS (
        RECORDS DELIMITED BY NEWLINE
        BADFILE 'bad_%a_%p.bad'
        LOGFILE 'log_%a_%p.log'
        FIELDS TERMINATED BY ';'
        OPTIONALLY ENCLOSED BY '"'
        MISSING FIELD VALUES ARE NULL
        REJECT ROWS WITH ALL NULL FIELDS
        (sdcardid, deviceid, recordnum, theyear, themonth, theday, thehour, themin, thesec, eventid, xmg, ymg, zmg, durationms)
    )
    LOCATION ('rs.csv')
)
--PARALLEL
REJECT LIMIT 0
NOMONITORING
;

--
-- Risk sensor thresholds for device
--
DROP TABLE t_rs_threshold;
CREATE TABLE t_rs_threshold (
    importedon TIMESTAMP DEFAULT SYSDATE NOT NULL
    , recordnum NUMBER NOT NULL
    , deviceid VARCHAR2(16) NOT NULL
    , validfrom TIMESTAMP NOT NULL
    , validto TIMESTAMP
    , eventid NUMBER(2) NOT NULL
    , cmdprefix VARCHAR2(5) NOT NULL
    , cmdvalue NUMBER(5) NOT NULL
);
ALTER TABLE t_rs_threshold ADD CONSTRAINT t_rs_threshold_pk PRIMARY KEY (deviceid, recordnum, validfrom, cmdprefix);

-- Risk sensor thresholds with warnings
DROP TABLE t_rs_threshold_warning;
CREATE TABLE t_rs_threshold_warning (
    importedon TIMESTAMP DEFAULT SYSDATE NOT NULL
    , recordnum NUMBER
    , deviceid VARCHAR2(16)
    , validfrom TIMESTAMP
    , validto TIMESTAMP
    , eventid NUMBER(2)
    , cmdprefix VARCHAR2(5)
    , cmdvalue NUMBER(5)
    , message CLOB
);
DROP SEQUENCE t_rs_tw_seq;
CREATE SEQUENCE t_rs_tw_seq START WITH 1 INCREMENT BY 1 NOCACHE;

--
-- Risk sensor data
--
DROP TABLE t_rs_data;
CREATE TABLE t_rs_data (
    importedon TIMESTAMP DEFAULT SYSDATE NOT NULL
    , recordnum NUMBER NOT NULL
    , sdcardid VARCHAR2(16) NOT NULL
    , deviceid VARCHAR2(16) NOT NULL
    , createdon TIMESTAMP NOT NULL
    , createddate TIMESTAMP NOT NULL
    , createdtime TIMESTAMP NOT NULL
    , datewarning VARCHAR2(100)
    , timewarning VARCHAR2(100)
    , eventid NUMBER(2) NOT NULL
    , xmg NUMBER(4)
    , ymg NUMBER(4)
    , zmg NUMBER(4)
    , durationms NUMBER(5)
);
-- same sdcard, device may begin with recordnum=1
ALTER TABLE t_rs_data ADD CONSTRAINT t_rs_data_pk PRIMARY KEY (sdcardid, deviceid, recordnum, createdon, eventid);
CREATE INDEX t_rs_data_idx1 ON t_rs_data (deviceid);
CREATE INDEX t_rs_data_idx2 ON t_rs_data (eventid);

-- Risk sensor data with warnings
DROP TABLE t_rs_data_warning;
CREATE TABLE t_rs_data_warning (
    importedon TIMESTAMP DEFAULT SYSDATE NOT NULL
    , recordnum NUMBER
    , sdcardid VARCHAR2(16)
    , deviceid VARCHAR2(16)
    , createdon TIMESTAMP
    , createddate TIMESTAMP
    , createdtime TIMESTAMP
    , datewarning VARCHAR2(100)
    , timewarning VARCHAR2(100)
    , eventid NUMBER(2)
    , xmg NUMBER(4)
    , ymg NUMBER(4)
    , zmg NUMBER(4)
    , durationms NUMBER(5)
    , message CLOB
);
DROP SEQUENCE t_rs_dw_seq;
CREATE SEQUENCE t_rs_dw_seq START WITH 1 INCREMENT BY 1 NOCACHE;

--
-- Tours
--
DROP TABLE t_rs_tour_data;
DROP TABLE t_rs_tour;

CREATE TABLE t_rs_tour (
    id NUMBER NOT NULL
    , analyzedon TIMESTAMP DEFAULT SYSDATE NOT NULL
    , sdcardid VARCHAR2(16)
    , deviceid VARCHAR2(16) NOT NULL
    , started TIMESTAMP NOT NULL -- event 1
    , ended TIMESTAMP -- event 2
    , durationts INTERVAL DAY(5) TO SECOND -- duration (between started and ended)
    , exceedcount NUMBER -- # of events/thresholds exceeded
    , points NUMBER(10, 2)
);
ALTER TABLE t_rs_tour ADD CONSTRAINT t_rs_tour_pk PRIMARY KEY (id);
CREATE INDEX t_rs_tour_idx1 ON t_rs_tour (analyzedon);
CREATE INDEX t_rs_tour_idx2 ON t_rs_tour (sdcardid);
CREATE INDEX t_rs_tour_idx3 ON t_rs_tour (deviceid);
CREATE INDEX t_rs_tour_idx4 ON t_rs_tour (started);
CREATE INDEX t_rs_tour_idx5 ON t_rs_tour (ended);
DROP SEQUENCE t_rs_tour_seq;
CREATE SEQUENCE t_rs_tour_seq START WITH 1 INCREMENT BY 1 NOCACHE;

--
-- Tour data
--
CREATE TABLE t_rs_tour_data (
    tourid NUMBER NOT NULL
    , createdon TIMESTAMP NOT NULL
    , recordnum NUMBER
    , deviceid VARCHAR2(16) NOT NULL
    , sdcardid VARCHAR2(16)
    , driventime INTERVAL DAY(9) TO SECOND NOT NULL -- Insgesamt gefahrene Zeit seit Start
    , relativetourtime INTERVAL DAY(9) TO SECOND NOT NULL -- Betriebszeit seit letztem Ereignis
    , eventid NUMBER(2) NOT NULL
    , absolutemg NUMBER
    , eventthreshold NUMBER
    , relativemg NUMBER(10, 2)
    , pctthrexceeded NUMBER(10, 2)
    , factor NUMBER(10, 2)
    , valuefactor NUMBER(10, 2)
    , vmg NUMBER(10, 2)
    , durationms NUMBER
    , durationthreshold NUMBER
    , relativedurationms NUMBER(10, 2)
    , pctdurexceeded NUMBER(10, 2)
    , timefactor NUMBER(10, 2)
    , vduration NUMBER(10, 2)
    , points NUMBER(10, 2)
    , eventlastoccured INTERVAL DAY(9) TO SECOND
);
ALTER TABLE t_rs_tour_data ADD CONSTRAINT t_rs_tour_data_pk PRIMARY KEY (tourid, createdon, recordnum, eventid);
ALTER TABLE t_rs_tour_data ADD CONSTRAINT t_rs_tour_data_fk FOREIGN KEY (tourid) REFERENCES t_rs_tour (id);

-- Tour data with warnings
DROP TABLE t_rs_tour_data_warning;
CREATE TABLE t_rs_tour_data_warning (
    tourid NUMBER
    , createdon TIMESTAMP
    , recordnum NUMBER
    , deviceid VARCHAR2(16) NOT NULL
    , sdcardid VARCHAR2(16)
    , driventime INTERVAL DAY(9) TO SECOND
    , relativetourtime INTERVAL DAY(9) TO SECOND
    , eventid NUMBER(2)
    , absolutemg NUMBER
    , eventthreshold NUMBER(4)
    , relativemg NUMBER(10, 2)
    , pctthrexceeded NUMBER(10, 2)
    , factor NUMBER(10, 2)
    , valuefactor NUMBER(10, 2)
    , vmg NUMBER(10, 2)
    , durationms NUMBER
    , durationthreshold NUMBER
    , relativedurationms NUMBER(10, 2)
    , pctdurexceeded NUMBER(10, 2)
    , timefactor NUMBER(10, 2)
    , vduration NUMBER(10, 2)
    , points NUMBER(10, 2)
    , eventlastoccured INTERVAL DAY(9) TO SECOND
    , message CLOB
);
DROP SEQUENCE t_rs_tdw_seq;
CREATE SEQUENCE t_rs_tdw_seq START WITH 1 INCREMENT BY 1 NOCACHE;
