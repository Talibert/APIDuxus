package br.com.duxusdesafio.dtos;

// Dto utilizado para transportar as informações entre as camadas de apresentação e dados
public class IntegranteDto {

    //Atributos
    private String nome;
    private String franquia;
    private String funcao;


    //Getters and Setters
    public String getNome() {
        return this.nome;
    }

    public String getFranquia() {
        return this.franquia;
    }

    public String getFuncao() {
        return this.funcao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFranquia(String franquia) {
        this.franquia = franquia;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}
