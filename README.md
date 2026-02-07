# SprinBootLvl1

Minimal notes for the wishlist and order endpoints added.

## Endpoints

### Wishlists
- `GET /api/wishlists/list/{customerId}`: List all wishlists for a customer.
- `PUT /api/wishlists/{customerId}`: Create a wishlist. Body: `{ "name": "...", "shared": true }`.
- `DELETE /api/wishlists/{wishlistId}`: Delete a wishlist (only if empty).
- `GET /api/wishlists/{wishlistId}`: List products in a wishlist.

### Orders
- `POST /api/orders/create/{customerId}`: Create an order from the cart.
- `POST /api/orders/send/{orderId}`: Create a shipment. Body: `{ "zipCode": "...", "address": "...", "city": "...", "state": "...", "country": "..." }`.

### Cart
- `GET /api/cart/{customerId}`: List cart items with total.
- `POST /api/cart/{customerId}`: Add to cart. Body: `{ "productId": 1, "quantity": 2 }`.
- `DELETE /api/cart/{customerId}/{productId}`: Remove a product from the cart.
- `POST /api/cart/empty/{customerId}`: Empty the cart.

## Run tests

```bash
./mvnw test
```
