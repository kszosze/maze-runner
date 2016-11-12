Summary
-------
The Maze Runner application is a command line application which interacts with 
the Maze server in other to move the runner and find the exit. 


Execute
-------
The execution is made by the following command.
```
java -jar head-runner-0.1.0.jar
```

URLs
----
The application will consume the rest services provided by a Maze server.
The urls for the entry points are configured by default in the application.yml
file inside the jar as values by default.

```
    endpoints:
       isOutUrl: "http://localhost:8080/rest/maze/isOut"
       moveUrl: "http://localhost:8080/rest/maze/move"
       mapUrl: "http://localhost:8080/rest/maze/map"
       pathUrl: "http://localhost:8080/rest/maze/path"
```
       
If you want to override the properties file it's possible to specify a new one 
with the --spring.config.location parameter in the command line like :
```
java -jar head-runner-0.1.0.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties
```

Remarks
-------
* The solution use 2 threads and communicate between then with 2 BlockingQueue: One to send messages and Other to receive them.
* Head and Runner print the message received in the console and yell the next action they will do
* At the end they celebrate and the program ends.
* Head will ask for the map to the Maze Server and calculate the shortest path using Breadth-Fist algorithm.
* In order to simply the development and process data from the server I added the third party library jackson-databind.
  As well got some problems to make run the ParameterizedTypeReference to unmarshall the Json into Objects, so instead of
  make my own unmarshall/converter choose to add that library that is already integrated in Spring.


Requirements
------------
* Server must be running and the end points must be accessible and listed in the properties file.
* It requires Java 8+