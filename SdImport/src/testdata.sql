TRUNCATE TABLE t_rs_sdcard;
TRUNCATE TABLE t_rs_lpn;
-- Remondis
INSERT INTO t_rs_sdcard VALUES ('5D367D3028647938', 'Remondis Fahrer 1');
-- Remondis
INSERT INTO t_rs_lpn VALUES ('Device04', 'LA-NG 2009');
-- Remondis
INSERT INTO t_rs_lpn VALUES ('Device06', 'RD-OY 312');
-- Remondis Wg. 6
INSERT INTO t_rs_sdcard VALUES ('4235A06100332865', 'Remondis Fahrer 2');
INSERT INTO t_rs_lpn VALUES ('Device07', 'RE-MO 6');
-- Remondis
INSERT INTO t_rs_lpn VALUES ('Device08', 'RD-RE 475');
-- Remondis
INSERT INTO t_rs_lpn VALUES ('Device12', 'RD-OY 312');
-- Remondis
INSERT INTO t_rs_lpn VALUES ('Device14', 'LE-ZE 123');
-- Remondis
INSERT INTO t_rs_lpn VALUES ('Device15', 'CUX-W 553');
-- Remondis
INSERT INTO t_rs_lpn VALUES ('Device16', 'HH-GI 671');
-- Wähler
INSERT INTO t_rs_sdcard VALUES ('78304C629F63A536', 'Wähler Herr Kamrau');
-- Wähler
INSERT INTO t_rs_lpn VALUES ('Device17', 'CUX-W 871');
-- Wähler
INSERT INTO t_rs_lpn VALUES ('Device19', 'CUX-WW 897');
-- Wähler
INSERT INTO t_rs_lpn VALUES ('Device20', 'CUX-W 527');
-- UPS Fahrer 1
INSERT INTO t_rs_sdcard VALUES ('41EA5713B247B20', 'UPS Fahrer 1');
INSERT INTO t_rs_lpn VALUES ('002-00000000022', 'UP-S 1');
-- UPS Fahrer 2
INSERT INTO t_rs_sdcard VALUES ('3538DC2B967FC144', 'UPS Fahrer 2');
INSERT INTO t_rs_lpn VALUES ('002-00000000023', 'UP-S 2');
-- Herr Unrath, Touran
INSERT INTO t_rs_sdcard VALUES ('002-00000000021', 'Herr Unrath');
INSERT INTO t_rs_lpn VALUES ('002-00000000021', 'UN-RA 1234');

TRUNCATE TABLE t_rs_eventfactor;
-- Device17 -> event factors
INSERT INTO t_rs_eventfactor VALUES (t_rs_eventfactor_seq.nextval, TO_TIMESTAMP('03.02.2009 00:00:00', 'DD.MM.YYYY HH24:MI:SS'), NULL, 'Device17', NULL, 7, 1.5, 2, 2);
INSERT INTO t_rs_eventfactor VALUES (t_rs_eventfactor_seq.nextval, TO_TIMESTAMP('04.02.2009 00:00:00', 'DD.MM.YYYY HH24:MI:SS'), NULL, 'Device17', NULL, 11, 1.5, 2, 2);
-- Device20 -> event factors
INSERT INTO t_rs_eventfactor VALUES (t_rs_eventfactor_seq.nextval, TO_TIMESTAMP('03.02.2009 00:00:00', 'DD.MM.YYYY HH24:MI:SS'), TO_TIMESTAMP('04.02.2009 20:00:00', 'DD.MM.YYYY HH24:MI:SS'), 'Device20', NULL, 7, 1.5, 2, 2);
INSERT INTO t_rs_eventfactor VALUES (t_rs_eventfactor_seq.nextval, TO_TIMESTAMP('04.02.2009 20:00:01', 'DD.MM.YYYY HH24:MI:SS'), NULL, 'Device20', NULL, 7, 1.5, 2, 2);
INSERT INTO t_rs_eventfactor VALUES (t_rs_eventfactor_seq.nextval, TO_TIMESTAMP('04.02.2009 00:00:00', 'DD.MM.YYYY HH24:MI:SS'), NULL, 'Device20', NULL, 11, 1.5, 2, 2);
