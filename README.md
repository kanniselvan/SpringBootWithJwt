# Spring Boot with JWT

## Architecture  
This project is built upon the Spring Boot framework, leveraging its capabilities for rapid development. The application is structured as a typical Spring Boot application, focusing on modularity and maintainability. The architecture is composed of:  
- **Controller Layer**: Handles incoming requests and routes them to appropriate services.  
- **Service Layer**: Contains business logic, processing data from the repository layer and returning results to the controllers.  
- **Repository Layer**: Communicates with the database for CRUD operations, abstracted through Spring Data JPA.

## API Endpoints  
- **Authentication**  
  - `POST /api/auth/login`: Authenticate user and return JWT token.  
  - `POST /api/auth/register`: Register a new user account.  

- **User Management**  
  - `GET /api/users`: Retrieve a list of users.  
  - `GET /api/users/{id}`: Get details of a specific user by ID.  
  - `PUT /api/users/{id}`: Update user information.  
  - `DELETE /api/users/{id}`: Delete a user account.

## Testing  
The application supports unit testing and integration testing using JUnit and Mockito. To run the tests, use the following command:  
```
./mvnw test
```
Ensure you have all the test dependencies included in your `pom.xml`.

## Dependencies  
- Spring Boot Starter Web  
- Spring Boot Starter Security  
- Spring Boot Starter Data JPA  
- JSON Web Token (JWT) library  
- H2 Database for in-memory testing  
- Lombok for reducing boilerplate code  

## Usage Examples  
1. **Login User**  
   Send a POST request to `/api/auth/login` with the following JSON body:  
   ```json
   {
     "username": "your_username",
     "password": "your_password"
   }
   ```  
   If successful, you'll receive a token:  
   ```json
   {
     "token": "your_jwt_token"
   }
   ```

2. **Access Protected Routes**  
   Use the token received to access protected routes by adding it in the Authorization header:  
   ```
   Authorization: Bearer your_jwt_token
   ```

## NimbusDS Overview  
NimbusDS is a library that provides implementation for JSON Web Tokens (JWT). It is used to create, sign, and verify tokens in conjunction with Spring Security in this application. The library is robust and compliant with the JWT specification, ensuring the security and integrity of the tokens being used in the application.  

This project showcases how to integrate NimbusDS with Spring Boot to manage authentication and authorization effectively.