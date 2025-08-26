package com.example.rht.controller;

import com.example.rht.dto.BankCreateRequest;
import com.example.rht.dto.BankDTO;
import com.example.rht.service.BankService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankController.class)
class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBank_success() throws Exception {
        BankCreateRequest req = new BankCreateRequest("Banco nombre", "Banco dir", "ABCDEFGH", "AR", true);
        BankDTO dto = new BankDTO(UUID.randomUUID(), "Banco nombre", "Banco dir", "ABCDEFGH", "AR", true, null, null);

        when(service.create(any())).thenReturn(dto);

        mockMvc.perform(post("/api/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banco nombre"));
    }

    @Test
    void listBanks_success() throws Exception {
        BankDTO dto = new BankDTO(UUID.randomUUID(), "Banco nombre", "Banco dir", "ABCDEFGH", "AR", true, null, null);
        when(service.list(null)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/banks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Banco nombre"));
    }

}
