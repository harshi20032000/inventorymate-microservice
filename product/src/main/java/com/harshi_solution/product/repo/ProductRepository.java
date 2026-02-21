package com.harshi_solution.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.product.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
