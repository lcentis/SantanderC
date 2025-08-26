package com.example.rht.service.BankServiceTest;

import com.example.rht.dto.BankCreateRequest;
import com.example.rht.entity.Bank;
import com.example.rht.exception.NotFoundException;
import com.example.rht.repository.BankRepository;
import com.example.rht.service.BankService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankServiceTest {

	@Mock
	private BankRepository repository;

	@InjectMocks
	private BankService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createBank_success() {
		BankCreateRequest req = new BankCreateRequest("Banco nombre", "Banco dir", "ABCDEFGH", "AR", true);

		when(repository.findBySwift(req.swift())).thenReturn(Optional.empty());
		when(repository.save(any(Bank.class))).thenAnswer(i -> i.getArgument(0));

		var result = service.create(req);
		assertNotNull(result);
		assertEquals("Banco nombre", result.name());
	}

	@Test
	void createBank_duplicateSwift_throws() {
		BankCreateRequest req = new BankCreateRequest("Banco nombre", "Banco dir", "ABCDEFGH", "AR", true);
		when(repository.findBySwift(req.swift())).thenReturn(Optional.of(new Bank()));

		assertThrows(IllegalStateException.class, () -> service.create(req));
	}

	@Test
	void getBank_notFound_throws() {
		UUID id = UUID.randomUUID();
		when(repository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> service.get(id));
	}

}
