package com.devpedrogo.tre_testes.service;

import com.devpedrogo.tre_testes.dto.SetorResponseDto;
import com.devpedrogo.tre_testes.model.SetorEntity;
import com.devpedrogo.tre_testes.repository.SetorDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa o Mockito de forma leve (Sem Spring/Sem Banco)
class SetorServiceTest {

    @Mock
    private SetorDao setorDao; // Mocka o seu DAO do JdbcTemplate

    @InjectMocks
    private SetorService setorService; // Injeta o DAO mockado dentro do Service automaticamente

    @Test
    @DisplayName("Deve retornar uma lista de SetorResponseDto mapeada corretamente quando existirem setores no banco")
    void deveListarSetoresComSucesso() {
        // Arrange (Cenário) - Criamos as entidades simulando o que o JdbcTemplate traria do banco
        SetorEntity setor1 = new SetorEntity(new BigDecimal("1001"), "Tecnologia da Informação", "TIC");
        SetorEntity setor2 = new SetorEntity(new BigDecimal("1002"), "Recursos Humanos", "RH");
        List<SetorEntity> entidadesMock = List.of(setor1, setor2);

        // Quando o service chamar o DAO, ele vai retornar a nossa lista moficada acima
        when(setorDao.listarSetores()).thenReturn(entidadesMock);

        // Act (Ação) - Executamos o método do service que queremos testar
        List<SetorResponseDto> resultado = setorService.listarSetores();

        // Assert (Validações) - Garantimos que o mapeamento de Entity para DTO funcionou perfeitamente
        assertNotNull(resultado, "A lista retornada não deve ser nula");
        assertEquals(2, resultado.size(), "O tamanho da lista deve ser 2");

        // Validação dos dados do primeiro setor convertido
        assertEquals(new BigDecimal("1001"), resultado.get(0).getCodigoSetor());
        assertEquals("Tecnologia da Informação", resultado.get(0).getNomeSetor());
        assertEquals("TIC", resultado.get(0).getSiglaSetor());

        // Validação dos dados do segundo setor convertido
        assertEquals(new BigDecimal("1002"), resultado.get(1).getCodigoSetor());
        assertEquals("Recursos Humanos", resultado.get(1).getNomeSetor());
        assertEquals("RH", resultado.get(1).getSiglaSetor());

        // Verifica se o Service realmente chamou o DAO exatamente 1 vez
        verify(setorDao, times(1)).listarSetores();
        verifyNoMoreInteractions(setorDao);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando o banco não possuir nenhum setor cadastrado")
    void deveRetornarListaVaziaQuandoNaoExistiremSetores() {
        // Arrange
        when(setorDao.listarSetores()).thenReturn(Collections.emptyList());

        // Act
        List<SetorResponseDto> resultado = setorService.listarSetores();

        // Assert
        assertNotNull(resultado, "A lista não deve ser nula mesmo vazia");
        assertTrue(resultado.isEmpty(), "A lista retornada deve estar vazia");

        // Verifica a chamada ao DAO
        verify(setorDao, times(1)).listarSetores();
        verifyNoMoreInteractions(setorDao);
    }
}

