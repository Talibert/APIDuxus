package br.com.duxusdesafio.controllers;

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

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import br.com.duxusdesafio.dtos.TimeDto;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repositories.ComposicaoTimeRepository;
import br.com.duxusdesafio.repositories.IntegranteRepository;
import br.com.duxusdesafio.repositories.TimeRepository;
import br.com.duxusdesafio.service.ApiService;

@RestController
@RequestMapping("/duxus/time")
public class TimeController {

    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private IntegranteRepository integranteRepository;
    @Autowired
    private ComposicaoTimeRepository composicaoTimeRepository;
    @Autowired
    private ApiService apiService;
    
    @GetMapping("/helloworld")
    public String teste() {
        return "hello world";
    }

    @SuppressWarnings("null")
    @PostMapping("/cadastro")
    public ResponseEntity<Time> cadastrarTime(@RequestBody TimeDto dto) {
        // Criando um novo time
        Time time = new Time ();
        // Recuperando dados do dto
        time.setData(dto.getData());
        time.setNome(dto.getNome());

        // Salvando o time na tabela de times
        timeRepository.save(time);

        // Cadastra o time se receber o ID do integrante
        if (dto.getIntegrantesID() != null && !dto.getIntegrantesID().isEmpty()) {
            for (Long integranteId: dto.getIntegrantesID()) {
                ComposicaoTime composicaoTime = new ComposicaoTime();
                composicaoTime.setTime(time);
                integranteRepository.findById(integranteId).ifPresent(integrante -> {
                    composicaoTime.setIntegrante(integrante);

                composicaoTimeRepository.save(composicaoTime);   
                });

            }
        // Cadastra o time se receber o nome do integrante
        } else {
            for (String integranteNome: dto.getIntegrantesNome()) {
                ComposicaoTime composicaoTime = new ComposicaoTime();
                composicaoTime.setTime(time);
                integranteRepository.findByNome(integranteNome).ifPresent(integrante -> {
                    composicaoTime.setIntegrante(integrante);

                composicaoTimeRepository.save(composicaoTime);   
                });

            }
        }

        return ResponseEntity.ok(time);
        
    }

    @GetMapping("/timenadata")
    public ResponseEntity<Map<String, Object>> getTimeNaData(
                    // Pegando a data dos parâmetros da requisição
                    @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

            // Coloca todos os times em uma lista de Time
            List<Time> times = timeRepository.findAll();
            // Chama o método timeDaData e passa data e a lista de times como argumento
            Map<String, Object> timeNaData = apiService.timeDaData(data, times);
            
            //Passa a key data, com o argumento data
            timeNaData.put("data", data);
            //Passa a key timeNome, com o timeNome retornado
            timeNaData.put("timeNome", timeNaData.get("timeNome"));
            //Passa a key timeDaData com o timeDaData retornado
            timeNaData.put("timeDaData", timeNaData.get("timeDaData"));
            
            //Retorno da resposta
            return new ResponseEntity<>(timeNaData, HttpStatus.OK);
    }

    @GetMapping("/integrantemaisusado")
    public ResponseEntity<Map<String, Object>> integranteMaisUsado(
                    // Pegando a data dos parâmetros da requisição
                    @RequestParam("data inicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                    @RequestParam("data final") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal)
        
        {

            // Coloca todos os times em uma lista de Time
            List<Time> times = timeRepository.findAll();
            // Chama o método timeDaData e passa data e a lista de times como argumento
            Integrante integrante = apiService.integranteMaisUsado(dataInicial, dataFinal, times);
            // Criando um Map que irá receber o integrante mais usado
            Map<String, Object> integranteMaisUsado = new HashMap<>();

            // Passando a key "integrante mais usado" e pegando o nome do integrante
            integranteMaisUsado.put("Integrante Mais Usado", integrante.getNome());

            //Retorno da resposta
            return new ResponseEntity<>(integranteMaisUsado, HttpStatus.OK);
    }

}
