# SVS Vastra Collections

SVS Vastra Collections is an e-commerce website for a saree business.

I developed this project using Java, Spring Boot, Thymeleaf, HTML, CSS, JavaScript and MySQL.

The main purpose of this project is to provide a simple online shopping website where customers can view sarees, add products to cart and wishlist, place orders and manage their account.

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
- Git & GitHub

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

## Project Structure

The project follows a simple Spring Boot structure.

```text
src
 └── main
      ├── java
      │    └── com.svs.svscollections
      │         ├── config
      │         ├── model
      │         ├── repository
      │         ├── service
      │         ├── AdminController.java
      │         ├── CartController.java
      │         ├── ContactController.java
      │         ├── HomeController.java
      │         └── SvsCollectionsApplication.java
      │
      └── resources
           ├── static
           │    ├── css
           │    ├── images
           │    └── js
           │
           ├── templates
           └── application.properties