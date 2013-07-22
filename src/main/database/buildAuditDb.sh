# This script automatically builds the chat auditing database and
# should be executed on the box that the database is on.
# Before running this script the file 'database.properties' should
# be updated to contain the information used to configure the
# database connections and users.

# Abort on error
set -e

# Import environment variables
. ./database.properties

# Create the database users
sqlplus $DBA_USERNAME/$DBA_PASSWORD AS SYSDBA @users/createUsers.sql $CHT_ADT_USR $CHT_ADT_PSSWRD $CHT_ADT_OWNER $CHT_ADT_OWNER_PSSWRD

# Create the database tables
sqlplus $DBA_USERNAME/$DBA_PASSWORD AS SYSDBA @tables/createTables.sql $CHT_ADT_OWNER $CHT_ADT_USR

# Create the database indexes
sqlplus $DBA_USERNAME/$DBA_PASSWORD AS SYSDBA @indexes/createIndexes.sql $CHT_ADT_OWNER

# Create the database sequences
sqlplus $DBA_USERNAME/$DBA_PASSWORD AS SYSDBA @sequences/createSequences.sql $CHT_ADT_OWNER 

# Create the database triggers
sqlplus $DBA_USERNAME/$DBA_PASSWORD AS SYSDBA @triggers/createTriggers.sql $CHT_ADT_OWNER
