package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}