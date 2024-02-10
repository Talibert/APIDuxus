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

    @Autowired
    private IntegranteRepository integranteRepository;

    @PostMapping("/cadastro")
    public ResponseEntity<Integrante> cadastrarIntegrante(@RequestBody IntegranteDto dto ) {
        // Instanciando um novo integrante
        Integrante integrante = new Integrante();
        // Atribuindo ao integrante os dados recebidos pelo dto
        integrante.setNome(dto.getNome());
        integrante.setFranquia(dto.getFranquia());
        integrante.setFuncao(dto.getFuncao());

        integranteRepository.save(integrante);

        return ResponseEntity.ok(integrante);

    }
}
