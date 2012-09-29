-- Load risk sensor configuration from CSV
TRUNCATE TABLE t_rs_threshold;
EXEC import_threshold;
-- Load risk sensor data from CSV
TRUNCATE TABLE t_rs_data;
EXEC import_data;
COMMIT;