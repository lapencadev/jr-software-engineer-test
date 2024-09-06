Documentation of the process
================================

## First steps
### Repository setup
* First of all, I forked the repository in GitHub and I cloned it to the local environment.
### DDBB
The project uses a PostgreSQL database for data storage and testing. Please follow these steps to set up the PostgreSQL database:
* I added the dependency needed in order to connect the database with PostgreSQL.
1. Install PostgreSQL: If PostgreSQL is not installed on your machine, you can download and install it from the official website:
  https://www.postgresql.org/download/. I installed version 15.7. Username and postgres will be "postgres".
2. Once PgAdmin is installed, you will find one server. We have to register a new server.
   * In "Quick links" > Add new server:
     * Name: local
     * Host name/address: localhost
     * Port: 5432 (by default)
     * Maintenance database: postgres
     * Username: postgres
     * Password: postgres
3. Create a Database: Right clik on "Databases" and create a new one named "bookstore". Owner will be postgres by default.
4. Create the new schema "bookstore": in our new bookstore database, we have "schemas", so create a new one called "bookstore".
5. application.properties file is already updated with the neccessary information:
   * spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore?currentSchema=bookstore 
   * spring.datasource.username=postgres 
   * spring.datasource.password=postgres

6. The first time we run the project, we have to set up this property from application.properties as follows:
spring.jpa.hibernate.ddl-auto=create. In order to avoid the creation of the database each time we run the project, you can change "create" for "none".
7. Once the project is running, you can access to the swagger URL and create new orders: http://localhost:8080/swagger-ui/index.html#/order-controller/createOrder

#### import.sql filed is updated with some example data to see orders.

## New entities
To effectively manage orders, several new entities were created:

1. Order: represents an order with basic attributes such as ID, order date, order code, status, and a list of ordered items.
2. OrderedItem: serves as a relational table linking orders with book stock. Each entry includes an ID, order ID, book ID, and quantity. This design facilitates efficient inventory management and allows for updates to book stock levels based on customer orders.
3. Status: I decided to add an entity for providing a status for each order to track its current state.

The entities are organized into folders by entity. Each folder contains the relevant class, and if needed, the repository, service, and controller, as well as a folder for DTO classes.

## Services and endpoints
Once the entities were established, the following components were created:
### GET method 
* Orders Endpoint: I implemented a GET endpoint for retrieving orders. A DTO class was created to manage the information exposed by this endpoint.
* URL: http://localhost:8080/orders

### Post method
* Create Order Endpoint: I implemented a POST method for creating new orders with the following features:
  * Error Management: Handles different types of errors.
  * Book Stock Management: Updates the quantity of books and ensures that the order is created even if the stock cannot be updated.
  * Stock Validation: Adds books to the order only if there is sufficient stock.
  * Order Code Generation: Generates a sequential order code and returns it to the client.

## Tests
### Test Enhancements
* SQL Annotations: I have slightly modified the test for book stock to include another @Sql annotation. The first annotation inserts test data into the database, and the second one deletes it after the test. This approach ensures that the database remains clean and test data is removed after execution.
### Test Cases
* Order with Books: Tests retrieval of orders with associated books.
* Create New Order: Tests the creation of a new order and verifies the generation of an order code.
* Update Book Stock: Tests the update of book stock levels following an order creation.

## Tools
### SQL designer
* Tool Used: I utilized a SQL designer tool to facilitate the creation and visualization of the database schema. This tool helped manage and implement relationships between tables effectively.
* URL: https://sql.toad.cz/
* 
### API Documentation
* I added the dependency and properties to enable testing the endpoints using Swagger.
* Swagger URL: http://localhost:8080/swagger-ui/index.html#