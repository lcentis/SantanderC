package com.example.rht.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record BankDTO(
		UUID id, 
		String name, 
		String address,
		String swift, 
		String country, 
		boolean active, 
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt) 
	{}
