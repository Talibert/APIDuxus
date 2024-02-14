package br.com.duxusdesafio.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.dtos.IntegranteDto;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repositories.IntegranteRepository;
import br.com.duxusdesafio.repositories.TimeRepository;
import br.com.duxusdesafio.service.ApiService;



@RestController
@RequestMapping("/duxus/integrante")
public class IntegranteController {

    // Injeção das dependencias do IntegranteRepository
    @Autowired
    private IntegranteRepository integranteRepository;
    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private ApiService apiService;

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

        @GetMapping("/integrantemaisusado")
    public ResponseEntity<Map<String, Object>> integranteMaisUsado(
                    // Pegando a data dos parâmetros da requisição
                    @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> dataInicial,
                    @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> dataFinal)
        
        {

            // Verifica se a primeira data é nula
            LocalDate dataInicialReal = dataInicial.orElse(LocalDate.MIN);

            // Verifica se a segunda data é nula
            LocalDate dataFinalReal = dataFinal.orElse(LocalDate.MAX);      

            // Coloca todos os times em uma lista de Time
            List<Time> times = timeRepository.findAll();
            // Chama o método timeDaData e passa data e a lista de times como argumento
            Integrante integrante = apiService.integranteMaisUsado(dataInicialReal, dataFinalReal, times);
            // Criando um Map que irá receber o integrante mais usado
            Map<String, Object> integranteMaisUsado = new HashMap<>();

            // Passando a key "integrante mais usado" e pegando o nome do integrante
            integranteMaisUsado.put("Integrante Mais Usado", integrante.getNome());

            //Retorno da resposta
            return new ResponseEntity<>(integranteMaisUsado, HttpStatus.OK);
    }

}
