package com.desafioitaujunior.transacao_api.controller;

import com.desafioitaujunior.transacao_api.business.services.EstatisticasService;
import com.desafioitaujunior.transacao_api.controller.dtos.EstatisticasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor
public class EstatisticasController {

    private final EstatisticasService estatisticasService;


    @GetMapping
    @Operation(description = "Endpoint para buscar estatísticas de transações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro na busca de estatísticas de transações."),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor.")
    })
    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
            @RequestParam(value = "intervaloBusca", required = false, defaultValue = "60") Integer intervaloBusca) {

        return ResponseEntity.ok(
                estatisticasService.calcularEstatisticas(intervaloBusca)
        );
    }
}
