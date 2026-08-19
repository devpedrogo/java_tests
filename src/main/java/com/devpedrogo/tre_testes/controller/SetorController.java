package com.devpedrogo.tre_testes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpedrogo.tre_testes.dto.SetorResponseDto;
import com.devpedrogo.tre_testes.service.SetorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    @GetMapping
    public ResponseEntity<List<SetorResponseDto>> listarSetores() {
        return ResponseEntity.ok(setorService.listarSetores());
    }
}
