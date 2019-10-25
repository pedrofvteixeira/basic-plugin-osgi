Pentaho Basic Plugin
=====================

Objective
---------

The goal of this project is provide a basic pentaho OSGi plugin that allows one to get a feel for all the available core configurations, when developing a plugin

Getting started
---------------

The first thing you should do is to confirm you have maven installed ( https://maven.apache.org/ ).

To prep the project, you first need to resolve/include the necessary dependencies.
pom.xml file contains a list of the dependencies needed to successfully compile this project.


How to use
----------

Following this steps should get you going:

### Compile the project

Just run *mvn package* and you should be all set (please ignore all warning messages displayed in console during this execution, mvn will eventually finish, this is a known issue)


### Deploying the plugin in your pentaho environment

Copy the .kar file in ./basic-plugin-assembly/target folder and drop it at 

pentaho-solutions/system/karaf/deploy

(There is a know issue during new file upload on Browse view, an error will occur on server's console, even so the file will be correctly uploaded)


