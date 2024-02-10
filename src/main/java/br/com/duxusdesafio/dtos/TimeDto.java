package br.com.duxusdesafio.dtos;

import java.time.LocalDate;
import java.util.List;



public class TimeDto {
    
    private LocalDate data;
    // Parâmetro adicionado para dar nome ao time
    private String nome;
    private List<Long> integrantesID;
    private List<String> integrantesNome;

    public LocalDate getData() {
        return data;
    }

    public String getNome() {
        return nome;
    }

    public List<Long> getIntegrantesID() {
        return integrantesID;
    }

    public List<String> getIntegrantesNome() {
        return integrantesNome;
    }

    public void setData (LocalDate data) {
        this.data = data;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIntegrantesID(List<Long> integrantesId) {
        this.integrantesID = integrantesId;
    }

    public void setIntegrantesNome(List<String> integrantesNome) {
        this.integrantesNome = integrantesNome;
    }


}
