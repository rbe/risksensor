-- Analyze tour data
DELETE FROM t_rs_tour_data;
DELETE FROM t_rs_tour;
EXEC analyze_tour;
COMMIT;
