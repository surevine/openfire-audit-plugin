#
# README
#

The Openfire audit plugin persists audit data to an RDBMS.

The plugin needs to be able to connect to a configured RDBMS
in order to persist the audit data. This component contains 
a set of SQL scripts that build the required database component.

The SQL scripts are tailored to work with Oracle.

In order to build the Openfire audit plugin database component the 
script 'buildAuditDb.sh' should be executed. This will run the 
relevant SQL scripts in the required order. Prior to running this 
script the 'database.properties' file should be altered to include
relevant values for the database usernames and password that are 
going to be used for the installation.