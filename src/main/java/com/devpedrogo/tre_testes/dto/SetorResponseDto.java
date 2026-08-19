package com.devpedrogo.tre_testes.dto;

import java.math.BigDecimal;

public class SetorResponseDto {
    private BigDecimal codigoSetor;
    private String nomeSetor;
    private String siglaSetor;

    public SetorResponseDto(BigDecimal codigoSetor, String nomeSetor, String siglaSetor) {
        this.codigoSetor = codigoSetor;
        this.nomeSetor = nomeSetor;
        this.siglaSetor = siglaSetor;
    }

    public BigDecimal getCodigoSetor() {
        return codigoSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public String getSiglaSetor() {
        return siglaSetor;
    }
}
