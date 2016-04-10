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

### To fetch dependencies

From the project root and using command-line simply type *mvn resolve*


How to use
----------

Following this steps should get you going:

### Compile the project

Just run *mvn package* and you should be all set


### Deploying the plugin in your pentaho environment

Copy the .kar file in ./basic-plugin-assembly/target folder and drop it at 

pentaho-solutions/system/karaf/deploy


