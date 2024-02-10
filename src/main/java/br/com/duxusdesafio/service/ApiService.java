package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service que possuirá as regras de negócio para o processamento dos dados
 * solicitados no desafio!
 *
 * @author carlosau
 */
@Service
public class ApiService {

    /**
     * @return Vai retornar o time da data
     */
    public Map<String, Object> timeDaData(LocalDate data, List<Time> todosOsTimes){
        
        // Definindo um novo Array
        List<String> timeDaData = new ArrayList<>();
        String timeNome = "";

        // Iterando em cada time
        for (Time time : todosOsTimes) {
            // Logica para ver se a data do time é igual a data definida pelo usuario
            if (time.getData().equals(data)) {
                // Iterando a composição
                    timeNome = time.getNome();
                for (ComposicaoTime composicaoTime : time.getComposicaoTime()) {
                    timeDaData.add(composicaoTime.getIntegrante().getNome());
                    
                }
            }
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("timeNome", timeNome);
        resultado.put("timeDaData", timeDaData);
        return resultado;
    }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @return Vai retornar o integrante que tiver presente na maior quantidade de times
     * dentro do período
     */
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @return Vai retornar uma lista com os nomes dos integrantes do time mais comum
     * dentro do período
     */
    public List<String> timeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @return Vai retornar a função mais comum nos times dentro do período
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @return Vai retornar o nome da Franquia mais comum nos times dentro do período
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        // TODO Implementar método seguindo as instruções!
        return null;
    }


    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @return Vai retornar o número (quantidade) de Franquias dentro do período
     */
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @Return Vai retornar o número (quantidade) de Funções dentro do período
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

}
