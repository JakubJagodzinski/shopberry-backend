# Shopberry (Backend)
This project is an e-commerce website that allows customers to browse products, manage their accounts, place orders, and track their purchase history. <br>
Employees handle order processing, returns, and customer complaints. <br>
Administrators manage products, categories, users, and reviews, while also configuring discounts and store settings. <br>
The store owner has full access to financial statistics, sales data, and overall business performance. <br>
The platform ensures a seamless shopping experience with efficient management tools for all roles.

<br>
<br>
<br>


## Table of Contents
1. [Application Functionalities](#application-functionalities)
   - [Customer Website Features](#customer-website-features)
   - [Employee Website Features](#employee-website-features)
   - [Administrator Website Features](#administrator-website-features)
   - [Owner Website Features](#owner-website-features)
2. [Technologies](#technologies)
3. [Database Schema](#database-schema)
4. [How to Run the Project](#how-to-run-the-project)


# Application Functionalities:
## Customer Website Features:
1. Account:
   * Creating a new account (registration)
   * Logging into an account
   * Resetting the password
   * Managing account details (email, first name, last name, password, etc.)
2. Browsing the store:
   * Displaying the product list (one below the other)
   * Viewing products by category
   * Filtering the product list
   * Sorting the product list
3. Wishlist:
   * Adding products to the wishlist
   * Removing products from the wishlist
4. Orders & Shopping:
   * Adding products to the cart
   * Removing products from the cart
   * Modifying the quantity of products in the cart
   * Finalizing an order from the cart
   * Adding coupons/promotion codes
   * Viewing order history

<br>

## Employee Website Features:
1. Order Management:
   * Viewing the list of orders
   * Changing the order status (e.g., "Processing," "Shipped," "Completed," "Canceled")
   * Viewing order details (customer, products, delivery address, payment method)
2. Handling Complaints & Returns:
   * Viewing customer complaints and return requests
   * Changing the complaint/return status
   * Contacting the customer regarding the return

<br>

## Administrator Website Features:
1. Managing Products & Categories:
   * Adding new products
   * Editing products (changing name, price, description, images, availability)
   * Deleting products
   * Adding new categories
   * Editing categories (changing name, filter attributes)
   * Deleting categories
2. Managing Users:
   * Viewing the user list
   * Deleting user accounts
   * Changing user account types
   * Activating/Deactivating user accounts
3. Managing Reviews & Ratings:
   * Viewing customer reviews
   * Approving or removing inappropriate reviews
4. Managing Discounts & Coupons:
   * Creating discount codes and promotions
5. Store Configuration:
   * Setting up payment methods
   * Configuring shipping costs and delivery methods

<br>

## Owner Website Features:
1. Financial Management:
   * Viewing sales statistics
   * Monitoring revenue and expenses
2. Viewing Store Data:
   * Checking stock levels and supply status
   * Viewing the number of users

<br>
<br>
<br>

# Technologies:
* Frontend: ReactJS, CSS (SCSS, BootStrap)
* Backend: Java (Spring Boot)
* Database: MSSQL
* APIs: InPost Points, (...to be added)

<br>
<br>
<br>

# Database schema:
<img src="assets/db_schema.png" alt="database schema">

# How to Run the Project:
### Prerequisites

- Java 21+
- Docker

### Step-by-step

1. **Clone the repository:**

```powershell
git clone https://github.com/JakubJagodzinski/shopberry-backend.git
cd shopberry-backend
```

2. **Prepare .env file from template**
<br><br>

3. **Load environment variables from the `.env` file**

```powershell
Get-Content .env | ForEach-Object {
    if ($_ -match '^\s*([^#][\w\.\-]+)\s*=\s*(.*)$') {
        $name = $matches[1]
        $value = $matches[2].Trim('"')
        [System.Environment]::SetEnvironmentVariable($name, $value, 'Process')
    }
}
```
3. **Create containers**

```powershell
docker-compose up -d
```

4. **Build the project:**

```powershell
./mvnw clean package
```

5. **Run the application:**

```powershell
./mvnw spring-boot:run
```
