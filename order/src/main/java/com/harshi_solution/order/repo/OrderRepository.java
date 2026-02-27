package com.harshi_solution.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.order.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
