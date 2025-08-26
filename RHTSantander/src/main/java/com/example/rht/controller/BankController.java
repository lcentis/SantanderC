package com.example.rht.controller;

import com.example.rht.dto.*;
import com.example.rht.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

/** expongo el servicio /api/banks*/
@RestController
@RequestMapping("/api/banks")
public class BankController {
	private final BankService service;
	private final WebClient webClient;

	public BankController(BankService service, WebClient webClient) {
		this.service = service;
		this.webClient = webClient;
	}

	/** creo uno nuevo */
	@PostMapping
	public ResponseEntity<BankDTO> create(@Valid @RequestBody BankCreateRequest req) {
		return ResponseEntity.ok(service.create(req));
	}

	/** devuelvo lista */
	@GetMapping
	public ResponseEntity<List<BankDTO>> list(@RequestParam(required = false) Boolean active) {
		return ResponseEntity.ok(service.list(active));
	}

	/** devuelvo uno por id */
	@GetMapping("/{id}")
	public ResponseEntity<BankDTO> get(@PathVariable UUID id) {
		return ResponseEntity.ok(service.get(id));
	}

	/** borro uno por id */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	/** Endpoint que se llama a sí mismo */
	@GetMapping("/proxy/active-count")
	public ResponseEntity<Map<String, Object>> activeCountProxy() {
		List<?> activeBanks = webClient.get().uri("/api/banks?active=true").retrieve().bodyToFlux(Object.class)
				.collectList().block();

		Map<String, Object> result = new HashMap<>();
		result.put("activeCount", activeBanks == null ? 0 : activeBanks.size());
		return ResponseEntity.ok(result);
	}

}
