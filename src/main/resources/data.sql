-- ============================================
-- CATEGORÍAS (4 categorías)
-- ============================================
INSERT INTO categories (name, description) VALUES ('Electrónica', 'Dispositivos electrónicos y componentes');
INSERT INTO categories (name, description) VALUES ('Informática', 'Computadoras y accesorios de computadora');
INSERT INTO categories (name, description) VALUES ('Periféricos', 'Ratones, teclados, monitores y accesorios');
INSERT INTO categories (name, description) VALUES ('Accesorios Gaming', 'Equipamiento especializado para gaming');

-- ============================================
-- CLIENTES (4 clientes)
-- ============================================
INSERT INTO customers (first_name, last_name, email, password, address, phone_number)
VALUES ('Juan', 'García', 'juan.garcia@email.com', 'pass123', 'Calle Principal 123, Madrid', '+34 91 123 4567');

INSERT INTO customers (first_name, last_name, email, password, address, phone_number)
VALUES ('María', 'López', 'maria.lopez@email.com', 'pass456', 'Avenida Central 456, Barcelona', '+34 93 456 7890');

INSERT INTO customers (first_name, last_name, email, password, address, phone_number)
VALUES ('Carlos', 'Martínez', 'carlos.martinez@email.com', 'pass789', 'Plaza Mayor 789, Valencia', '+34 96 789 0123');

INSERT INTO customers (first_name, last_name, email, password, address, phone_number)
VALUES ('Ana', 'Rodríguez', 'ana.rodriguez@email.com', 'pass101112', 'Paseo de la Castellana 101, Madrid', '+34 91 234 5678');

-- ============================================
-- PRODUCTOS (20 productos)
-- 10 con 1 categoría
-- 10 con 2 categorías
-- ============================================

-- PRODUCTOS CON 1 CATEGORÍA (Categoría 1: Electrónica)
INSERT INTO products (name, sku, description, price, stock)
VALUES ('Laptop Dell XPS 15', 'DELL-XPS15-2026', 'Portátil de alto rendimiento para profesionales', 1299.99, 15);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Monitor LG 27 pulgadas 4K', 'LG-MON27-4K', 'Monitor ultra HD con tecnología IPS', 449.99, 8);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Webcam Logitech C920', 'LOG-C920-HD', 'Cámara web Full HD para videoconferencias', 79.99, 25);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Altavoces JBL Studio 5', 'JBL-STUDIO5', 'Sistema de altavoces estéreo profesionales', 299.99, 12);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Router WiFi 6 ASUS', 'ASUS-AX6000', 'Router inalámbrico WiFi 6 de alto rendimiento', 189.99, 20);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Disco SSD Samsung 1TB', 'SAMSUNG-1TB-SSD', 'Unidad de estado sólido rápida y confiable', 89.99, 50);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Fuente de Alimentación Corsair 850W', 'CORSAIR-850W', 'Fuente de poder modular certificada 80 Plus Gold', 129.99, 18);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Tarjeta Gráfica RTX 4070', 'NVIDIA-RTX4070', 'Tarjeta gráfica profesional para diseño 3D', 599.99, 5);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Procesador Intel i9-13900K', 'INTEL-I913900K', 'Procesador de última generación de alto rendimiento', 589.99, 10);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Memoria RAM Corsair 32GB', 'CORSAIR-32GBRAM', 'Memoria DDR5 rápida para gaming y trabajo', 159.99, 35);

-- PRODUCTOS CON 2 CATEGORÍAS (Categorías 2 y 3: Informática + Periféricos)
INSERT INTO products (name, sku, description, price, stock)
VALUES ('Ratón Gaming Razer DeathAdder', 'RAZER-DEATHADDER', 'Ratón gaming con sensor 20000 DPI', 69.99, 40);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Teclado Mecánico RGB Cherry MX', 'CHERRY-MX-RGB', 'Teclado mecánico con switches Cherry MX', 159.99, 22);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Mousepad SteelSeries QcK XL', 'STEELSERIES-QCKXL', 'Mousepad grande para gaming profesional', 49.99, 60);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Docking Station USB-C Lenovo', 'LENOVO-DOCKING-C', 'Estación de acoplamiento para portátiles USB-C', 129.99, 15);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Cable HDMI 2.1 de 2 metros', 'HDMI21-2M', 'Cable HDMI 2.1 de alta velocidad', 19.99, 100);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Hub USB 3.0 de 7 puertos', 'USB30-HUB7', 'Concentrador USB con alimentación externa', 39.99, 45);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Adaptador DisplayPort a HDMI', 'DISPLAYPORT-HDMI', 'Convertidor de video DisplayPort a HDMI', 29.99, 55);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Auriculares Sony WH-1000XM5', 'SONY-WH1000XM5', 'Auriculares con cancelación de ruido activa', 379.99, 12);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Funda de Portátil Targus 15.6', 'TARGUS-CASE15', 'Funda protectora para portátiles hasta 15.6 pulgadas', 39.99, 70);

INSERT INTO products (name, sku, description, price, stock)
VALUES ('Ventilador RGB Corsair LL120', 'CORSAIR-LL120', 'Ventilador RGB para cajas de PC', 49.99, 80);

-- ============================================
-- ASIGNAR CATEGORÍAS A PRODUCTOS
-- ============================================

-- Productos 1-10: 1 categoría (Categoría 1 - Electrónica)
INSERT INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO product_category (product_id, category_id) VALUES (2, 1);
INSERT INTO product_category (product_id, category_id) VALUES (3, 1);
INSERT INTO product_category (product_id, category_id) VALUES (4, 1);
INSERT INTO product_category (product_id, category_id) VALUES (5, 1);
INSERT INTO product_category (product_id, category_id) VALUES (6, 1);
INSERT INTO product_category (product_id, category_id) VALUES (7, 1);
INSERT INTO product_category (product_id, category_id) VALUES (8, 1);
INSERT INTO product_category (product_id, category_id) VALUES (9, 1);
INSERT INTO product_category (product_id, category_id) VALUES (10, 1);

-- Productos 11-20: 2 categorías (Categorías 2 y 3 - Informática + Periféricos)
INSERT INTO product_category (product_id, category_id) VALUES (11, 2);
INSERT INTO product_category (product_id, category_id) VALUES (11, 3);

INSERT INTO product_category (product_id, category_id) VALUES (12, 2);
INSERT INTO product_category (product_id, category_id) VALUES (12, 3);

INSERT INTO product_category (product_id, category_id) VALUES (13, 2);
INSERT INTO product_category (product_id, category_id) VALUES (13, 3);

INSERT INTO product_category (product_id, category_id) VALUES (14, 2);
INSERT INTO product_category (product_id, category_id) VALUES (14, 3);

INSERT INTO product_category (product_id, category_id) VALUES (15, 2);
INSERT INTO product_category (product_id, category_id) VALUES (15, 3);

INSERT INTO product_category (product_id, category_id) VALUES (16, 2);
INSERT INTO product_category (product_id, category_id) VALUES (16, 3);

INSERT INTO product_category (product_id, category_id) VALUES (17, 2);
INSERT INTO product_category (product_id, category_id) VALUES (17, 3);

INSERT INTO product_category (product_id, category_id) VALUES (18, 2);
INSERT INTO product_category (product_id, category_id) VALUES (18, 3);

INSERT INTO product_category (product_id, category_id) VALUES (19, 2);
INSERT INTO product_category (product_id, category_id) VALUES (19, 3);

INSERT INTO product_category (product_id, category_id) VALUES (20, 2);
INSERT INTO product_category (product_id, category_id) VALUES (20, 3);

-- ============================================
-- CARRITO DE COMPRA (4 productos para cliente 1)
-- ============================================
INSERT INTO cart_items (customer_id, product_id, quantity, unit_price)
VALUES (1, 1, 1, 1299.99);

INSERT INTO cart_items (customer_id, product_id, quantity, unit_price)
VALUES (1, 11, 2, 69.99);

INSERT INTO cart_items (customer_id, product_id, quantity, unit_price)
VALUES (1, 12, 1, 159.99);

INSERT INTO cart_items (customer_id, product_id, quantity, unit_price)
VALUES (1, 18, 1, 379.99);

-- ============================================
-- LISTA DE DESEOS (3 productos para cliente 2)
-- ============================================
INSERT INTO wishlists (customer_id, name, shared)
VALUES (2, 'Mi Setup Gaming', true);

INSERT INTO wishlist_products (wishlist_id, product_id)
VALUES (1, 11);

INSERT INTO wishlist_products (wishlist_id, product_id)
VALUES (1, 12);

INSERT INTO wishlist_products (wishlist_id, product_id)
VALUES (1, 20);

-- ============================================
-- PEDIDO (1 pedido con 3 productos para cliente 3)
-- ============================================
INSERT INTO orders (customer_id, order_date, order_total)
VALUES (3, CURRENT_TIMESTAMP, 0);

-- El total se calcula sumando los items
INSERT INTO order_items (order_id, product_id, quantity, unit_price)
VALUES (1, 5, 1, 189.99);

INSERT INTO order_items (order_id, product_id, quantity, unit_price)
VALUES (1, 13, 2, 49.99);

INSERT INTO order_items (order_id, product_id, quantity, unit_price)
VALUES (1, 14, 1, 129.99);

-- Actualizar total del pedido: 189.99 + (49.99 * 2) + 129.99 = 549.96
UPDATE orders SET order_total = 549.96 WHERE order_id = 1;

-- ============================================
-- ENVÍO (1 envío para el pedido 1)
-- ============================================
INSERT INTO shipments (shipment_date, zip_code, address, city, state, country)
VALUES (CURRENT_TIMESTAMP, '46001', 'Calle de la Paz 123', 'Valencia', 'Valencia', 'España');

-- Asociar el envío al pedido
UPDATE shipments SET order_id = 1 WHERE shipment_id = 1;






