/*
 * This SQL file creates all the users that are required to access
 * the chat auditing component.
 *
 * Expects 'auditer' user as parameter $1.
 * Expects 'auditer' user password as parameter $2.
 * Expects schema owner as parameter &3.
 * Expects schema owner password as parameter $4.
 *
 * Copyright (C) 2010 Surevine Ltd.
 * 
 * All rights reserved.
 */

-- Create owner schema
create user &3 identified by &4;
grant unlimited tablespace to &3;

-- Create auditer user
create user &1 identified by &2;
grant connect to &1;

exit;
