package com.example.rht.dto;

import jakarta.validation.constraints.*;

public record BankCreateRequest(
		@NotBlank String name,
		@NotBlank String address,
		@NotBlank @Pattern(regexp = "^[A-Z0-9]{8}([A-Z0-9]{3})?$", message = "SWIFT debe tener 8 u 11 alfanuméricos en mayúscula") String swift,
		@NotBlank @Pattern(regexp = "^[A-Z]{2}$", message = "País debe ser ISO 3166-1 alfa-2") String country,
		Boolean active) {
}