package br.edu.projeto.model;

public class TipoNacionalidade {
    private Integer id;
    private String tipo_nacionalidade;

     public void setId(Integer id){
        this.id = id;
    }

    public Integer gInteger(){
        return id;
    }

    public void setString(String tipo_nacionalidade){
        this.tipo_nacionalidade = tipo_nacionalidade;
    }

    public String getTipo_nacionalidade(){
        return tipo_nacionalidade;
    }

    public void setTipo_nacionalidade(String string) {
        
    }
}
