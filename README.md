# Internet Service for the BPF (Bundle Protocol Framework) 

## General
### About
This is a project made by (or partially by) WSN-Team 2012 in the course CSD, which is part of the [Technology Transfer Alliance](http://ttaportal.org/).
### Purpose
One of the goals of the project is to be able to send data upstream from rural areas and to do this DTN communication has been used.
### Description
This project is a service implementing the BPF and providing the functionalities for the server needed within the context of the WSN-Team 2012 project.  This service is based on a Linux environment and implements the platform-dependent classes of the BPF. In particular for the server node, the service collects all received bundles, decrypts them if necessary, parses the payload content according to the format that we expect from the WSN gateway, and finally stores the data in the database. Data stored in the database can be accessed by using another project developed as part of the WSN-Team 2012 project: [Website](https://github.com/WSN-2012/Website).
## Build & Install
### Prerequisites
You will need to have ant to compile this in an easy way. To get ant look into how to install it on your platform.

The project is also using a database in order to store bundle payload. The database folder is the same with the one in Website project available in WSN-2012 repository.
In this project we used postreSQL vesrion 9.1 to create our database schema. Any database can be used if appropriate changes are commited in the Database.java and SQLQueries.java files.
To download and install postgreSQL you can go to [PostgreSQL](http://www.postgresql.org/download/) and download the appropriate source. Then follow the [installation instructions](http://www.postgresql.org/docs/9.1/interactive/index.html). To load the database schema you have to do the following from command line:
* `psql postgres`(connect to database as postgres user)
* `CREATE DATABASE "DatabaseName";` (create database)
* `psql databasename < data_base_dump` (import DB from dump file *outside the psql command line*. The projects database dump file is stored in the database folder. **Notice:** database should be created before importing the dump file)

**Notice** 
You need to Insert your database specifications in the [src/database/SQLQueries.java](https://github.com/WSN-2012/Website/blob/master/src/database/SQLQueries.java) file where todo comments appear.

### Building
The BPF is included as a submodule and it is built automatically when building the service. It is possible to configure several settings in the configuration file.
Follow the below steps to build service and BPF.

1.  `git clone --recursive https://github.com/WSN-2012/BPF-Base-Service.git`
2.  `cd BPF-Base-Service`
3.  change config file config/dtn.config.xml
4.  `ant`

### Start/Stop script
To install a start/stop script, follow the below steps:

1.  Copy the start/stop script: `cp linux-scripts/bpf-service /etc/init.d/bpf-service`
2.  Start it: `/etc/init.d/bpf-service start`
3.  Stop it: `/etc/init.d/bpf-service stop`

### Monitoring (automatic restarting)
To be able to monitor the service and automatically restarting it, we suggest using monit. Follow the below steps to set it up:

1.  Install monit on your system. E.g: `sudo apt-get install monit`
2.  Copy the monitor script to the monit path in your system: E.g: `cp linux-scripts/bpf-service.monit /etc/monit/conf.d/bpf-service`
3.  Restart monit: `/etc/init.d/monit restart`
