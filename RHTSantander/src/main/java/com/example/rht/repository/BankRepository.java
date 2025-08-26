package com.example.rht.repository;

import com.example.rht.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
	Optional<Bank> findBySwift(String swift);

	List<Bank> findByActive(boolean active);
}