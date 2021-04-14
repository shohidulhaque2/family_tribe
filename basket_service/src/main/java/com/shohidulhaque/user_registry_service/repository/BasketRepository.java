package com.shohidulhaque.user_registry_service.repository;

import com.shohidulhaque.user_registry_service.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BasketRepository extends JpaRepository<Basket, Long> {


}
