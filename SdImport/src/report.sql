--
--
--
CREATE OR REPLACE PROCEDURE report_device(
    p_deviceid IN t_rs_lpn.deviceid%TYPE
    , p_sdcardid IN t_rs_sdcard.sdcardid%TYPE DEFAULT NULL
)
AS
    CURSOR v_cur(
        p_deviceid t_rs_lpn.deviceid%TYPE
        , p_sdcardid IN t_rs_sdcard.sdcardid%TYPE
    ) IS
        SELECT
                deviceid
                , sdcardid
          FROM
                v_device_sdcard
         WHERE
                deviceid = p_deviceid
                AND sdcardid = p_sdcardid
        ;
    v_rec           v_cur%ROWTYPE;
    v_table_name    VARCHAR2(100);
    v_stmt          VARCHAR2(4000);
BEGIN
    FOR v_rec IN v_cur(p_deviceid, p_sdcardid) LOOP
        v_table_name := REPLACE(SUBSTR('r' || v_rec.deviceid || '_' || v_rec.sdcardid, 0, 23), '-', '_');
        BEGIN
            EXECUTE IMMEDIATE 'drop table ' || v_table_name;
        EXCEPTION
            WHEN OTHERS THEN NULL;
        END;
        v_stmt := 'create table ' || v_table_name || ' as select * from v_tour_log where deviceid = ''' || v_rec.deviceid || ''' and sdcardid = ''' || v_rec.sdcardid || '''';
        --DBMS_OUTPUT.PUT_LINE(v_stmt);
        EXECUTE IMMEDIATE v_stmt;
        --
        BEGIN
            EXECUTE IMMEDIATE 'drop table ' || v_table_name || '_bymin';
        EXCEPTION
            WHEN OTHERS THEN NULL;
        END;
        v_stmt := 'create table ' || v_table_name || '_bymin as select * from v_tour_log_bymin where deviceid = ''' || v_rec.deviceid || ''' and sdcardid = ''' || v_rec.sdcardid || '''';
        --DBMS_OUTPUT.PUT_LINE(v_stmt);
        EXECUTE IMMEDIATE v_stmt;
        --
        BEGIN
            EXECUTE IMMEDIATE 'drop table ' || v_table_name || '_byhour';
        EXCEPTION
            WHEN OTHERS THEN NULL;
        END;
        v_stmt := 'create table ' || v_table_name || '_byhour as select * from v_tour_log_byhour where deviceid = ''' || v_rec.deviceid || ''' and sdcardid = ''' || v_rec.sdcardid || '''';
        --DBMS_OUTPUT.PUT_LINE(v_stmt);
        EXECUTE IMMEDIATE v_stmt;
    END LOOP;
END;
/
SHOW ERRORS;

--
--
--
CREATE OR REPLACE PROCEDURE report_all_devices
AS
    CURSOR v_cur IS
        SELECT
                deviceid
                , sdcardid
          FROM
                v_device_sdcard
        ;
    v_rec           v_cur%ROWTYPE;
    v_table_name    VARCHAR2(100);
    v_stmt          VARCHAR2(4000);
BEGIN
    FOR v_rec IN v_cur LOOP
        report_device(p_deviceid => v_rec.deviceid, p_sdcardid => v_rec.sdcardid);
    END LOOP;
END;
/
SHOW ERRORS;

--SET SERVEROUTPUT ON SIZE 100000
--EXEC report_device(p_deviceid => 'bla');
