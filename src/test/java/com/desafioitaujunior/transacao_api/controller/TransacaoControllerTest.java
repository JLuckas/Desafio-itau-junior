package com.desafioitaujunior.transacao_api.controller;

import com.desafioitaujunior.transacao_api.business.services.TransacaoService;
import com.desafioitaujunior.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.desafioitaujunior.transacao_api.infrastructure.exceptions.UnprocessableEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoControllerTest {

    @InjectMocks
    TransacaoController transacaoController;

    @Mock
    TransacaoService transacaoService;

    @Autowired
    final ObjectMapper objectMapper = new ObjectMapper();

    TransacaoRequestDTO request;

    MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        request = new TransacaoRequestDTO(20.0, OffsetDateTime.of(2025, 6, 18, 0, 29, 0, 0, ZoneOffset.UTC));
    }

    @Test
    void deveAdicionarTransacaoERetornarStatusOk() throws Exception {

        doNothing().when(transacaoService).adicionarTransacoes(request);

        mockMvc.perform(post("/transacao")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }

    @Test
    void deveGerarExcecaoAoAdicionarTransacao() throws Exception {

        doThrow(new UnprocessableEntity("Erro de requisição")).when(transacaoService).adicionarTransacoes(request);

        mockMvc.perform(post("/transacao")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is4xxClientError());
    }

    @Test
    void deveRemoverTransacaoERetornarStatusOk() throws Exception {

        doNothing().when(transacaoService).limparTransacoes();

        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());
    }
}
