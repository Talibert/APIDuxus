package br.com.duxusdesafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import br.com.duxusdesafio.dtos.TimeDto;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repositories.ComposicaoTimeRepository;
import br.com.duxusdesafio.repositories.IntegranteRepository;
import br.com.duxusdesafio.repositories.TimeRepository;

@RestController
@RequestMapping("/duxus/time")
public class TimeController {

    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private IntegranteRepository integranteRepository;
    @Autowired
    private ComposicaoTimeRepository composicaoTimeRepository;
    
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

        if (dto.getIntegrantesID() != null && !dto.getIntegrantesID().isEmpty()) {
            for (Long integranteId: dto.getIntegrantesID()) {
                ComposicaoTime composicaoTime = new ComposicaoTime();
                composicaoTime.setTime(time);
                integranteRepository.findById(integranteId).ifPresent(integrante -> {
                    composicaoTime.setIntegrante(integrante);

                composicaoTimeRepository.save(composicaoTime);   
                });

            }
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

        Optional<Time> timeOptional = timeRepository.findById(time.getId());
        if (timeOptional.isPresent()) {
                Time timeWithComposition = timeOptional.get();
                return ResponseEntity.ok(timeWithComposition);
        } else {
                return ResponseEntity.notFound().build();
        }
        
    }

}
