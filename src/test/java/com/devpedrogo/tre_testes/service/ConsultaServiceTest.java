package com.devpedrogo.tre_testes.service;

import com.devpedrogo.tre_testes.dto.UsuarioResponseDto;
import com.devpedrogo.tre_testes.model.UsuarioEntity;
import com.devpedrogo.tre_testes.repository.IUsuarioRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Nested
    @DisplayName("Testes do método consultarUsuarios()")
    class ConsultarUsuariosTests {

        @Test
        @DisplayName("Deve retornar uma lista de UsuarioResponseDto quando existirem usuários cadastrados")
        void deveRetornarListaDeUsuariosComSucesso() {
            // Arrange (Cenário / Preparação)
            UsuarioEntity usuario1 = new UsuarioEntity("Carlos Silva", "carlos@email.com", "12345678901");
            UsuarioEntity usuario2 = new UsuarioEntity("Ana Souza", "ana@email.com", "98765432100");
            
            List<UsuarioEntity> entidadesMock = List.of(usuario1, usuario2);

            when(usuarioRepository.findAll()).thenReturn(entidadesMock);

            // Act (Ação / Execução do método)
            List<UsuarioResponseDto> resultado = usuarioService.consultarUsuarios();

            // Assert (Validações)
            assertNotNull(resultado, "A lista retornada não deve ser nula");
            assertEquals(2, resultado.size(), "O tamanho da lista deve ser 2");

            // Validação dos dados mapeados do primeiro usuário
            assertEquals("Carlos Silva", resultado.get(0).getNome());
            assertEquals("carlos@email.com", resultado.get(0).getEmail());
            assertEquals("12345678901", resultado.get(0).getCpf());

            // Validação dos dados mapeados do segundo usuário
            assertEquals("Ana Souza", resultado.get(1).getNome());
            assertEquals("ana@email.com", resultado.get(1).getEmail());
            assertEquals("98765432100", resultado.get(1).getCpf());

            // Garante que o método findAll() do repositório foi chamado exatamente 1 vez
            verify(usuarioRepository, times(1)).findAll();
            verifyNoMoreInteractions(usuarioRepository);
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia quando o repositório não encontrar registros")
        void deveRetornarListaVaziaQuandoNaoExistiremUsuarios() {
            // Arrange
            when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

            // Act
            List<UsuarioResponseDto> resultado = usuarioService.consultarUsuarios();

            // Assert
            assertNotNull(resultado, "A lista não deve ser nula mesmo vazia");
            assertTrue(resultado.isEmpty(), "A lista retornada deve estar vazia");

            // Valida a chamada ao repository
            verify(usuarioRepository, times(1)).findAll();
            verifyNoMoreInteractions(usuarioRepository);
        }
    }
}