package com.desafioitaujunior.transacao_api.business.service;

import com.desafioitaujunior.transacao_api.business.services.TransacaoService;
import com.desafioitaujunior.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.desafioitaujunior.transacao_api.infrastructure.exceptions.UnprocessableEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {
    @InjectMocks
    private TransacaoService transacaoService;

    TransacaoRequestDTO transacaoRequestDTO;

    @BeforeEach
    void setUp() {
        transacaoRequestDTO = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
    }

    @Test
    void deveAdicionarTransacaoComSucesso() {

        transacaoService.adicionarTransacoes(transacaoRequestDTO);

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(6000);

        Assertions.assertThat(transacoes).hasSize(1);
    }

    @Test
    void deveLancarExcecaoCasoValorSejaNegativo() {
        UnprocessableEntity exception = assertThrows(UnprocessableEntity.class,
                () -> transacaoService.adicionarTransacoes(new TransacaoRequestDTO(-1.0, OffsetDateTime.now())));

        assertEquals("Valor não pode ser menor que 0", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoCasoDataHoraSejaMaiorQueAtual() {
        UnprocessableEntity exception = assertThrows(UnprocessableEntity.class,
                () -> transacaoService.adicionarTransacoes(new TransacaoRequestDTO(1.0, OffsetDateTime.now().plusDays(1))));

        assertEquals("Data e hora maiores que a data e hora atuais.", exception.getMessage());
    }

    @Test
    void deveLimparTransacoes() {
        transacaoService.adicionarTransacoes(transacaoRequestDTO);

        transacaoService.limparTransacoes();

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(6000);

        Assertions.assertThat(transacoes).hasSize(0);
    }

    @Test
    void deveBuscarTransacaoComSucessoDentroDoIntervalo() {

        TransacaoRequestDTO transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now().minusSeconds(65));
        transacaoService.adicionarTransacoes(transacaoRequestDTO);
        transacaoService.adicionarTransacoes(transacao);

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(60);

        assertTrue(transacoes.contains(transacaoRequestDTO));
        assertFalse(transacoes.contains(transacao));
    }
}
