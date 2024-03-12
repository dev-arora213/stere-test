package com.store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.RegisterOrderDto;
import com.store.model.Order;
import com.store.model.User;
import com.store.service.OrderService;

import jakarta.annotation.security.RolesAllowed;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Add a order
    @PostMapping
    // @RolesAllowed("ROLE_ADMIN")
    public Order createOrder(@RequestBody RegisterOrderDto order) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(order, user);
    }

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get order by ID
    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Update order by ID
    @PutMapping("/{id}")
    // @RolesAllowed("ROLE_ADMIN")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        return orderService.updateOrder(id, orderDetails);
    }

    // Delete all orders
    @DeleteMapping
    // @RolesAllowed("ROLE_ADMIN")
    public String deleteAllOrders() {
        orderService.deleteAllOrders();
        return "All orders have been deleted successfully.";
    }

    // Delete order by ID
    @DeleteMapping("/{id}")
    // @RolesAllowed("ROLE_ADMIN")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}
