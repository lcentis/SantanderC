package com.example.rht.service;

import com.example.rht.dto.*;
import com.example.rht.entity.Bank;
import com.example.rht.exception.NotFoundException;
import com.example.rht.repository.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BankService {
	private final BankRepository repo;

	public BankService(BankRepository repo) {
		this.repo = repo;
	}

	private static BankDTO toDTO(Bank b) {
		return new BankDTO(b.getId(), b.getName(), b.getAddress(), b.getSwift(), b.getCountry(), b.isActive(), b.getCreatedAt(),
				b.getUpdatedAt());
	}

	@Transactional
	public BankDTO create(BankCreateRequest req) {
		repo.findBySwift(req.swift()).ifPresent(b -> {
			throw new IllegalStateException("Ya existe un banco con SWIFT " + req.swift());
		});
		Bank b = new Bank();
		b.setName(req.name());
		b.setAddress(req.address());
		b.setSwift(req.swift());
		b.setCountry(req.country());
		if (req.active() != null)
			b.setActive(req.active());
		return toDTO(repo.save(b));
	}

	@Transactional(readOnly = true)
	public List<BankDTO> list(Boolean active) {
		return (active == null ? repo.findAll() : repo.findByActive(active)).stream().map(BankService::toDTO).toList();
	}

	@Transactional(readOnly = true)
	public BankDTO get(UUID id) {
		return toDTO(find(id));
	}

	@Transactional
	public void delete(UUID id) {
		repo.delete(find(id));
	}

	private Bank find(UUID id) {
		return repo.findById(id).orElseThrow(() -> new NotFoundException("Banco " + id + " no encontrado"));
	}

}
