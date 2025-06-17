package com.desafioitaujunior.transacao_api.business.services;

import com.desafioitaujunior.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.desafioitaujunior.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstatisticas(Integer intervalo) {
        log.info("Iniciando busca de estatisticas de transações pelo intervalo de tempo de " + intervalo + " segundos." );
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervalo);
        if (transacoes.isEmpty()) {
            return new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }

        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream().mapToDouble(TransacaoRequestDTO::valor)
                .summaryStatistics();


        log.info("Estatísticas retornadas com sucesso!");
        return new EstatisticasResponseDTO(estatisticasTransacoes.getCount(), estatisticasTransacoes.getSum(),
                estatisticasTransacoes.getAverage(), estatisticasTransacoes.getMax(), estatisticasTransacoes.getMin());
    }
}
