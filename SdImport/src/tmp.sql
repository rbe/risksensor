select * from xt_rs_import i
where trim(deviceid)='Device07' and xmg like 'AVG%'
 ORDER BY
        i.deviceid
        , i.recordnum
;
select * from v_rs_import_threshold
where deviceid='Device07';
select * from v_rs_import_data
where deviceid='Device07';

select * from t_rs_threshold
where deviceid='Device07';
select * from t_rs_threshold
where validfrom=validto
order by deviceid,validfrom,validto;
select * from t_rs_threshold
where deviceid='Device07'
order by deviceid,validfrom,validto;

select * from v_rs_threshold
where deviceid='Device07';

drop table t_rs_tour_state;
drop table t_rs_tour_log;
truncate table t_rs_tour_state;
truncate table t_rs_tour_log;
desc t_rs_tour_state;
desc t_rs_tour_log;

select deviceid,sdcardid,tourcount,drivenseconds,numberofeventssum,pointssum,actualsensorevent, actualeventoccurredon
from t_rs_tour_state
order by deviceid,sdcardid,actualeventoccurredon;

select *
from t_rs_tour_log
where deviceid='002-00000000021'
order by createdon;

select deviceid,sdcardid,createdon, sensorevent,drivenseconds,pointssecond,pointssum,rightcurvepointssum
from v_tour_log
where deviceid='Device08';
select *
from v_tour_log_bymin
where deviceid='Device08';
select *
from v_tour_log_byhour
where deviceid='Device07';
