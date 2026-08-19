package com.devpedrogo.tre_testes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devpedrogo.tre_testes.dto.SetorResponseDto;
import com.devpedrogo.tre_testes.model.SetorEntity;
import com.devpedrogo.tre_testes.repository.SetorDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SetorService {
    private final SetorDao setorDao;

    public List<SetorResponseDto> listarSetores() {
        List<SetorEntity> setores = setorDao.listarSetores();

        return setores.stream().map(setor -> new SetorResponseDto(
                setor.getCodigoSetor(),
                setor.getNomeSetor(),
                setor.getSiglaSetor()
        )).toList();
    }
}
