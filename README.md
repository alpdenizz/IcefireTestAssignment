# IcefireTestAssignment
Icefire Test Assignment as specified in 'specification.pdf'

## Tech Stack
* Java 8
* Spring boot
* H2 in memory database
* Maven as dependency manager
* Javascript, HTML and CSS for UI

### How to run

### For API
java -jar TestAssignment-0.0.1-SNAPSHOT.jar  
After this API will listen http://localhost:8080/  

### For UI
cd UI/  
npm install live-server  
node main.js  
After this UI is opened http://localhost:8081/  

### How to build
You can import it your IDE as a Maven project.  
In Eclipse: Import -> Maven -> Existing Maven projects  
You can find more in: https://spring.io/guides/gs/serving-web-content/  

#### Rules  
After you running both UI and API  
1. In UI, put your text into input area, then encrypt it with clicking "Encrypt" button. In the end, encryption will be stored in the table below.  
2. From the table, you can click an encrypted value. It will load it into the input area. You can decrypt it with clicking "Decrypt" button. This will show the result value in the input area. Or you can click "Encrypt", it will do rule 1.  
3. Text used for both operations must not be empty.

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
