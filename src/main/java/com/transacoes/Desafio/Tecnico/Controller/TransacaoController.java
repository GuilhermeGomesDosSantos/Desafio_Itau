package com.transacoes.Desafio.Tecnico.Controller;

import com.transacoes.Desafio.Tecnico.Domain.Transacao.Transacao;
import com.transacoes.Desafio.Tecnico.Domain.Transacao.TransacaoDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private List<TransacaoDTO> listTransacao = new ArrayList<>();

    private DateTimeFormatter formatter;

    @PostMapping
    public ResponseEntity transacao(@Valid @RequestBody TransacaoDTO trs){

        if(trs.valor().compareTo(BigDecimal.ZERO) < 0) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        if(trs.dataHora().isAfter(OffsetDateTime.now())) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        var getTransacao = listTransacao.add(trs);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
