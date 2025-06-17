package com.desafioitaujunior.transacao_api.business.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.desafioitaujunior.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.desafioitaujunior.transacao_api.infrastructure.exceptions.Unprocessableentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {
    
   private final ArrayList<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

   public void adicionarTransacoes(TransacaoRequestDTO dto) {

      log.info("Iniciado o processamento de gravar transações " + dto);
      if (dto.dataHora().isAfter(OffsetDateTime.now())) {
         log.error("Data e hora maiores que a data atual.");
         throw new Unprocessableentity("Data e hora maiores que a data e hora atuais.");
      }
      if (dto.valor() < 0){
         log.error("Valor não pode ser menor que 0.");
         throw new Unprocessableentity("Valor não pode ser menor que 0");
      }

      log.info("Transações adicionadas com sucesso!");
      listaTransacoes.add(dto);
   }

   public void limparTransacoes() {
      log.info("Iniciado processamento para deletar transações.");
      listaTransacoes.clear();
      log.info("Transações deletadas com sucesso!");
   }

   public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca) {
      log.info("Iniciadas buscas de transações pelo intervalo de " + intervaloBusca + " segundos.");
      OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);

      log.info("Transações retornadas com sucesso!");
      return listaTransacoes.stream()
              .filter(transacao -> transacao.dataHora()
              .isAfter(dataHoraIntervalo)).toList();
   }
}
