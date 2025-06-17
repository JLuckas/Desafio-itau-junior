package com.desafioitaujunior.transacao_api.controller;

import com.desafioitaujunior.transacao_api.business.services.TransacaoService;
import com.desafioitaujunior.transacao_api.controller.dtos.TransacaoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @PostMapping
    @Operation(description = "Endpoint responsável por adicionar transações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação gravada com sucesso."),
            @ApiResponse(responseCode = "422", description = "Campos não atendem os requisitor da transação"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição"),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor")
    })
    public ResponseEntity<Void> adicionarTransacao(@RequestBody TransacaoRequestDTO transacaoRequestDTO) {

        transacaoService.adicionarTransacoes(transacaoRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private final TransacaoService transacaoService;

    @DeleteMapping
    @Operation(description = "Endpoint responsável por deletar transações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações deletadas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de requisição"),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor")
    })
    public ResponseEntity<Void> removerTransacao() {
        transacaoService.limparTransacoes();

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
