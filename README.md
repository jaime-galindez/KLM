# KLM

The project uses maven as build tool.

It's a multimodule project, with the parent pom as parent moduile, frontend for the angular part and backend for the backend.

A previous step to have the project working is needed. Go to frontend folder with the command line and run npm install to download all the angular dependencies

The different modules can be built separately, backend will include the module frontend and will serve the pages of the module

To build the full module run: mvn clean install and to tun it, go to backend/target and run java -jar backend-0.0.1-SNAPSHOT.jar

To access the application go to http://localhost:8888

The simple travel api mock application just be running in order to ahve this application working.

in the folder backend/src/main/resources you can find the file application.yml which allows you to configure the application:

- listening port
- OAuth configuration
- async configuration
- end points 

if you want to run the angular application separately, that can be done going to the folder frontend and running npm run-script start-proxy, doing so, you can access the client application on http://localhost:4200 and the rest calls from the browser will be redirected to http://localhost:8888/api


