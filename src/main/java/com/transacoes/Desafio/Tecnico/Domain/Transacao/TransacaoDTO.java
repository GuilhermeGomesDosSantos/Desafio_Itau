package com.transacoes.Desafio.Tecnico.Domain.Transacao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransacaoDTO(@NotNull BigDecimal valor, @NotNull OffsetDateTime dataHora) {
}
