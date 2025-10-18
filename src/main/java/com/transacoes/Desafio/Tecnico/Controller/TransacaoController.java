package com.transacoes.Desafio.Tecnico.Controller;

import com.transacoes.Desafio.Tecnico.Domain.Transacao.Transacao;
import com.transacoes.Desafio.Tecnico.Domain.Transacao.TransacaoDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class TransacaoController {

    private LocalDateTime data;

    private List<TransacaoDTO> listTransacao = new ArrayList<>();

    private DateTimeFormatter formatter;

    @PostMapping("/transacao")
    public ResponseEntity transacao(@Valid @RequestBody TransacaoDTO trs){

        if(trs.valor().compareTo(BigDecimal.ZERO) < 0) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        if(trs.dataHora().isAfter(OffsetDateTime.now())) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        listTransacao.add(trs);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/transacao")
    public ResponseEntity deletarTransacoes(){
        listTransacao.clear();
        return ResponseEntity.ok().build();
    };

    @GetMapping("/estatistica")
    public ResponseEntity transacoes1Minuto(TransacaoDTO dto){
        List<TransacaoDTO> diferencas = listTransacao.stream()
                .filter(lt -> ChronoUnit.SECONDS.between(lt.dataHora(), OffsetDateTime.now()) < 61)
                .collect(Collectors.toList());

        DoubleSummaryStatistics statistics = diferencas.stream()
                .collect(Collectors.summarizingDouble(
                        s -> Optional.ofNullable(s.valor())
                                .orElse(BigDecimal.ZERO)
                                .doubleValue()));

        return ResponseEntity.ok().body(statistics);
    }
}
