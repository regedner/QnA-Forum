# Stack Overflow-like Q&A Platform - QnA Forum

A simple Question & Answer platform inspired by Stack Overflow. Built using Java Spring Boot and Thymeleaf. This project aims to create a functional internal Q&A platform where users can ask questions, answer them, comment, like, and categorize the posts. Every action, such as posting, commenting, liking, and category/tag assignments, is instantly saved to the MySQL database.

## Features

- **User Authentication**: Register, log in, and manage your profile
- **Post Creation**: Users can create questions and answers
- **Comments**: Users can comment on posts and other comments
- **Likes**: Users can like posts and comments
- **Categories & Tags**: Organize posts by categories and tags
- **Notifications**: Users receive notifications for activities related to their posts or comments
- **Attachments**: Users can attach files to their posts
- **Instant Database Updates**: Every user action (creating posts, commenting, liking, etc.) is immediately saved to the MySQL database, ensuring real-time data persistence

![demo](https://github.com/user-attachments/assets/316896e6-ad11-436c-8d5d-b59d4e06f6f3)

## Technologies

- **Java Spring Boot**: Backend framework for the web application
- **Thymeleaf**: Template engine for rendering HTML views
- **MySQL**: Relational database for storing user, post, comment, and other data
- **WebSocket** (Optional): Real-time communication for notifications

## Model Classes

- **User**: Stores user information (username, password, email, etc.)
- **Post**: Represents a question or answer with details like title, body, user, etc.
- **Comment**: Comments on posts, including the content and the user who wrote it
- **Like**: Tracks likes on posts and comments
- **Category**: Groups posts into categories
- **Tag**: Labels posts with relevant keywords
- **Attachment**: Stores file attachments related to posts
- **Notification**: Notifies users about activities on their posts or comments

## Project Setup

### Prerequisites

- JDK 11 or higher
- Maven
- MySQL (or any other database)

## Usage

- **Register**: Create an account to start asking questions and answering others
- **Ask a Question**: Submit questions that other users can answer
- **Answer**: Respond to questions posted by others
- **Comment**: Discuss posts and answers
- **Like**: Like posts or comments you find helpful
- **Categorize**: Tag your posts with relevant categories and tags for better discoverability
- **Real-Time Data**: Every action (posting, commenting, liking) is instantly saved in the database, ensuring your data is always up-to-date
