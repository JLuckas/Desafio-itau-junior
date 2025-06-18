package com.desafioitaujunior.transacao_api.business.service;

import com.desafioitaujunior.transacao_api.business.services.EstatisticasService;
import com.desafioitaujunior.transacao_api.business.services.TransacaoService;
import com.desafioitaujunior.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.desafioitaujunior.transacao_api.controller.dtos.TransacaoRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticasServiceTest {

    @InjectMocks
    private EstatisticasService service;

    @Mock
    private TransacaoService transacaoService;

    TransacaoRequestDTO transacaoRequestDTO;
    EstatisticasResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        transacaoRequestDTO = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
        responseDTO = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void calcularEstatisticasComSucesso() {
        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.singletonList(transacaoRequestDTO));

        EstatisticasResponseDTO responseDTO = service.calcularEstatisticas(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);
        Assertions.assertThat(responseDTO).usingRecursiveComparison().isEqualTo(responseDTO);
    }

    @Test
    void calcularEstatisticasSemOcorrencia() {
        EstatisticasResponseDTO responseEsperado =  new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);

        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.EMPTY_LIST);
        EstatisticasResponseDTO responseDTO = service.calcularEstatisticas(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);
        Assertions.assertThat(responseDTO).usingRecursiveComparison().isEqualTo(responseEsperado);
    }
}
