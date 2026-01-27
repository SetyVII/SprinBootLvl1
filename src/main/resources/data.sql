-- ==========================================
-- 1. CREAR DATOS BASE (Padres)
-- ==========================================

-- CATEGORY
INSERT INTO categories (name, description) VALUES ('Tecnología', 'Dispositivos electronicos');
INSERT INTO categories (name, description) VALUES ('Ropa', 'Vestuario general');

-- CUSTOMER
-- Nota: Si quieres ser consistente, también deberías cambiar Customer int id -> Long id, pero este SQL funcionará igual.
INSERT INTO customers (first_name, last_name, email, password, address, phone_number)
VALUES ('Juan', 'Perez', 'juan@test.com', 'pass123', 'Av. Siempre Viva 123', '+34600000000');

-- PRODUCT
-- CAMBIOS: SKU va entre comillas ('1001') porque ahora es String.
-- Precio funciona igual con Double.
INSERT INTO products (name, sku, stock, price, description)
VALUES ('Laptop Gamer', 'SKU-1001', 50, 1250.99, 'Alta potencia');

-- ==========================================
-- 2. CREAR LA WISHLIST
-- ==========================================

INSERT INTO wishlists (name, shared, customer_id)
VALUES ('Lista de Deseos', false, 1);

-- ==========================================
-- 3. RELACIONES
-- ==========================================

-- PRODUCT_CATEGORY
INSERT INTO product_category (product_id, category_id) VALUES (1, 1);

-- WISHLIST_PRODUCTS
INSERT INTO wishlist_products (wishlist_id, product_id) VALUES (1, 1);