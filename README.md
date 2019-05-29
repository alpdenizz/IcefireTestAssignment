# IcefireTestAssignment
Icefire Test Assignment as specified in 'specification.pdf'

## Tech Stack
* Java 8
* Spring boot
* H2 in memory database
* Maven as dependency manager
* Javascript for UI

### How to run
java -jar bidibidi.jar
After this the application will listen http://localhost:8080/

### How to build
You can import it your IDE as a Maven project.
In Eclipse: Import -> Maven -> Existing Maven projects
You can find more in: https://spring.io/guides/gs/serving-web-content/

#### API
1. Encrypt a text value and save it to DB  
POST http://localhost:8080/encrypt  
Header: 'Content-type': 'application/json'
Request body: {"encrypted": "your_text_here"}  
Response: 200 with body: {"encrypted": encryption_result, "id": generated_id}  
If your text is blank, then it will return 400

2. Decrypt a text value  
GET http://localhost:8080/decrypt?text=your_text_here  
Response: 200 with body: {"encrypted": decryption_result, "id": 0}  
If your text is blank, then it will return 400

3. List all encrypted values  
GET http://localhost:8080/  
Response: 200 with json array as: [{"encrypted": e1, "id": id1},{"encrypted": e2, "id": id2}...]
