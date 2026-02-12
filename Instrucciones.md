Perfecto, he leído el PDF completo. Ahora voy a crear un documento Markdown estructurado con todos los requisitos, especificaciones técnicas y detalles del proyecto para que puedas pasárselo claramente a una IA.

# ACTIVIDAD UT03 - Spring Data JPA / ORM / REST API

## 📋 INFORMACIÓN GENERAL

**Asignatura:** Acceso a Datos  
**Unidad:** UT 03 - Herramientas de mapeo objeto-relacional  
**Tecnologías:** Spring Boot, Spring Web, Spring Data JPA, H2 Database  
**Enfoque:** Code First (las entidades Java generan la estructura de BD)  
**Tipo:** Actividad en equipo (3 personas)

***

## 🎯 OBJETIVOS

1. Ampliar conocimientos sobre ORM en Java, específicamente Spring Data JPA
2. Practicar desarrollo de modelos de clases con enfoque "code first"
3. Practicar creación de relaciones entre entidades y navegación entre ellas
4. Practicar uso de repositorios de Spring Data
5. Practicar personalización de consultas (derivadas, JPQL, SQL nativo)
6. Practicar creación de servicios REST/JSON con Spring Web

***

## 🛠️ REQUISITOS TÉCNICOS DEL PROYECTO

### Configuración Base
- **Lenguaje:** Java 21
- **SDK:** Amazon Corretto 21 (OBLIGATORIO, no usar OpenJDK ni Oracle SDK)
- **Build Tool:** Maven/Gradle
- **Framework:** Spring Boot

### Dependencias Obligatorias
- ✅ Spring Web (servicios REST)
- ✅ Spring Data JPA
- ✅ H2 Database

### Dependencias Recomendadas
- 📦 Lombok
- 📦 Spring Boot DevTools

### Estructura de Paquetes Mínima
```
src/main/java/com.ejemplo.proyecto/
├── entities/        (Entidades JPA)
├── repositories/    (Repositorios Spring Data)
├── services/        (Lógica de negocio)
├── controllers/     (Controladores REST)
└── dto/            (Data Transfer Objects - si necesario)
```

***

## 🗄️ MODELO DE BASE DE DATOS

### Mapeo de Tipos H2 ↔ Java

| Tipo H2                | Tipo Java         |
|------------------------|-------------------|
| `INTEGER`              | `Integer`         |
| `CHARACTER VARYING`    | `String`          |
| `TIMESTAMP`            | `LocalDateTime`   |
| `DOUBLE PRECISION(nn)` | `Double`          |

### Tablas del Sistema

#### 1️⃣ **CUSTOMERS** (Clientes)
```
Tabla: customers
├── customer_id (INTEGER) PK - AUTO_INCREMENT
├── name (VARCHAR) NOT NULL
├── email (VARCHAR) NOT NULL
├── phone_number (VARCHAR) NULLABLE ⚠️
└── address (VARCHAR) NOT NULL
```

#### 2️⃣ **CATEGORIES** (Categorías de productos)
```
Tabla: categories
├── category_id (INTEGER) PK - AUTO_INCREMENT
├── name (VARCHAR) NOT NULL
└── description (VARCHAR) NULLABLE ⚠️
```

#### 3️⃣ **PRODUCTS** (Productos)
```
Tabla: products
├── product_id (INTEGER) PK - AUTO_INCREMENT
├── name (VARCHAR) NOT NULL
├── description (VARCHAR) NULLABLE ⚠️
├── price (DOUBLE PRECISION) NOT NULL
└── stock (INTEGER) NOT NULL
```

#### 4️⃣ **PRODUCT_CATEGORIES** (Relación Muchos a Muchos)
```
Tabla: product_categories
├── product_id (INTEGER) FK → products.product_id - NOT NULL
└── category_id (INTEGER) FK → categories.category_id - NOT NULL
└── UNIQUE INDEX (product_id, category_id)
```

#### 5️⃣ **CART_ITEMS** (Carrito de compra)
```
Tabla: cart_items
├── cart_item_id (INTEGER) PK - AUTO_INCREMENT
├── customer_id (INTEGER) FK → customers.customer_id - NOT NULL
├── product_id (INTEGER) FK → products.product_id - NOT NULL
├── quantity (INTEGER) NOT NULL
└── UNIQUE INDEX (customer_id, product_id) ⚠️
   (No se puede añadir el mismo producto dos veces, se incrementa quantity)
```

#### 6️⃣ **WISHLISTS** (Listas de deseos)
```
Tabla: wishlists
├── wishlist_id (INTEGER) PK - AUTO_INCREMENT
├── customer_id (INTEGER) FK → customers.customer_id - NOT NULL
├── name (VARCHAR) NOT NULL
└── is_shared (BOOLEAN/INTEGER) NOT NULL
```

#### 7️⃣ **WISHLIST_ITEMS** (Productos en listas de deseos)
```
Tabla: wishlist_items
├── wishlist_item_id (INTEGER) PK - AUTO_INCREMENT
├── wishlist_id (INTEGER) FK → wishlists.wishlist_id - NOT NULL
└── product_id (INTEGER) FK → products.product_id - NOT NULL
```

#### 8️⃣ **ORDERS** (Pedidos)
```
Tabla: orders
├── order_id (INTEGER) PK - AUTO_INCREMENT
├── customer_id (INTEGER) FK → customers.customer_id - NOT NULL
├── order_date (TIMESTAMP) NOT NULL - DEFAULT CURRENT_TIMESTAMP ⚠️
└── shipment_id (INTEGER) FK → shipments.shipment_id - NULLABLE ⚠️
   (Relación 1 a 0..1: un pedido puede no estar enviado aún)
```

#### 9️⃣ **ORDER_ITEMS** (Productos en pedidos)
```
Tabla: order_items
├── order_item_id (INTEGER) PK - AUTO_INCREMENT
├── order_id (INTEGER) FK → orders.order_id - NOT NULL
├── product_id (INTEGER) FK → products.product_id - NOT NULL
├── quantity (INTEGER) NOT NULL
├── unit_price (DOUBLE PRECISION) NOT NULL
└── UNIQUE INDEX (order_id, product_id) ⚠️
   (No se puede añadir el mismo producto dos veces al mismo pedido)
```

#### 🔟 **SHIPMENTS** (Envíos)
```
Tabla: shipments
├── shipment_id (INTEGER) PK - AUTO_INCREMENT
├── shipment_date (TIMESTAMP) NOT NULL - DEFAULT CURRENT_TIMESTAMP ⚠️
├── address (VARCHAR) NOT NULL
├── city (VARCHAR) NOT NULL
├── postal_code (VARCHAR) NOT NULL
└── state (VARCHAR) NOT NULL
```

***

## ⚠️ RESTRICCIONES Y REGLAS ESPECIALES

### Columnas Opcionales (NULLABLE)
Solo estas columnas pueden ser NULL:
- `customers.phone_number`
- `products.description`
- `categories.description`
- `orders.shipment_id` (hasta que se envíe el pedido)

### Índices Únicos Compuestos
- `cart_items`: (customer_id, product_id) - Evita duplicados, incrementa quantity
- `order_items`: (order_id, product_id) - Evita duplicados en pedidos
- `product_categories`: (product_id, category_id) - Evita duplicados en categorización

### Valores por Defecto
- `orders.order_date` → CURRENT_TIMESTAMP al crear
- `shipments.shipment_date` → CURRENT_TIMESTAMP al crear

### Claves Primarias
- TODAS las PKs son AUTO_INCREMENT (IDENTITY)

***

## 📝 CONFIGURACIÓN DE application.properties / application.yml

```properties
# Base de datos H2 en memoria
spring.datasource.url=jdbc:h2:mem:onlineshop;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;DATABASE_TO_LOWER=TRUE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Consola H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Diferir ejecución de data.sql hasta después de crear tablas
spring.jpa.defer-datasource-initialization=true
```

***

## 🌱 SCRIPT DE INICIALIZACIÓN (data.sql)

Crear archivo `src/main/resources/data.sql` con:

### Datos Mínimos Requeridos
- ✅ **4 categorías** mínimo
- ✅ **20 productos** mínimo:
    - 10 productos con 1 categoría
    - 10 productos con 2 categorías
- ✅ **4 clientes** mínimo
- ✅ **1 pedido** mínimo con 3 productos distintos
- ✅ **1 lista de deseos** con 3 productos
- ✅ **4 productos en carrito** de algún cliente

⚠️ **IMPORTANTE:** Usar nombres realistas (no "asdasdas" ni "lorem ipsum")

***

## 🌐 API REST - ENDPOINTS A IMPLEMENTAR

### 📦 1. PRODUCTOS (`/api/products`)

#### **GET** `/api/products/{page}/{pageSize}`
**Descripción:** Listado paginado de productos  
**Parámetros:**
- `page` (path) - Número de página
- `pageSize` (path) - Tamaño de página

**Respuesta (200 OK):**
```json
[
  {
    "productId": 1,
    "name": "Laptop Dell XPS 15",
    "description": "Portátil de alto rendimiento",
    "price": 1299.99,
    "stock": 15,
    "categories": [
      {"categoryId": 1, "name": "Electrónica"},
      {"categoryId": 3, "name": "Informática"}
    ]
  }
]
```

**Requisitos:**
- Ordenar por nombre alfabéticamente
- Incluir datos de categorías de cada producto
- Usar **consulta derivada** personalizada para paginación y ordenamiento

***

#### **GET** `/api/products/search/{query}/{page}/{pageSize}`
**Descripción:** Búsqueda de productos con paginación  
**Parámetros:**
- `query` (path) - Texto a buscar
- `page` (path) - Número de página
- `pageSize` (path) - Tamaño de página

**Respuesta (200 OK):** Mismo formato que listado

**Requisitos:**
- Buscar en `name` y `description`
- Ordenar por nombre alfabéticamente
- Usar **consulta derivada** personalizada

***

### 🛒 2. CARRITO DE COMPRA (`/api/cart`)

#### **GET** `/api/cart/{customerId}`
**Descripción:** Listar carrito de compra de un cliente  
**Parámetros:**
- `customerId` (path) - ID del cliente

**Respuesta (200 OK):**
```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 5,
      "productName": "Teclado Mecánico RGB",
      "unitPrice": 89.99,
      "quantity": 2,
      "subtotal": 179.98
    }
  ],
  "totalAmount": 179.98
}
```

**Respuesta (404 NOT FOUND):**
```json
{
  "error": "No existe el cliente con el código 999"
}
```

**Requisitos:**
- Ordenar items por nombre de producto
- Incluir importe total del carrito
- Devolver **DTO específico** con toda la información
- Usar **consulta derivada** para búsqueda y ordenamiento

***

#### **POST** `/api/cart/{customerId}`
**Descripción:** Añadir producto al carrito  
**Parámetros:**
- `customerId` (path) - ID del cliente

**Request Body:**
```json
{
  "productId": 5,
  "quantity": 2
}
```

**Respuesta (200 OK):** Mismo formato que GET del carrito

**Lógica:**
- Si el producto YA está en el carrito → incrementar quantity
- Si es nuevo → crear nuevo cart_item

***

#### **DELETE** `/api/cart/{customerId}/{productId}`
**Descripción:** Quitar producto del carrito  
**Parámetros:**
- `customerId` (path) - ID del cliente
- `productId` (path) - ID del producto

**Respuesta (200 OK):** Mismo formato que GET del carrito

**Lógica:**
- Eliminar el cart_item correspondiente
- Si el producto no estaba en el carrito → operar normalmente (sin error)

***

#### **POST** `/api/cart/empty/{customerId}`
**Descripción:** Vaciar carrito completamente  
**Parámetros:**
- `customerId` (path) - ID del cliente

**Respuesta (200 OK):** Mismo formato que GET del carrito (vacío)

**Lógica:**
- Eliminar TODOS los cart_items del cliente
- Si ya estaba vacío → operar normalmente (sin error)

***

### 💚 3. LISTAS DE DESEOS (`/api/wishlists`)

#### **GET** `/api/wishlists/list/{customerId}`
**Descripción:** Listar todas las listas de deseos de un cliente  
**Parámetros:**
- `customerId` (path) - ID del cliente

**Respuesta (200 OK):**
```json
[
  {
    "wishlistId": 1,
    "name": "Cumpleaños",
    "isShared": true
  },
  {
    "wishlistId": 2,
    "name": "Gaming Setup",
    "isShared": false
  }
]
```

***

#### **PUT** `/api/wishlists/{customerId}`
**Descripción:** Crear nueva lista de deseos  
**Parámetros:**
- `customerId` (path) - ID del cliente

**Request Body:**
```json
{
  "name": "Navidad 2026",
  "isShared": false
}
```

**Respuesta (201 CREATED):**
```json
{
  "wishlistId": 3,
  "customerId": 1,
  "name": "Navidad 2026",
  "isShared": false
}
```

***

#### **DELETE** `/api/wishlists/{wishlistId}`
**Descripción:** Eliminar lista de deseos  
**Parámetros:**
- `wishlistId` (path) - ID de la lista

**Respuesta (200 OK):** Sin contenido

**Respuesta (400 BAD REQUEST):**
```json
{
  "error": "No se puede eliminar la lista porque contiene productos"
}
```

**Lógica:**
- Solo se puede eliminar si está vacía (sin wishlist_items)

***

#### **GET** `/api/wishlists/{wishlistId}`
**Descripción:** Listar productos de una lista de deseos  
**Parámetros:**
- `wishlistId` (path) - ID de la lista

**Respuesta (200 OK):**
```json
{
  "wishlistId": 1,
  "name": "Cumpleaños",
  "products": [
    {
      "productId": 8,
      "name": "Monitor 4K 27 pulgadas",
      "price": 449.99
    }
  ]
}
```

***

### 📦 4. PEDIDOS (`/api/orders`)

#### **POST** `/api/orders/create/{customerId}`
**Descripción:** Completar pedido (convertir carrito en pedido)  
**Parámetros:**
- `customerId` (path) - ID del cliente

**Respuesta (201 CREATED):**
```json
{
  "orderId": 5,
  "customerId": 1,
  "orderDate": "2026-02-12T13:45:00",
  "items": [
    {
      "productId": 3,
      "productName": "Ratón Gaming",
      "quantity": 1,
      "unitPrice": 45.99
    }
  ],
  "totalAmount": 45.99,
  "shipmentId": null
}
```

**Respuesta (400 BAD REQUEST):**
```json
{
  "error": "Stock insuficiente para el producto: Ratón Gaming (disponible: 0, requerido: 1)"
}
```

**LÓGICA CRÍTICA (TRANSACCIONAL ⚠️):**
1. **Validar stock:** Verificar que hay suficientes unidades de TODOS los productos
2. **Crear order:** Generar registro en `orders` con `order_date = CURRENT_TIMESTAMP`
3. **Crear order_items:** Copiar cart_items → order_items con precios actuales
4. **Reducir stock:** Actualizar `products.stock` para cada producto
5. **Vaciar carrito:** Eliminar todos los cart_items del cliente

**⚠️ IMPORTANTE:**
- TODO en UNA transacción (@Transactional)
- Si falla cualquier paso → ROLLBACK completo
- Los precios se obtienen de la BD (no del request)
- El `order_date` se establece automáticamente

***

#### **POST** `/api/orders/send/{orderId}`
**Descripción:** Enviar pedido (crear shipment)  
**Parámetros:**
- `orderId` (path) - ID del pedido

**Request Body:**
```json
{
  "address": "Calle Gran Vía 28",
  "city": "Madrid",
  "postalCode": "28013",
  "state": "Madrid"
}
```

**Respuesta (201 CREATED):**
```json
{
  "shipmentId": 3,
  "orderId": 5,
  "shipmentDate": "2026-02-12T14:00:00",
  "address": "Calle Gran Vía 28",
  "city": "Madrid",
  "postalCode": "28013",
  "state": "Madrid"
}
```

**Respuesta (400 BAD REQUEST):**
```json
{
  "error": "El pedido 5 ya había sido enviado"
}
```

**LÓGICA:**
1. Verificar que el pedido existe
2. Verificar que `orders.shipment_id` es NULL (no enviado previamente)
3. Crear registro en `shipments` con `shipment_date = CURRENT_TIMESTAMP`
4. Actualizar `orders.shipment_id` con el nuevo shipment

***

## 📊 CÓDIGOS DE RESPUESTA HTTP

### Códigos a Usar Correctamente

| Código | Situación |
|--------|-----------|
| **200 OK** | Operación exitosa (GET, DELETE, POST que no crea recurso) |
| **201 CREATED** | Recurso creado exitosamente (POST crear pedido, PUT crear wishlist) |
| **400 BAD REQUEST** | Error del cliente (pedido ya enviado, stock insuficiente, lista no vacía) |
| **404 NOT FOUND** | Recurso no existe (cliente no encontrado, producto no existe) |

### Ejemplos de Mensajes de Error

```json
// 404 - Cliente no encontrado
{"error": "No existe el cliente con el código 999"}

// 400 - Pedido ya enviado
{"error": "El pedido 5 ya había sido enviado"}

// 400 - Stock insuficiente
{"error": "Stock insuficiente para el producto: Laptop Dell (disponible: 2, requerido: 5)"}

// 400 - Lista de deseos no vacía
{"error": "No se puede eliminar la lista porque contiene 3 productos"}
```

***

## 🔧 TÉCNICAS A UTILIZAR Y DEMOSTRAR

### Variedad de Técnicas (OBLIGATORIO)
Usar DIFERENTES técnicas en distintos servicios:
- ✅ Métodos de repositorio predefinidos (`findById`, `save`, `delete`)
- ✅ Consultas derivadas (`findByCustomerIdOrderByProductName`)
- ✅ Consultas JPQL (`@Query` con JPQL)
- ✅ Consultas SQL nativo (`@Query(nativeQuery = true)`)
- ✅ Transacciones (`@Transactional` en crear pedido)
- ✅ DTOs para respuestas estructuradas

### Documentar en Cuaderno de Trabajo
Para cada servicio, indicar:
- Qué técnica se usó
- Por qué se eligió esa técnica
- En qué clase/método se implementó

***

## 📄 ENTREGABLES

### 1. Proyecto Completo (ZIP)
- Incluir TODO el proyecto (no solo `/src`)
- Debe incluir `pom.xml` o `build.gradle`
- Debe incluir `application.properties`
- Debe incluir `data.sql`

### 2. Cuaderno de Trabajo (PDF)
**Contenido mínimo:**
- 📌 Portada con nombres del equipo
- 📌 Índice
- 📌 Explicación de la estructura del proyecto
- 📌 Descripción de las entidades creadas
- 📌 Explicación de las relaciones JPA
- 📌 Técnicas utilizadas en cada servicio:
    - Clase y método donde se usó
    - Qué técnica (consulta derivada, JPQL, SQL nativo, etc.)
    - Por qué se eligió esa técnica
- 📌 Problemas encontrados y soluciones aplicadas
- 📌 Capturas de pantalla de:
    - Consola H2 mostrando tablas creadas
    - Pruebas de endpoints en Postman/Insomnia
    - Logs de SQL generado

**Calidad del documento:**
- Redacción clara y correcta
- Sin faltas de ortografía
- Formato profesional

***

## ✅ CHECKLIST DE VERIFICACIÓN

### Base de Datos
- [ ] Nombres de tablas coinciden exactamente con el modelo
- [ ] Nombres de columnas coinciden exactamente
- [ ] Tipos de datos correctos (INTEGER, VARCHAR, TIMESTAMP, DOUBLE)
- [ ] Columnas obligatorias (NOT NULL) correctas
- [ ] Columnas opcionales solo las 3 especificadas
- [ ] Todas las PKs son AUTO_INCREMENT
- [ ] Relaciones (FKs) creadas correctamente
- [ ] Relación `orders.shipment_id` es opcional (nullable)
- [ ] Índice único en `cart_items(customer_id, product_id)`
- [ ] Índice único en `order_items(order_id, product_id)`
- [ ] Default CURRENT_TIMESTAMP en `orders.order_date`
- [ ] Default CURRENT_TIMESTAMP en `shipments.shipment_date`

### Datos de Prueba
- [ ] Al menos 4 categorías con nombres realistas
- [ ] Al menos 20 productos con nombres realistas
- [ ] 10 productos con 1 categoría
- [ ] 10 productos con 2 categorías
- [ ] Al menos 4 clientes
- [ ] Al menos 1 pedido con 3 productos
- [ ] Al menos 1 lista de deseos con 3 productos
- [ ] Al menos 4 productos en algún carrito

### Endpoints - Productos
- [ ] GET `/api/products/{page}/{pageSize}` devuelve lista paginada ordenada
- [ ] Incluye categorías de cada producto
- [ ] GET `/api/products/search/{query}/{page}/{pageSize}` busca en nombre y descripción
- [ ] Ambos usan consultas derivadas personalizadas

### Endpoints - Carrito
- [ ] GET `/api/cart/{customerId}` devuelve DTO con items y total
- [ ] POST `/api/cart/{customerId}` añade o incrementa quantity
- [ ] DELETE `/api/cart/{customerId}/{productId}` elimina producto
- [ ] POST `/api/cart/empty/{customerId}` vacía carrito
- [ ] 404 si cliente no existe
- [ ] 200 si carrito vacío (no es error)

### Endpoints - Listas de Deseos
- [ ] GET `/api/wishlists/list/{customerId}` lista todas las listas
- [ ] PUT `/api/wishlists/{customerId}` crea nueva lista (201)
- [ ] DELETE `/api/wishlists/{wishlistId}` elimina si está vacía
- [ ] 400 si lista no vacía al intentar eliminar
- [ ] GET `/api/wishlists/{wishlistId}` lista productos de la lista

### Endpoints - Pedidos
- [ ] POST `/api/orders/create/{customerId}` convierte carrito en pedido
- [ ] Verifica stock antes de crear
- [ ] Crea order + order_items en transacción
- [ ] Reduce stock de productos
- [ ] Vacía carrito tras crear pedido
- [ ] 400 si stock insuficiente
- [ ] POST `/api/orders/send/{orderId}` crea shipment
- [ ] 400 si pedido ya enviado
- [ ] Asocia shipment_id al order

### Códigos HTTP
- [ ] 200 OK para operaciones exitosas
- [ ] 201 CREATED para recursos creados
- [ ] 400 BAD REQUEST para errores de lógica de negocio
- [ ] 404 NOT FOUND para recursos inexistentes
- [ ] Mensajes de error descriptivos

### Técnicas Utilizadas
- [ ] Consultas derivadas
- [ ] Consultas JPQL
- [ ] Consultas SQL nativo
- [ ] Transacciones (@Transactional)
- [ ] DTOs personalizados
- [ ] Variedad demostrada en el cuaderno

### Documentación
- [ ] Cuaderno con portada e índice
- [ ] Estructura del proyecto explicada
- [ ] Técnicas documentadas por servicio
- [ ] Capturas de pantalla incluidas
- [ ] Redacción clara y profesional

***

## 🎓 RECOMENDACIONES FINALES

1. **Verificar modelo en H2 Console** antes de implementar servicios
2. **Probar endpoints con Postman/Insomnia** progresivamente
3. **Implementar transacciones correctamente** en crear pedido
4. **Manejar errores con ResponseEntity** y códigos HTTP apropiados
5. **Documentar mientras desarrollas** (no al final)
6. **Usar Lombok** para reducir boilerplate (@Data, @Builder, etc.)
7. **Habilitar logs de SQL** para depuración
8. **Probar casos límite:** carrito vacío, stock cero, cliente inexistente

***

## 📞 NOTAS ADICIONALES

- **Base de datos en memoria:** Se reinicia cada vez que arrancas la aplicación
- **Script data.sql:** Se ejecuta automáticamente al arrancar
- **Consola H2:** Accesible en `http://localhost:8080/h2-console`
- **Convenciones:** Respetar nombres Java (camelCase) y SQL (snake_case con anotaciones)

***

**Este documento contiene TODA la información estructurada del PDF para pasarla a una IA y desarrollar el proyecto correctamente. Todos los requisitos, tablas, endpoints, validaciones y restricciones están especificados.**