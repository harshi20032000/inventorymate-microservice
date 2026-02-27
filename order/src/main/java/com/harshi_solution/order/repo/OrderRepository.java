package com.harshi_solution.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.order.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
