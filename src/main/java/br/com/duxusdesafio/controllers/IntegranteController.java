package br.com.duxusdesafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.dtos.IntegranteDto;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repositories.IntegranteRepository;



@RestController
@RequestMapping("/duxus/integrante")
public class IntegranteController {

    // Injeção das dependencias do IntegranteRepository
    @Autowired
    private IntegranteRepository integranteRepository;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarIntegrante(@RequestBody IntegranteDto dto ) {
        // Verifica se o atributo 'nome' é nulo
        if (dto.getNome() == null || dto.getNome().isEmpty()) {
            // Retorna uma resposta de erro indicando que o nome é obrigatório
            return ResponseEntity.badRequest().body("O atributo 'nome' é obrigatório.");
        }
        
        // Instanciando um novo integrante
        Integrante integrante = new Integrante();
        // Atribuindo ao integrante os dados recebidos pelo dto
        integrante.setNome(dto.getNome());
        integrante.setFranquia(dto.getFranquia());
        integrante.setFuncao(dto.getFuncao());

        // Salvado o integrante no banco de dados
        integranteRepository.save(integrante);

        // Retorno do JSON
        return ResponseEntity.ok(integrante);

    }
}
