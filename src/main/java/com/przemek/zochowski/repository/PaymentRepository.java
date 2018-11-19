package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.EPayment;
import com.przemek.zochowski.model.Payment;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Optional;

public interface PaymentRepository extends GenericRepository<Payment> {
    Optional<Payment> findByName(String name);
}
