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
import java.util.Comparator;

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
    public Map<String, Object> timeDaData(LocalDate data, List<Time> todosOsTimes) {
        // Criando uma lista de times
        List<Time> timesDaData = new ArrayList<>();

        // Iterando por todos os times
        for (Time time : todosOsTimes) {
            // Verificando se a data do time é igual à data fornecida
            if (time.getData().equals(data)) {
                // adicionando o time em uma lista de times
                timesDaData.add(time);
            }
        }

        // Iniciando o nome do time e a lista com os integrantes do ultimo time cadastrado
        String timeNome = "";
        List<String> integrantes = new ArrayList<>();

        // Verifica se há algum time na data
        if (!timesDaData.isEmpty()) {
            // Obtém o último time da lista
            Time ultimoTime = timesDaData.get(timesDaData.size() - 1);
            // Pegando o nome dos integrantes
            timeNome = ultimoTime.getNome();
            
            // Iterando sobre a composição do último time
            for (ComposicaoTime composicaoTime : ultimoTime.getComposicaoTime()) {
                integrantes.add(composicaoTime.getIntegrante().getNome());
            }
        }

        // Criando o mapa de resultados
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("timeNome", timeNome);
        resultado.put("integrantes", integrantes);

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
        Map<String, Integer> timeContagem = new HashMap<>();
        
        // Iterando por todos os times
        for (Time time : todosOsTimes) {
            // Verificando se a data do time está dentro da data fornecida
            if(!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
                // Iterando as composições para pegar os integrantes
                for (ComposicaoTime composicaoTime : time.getComposicaoTime()){
                    // Verificando se o integrante já está no mapa
                    if (timeContagem.containsKey(composicaoTime.getIntegrante().getNome())) {
                        // Se estiver, incrementa a contagem
                        timeContagem.put(composicaoTime.getIntegrante().getNome(), timeContagem.get(composicaoTime.getIntegrante().getNome()) + 1);
                    } else {
                        // Se não estiver, adiciona ao mapa com contagem 1
                        timeContagem.put(composicaoTime.getIntegrante().getNome(), 1);
                    }
                }
            }
        }

        // Convertendo o mapa para uma lista de entradas
        List<Map.Entry<String, Integer>> timeContagemConvertido = new ArrayList<>(timeContagem.entrySet());

        // Ordenando a lista com base nos valores em ordem decrescente
        timeContagemConvertido.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Lista que irá receber os nomes do timeMaisComum
        List<String> timeMaisComum = new ArrayList<>();

        // Adicionando os nomes dos integrantes dos cinco maiores pares chave-valor à lista
        int count = 0;
        for (Map.Entry<String, Integer> integrante : timeContagemConvertido) {
            // Verifica o time está completo e para o loop
            if (count >= 5) {
                break;
            }
            // Adiciona o nome do integrante ao time
            timeMaisComum.add(integrante.getKey());
            count++;
        }

        // Retornando a lista com os nomes dos integrantes dos cinco maiores pares chave-valor
        return timeMaisComum;
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
        
        Map<String, Long> franquiaContagem = new HashMap<>();

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
                        franquiaContagem.put(composicaoTime.getIntegrante().getFranquia(), 1L);
                    }
                }
            }
        }


        return franquiaContagem;
    }

    /**
     * @param dataInicial pode ser nulo
     * @param dataFinal pode ser nulo
     * @Return Vai retornar o número (quantidade) de Funções dentro do período
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        
        Map<String, Long> funcaoContagem = new HashMap<>();

        // Iterando por todos os times
        for (Time time : todosOsTimes) {
            // Verificando se a data do time está dentro da data fornecida
            if(!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
                // Iterando as composições para pegar os integrantes
                for (ComposicaoTime composicaoTime : time.getComposicaoTime()){
                    // Verificando se a franquia já está no mapa
                    if (funcaoContagem.containsKey(composicaoTime.getIntegrante().getFuncao())) {
                        // Se estiver, incrementa a contagem
                        funcaoContagem.put(composicaoTime.getIntegrante().getFuncao(), funcaoContagem.get(composicaoTime.getIntegrante().getFuncao()) + 1);
                    } else {
                        // Se não estiver, adiciona ao mapa com contagem 1
                        funcaoContagem.put(composicaoTime.getIntegrante().getFuncao(), 1L);
                    }
                }
            }
        }


        return funcaoContagem;

    }

}
