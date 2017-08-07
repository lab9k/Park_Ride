#Een Park & Ride routeplanner voor Gent
##Setup project
**Projectnummer:** pr-01

###Setting up project in Netbeans
Clone this repository &rightarrow; choose to create a new project in Netbeans &rightarrow; Under categories choose Maven project &rightarrow; Choose project with existing POM &rightarrow; choose folder ParkAndRideGent

###Running Test
The integration tests are run with [Selenide](http://selenide.org/) using the chrome webdriver. The webdriver must be available in your path before the tests can be run. The easiest way to do this on an OS X system with Homebrew installed is with the command: 

```
brew install chromedriver
```

Windows users can download the latest release from [The chromedriver website](https://sites.google.com/a/chromium.org/chromedriver/getting-started) and add the executable to their path. 

###Configuration of the Application
The application needs a google maps key to run correctly. The key should be placed in the ``ParkAndRideGent/src/main/resources`` folder, in a file named ``config.properties``, which you have to create yourself. The configuration parameters are listed in the ``config.example.properties`` file in the same folder. Currently the only key required is named ``GMAPS.KEY``. A Google account is required for the creation of this key via the website [Google Developers Console](https://console.developers.google.com/). Keys can be created by clicking the *get a key* button on [this page](https://developers.google.com/maps/documentation/javascript/get-api-key). After a key has been created the following API's must be enabled via the developer console:

* Google Maps Directions API
* Google Maps Distance Matrix API
* Google Maps JavaScript API
* Google Places API Web Service
 
When the necessary API's have been enabled, the key can be added to the ``config.properties`` file. 

###Building and deploying the application
*Using Netbeans*
After the project has been imported into Netbeans, the application can be build using the clean and build button located on the toolbar at the top, or by right clicking on the project and choosing *clean and build*. 

*Using Maven*
When maven and JAVA JDK are installed on the system the application can be build using the following commands:

```cd path/to/pr-01/ParkAndRideGent```

```mvn package -Dmaven.test.skip=true``` 

This will use the buildscript (pom.xml) to load dependencies, gather source files and specify where to place the output .war file.

Installation instructions for Maven can be found on the [Maven Website](https://maven.apache.org/download.cgi). 
Installation instrcutions for JDK can be found on the [Oracle Website](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

In both cases the packaged application will be located in ``path/to/pr-01/ParkAndRideGent/target/ParkAndRideGent-1.0-SNAPSHOT.war``. This file can be uploaded to your Java Web Server. For instructions on how to upload the file see the docs of your server. Some popular ones are :

* [Glassfish](https://docs.oracle.com/cd/E19798-01/821-1757/6nmni99aj/index.html)
* [Tomcat](https://tomcat.apache.org/tomcat-6.0-doc/deployer-howto.html)
* [Jetty](http://www.eclipse.org/jetty/documentation/current/configuring-deployment.html)


