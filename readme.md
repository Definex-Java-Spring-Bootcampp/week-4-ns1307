# kredinbizde (DefineX Final Project)

## Project Description

This project is developed to facilitate banks operating in the credit sector to retrieve customer information and process credit card or loan applications using a shared database among banks. Through a centralized database and services like Akbank, banks can access customer information and process applications. The project performs routing via a gateway, communicates with other projects via APIs in the background, and utilizes MySQL as the database. As an example for usage of RabbitMQ, every query made in the project saved as log information to the notification-service via RabbitMQ and store this information in a database. For this reason, queries take some time. If you wish, you can put these lines in the comment line and perform faster queries.Unit tests for "kredinbizde-service" and "akbank-service" are successfully executed.

### Running the Project

1. Run the docker-compose files in the "kredinbizde-service" and "akbank-service" directories to set up the necessary database and services.
2. Initially, run the Eureka server in the "kredinbizde-discovery" project.
3. Start the "kredinbizde-gw" (gateway) project.
4. Start the "kredinbizde-service" project, make sure databases and required services are ready.
5. Run the "akbank-service" project.
6. Optionally, run the "notification-service". (Notification service is a consumer project that receives messages from rabbitMQ.)
7. If the projects run smoothly, you can perform queries using sample Postman requests contained in a JSON file that you can import into Postman.

#### Notes

- To ensure the correct functioning of queries in "akbank-service", the ID of Akbank in the "banks" table in the "kredinbizde" database needs to be manually configured in the "akbank-service" project. The default ID value is 2. So, if the ID of Akbank is not 2 in the database, queries may not work correctly or errors may occur.
- The "delete user" feature is added solely for the deletion of objects created for unit tests in the akbank-service. In any application, especially in critical and live projects, instead of directly deleting from the database, it would be more appropriate to maintain an "active" boolean flag and update it accordingly.