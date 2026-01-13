# Journal App – Spring Boot Backend

Journal App is a backend application built using **Spring Boot** that allows users to write personal journal entries and receive **automatic sentiment-based supportive emails**.

The idea behind this project is simple:  
when a user writes about their thoughts or problems, the system analyzes the content using an AI service and sends a helpful, motivational email back to the user.

This project focuses on **real-world backend development practices**, including security, external API integration, and clean architecture.

---

## Why I Built This Project

Many people write journals to express emotions, stress, or personal struggles.  
I wanted to build a system where journaling is not just storage, but also **supportive and interactive**.

This project helped me learn:
- How real backend systems are structured
- How to integrate AI APIs
- How to secure sensitive data properly
- How to automate emails based on user actions

---

## Key Features

- User registration and login
- JWT-based authentication and authorization
- Create and manage personal journal entries
- AI-based sentiment analysis of journal content
- Automatic email sent after writing a journal entry
- MongoDB Atlas for database storage
- Redis for caching
- Weather API integration
- Swagger UI for API documentation

---

## Tech Stack Used

- **Java 21**
- **Spring Boot**
- **Spring Security + JWT**
- **MongoDB Atlas**
- **Redis**
- **Spring Boot Mail**
- **Cohere AI API**
- **Swagger / OpenAPI**

---

## How the Application Works

1. A user registers and logs in
2. After login, the user writes a journal entry
3. The journal entry is saved in MongoDB
4. The journal content is sent to an AI API for sentiment analysis
5. The AI generates a supportive message with improvement tips
6. An email is automatically sent to the user with the analysis

All of this happens securely in the backend without exposing any sensitive information.

```text

## Project Structure (Simplified)

controller  →  service  →  repository
                ↓
       AI API / Email Service / Redis
