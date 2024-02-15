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
import java.util.Optional;
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
    
    @GetMapping("/cadastro")
    public ResponseEntity<Map<Long, Map<String, Object>>> obterIntegrantes() {

        List<Integrante> todosOsIntegrantes = integranteRepository.findAll();
        // Criando um Map para armazenar o ID e a chave/valor de outro Map
        Map<Long, Map<String,Object>> integrantesMap = new HashMap<>();
        
        // Adicionar os integrantes ao Map aninhado
        for (Integrante integrante : todosOsIntegrantes) {
            Map<String, Object> integranteData = new HashMap<>();
            integranteData.put("funcao", integrante.getFuncao());
            integranteData.put("nome", integrante.getNome());
            integranteData.put("franquia", integrante.getFranquia());

            //Adicionando o ID e a chave/valor do Map aninhado
            integrantesMap.put(integrante.getId(), integranteData);
        }
    
        return new ResponseEntity<>(integrantesMap, HttpStatus.OK);
    }

    @SuppressWarnings("null")
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarTime(@RequestBody TimeDto dto) {
        // Verifica se o atributo 'nome' é nulo
        if (dto.getNome() == null || dto.getNome().isEmpty()) {
            // Retorna uma resposta de erro indicando que o nome é obrigatório
            return ResponseEntity.badRequest().body("O atributo 'nome' é obrigatório.");
        }
        
        List<Time> times = timeRepository.findAll();

        for (Time time : times) {
            if(dto.getNome().equalsIgnoreCase(time.getNome())){
                return ResponseEntity.badRequest().body("O nome já está em uso.");
            }
        }

        // Criando um novo time
        Time time = new Time ();
        // Recuperando dados do dto
        time.setData(dto.getData());
        time.setNome(dto.getNome());

        // Salvando o time na tabela de times
        timeRepository.save(time);

        // Cadastra o time se receber o ID do integrante
        for (Long integranteId: dto.getIntegrantesID()) {
                ComposicaoTime composicaoTime = new ComposicaoTime();
                composicaoTime.setTime(time);
                integranteRepository.findById(integranteId).ifPresent(integrante -> {
                    composicaoTime.setIntegrante(integrante);

                composicaoTimeRepository.save(composicaoTime);   
                });
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
            
            //Retorno da resposta
            return new ResponseEntity<>(timeNaData, HttpStatus.OK);
    }



    @GetMapping("/timemaiscomum")
    public ResponseEntity<Map<String, Object>> timeMaiscomum(
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
            List<String> integrantes = apiService.timeMaisComum(dataInicialReal, dataFinalReal, times);
            // Criando um Map que irá receber o integrante mais usado
            Map<String, Object> timeMaisComum = new HashMap<>();

            // Passando a key "integrante mais usado" e pegando o nome do integrante
            timeMaisComum.put("Time mais comum:", integrantes);

            //Retorno da resposta
            return new ResponseEntity<>(timeMaisComum, HttpStatus.OK);
    }

    @GetMapping("/funcaomaiscomum")
    public ResponseEntity<Map<String, Object>> funcaoMaisComum(
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
            String funcao = apiService.funcaoMaisComum(dataInicialReal, dataFinalReal, times);
            // Criando um Map que irá receber o integrante mais usado
            Map<String, Object> funcaoMaisComum = new HashMap<>();

            // Passando a key "integrante mais usado" e pegando o nome do integrante
            funcaoMaisComum.put("Função mais comum:", funcao);

            //Retorno da resposta
            return new ResponseEntity<>(funcaoMaisComum, HttpStatus.OK);
    }

    @GetMapping("/franquiamaisfamosa")
    public ResponseEntity<Map<String, Object>> franquiaMaisFamosa(
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
            String franquia = apiService.franquiaMaisFamosa(dataInicialReal, dataFinalReal, times);
            // Criando um Map que irá receber o integrante mais usado
            Map<String, Object> franquiaMaisFamosa = new HashMap<>();

            // Passando a key "integrante mais usado" e pegando o nome do integrante
            franquiaMaisFamosa.put("Franquia mais famosa:", franquia);

            //Retorno da resposta
            return new ResponseEntity<>(franquiaMaisFamosa, HttpStatus.OK);
    }

    @GetMapping("/contagemfranquia")
    public ResponseEntity<Map<String, Long>> contagemPorFranquia(
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
            Map<String, Long> franquia = apiService.contagemPorFranquia(dataInicialReal, dataFinalReal, times);

            //Retorno da resposta
            return new ResponseEntity<>(franquia, HttpStatus.OK);
    }

    @GetMapping("/contagemfuncao")
    public ResponseEntity<Map<String, Long>> contagemPorFuncao(
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
            Map<String, Long> franquia = apiService.contagemPorFuncao(dataInicialReal, dataFinalReal, times);

            //Retorno da resposta
            return new ResponseEntity<>(franquia, HttpStatus.OK);
    }

}
