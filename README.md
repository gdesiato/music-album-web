# Rock Albums Web Application

## Overview
The Rock Albums web application is designed for rock music enthusiasts. It serves as a platform where users can discover, explore, and interact with their favorite rock albums. The application targets music lovers, collectors, and fans with a specific interest in rock music.

## Features

### Discover and Explore
Users and guests can browse a collection of rock albums, including classic favorites, new releases, and hidden gems. They can access detailed information about each album, including artist, title, release year, genres, and tracks. For the purpose of the project, ten albums are used.

### User Authentication
To contribute reviews and ratings, users need to register and log in to the system. This process ensures user authenticity and encourages accountability when posting reviews. Users have the option to browse through the collection as guests but will need to log in to participate in rating and reviewing.

## System Architecture
The Rock Albums web application follows a client-server architecture.

### Front-end Technologies
- **HTML, CSS, and JavaScript**: These fundamental web technologies are used for structuring and styling the application's interface and implementing dynamic functionalities.

### Back-end Technologies
The application's back-end handles data storage, processing, and communication with the front-end. The chosen technologies include:
- **Java**: This serves as the core language for back-end development.
- **Spring Boot**: This is used to facilitate rapid development, dependency management, and RESTful API implementation.
- **Spring Data MongoDB**: This library provides seamless integration between the Spring Boot application and the MongoDB database, simplifying data access and manipulation.

### Database
**MongoDB** is used to store and retrieve album information, reviews, ratings, and user registration data.

By leveraging these technologies, the Rock Albums web application provides a seamless, engaging experience for rock music enthusiasts, enabling them to discover, review, and bond over their shared love for rock albums.

![Rock Album Cover](src/main/resources/static/images/rock_cover.png)
