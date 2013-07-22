# Openfire Audit Plugin

This file contains information relating to the openfire-audit-plugin. The 
information includes the packaging structure, build mechanism and installation
instructions.

## Packaging Structure

This Plugin follows the standard packaging structure as defined by Openfire
documentation. The plugin has no admin console extensions.

	plugin
	  - README.txt
	  - plugin.xml
	  - database   <-- Folder containing the database scripts
	  - lib        <-- Folder containing the plugin dependencies


## Build Mechanism

The project uses Maven as its build tool. The build process builds three artifacts:

- Database zip file containing all the database scripts.
- Plugin jar file containing the entire plugin for installation.
- Plugin jar file containing the plugin classes.

To build the the artifacts use the maven command 'mvn clean package'.


## Installation Instructions

To install the plugin, take the Plugin jar file and copy it into the plugin
directory of the Openfire server. Openfire will explode the jar file automatically.
The database scripts should be run in order to build the database that the plugin 
will interact with. Once done the 'jdbc.properties' file in the openfire-audit-plugin 
jar file should be updated to hold the correct database connection strings and 
credentials. This can be extracted and updated with the following commands:

	jar -xvf <openfire-audit-plugin>.jar jdbc.properties
	vim jdbc.properties
	jar -uvf <openfire-audit-plugin>.jar jdbc.properties 

Once all these steps have been completed the openfire server should be restarted.