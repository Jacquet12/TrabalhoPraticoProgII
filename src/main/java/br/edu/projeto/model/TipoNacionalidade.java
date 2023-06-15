package br.edu.projeto.model;

public class TipoNacionalidade {
    private Integer id;
    private String descricao;

     public void setId(Integer id){
        this.id = id;
    }

    public Integer gInteger(){
        return id;
    }

    public void setString(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
