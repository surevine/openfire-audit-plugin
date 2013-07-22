/*
 * This SQL file creates all the indexes that are required for the  
 * chat auditing component.
 *
 * Expects schema owner as parameter &1.
 *
 * Copyright (C) 2010 Surevine Ltd.
 * 
 * All rights reserved.
 */

create index &1..event_time_idx on &1..chat_audit_log ( event_time asc );

exit;
