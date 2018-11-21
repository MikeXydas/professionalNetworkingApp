# LinkedIn Clone

## *Introduction*
This project aims to help me learn the basics of full stack and web app development by creating a plain and basic **professional networking app** like linkedIn. Briefly, the app follows **REST** architecture with **Java JPA entities** on the backend and **angular6** on the forntend.


## *Setting up*
I must say that managing to set up the whole project can be tedious due to the number of dependencies being used for the backend (which was writtend in eclipse). A `.war` file is also included in `/build`
However, the general steps are.
Backend:
1. Create the database using the `createLinkedInDB.sql` script
2. Set up the backend project installing the dependencies (Written at the boottom of the README)
3. Run the server through `Tomcatv8.5`

Frontend:
1. Install a `CORS` addon on your browser and enable it (Care to have it disabled when you are not using this locally installed webApp)
2.  Use the angular6 deployment directory `build/linkedInFront` to run the frontend or run it with `ng serve` in `source/Front_src directory`

However, the app will face errors since it will not be able to find the fileSystem directory used for saving uploaded files like pictures.

## *Services*
### Implemented
* LogIn / Register
* Article reading, commenting, liking and writing
* Apply to a job advertisement or post one
* Friend request/accept system
* Messaging between friends
* Notifications of likes, comments, friend requests
* Public/ Private profile editing

The order in which the articles are shown is based on a KNN which finds the k writers that act like you (comment and like the sam articles).
The order in which the ads are shown depends on the number of common skills between yours (written in the profile) and the skills requested by the ad. Care: The correct format of writing skills is `skill1, skill2, skill3`.

###  TO DOs
* Fix the CORS filter in order to avoid using the browser addon
* Set up an SSL encryption on the requests
### Avoided Services
In this project not a lot of effort was put into making the app aesthetically pleasing. The graphics and layout are simple, no responsiveness implemented and the user has no major help on how to navigate through the app.

## *Workflow followed*
These are the steps that I followed in order to create and implement this project
1. Create  UML use case and class diagrams
2. Create the EER diagram (`databaseModelVer2.mwb`) and the mySQL database (`createLinkedInDB.sql`)based on the class diagram
3. Setting up the backend server project in eclipse, create the JPA entities, implement the services needed and check them through Postman
4. Create some low fidelity wireframes (They were not all of them 100% followed when implementing them)
5. Setting up the frontend with angular6 and implemented it

## *Dependencies*
### Backend 

* Server: Tomcat v8.5
* JRE: JDK1.8
* JPA: eclipseLink.2.5.2
* commons-io-2.6.jar
* jackson-core-2.9.0.jar
* jackson-databind-2.9.0.jar
* jjwt-0.9.1.jar
* jackson-jaxrs-json-provider-2.9.0.jar
* jackson-jaxrs-base-2.9.0.jar
* jackson-jaxrs-xml-provider-2.9.0.jar
* jaxb-core-2.3.0.jar
* mimepull-1.9.3.jar
* jersey-media-multipart-2.17.jar
* JAX-RS-Jersey-API (βιβλιοθήκη)

### Frontend
* Node.js with NPM
* bootstrap4
* file-saver



```
