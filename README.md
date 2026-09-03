# SVS Vastra Collections

SVS Vastra Collections is an e-commerce website developed for a saree business.

This project was developed as a freelance project for a client. The main goal was to build a simple and user-friendly online shopping website where customers can browse saree collections, view product details, add products to the cart and place orders.

The application also includes an admin side for managing products, customers and orders.

## Project Background

This project was developed based on the requirements of a real client who runs a saree business.

I worked on the development of the application and implemented both customer-side and admin-side functionalities.

The project helped me gain practical experience in developing a complete web application, connecting the application with a MySQL database and working with different modules of an e-commerce system.

## My Role

**Freelance Full Stack Developer**

My responsibilities included:

- Developing the application using Java and Spring Boot.
- Creating customer and admin functionalities.
- Implementing product management features.
- Implementing shopping cart and order-related functionality.
- Connecting the application with MySQL using Spring Data JPA and Hibernate.
- Creating web pages using Thymeleaf, HTML, CSS and JavaScript.
- Working with Spring MVC controllers, services, repositories and models.
- Testing the application and fixing issues during development.
- Organizing the project using a standard Spring Boot structure.

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
- View saree collections
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