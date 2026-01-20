# 🛒 E-Commerce Backend API

A robust E-Commerce REST API backend built with **Spring Boot** and **MongoDB**, featuring secure payment integration using **Razorpay**. This project was designed to demonstrate clean architecture, scalable database patterns, and seamless third-party service integration.

## 🚀 Features

- **Product Management**: Create, read, update, and delete products with stock management.
- **Shopping Cart**: Add items, view cart, and auto-clear upon order placement.
- **Order Processing**: Create orders from cart items with status tracking.
- **Payment Integration**: Complete payment flow with **Razorpay** (Orders + Webhooks).
- **Stock Control**: Automatic stock deduction upon paid orders.
- **Webhook Handling**: Real-time payment status updates via Razorpay webhooks.

---

## 🛠️ Technology Stack

- **Core**: Java 21, Spring Boot 3.2.2
- **Database**: MongoDB (Atlas or Local)
- **Payment Gateway**: Razorpay
- **Build Tool**: Maven
- **Testing**: Postman

---

## ⚙️ Setup & Installation

### 1. Prerequisites
- Java 17 or 21 installed (`java -version`)
- MongoDB running (Local or Atlas Connection String)
- Maven (optional, project includes wrapper)

### 2. Clone the Repository
```bash
git clone https://github.com/BinaryBhakti/eCommerce.git
cd eCommerce
```

### 3. Configuration
Update `src/main/resources/application.properties` with your credentials:

```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/<database>

# Razorpay Keys (Get from Dashboard)
razorpay.key.id=your_key_id
razorpay.key.secret=your_key_secret
```

### 4. Build and Run
Use the included Maven Wrapper to build and start the server:

**Windows (PowerShell/CMD):**
```powershell
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

The server will start on `http://localhost:8080`.

---

## 🔌 API Endpoints

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | List all products |
| GET | `/api/products/{id}` | Get product details |
| POST | `/api/products` | Create a new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### Cart
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cart/{userId}` | Get user's cart |
| POST | `/api/cart/add` | Add item to cart |
| DELETE | `/api/cart/{userId}/clear` | Clear cart |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders` | Create order from cart |
| GET | `/api/orders/{orderId}` | Get order details |

### Payments
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/payments/create` | Initiate Razorpay payment |
| POST | `/api/webhooks/payment` | Handle payment event |

---

## 🧪 Testing

A complete **Postman Collection** is included in the root directory: `E-Commerce-API.postman_collection.json`.

1. Import the file into Postman.
2. Ensure server is running.
3. Test the flows in order: `Create Product` -> `Add to Cart` -> `Create Order` -> `Create Payment`.

---

## 📁 Project Structure

```
src/main/java/com/example/ecommerce
├── config       # App configurations (RestTemplate, Razorpay)
├── controller   # REST Controllers
├── dto          # Data Transfer Objects
├── model        # MongoDB Documents
├── repository   # Database Repositories
├── service      # Business Logic
└── webhook      # Payment Webhook Handler
```