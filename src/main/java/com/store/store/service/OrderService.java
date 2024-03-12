package com.store.store.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.store.store.dto.RegisterOrderDto;
import com.store.store.model.Order;
import com.store.store.model.User;
import com.store.store.model.Book;
import com.store.store.model.CategoryName;
import com.store.store.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookService bookService;
    // private final OrdersStatusRepository ordersCategoryRepository;

    public OrderService(OrderRepository orderRepository, BookService bookService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
    }

    @Transactional
    public Order createOrder(RegisterOrderDto registerOrderDto, User user) {
        Order orderData = new Order(registerOrderDto.getAmount(),
                registerOrderDto.getQuantity(), user);

        Set<Long> productIds = registerOrderDto.getProductIds();
        Set<Book> productNames = new HashSet<>();
        for (Long category : productIds) {
            Book categoryName = bookService
                    .getBookById(category)
                    .orElseThrow(() -> new DataAccessResourceFailureException("Book not found."));
            productNames.add(categoryName);
        }
        orderData.setBooks(productNames);
        return orderRepository.save(orderData);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Long id, Order OrderDetails) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setTotalAmount(OrderDetails.getTotalAmount());
            existingOrder.setQuantity(OrderDetails.getQuantity());
            return orderRepository.save(existingOrder);
        }
        return null;
    }
}
