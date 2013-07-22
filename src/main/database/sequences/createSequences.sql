/*
 * This SQL file creates all the sequences that are required for the  
 * chat auditing component.
 *
 * Expects schema owner as parameter &1.
 *
 * Copyright (C) 2010 Surevine Ltd.
 * 
 * All rights reserved.
 */

create sequence &1..chat_audit_log_seq nocycle minvalue 1 start with 1 increment by 1 cache 20;

exit;
