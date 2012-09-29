select name,type,line,text
from user_source
where
name = 'CREATE_TOUR_DATA'
and line between 190 and 220
;
select object_name,object_type,status
from user_objects
where status='INVALID'
;
select name,type,referenced_name,referenced_type
from user_dependencies
where type='PROCEDURE'
;
