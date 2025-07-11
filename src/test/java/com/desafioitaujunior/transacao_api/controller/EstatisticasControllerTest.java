package com.desafioitaujunior.transacao_api.controller;

import com.desafioitaujunior.transacao_api.business.services.EstatisticasService;
import com.desafioitaujunior.transacao_api.controller.dtos.EstatisticasResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticasControllerTest {

    @InjectMocks
    EstatisticasController estatisticasController;

    @Mock
    EstatisticasService estatisticasService;

    EstatisticasResponseDTO  responseDTO;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(estatisticasController).build();
        responseDTO = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void deveCalcularEstatisticaERetornarStatusOk() throws Exception {

        when(estatisticasService.calcularEstatisticas(60)).thenReturn(responseDTO);

        mockMvc.perform(get("/estatistica")
                .param("intervaloBusca", "60")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(responseDTO.count()))
                .andExpect(jsonPath("$.sum").value(responseDTO.sum()))
                .andExpect(jsonPath("$.avg").value(responseDTO.avg()))
                .andExpect(jsonPath("$.max").value(responseDTO.max()))
                .andExpect(jsonPath("$.min").value(responseDTO.min()));
    }
}
