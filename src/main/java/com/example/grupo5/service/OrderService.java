package com.example.grupo5.service;

import com.example.grupo5.Exceptions.BadRequestException;
import com.example.grupo5.Exceptions.NotFoundException;
import com.example.grupo5.dto.OrderDTO;
import com.example.grupo5.dto.OrderItemDTO;
import com.example.grupo5.dto.ShipmentCreateDTO;
import com.example.grupo5.dto.ShipmentDTO;
import com.example.grupo5.entity.CartItem;
import com.example.grupo5.entity.Customer;
import com.example.grupo5.entity.Order;
import com.example.grupo5.entity.OrderItem;
import com.example.grupo5.entity.Product;
import com.example.grupo5.entity.Shipment;
import com.example.grupo5.repository.CartItemRepository;
import com.example.grupo5.repository.CustomerRepository;
import com.example.grupo5.repository.OrderItemRepository;
import com.example.grupo5.repository.OrderRepository;
import com.example.grupo5.repository.ProductRepository;
import com.example.grupo5.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        ShipmentRepository shipmentRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository,
                        CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shipmentRepository = shipmentRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public OrderDTO createFromCart(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        List<CartItem> cartItems = cartItemRepository.findByCustomerIdOrderByProductNameAsc(customerId);
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty for customer id: " + customerId);
        }

        // Validar stock disponible para todos los productos
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new NotFoundException("Product not found with id: " + cartItem.getProduct().getId()));

            if (product.getStock() < cartItem.getQuantity()) {
                throw new BadRequestException("Stock insuficiente para el producto: " + product.getName() +
                        " (disponible: " + product.getStock() + ", requerido: " + cartItem.getQuantity() + ")");
            }
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setTotal(0.0);
        order = orderRepository.save(order);

        double total = 0.0;
        List<OrderItemDTO> itemDTOs = new java.util.ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItemRepository.save(orderItem);

            total += product.getPrice() * cartItem.getQuantity();
            itemDTOs.add(new OrderItemDTO(product.getId(), product.getName(), cartItem.getQuantity(), product.getPrice()));
        }

        order.setTotal(total);
        order = orderRepository.save(order);

        cartItemRepository.deleteByCustomerId(customerId);

        return new OrderDTO(order.getId(), customerId, order.getDate(), itemDTOs, total, order.getShipment() != null ? order.getShipment().getId() : null);
    }

    @Transactional
    public ShipmentDTO createShipment(int orderId, ShipmentCreateDTO dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));

        if (order.getShipment() != null) {
            throw new BadRequestException("El pedido " + orderId + " ya había sido enviado");
        }

        Shipment shipment = new Shipment();
        shipment.setAddress(dto.address());
        shipment.setCity(dto.city());
        shipment.setZipCode(dto.zipCode());
        shipment.setState(dto.state());
        shipment.setCountry(dto.country());
        shipment.setOrder(order);

        Shipment saved = shipmentRepository.save(shipment);
        order.setShipment(saved);
        orderRepository.save(order);

        return new ShipmentDTO(
                saved.getId(),
                orderId,
                saved.getDate(),
                saved.getAddress(),
                saved.getCity(),
                saved.getZipCode(),
                saved.getState(),
                saved.getCountry()
        );
    }
}
