*Required*

- Node Js 6.14.15
- java 11
- maven

####This sample consists of 
- a front end that has been built with react that runs on a node server
- Backend that has been built using springboot and runs with an embedded tomcat.

####To build and start the backend from the command line 

1. Run command `mvn clean install` from the project root (/daily-flights)
to build and generate an executable jar file.
2. To start the application run the next command in the build directory (/daily-flights/target) `java -jar app.jar` 
to start the back-end application on port 8084
3. go to http://localhost:8084/actuator/healthconfirm to confirm that application is up 

#### To build and start the front-end from the command line
1. from the frontend root (/daily-flights/frontend) run `npm ci` command to build the project. This will
ensure that the specified package versions in the package-lock.json are imported.
2. Then run `npm start` to start the node server.
3. Then open http://localhost:3000/flights and enter a date in the form to view the daily schedule.
