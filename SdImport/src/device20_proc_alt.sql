SET SERVEROUTPUT ON SIZE 1000000
EXEC DBMS_OUTPUT.ENABLE(10000000);

--
--
--
DROP TABLE t_analysis;
CREATE TABLE t_analysis (
    deviceid VARCHAR2(16)
    , sdcardid VARCHAR2(16)
    , drivenmin NUMBER
    , points7inmin NUMBER(10, 2)
    , sumpoints7 NUMBER(10, 2)
    , sumpoints7_div_min NUMBER(10, 2)
    , points11inmin NUMBER(10, 2)
    , sumpoints11 NUMBER(10, 2)
    , sumpoints11_div_min NUMBER(10, 2)
    , points8inmin NUMBER(10, 2)
    , sumpoints8 NUMBER(10, 2)
    , sumpoints8_div_min NUMBER(10, 2)
    , points12inmin NUMBER(10, 2)
    , sumpoints12 NUMBER(10, 2)
    , sumpoints12_div_min NUMBER(10, 2)
    , points9inmin NUMBER(10, 2)
    , sumpoints9 NUMBER(10, 2)
    , sumpoints9_div_min NUMBER(10, 2)
    , points13inmin NUMBER(10, 2)
    , sumpoints13 NUMBER(10, 2)
    , sumpoints13_div_min NUMBER(10, 2)
    , pointsinmin NUMBER(10, 2)
    , sumpoints NUMBER(10, 2)
    , sumpoints_div_min NUMBER(10, 2)
);
CREATE UNIQUE INDEX t_analysis_uq1 ON t_analysis (deviceid, sdcardid, drivenmin);

CREATE OR REPLACE PROCEDURE create_empty_data(
    /*p_deviceid t_analysis.deviceid%TYPE
    , p_sdcardid t_analysis.sdcardid%TYPE
    , p_deltamin NUMBER
    , p_drivenmin t_analysis.drivenmin%TYPE
    , p_sumpoints7 t_analysis.sumpoints7%TYPE
    , p_sumpoints7_div_min t_analysis.sumpoints7_div_min%TYPE
    , p_sumpoints11 t_analysis.sumpoints11%TYPE
    , p_sumpoints11_div_min t_analysis.sumpoints11_div_min%TYPE
    , p_sumpoints8 t_analysis.sumpoints8%TYPE
    , p_sumpoints8_div_min t_analysis.sumpoints8_div_min%TYPE
    , p_sumpoints12 t_analysis.sumpoints12%TYPE
    , p_sumpoints12_div_min t_analysis.sumpoints12_div_min%TYPE
    , p_sumpoints9 t_analysis.sumpoints9%TYPE
    , p_sumpoints9_div_min t_analysis.sumpoints9_div_min%TYPE
    , p_sumpoints13 t_analysis.sumpoints13%TYPE
    , p_sumpoints13_div_min t_analysis.sumpoints13_div_min%TYPE
    , p_sumpoints t_analysis.sumpoints%TYPE
    , p_sumpoints_div_min t_analysis.sumpoints_div_min%TYPE*/
    p_deviceid v_rs_tour_data_bymin.deviceid%TYPE
    , p_sdcardid v_rs_tour_data_bymin.sdcardid%TYPE
    , p_deltamin NUMBER
    , p_drivenmin NUMBER
    , p_sumpoints7 NUMBER
    , p_sumpoints7_div_min NUMBER
    , p_sumpoints11 NUMBER
    , p_sumpoints11_div_min NUMBER
    , p_sumpoints8 NUMBER
    , p_sumpoints8_div_min NUMBER
    , p_sumpoints12 NUMBER
    , p_sumpoints12_div_min NUMBER
    , p_sumpoints9 NUMBER
    , p_sumpoints9_div_min NUMBER
    , p_sumpoints13 NUMBER
    , p_sumpoints13_div_min NUMBER
    , p_sumpoints NUMBER
    , p_sumpoints_div_min NUMBER
)
AS
    x NUMBER;
    y NUMBER;
BEGIN
    x := p_drivenmin - p_deltamin;
    y := p_drivenmin - 1;
    IF x = y
    THEN
        RETURN;
    END IF;
    IF x + 1 = y
    THEN
        x := y;
    END IF;
dbms_output.put_line('EMPTY: ' || x ||' -> '|| y);
    FOR i IN x .. y LOOP
        BEGIN
            INSERT INTO t_analysis (
                deviceid
                , sdcardid
                , drivenmin
                , points7inmin
                , sumpoints7
                , sumpoints7_div_min
                , points11inmin
                , sumpoints11
                , sumpoints11_div_min
                , points8inmin
                , sumpoints8
                , sumpoints8_div_min
                , points12inmin
                , sumpoints12
                , sumpoints12_div_min
                , points9inmin
                , sumpoints9
                , sumpoints9_div_min
                , points13inmin
                , sumpoints13
                , sumpoints13_div_min
                , pointsinmin
                , sumpoints
                , sumpoints_div_min
            )
            VALUES (
                p_deviceid
                , p_sdcardid
                , i
                , 0
                , p_sumpoints7
                , p_sumpoints7_div_min
                , 0
                , p_sumpoints11
                , p_sumpoints11_div_min
                , 0
                , p_sumpoints8
                , p_sumpoints8_div_min
                , 0
                , p_sumpoints12
                , p_sumpoints12_div_min
                , 0
                , p_sumpoints9
                , p_sumpoints9_div_min
                , 0
                , p_sumpoints13
                , p_sumpoints13_div_min
                , 0
                , p_sumpoints
                , p_sumpoints_div_min
            );
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('create_empty_data: ' || SQLERRM || ': ' || p_deviceid || ' ' || p_sdcardid || ' ' || i);
        END;
    END LOOP;
END;
/
SHOW ERRORS;

--
--
--
DECLARE
    CURSOR v_cur IS
        SELECT
                deviceid
                , sdcardid
                , driventime
                , eventid
                , points
          FROM
                v_rs_tour_data_bymin
         WHERE
                deviceid = 'Device20'
         ORDER BY
                driventime
        ;
    v_rec                 v_cur%ROWTYPE;
    v_onroad              BOOLEAN;
    v_ignitiontime        TIMESTAMP;
    v_lasttime            TIMESTAMP;
    v_deltamin            INTERVAL DAY TO SECOND;
    v_drivenmin           NUMBER;
    v_points7inmin        NUMBER;
    v_sumpoints7          NUMBER;
    v_sumpoints7_div_min  NUMBER;
    v_points11inmin       NUMBER;
    v_sumpoints11         NUMBER;
    v_sumpoints11_div_min NUMBER;
    v_points8inmin        NUMBER;
    v_sumpoints8          NUMBER;
    v_sumpoints8_div_min  NUMBER;
    v_points12inmin       NUMBER;
    v_sumpoints12         NUMBER;
    v_sumpoints12_div_min NUMBER;
    v_points9inmin        NUMBER;
    v_sumpoints9          NUMBER;
    v_sumpoints9_div_min  NUMBER;
    v_points13inmin       NUMBER;
    v_sumpoints13         NUMBER;
    v_sumpoints13_div_min NUMBER;
    v_pointsinmin         NUMBER;
    v_sumpoints           NUMBER;
    v_sumpoints_div_min   NUMBER;
BEGIN
    v_onroad              := FALSE;
    v_ignitiontime        := NULL;
    v_lasttime            := NULL;
    v_deltamin            := INTERVAL '0' MINUTE;
    v_drivenmin           := 0;
    v_points7inmin        := 0.0;
    v_sumpoints7          := 0.0;
    v_sumpoints7_div_min  := 0.0;
    v_points11inmin       := 0.0;
    v_sumpoints11         := 0.0;
    v_sumpoints11_div_min := 0.0;
    v_points8inmin        := 0.0;
    v_sumpoints8          := 0.0;
    v_sumpoints8_div_min  := 0.0;
    v_points12inmin       := 0.0;
    v_sumpoints12         := 0.0;
    v_sumpoints12_div_min := 0.0;
    v_points9inmin        := 0.0;
    v_sumpoints9          := 0.0;
    v_sumpoints9_div_min  := 0.0;
    v_points13inmin       := 0.0;
    v_sumpoints13         := 0.0;
    v_sumpoints13_div_min := 0.0;
    v_pointsinmin         := 0.0;
    v_sumpoints           := 0.0;
    v_sumpoints_div_min   := 0.0;
    FOR v_rec IN v_cur LOOP

        IF v_lasttime IS NULL
        THEN
            v_lasttime := v_rec.driventime;
        END IF;

        IF NOT v_onroad AND v_rec.eventid = 1
        THEN
            v_onroad := TRUE;
            v_ignitiontime := v_rec.driventime;
            --dbms_output.put_line(v_rec.driventime || ' onroad=true');
            --CONTINUE;
        ELSIF v_onroad AND v_rec.eventid = 2
        THEN
            v_onroad := FALSE;
            v_ignitiontime := NULL;
            --dbms_output.put_line(v_rec.driventime || ' onroad=false');
            --CONTINUE;
        ELSIF v_onroad AND v_rec.eventid IN (7, 8, 9, 11, 12, 13)
        THEN
            -- Compute delta between driventime and v_lasttime
            v_deltamin := v_rec.driventime - v_lasttime;
            --
            v_drivenmin := v_drivenmin + (EXTRACT(HOUR FROM v_deltamin) * 60 + EXTRACT(MINUTE FROM v_deltamin));
            --dbms_output.put_line('   -> v_rec.driventime=' || v_rec.driventime || ' v_deltamin=' || v_deltamin || ' drivenmin=' || v_drivenmin);
            -- Erzeuge 0-datensätze
            IF (EXTRACT(HOUR FROM v_deltamin) * 60 + EXTRACT(MINUTE FROM v_deltamin)) > 1
            THEN
                create_empty_data(
                    v_rec.deviceid
                    , v_rec.sdcardid
                    , (EXTRACT(HOUR FROM v_deltamin) * 60 + EXTRACT(MINUTE FROM v_deltamin))
                    , v_drivenmin
                    , v_sumpoints7
                    , v_sumpoints7_div_min
                    , v_sumpoints11
                    , v_sumpoints11_div_min
                    , v_sumpoints8
                    , v_sumpoints8_div_min
                    , v_sumpoints12
                    , v_sumpoints12_div_min
                    , v_sumpoints9
                    , v_sumpoints9_div_min
                    , v_sumpoints13
                    , v_sumpoints13_div_min
                    , v_sumpoints
                    , v_sumpoints_div_min
                );
            END IF;
            --
            IF v_drivenmin > 0
            THEN
                IF v_rec.eventid = 7
                THEN
                    v_points7inmin        := v_rec.points;
                    v_sumpoints7          := v_sumpoints7 + v_rec.points;
                    v_sumpoints7_div_min  := v_sumpoints7 / v_drivenmin;
                END IF;
                IF v_rec.eventid = 11
                THEN
                    v_points11inmin       := v_rec.points;
                    v_sumpoints11         := v_sumpoints11 + v_rec.points;
                    v_sumpoints11_div_min := v_sumpoints11 / v_drivenmin;
                END IF;
                IF v_rec.eventid = 8
                THEN
                    v_points8inmin        := v_rec.points;
                    v_sumpoints8          := v_sumpoints8 + v_rec.points;
                    v_sumpoints8_div_min  := v_sumpoints8 / v_drivenmin;
                END IF;
                IF v_rec.eventid = 12
                THEN
                    v_points12inmin       := v_rec.points;
                    v_sumpoints12         := v_sumpoints12 + v_rec.points;
                    v_sumpoints12_div_min := v_sumpoints12 / v_drivenmin;
                END IF;
                IF v_rec.eventid = 9
                THEN
                    v_points9inmin        := v_rec.points;
                    v_sumpoints9          := v_sumpoints9 + v_rec.points;
                    v_sumpoints9_div_min  := v_sumpoints9 / v_drivenmin;
                END IF;
                IF v_rec.eventid = 13
                THEN
                    v_points13inmin       := v_rec.points;
                    v_sumpoints13         := v_sumpoints13 + v_rec.points;
                    v_sumpoints13_div_min := v_sumpoints13 / v_drivenmin;
                END IF;
                
                v_pointsinmin         := v_rec.points;
                v_sumpoints           := v_sumpoints + v_rec.points;
                v_sumpoints_div_min   := v_sumpoints / v_drivenmin;
            END IF;
            
            IF (EXTRACT(HOUR FROM v_deltamin) * 60 + EXTRACT(MINUTE FROM v_deltamin)) > 0
            THEN
                --dbms_output.put_line('   -> INSERT deltamin='||(EXTRACT(HOUR FROM v_deltamin) * 60 + EXTRACT(MINUTE FROM v_deltamin))||' drivenmin='||v_drivenmin);
                BEGIN
                    INSERT INTO t_analysis (
                        deviceid
                        , sdcardid
                        , drivenmin
                        , points7inmin
                        , sumpoints7
                        , sumpoints7_div_min
                        , points11inmin
                        , sumpoints11
                        , sumpoints11_div_min
                        , points8inmin
                        , sumpoints8
                        , sumpoints8_div_min
                        , points12inmin
                        , sumpoints12
                        , sumpoints12_div_min
                        , points9inmin
                        , sumpoints9
                        , sumpoints9_div_min
                        , points13inmin
                        , sumpoints13
                        , sumpoints13_div_min
                        , pointsinmin
                        , sumpoints
                        , sumpoints_div_min
                    )
                    VALUES (
                        v_rec.deviceid
                        , v_rec.sdcardid
                        , v_drivenmin
                        , v_points7inmin
                        , v_sumpoints7
                        , v_sumpoints7_div_min
                        , v_points11inmin
                        , v_sumpoints11
                        , v_sumpoints11_div_min
                        , v_points8inmin
                        , v_sumpoints8
                        , v_sumpoints8_div_min
                        , v_points12inmin
                        , v_sumpoints12
                        , v_sumpoints12_div_min
                        , v_points9inmin
                        , v_sumpoints9
                        , v_sumpoints9_div_min
                        , v_points13inmin
                        , v_sumpoints13
                        , v_sumpoints13_div_min
                        , v_pointsinmin
                        , v_sumpoints
                        , v_sumpoints_div_min
                    );
                EXCEPTION
                    WHEN OTHERS THEN
                        DBMS_OUTPUT.PUT_LINE('   -> xxx: ' || SQLERRM);
                END;
            ELSE
                --dbms_output.put_line('   -> UPDATE deltamin='||(EXTRACT(HOUR FROM v_deltamin) * 60 + EXTRACT(MINUTE FROM v_deltamin))||' drivenmin='||v_drivenmin);
                UPDATE
                        t_analysis
                   SET
                        points7inmin = v_points7inmin
                        , sumpoints7 = v_sumpoints7
                        , sumpoints7_div_min = v_sumpoints7_div_min
                        , points11inmin = v_points11inmin
                        , sumpoints11 = v_sumpoints11
                        , sumpoints11_div_min = v_sumpoints11_div_min
                        , points8inmin = v_points8inmin
                        , sumpoints8 = v_sumpoints8
                        , sumpoints8_div_min = v_sumpoints8_div_min
                        , points12inmin = v_points12inmin
                        , sumpoints12 = v_sumpoints12
                        , sumpoints12_div_min = v_sumpoints12_div_min
                        , points9inmin = v_points9inmin
                        , sumpoints9 = v_sumpoints9
                        , sumpoints9_div_min = v_sumpoints9_div_min
                        , points13inmin = v_points13inmin
                        , sumpoints13 = v_sumpoints13
                        , sumpoints13_div_min = v_sumpoints13_div_min
                        , pointsinmin = v_pointsinmin
                        , sumpoints = v_sumpoints
                        , sumpoints_div_min = v_sumpoints_div_min
                 WHERE
                        deviceid = v_rec.deviceid
                        AND sdcardid = v_rec.sdcardid
                        AND drivenmin = v_drivenmin
                ;
            END IF;
        END IF;
        --
        v_lasttime := v_rec.driventime;
    END LOOP;
END;
/
