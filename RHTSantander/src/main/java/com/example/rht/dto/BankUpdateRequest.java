package com.example.rht.dto;

import jakarta.validation.constraints.Pattern;

public record BankUpdateRequest(
		String name,
		String address,
		@Pattern(regexp = "^[A-Z0-9]{8}([A-Z0-9]{3})?$", message = "SWIFT debe tener 8 u 11 alfanuméricos en mayúscula") String swift,
		@Pattern(regexp = "^[A-Z]{2}$", message = "País debe ser ISO 3166-1 alfa-2") String country, 
		Boolean active) {
}