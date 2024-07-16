# Store Management Application

## Description
This project is a graphical user interface (GUI) application designed to manage store operations including purchasing, selling, storing products, and providing user assistance. The application also allows for opening secondary windows for each option and changing the database configuration.

## Author
Ana Laura Chenoweth Galaz

## Features
- Purchase management
- Sales management
- Inventory management
- Database configuration
- User assistance

## Project Structure
The project consists of several classes, each responsible for a different aspect of the application.

### Classes

#### Tienda
The `Tienda` class represents the main graphical interface of the store application. It manages operations such as purchasing, selling, storing products, and providing user help. It also handles the opening of secondary windows for each option and allows for database configuration changes.

#### Conexion
The `Conexion` class provides methods to establish and manage a connection to a MySQL database. It includes methods for setting up the connection and retrieving the connection.

#### Almacen
The `Almacen` class represents a user interface panel that displays information about the products stored in the store. It allows users to view the list of available products and add new products to the panel.

#### VentanaCompra
The `VentanaCompra` class represents the product purchase window. It contains methods for managing purchases, such as updating the table, calculating the subtotal, and interacting with the database through the `Conexion` class.

#### VentanaVenta
The `VentanaVenta` class represents a graphical interface window for managing product sales. It includes functionalities for adding products to a table, calculating the subtotal and total, updating product quantities in the database upon a sale, and recording the quantity sold and the date of the sale.

It uses Swing components for the graphical interface and connects to the database via the provided `Conexion` class. The `EscuchadorBotones` class is an implementation of `ActionListener` to handle button actions.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL database

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/store-management.git
    ```
2. Open the project in your preferred IDE.

3. Configure the database connection in the `Conexion` class.

4. Run the `Tienda` class to start the application.

## Usage
- **Purchasing**: Open the purchase window to manage product purchases.
- **Sales**: Open the sales window to manage product sales.
- **Inventory**: View and manage the list of stored products in the inventory panel.
- **Database Configuration**: Change the database settings through the provided interface.

## Contact
For any inquiries or support, please contact Ana Laura Chenoweth Galaz at chenowethgalazanalaura@gmail.com.
