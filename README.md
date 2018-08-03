# linkedInAssignment

UML class diagram is not updated, see the EER diagram on sqlModel directory


The project has many dependencies in order to be able to run correctly

TO DO:
	1) DONE Complete all the java beans (Not sure of the association lists) 
	2) Create Endpoints (Care for their name in web.xml)

Query working: http://localhost:8080/linkedInRestfulProject/services/User/query?id=10

CARE ON UPDATING USER: Two services must be called. One for updating the skills of the user and one for everything else

CARE ON CONNECTION_REQUEST: senderId must be considered as the exact opposite (receiver id) TO BE FIXED
	
!! No persistence.xml on the src/META-INF folder  !!
