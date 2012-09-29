--CONN rbe/rbe@rbe1

SET ECHO OFF
SET FEEDBACK 1
SET TERMOUT OFF
SET TIMING ON

SET SERVEROUTPUT ON SIZE 1000000
EXEC DBMS_OUTPUT.ENABLE(10000000);

SPOOL risksensor.log

SELECT TO_CHAR(SYSDATE, 'HH24:MI:SS') FROM dual;

--@@user
@@dir
@@interval_sum_aggtype
@@interval_avg_aggtype
@@tables
@@views
@@proc
@@testdata
@@loadcsv
--@@gtt
@@analyzedata

SELECT TO_CHAR(SYSDATE, 'HH24:MI:SS') FROM dual;

COMMIT;

/*
SELECT COUNT(*) FROM t_rs_threshold_warning;
SELECT COUNT(*) FROM t_rs_data_warning;
SELECT COUNT(*) FROM t_rs_tour_data_warning;
SELECT * FROM t_rs_tour_data_warning;
*/

SPOOL OFF
