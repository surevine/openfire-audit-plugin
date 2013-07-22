/*
 * This SQL file creates all the tables that are required for the  
 * chat auditing component.
 *
 * Expects schema owner as parameter &1.
 * Expects 'auditer' user as parameter &2.
 *
 * Copyright (C) 2010 Surevine Ltd.
 * 
 * All rights reserved.
 */

create table &1..chat_audit_log
(
	log_id number(38) not null,
	event varchar2(50) not null, -- Defines the nature of the audit event
	sender varchar2(4000) not null,
	receiver varchar2(4000) not null,
	event_time timestamp,
	content varchar2(4000),
	label varchar2(2000),
	constraint chat_audit_log_pk primary key (log_id)
);

-- Grant select and insert privileges to auditer user
grant select, insert on &1..chat_audit_log to &2;

-- Create synonym so just 'chat_audit_log'
create or replace synonym &2..chat_audit_log for &1..chat_audit_log; 

exit;
