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

        // Iterando por todos os times
        for (Time time : todosOsTimes) {
            // Verificando se data do time é igual a data fornecida
            if (time.getData().equals(data)) {
                // Pegando o nome do time
                timeNome = time.getNome();
                // Iterando a composição e adicionando ao time
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

         // Criando um Map para armazenar a contagem de cada integrante
        Map<Integrante, Integer> integranteContagem = new HashMap<>();

        // Iterando por todos os times
        for (Time time : todosOsTimes) {
            // Verificando se a data do time está dentro da data fornecida
            if(!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
                // Iterando as composições para pegar os integrantes
                for (ComposicaoTime composicaoTime : time.getComposicaoTime()){
                    // Verificando se o integrante já está no mapa
                    if (integranteContagem.containsKey(composicaoTime.getIntegrante())) {
                        // Se estiver, incrementa a contagem
                        integranteContagem.put(composicaoTime.getIntegrante(), integranteContagem.get(composicaoTime.getIntegrante()) + 1);
                    } else {
                        // Se não estiver, adiciona ao mapa com contagem 1
                        integranteContagem.put(composicaoTime.getIntegrante(), 1);
                    }
                }
            }
        }

        // Encontrando o integrante com a maior contagem
        Integrante integranteMaisUsado = null;
        int maiorContagem = 0;
        
        // Percorrento as posições do map integranteContagem
        for (Map.Entry<Integrante, Integer> entry : integranteContagem.entrySet()) {
            // Se o valor do elemento atual for a maior contagem, atualiza o valor e a chave
            if (entry.getValue() > maiorContagem) {
                // Atribui o valor da contagem para comparar na próxima iteração
                maiorContagem = entry.getValue();
                // Atribui o valor da chave para pegar o nome do Integrante
                integranteMaisUsado = entry.getKey();
            }
        }

        // Retorna o valor atribuido pela chave que mais apareceu com maior contagem
        return integranteMaisUsado;
    }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @return Vai retornar uma lista com os nomes dos integrantes do time mais comum
     * dentro do período
     */
    public List<String> timeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){

        // Criando um Map para armazenar a contagem de cada integrante
        Map<Integrante, Integer> timeContagem = new HashMap<>();
        
        // Iterando por todos os times
        for (Time time : todosOsTimes) {
            // Verificando se a data do time está dentro da data fornecida
            if(!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
                // Iterando as composições para pegar os integrantes
                for (ComposicaoTime composicaoTime : time.getComposicaoTime()){
                    // Verificando se o integrante já está no mapa
                    if (timeContagem.containsKey(composicaoTime.getIntegrante())) {
                        // Se estiver, incrementa a contagem
                        timeContagem.put(composicaoTime.getIntegrante(), timeContagem.get(composicaoTime.getIntegrante()) + 1);
                    } else {
                        // Se não estiver, adiciona ao mapa com contagem 1
                        timeContagem.put(composicaoTime.getIntegrante(), 1);
                    }
                }
            }
        }

        // List<ComposicaoTime> timeMaisComum = new ArrayList<>();
        // int maiorContagem = 0;

        // // Percorrento as posições do map integranteContagem
        // for (Map.Entry<ComposicaoTime, Integer> entry : timeContagem.entrySet()) {
        //     // Se o valor do elemento atual for a maior contagem, atualiza o valor e a chave
        //     if (entry.getValue() > maiorContagem) {
        //         // Atribui o valor da contagem para comparar na próxima iteração
        //         maiorContagem = entry.getValue();
        //         // Atribui o valor da chave para pegar o nome do Integrante
        //         timeMaisComum.add(entry.getKey());
        //     }
        // }

        // Construindo a lista de Strings com os nomes dos integrantes da composição de time mais comum
        List<String> integrantesMaisUsados = new ArrayList<>();
        // String nomeIntegrante = "";
        //     if (timeMaisComum != null) {
        //         for (ComposicaoTime composicaoTime : timeMaisComum) {
        //             nomeIntegrante = composicaoTime.getIntegrante().getNome();
        //             integrantesMaisUsados.add(nomeIntegrante);
        //         }
        //     }
            System.out.println(timeContagem);
            return integrantesMaisUsados;
        }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @return Vai retornar a função mais comum nos times dentro do período
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){

        Map<String, Integer> funcaoContagem = new HashMap<>();

        // Iterando por todos os times
        for (Time time : todosOsTimes) {
            // Verificando se a data do time está dentro da data fornecida
            if(!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
                // Iterando as composições para pegar os integrantes
                for (ComposicaoTime composicaoTime : time.getComposicaoTime()){
                    // Verificando se a função já está no mapa
                    if (funcaoContagem.containsKey(composicaoTime.getIntegrante().getFuncao())) {
                        // Se estiver, incrementa a contagem
                        funcaoContagem.put(composicaoTime.getIntegrante().getFuncao(), funcaoContagem.get(composicaoTime.getIntegrante().getFuncao()) + 1);
                    } else {
                        // Se não estiver, adiciona ao mapa com contagem 1
                        funcaoContagem.put(composicaoTime.getIntegrante().getFuncao(), 1);
                    }
                }
            }
        }

        // Encontrando a função com a maior contagem
        String funcaoMaisComum = "";
        int maiorContagem = 0;
        
        // Percorrendo as posições do map funcaoContagem
        for (Map.Entry<String, Integer> entry : funcaoContagem.entrySet()) {
            // Se o valor do elemento atual for a maior contagem, atualiza o valor e a chave
            if (entry.getValue() > maiorContagem) {
                // Atribui o valor da contagem para comparar na próxima iteração
                maiorContagem = entry.getValue();
                // Atribui o valor da chave para pegar o nome da Função
                funcaoMaisComum = entry.getKey();
            }
        }

        // Retorna o valor atribuido pela chave que mais apareceu com maior contagem
        return funcaoMaisComum;

    }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @return Vai retornar o número (quantidade) de Franquias dentro do período
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {

        Map<String, Integer> franquiaContagem = new HashMap<>();

        // Iterando por todos os times
        for (Time time : todosOsTimes) {
            // Verificando se a data do time está dentro da data fornecida
            if(!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
                // Iterando as composições para pegar os integrantes
                for (ComposicaoTime composicaoTime : time.getComposicaoTime()){
                    // Verificando se a franquia já está no mapa
                    if (franquiaContagem.containsKey(composicaoTime.getIntegrante().getFranquia())) {
                        // Se estiver, incrementa a contagem
                        franquiaContagem.put(composicaoTime.getIntegrante().getFranquia(), franquiaContagem.get(composicaoTime.getIntegrante().getFranquia()) + 1);
                    } else {
                        // Se não estiver, adiciona ao mapa com contagem 1
                        franquiaContagem.put(composicaoTime.getIntegrante().getFranquia(), 1);
                    }
                }
            }
        }

        // Encontrando a franquia com a maior contagem
        String franquiaMaisFamosa = "";
        int maiorContagem = 0;
        
        // Percorrendo as posições do map franquiaContagem
        for (Map.Entry<String, Integer> entry : franquiaContagem.entrySet()) {
            // Se o valor do elemento atual for a maior contagem, atualiza o valor e a chave
            if (entry.getValue() > maiorContagem) {
                // Atribui o valor da contagem para comparar na próxima iteração
                maiorContagem = entry.getValue();
                // Atribui o valor da chave para pegar o nome da Função
                franquiaMaisFamosa = entry.getKey();
            }
        }

        // Retorna o valor atribuido pela chave que mais apareceu com maior contagem
        return franquiaMaisFamosa;

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
