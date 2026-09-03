# SVS Vastra Collections

SVS Vastra Collections is an e-commerce website developed for a saree business.

This project was developed as a freelance project for a client. The main purpose of the application is to provide an online platform where customers can browse saree collections, view product details, add products to their cart and place orders.

The application also provides an admin side for managing products, customers and orders.

## Project Background

This project was developed based on the requirements of a real client who runs a saree business.

I worked on the development of the application and implemented the customer and admin sides of the website. The project gave me practical experience in developing an e-commerce application, working with a relational database and implementing different application modules.

## My Role

**Freelance Full Stack Developer**

My responsibilities included:

- Developing the application using Java and Spring Boot.
- Implementing customer and admin functionalities.
- Developing product, cart and order-related features.
- Creating web pages using Thymeleaf, HTML, CSS and JavaScript.
- Connecting the application with MySQL using Spring Data JPA and Hibernate.
- Working with controllers, services, repositories and models.
- Testing the application and fixing issues during development.
- Organizing the application using a layered Spring Boot structure.

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- MySQL
- Thymeleaf
- HTML
- CSS
- JavaScript
- Maven
- Git
- GitHub

## Features

### Customer

- User registration
- User login
- Forgot password
- Browse saree collections
- View product details
- Add products to cart
- Update cart
- Wishlist
- Checkout
- Place orders
- View orders
- Customer profile
- Contact page

### Admin

- Admin login
- Admin dashboard
- Add products
- Edit products
- Manage products
- Manage orders
- View customers
- View customer details
- Sales report
- Settings

## Application Structure

The project follows a layered Spring Boot structure.

```text
src
├── main
│   ├── java
│   │   └── com.svs.svscollections
│   │       ├── config
│   │       ├── model
│   │       ├── repository
│   │       ├── service
│   │       ├── AdminController.java
│   │       ├── CartController.java
│   │       ├── ContactController.java
│   │       ├── HomeController.java
│   │       └── SvsCollectionsApplication.java
│   │
│   └── resources
│       ├── static
│       │   ├── css
│       │   ├── images
│       │   └── js
│       │
│       ├── templates
│       └── application.properties
│
└── test
    └── java
```

## Backend

The backend is developed using Java and Spring Boot.

The application uses:

- Spring MVC for handling web requests.
- Controllers for handling application requests.
- Service layer for application logic.
- Repository layer for database operations.
- Spring Data JPA and Hibernate for database interaction.
- MySQL for storing application data.

The different layers help keep the application organized and easier to maintain.

## Frontend

The frontend is developed using:

- HTML
- CSS
- JavaScript
- Thymeleaf

Thymeleaf is used to connect the frontend pages with the Spring Boot application and display dynamic data.

The project contains separate pages for customer and admin functionality.

## Database

The application uses **MySQL** as the database.

Spring Data JPA and Hibernate are used for database operations and mapping Java objects with database tables.

Database configuration is maintained through the Spring Boot:

```text
src/main/resources/application.properties
```

## How to Run the Project

### Prerequisites

Make sure the following are installed:

- Java
- Maven
- MySQL
- IntelliJ IDEA or another Java IDE

### Steps

1. Clone the repository.

2. Open the project in IntelliJ IDEA.

3. Create a MySQL database for the application.

4. Open:

```text
src/main/resources/application.properties
```

5. Configure the MySQL database URL, username and password according to your local setup.

6. Run the Spring Boot application.

7. Open the application in a browser:

```text
http://localhost:8080
```

## Configuration

The application uses Spring Boot configuration through:

```text
application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**Note:** Database credentials should not be committed to a public GitHub repository.

## Project Highlights

- Developed as a freelance project for a real client.
- Built a complete e-commerce workflow for a saree business.
- Implemented separate customer and admin functionalities.
- Integrated MySQL with Spring Data JPA and Hibernate.
- Followed a layered Spring Boot application structure.
- Used Thymeleaf for dynamic server-side web pages.
- Used Git and GitHub for source code management.

## What I Learned

Working on this project helped me gain practical experience in:

- Developing a complete web application using Spring Boot.
- Understanding the MVC architecture.
- Working with controllers, services, repositories and models.
- Connecting a Java application with MySQL.
- Using Spring Data JPA and Hibernate.
- Creating dynamic pages using Thymeleaf.
- Implementing customer and admin workflows.
- Managing products and orders in an e-commerce application.
- Using Git and GitHub to manage project source code.
- Understanding and working with real client requirements.

## Future Improvements

The application can be improved further by adding:

- Online payment gateway integration.
- Product search and advanced filtering.
- Product reviews and ratings.
- Email notifications for orders.
- Improved authentication and security.
- Cloud deployment.
- Further responsive UI improvements.

## Author

**VenkataPathi Raju Chamarthi**

Freelance Full Stack Developer

GitHub: cvenkatapathi

## Project Status

This project was developed as a freelance e-commerce project for SVS Vastra Collections.

The application is currently maintained as part of my development portfolio.