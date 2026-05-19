# FirstClub Membership Program Backend
This is the backend system for the **FirstClub Membership Program**, built with **Java 17+**, **Spring Boot 3**, and **H2 In-Memory Database**. 
The platform offers users subscription-based memberships with tiered benefits and a smooth experience integrated into their shopping and checkout journey.
## 🚀 Features
- **Membership Plans**: Supports `Monthly`, `Quarterly`, and `Yearly` plans with base pricing.
- **Membership Tiers**: Supports `Silver`, `Gold`, and `Platinum` tiers.
- **Configurable Benefits**: Perks (like Free Delivery, Extra Discounts, Priority Support) are data-driven and easily configurable per tier.
- **Subscription Management**: Users can subscribe to a base plan and tier, check their active status, or cancel their subscription.
- **Automated Tier Upgrades**: An asynchronous event-driven engine evaluates user shopping metrics (total orders, total spend) and automatically upgrades a user's tier if they cross the configured thresholds.
## 🛠️ Technology Stack
- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **H2 Database** (In-memory for easy testing)
- **Lombok** (Boilerplate reduction)
- **Springdoc OpenAPI / Swagger UI** (API Documentation)
## ⚙️ Running the Application Locally
### Prerequisites
Make sure you have **Java 21** and **Maven** installed on your machine.
### Installation & Execution
1. Clone the repository:
   ```bash
   git clone https://github.com/harshitmalhotra/membership-backend.git
   cd membership-backend
   ```
2. Run the application using the Maven wrapper:
   ```bash
   mvn spring-boot:run
   ```
   *Note: On startup, a `DataLoader` will automatically seed the in-memory database with test plans, tiers, benefits, and a test user (User ID: 1).*
3. The application will start on port `8080`.
## 📖 API Documentation (Swagger UI)
You can interact with and test all the REST APIs directly from your browser using the auto-generated Swagger UI.
Once the application is running, navigate to:
**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**
### Key Endpoints
- **`GET /api/catalog/plans`**: List all available membership plans and prices.
- **`GET /api/catalog/tiers`**: List all available tiers, their requirements, and their configured benefits.
- **`POST /api/subscriptions`**: Subscribe a user to a plan.
- **`GET /api/subscriptions/{userId}`**: Get a user's active subscription status.
- **`PUT /api/subscriptions/{userId}/cancel`**: Cancel an active subscription.
- **`POST /api/events/order`**: Simulate an order being placed. This triggers the asynchronous engine to update user metrics and evaluate if they qualify for an automatic tier upgrade!
