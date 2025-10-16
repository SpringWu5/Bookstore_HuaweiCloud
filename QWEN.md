# Campus Mall Repository - QWEN Context

## Project Overview

The campus-mall-repo is a comprehensive campus second-hand marketplace application with a complete full-stack architecture. It includes both a backend (Spring Boot/Maven) and frontend (Vue 3/Vite) system designed for campus users to buy, sell, and trade second-hand items as well as participate in forums, seek items, and more.

### Architecture
- **Backend**: Spring Boot multi-module application (Maven-based)
- **Frontend**: Vue 3 application built with Vite
- **Database**: MySQL 8.0 with comprehensive schema
- **DevOps**: CI/CD pipeline with GitHub Actions, Docker support

### Backend Structure
The backend is structured as a Maven multi-module project:
- `second-common`: Shared utilities, constants, exceptions, and configuration
- `second-pojo`: Data transfer objects (DTOs), entities, and view objects (VOs)
- `second-server`: Core business logic, controllers, services, mappers, and configurations

### Frontend Structure
The frontend is a modern Vue 3 application using:
- Vite as the build tool
- Element Plus UI library
- Pinia for state management
- Axios for API communication
- Vue Router for navigation
- SASS for styling
- Vue Quill for rich text editing

## Key Features

### User Management
- User registration and authentication with JWT
- Role-based access (admin/user)
- Profile management
- Account status controls

### Marketplace Features
- Product listing and catalog management
- Category management (items, colleges, dormitories)
- Search and filtering capabilities
- Shopping cart functionality
- Order processing pipeline

### Additional Features
- Forum with categorized discussions
- Seek/Request functionality (users can request specific items)
- Commenting system with nested replies
- Rating and review system
- File upload via Aliyun OSS
- Address book management

### Administrative Features
- User management dashboard
- Product moderation tools
- Category management
- Order tracking and management
- System announcements

## Technologies and Dependencies

### Backend Technologies
- **Framework**: Spring Boot 2.7.3
- **Database**: MySQL 8.0, MyBatis-Plus for ORM
- **Cache**: Redis (implied from documentation)
- **Security**: JWT for authentication
- **Documentation**: Knife4j (Swagger integration)
- **Storage**: Aliyun OSS for file uploads
- **Payment**: WeChat Pay integration
- **Utilities**: Lombok, FastJSON, Apache POI, Druid connection pool

### Frontend Technologies
- **Framework**: Vue 3
- **Build Tool**: Vite
- **UI Library**: Element Plus
- **State Management**: Pinia with persistence plugin
- **HTTP Client**: Axios
- **Routing**: Vue Router
- **Rich Text**: Vue Quill
- **Styling**: SASS

## Building and Running

### Backend
1. **Prerequisites**: JDK 8+, Maven 3.6+
2. **Setup**:
   - Configure database connection in `application-dev.yml`
   - Execute database script `docs/campus_service.sql`
   - Set up Redis if required
3. **Run**: `mvn spring-boot:run -pl second-server`
4. **Default Port**: 8080

### Frontend
1. **Prerequisites**: Node.js 14+
2. **Setup**:
   - Navigate to `/frontend` directory
   - Install dependencies: `npm install`
3. **Development Server**: `npm run dev`
4. **Build for Production**: `npm run build`
5. **Default Port**: 7000 (with proxy to backend at 8080)

## Database Schema

The application uses a comprehensive MySQL database schema with the following key tables:
- `user`: User account management with roles and statuses
- `goods`: Product listings including pricing and descriptions
- `orders` and `order_detail`: Order management system
- `category`, `college`, `dormitory`, `forum`: Classification systems
- `posts`: Forum posts with category support
- `seek`: Item request system (users can request specific items)
- `comment`: Comment system for products, forums, and requests
- `address_book`: Shipping address management
- `announcement`: System announcements
- `goods_detail`: Detailed stats for products (views, likes, etc.)

## Development Conventions

### Backend
- **Naming**: Database uses snake_case, Java code uses camelCase
- **Encoding**: UTF-8 throughout
- **API Structure**: RESTful endpoints with JWT authentication
- **Response Format**: Standardized `Result` wrapper class
- **Security**: MD5 hash for passwords (not recommended but implemented)

### Frontend
- **Component Structure**: Separated by page (admin/user) and function
- **State Management**: Pinia stores for different domain functionality
- **API Integration**: Centralized in `/src/api/` directory
- **Routing**: Organized by user roles

## CI/CD Pipeline

The project includes a comprehensive CI/CD setup using GitHub Actions:
- **Build and Test**: On every push/PR to main branch
- **PR Testing**: With MySQL and Redis services integration
- **Production Deployment**: Triggered by semantic version tags (v1.0.0)
- **Docker Support**: Builds and pushes Docker images to DockerHub
- **Secrets Required**: For deployment (SSH credentials, DockerHub tokens)

## Project Context for Development

This is a fully functional campus second-hand marketplace with a rich feature set. The application is well-structured as a multi-module Maven project for the backend with clear separation of concerns. The frontend follows modern Vue 3 patterns with component-based architecture and Pinia for state management.

When working on the project:
1. The backend uses a layered architecture (controller-service-mapper)
2. The frontend organizes functionality by views and shared components
3. The database schema supports complex relationships between users, products, orders, and social features
4. Authentication is handled via JWT tokens
5. File uploads are managed through Aliyun OSS integration
6. The application supports both user and admin interfaces with different permission levels