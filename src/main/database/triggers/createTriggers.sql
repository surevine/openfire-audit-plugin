/*
 * This SQL file creates all the triggers that are required for the  
 * chat auditing component.
 *
 * Expects schema owner as parameter &1.
 *
 * Copyright (C) 2010 Surevine Ltd.
 *
 * All rights reserved.
 */

create or replace trigger &1..chat_audit_log_trg
before insert on &1..chat_audit_log
for each row
declare
	gen_id number;
begin
	select &1..chat_audit_log_seq.nextval into gen_id from dual;
	:new.log_id := gen_id;
end;
/

exit;
