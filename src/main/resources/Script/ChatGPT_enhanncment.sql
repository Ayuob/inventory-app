-- Create the sales schema
CREATE SCHEMA sales;

-- Create the sales.customers table
CREATE TABLE sales.customers (
                                 customer_id CHAR(5) NOT NULL PRIMARY KEY,
                                 company     VARCHAR(100),
                                 address     VARCHAR(100),
                                 city        VARCHAR(50),
                                 state       CHAR(2),
                                 zip         CHAR(5),
                                 newsletter  BOOLEAN
);

-- Create the sales.orders table
CREATE TABLE sales.orders (
                              order_id     INT GENERATED ALWAYS AS IDENTITY (START WITH 100 INCREMENT BY 1) NOT NULL PRIMARY KEY,
                              order_date   DATE,
                              customer_id  CHAR(5),
                              CONSTRAINT fk_customers_customer_id FOREIGN KEY (customer_id)
                                  REFERENCES sales.customers (customer_id)
);

-- Create the sales.order_lines table
CREATE TABLE sales.order_lines (
                                   order_id    INT,
                                   line_id     INT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL PRIMARY KEY,
                                   sku         VARCHAR(7),
                                   quantity    INT,
                                   CONSTRAINT fk_orders_order_id FOREIGN KEY (order_id)
                                       REFERENCES sales.orders (order_id)
);


-- Create the inventory schema
CREATE SCHEMA inventory;

-- Create the inventory.categories table
CREATE TABLE inventory.categories (
                                      category_id          INT NOT NULL PRIMARY KEY,
                                      category_description VARCHAR(50),
                                      product_line         VARCHAR(25)
);

-- Create the inventory.products table
CREATE TABLE inventory.products (
                                    sku             VARCHAR(7) NOT NULL PRIMARY KEY,
                                    product_name    VARCHAR(50) NOT NULL,
                                    category_id     INT,
                                    size            INT,
                                    price           DECIMAL(5,2) NOT NULL,
                                    CONSTRAINT fk_products_category_id FOREIGN KEY (category_id)
                                        REFERENCES inventory.categories (category_id)
);


-- Create the purchase schema
CREATE SCHEMA purchase;

-- Create the purchase.suppliers table
CREATE TABLE purchase.suppliers (
                                    supplier_id   INT NOT NULL PRIMARY KEY,
                                    supplier_name VARCHAR(100),
                                    address       VARCHAR(100),
                                    city          VARCHAR(50),
                                    state         CHAR(2),
                                    zip           CHAR(5),
                                    contact_email VARCHAR(100),
                                    contact_phone VARCHAR(20)
);

-- Create the purchase.purchase_orders table
CREATE TABLE purchase.purchase_orders (
                                          order_id       INT GENERATED ALWAYS AS IDENTITY (START WITH 100 INCREMENT BY 1) NOT NULL PRIMARY KEY,
                                          order_date     DATE,
                                          supplier_id    INT,
                                          status         VARCHAR(20),
                                          total_quantity INT,
                                          total_amount   DECIMAL(10,2),
                                          CONSTRAINT fk_suppliers_supplier_id FOREIGN KEY (supplier_id)
                                              REFERENCES purchase.suppliers (supplier_id)
);

-- Create the purchase.purchase_order_lines table
CREATE TABLE purchase.purchase_order_lines (
                                               line_id     INT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL PRIMARY KEY,
                                               order_id    INT,
                                               sku         VARCHAR(7),
                                               quantity    INT,
                                               price       DECIMAL(5,2),
                                               total_amount DECIMAL(10,2),
                                               CONSTRAINT fk_purchase_orders_order_id FOREIGN KEY (order_id)
                                                   REFERENCES purchase.purchase_orders (order_id)
);
