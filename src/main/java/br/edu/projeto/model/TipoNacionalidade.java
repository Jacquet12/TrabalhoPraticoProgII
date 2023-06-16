package br.edu.projeto.model;

public class TipoNacionalidade {
    private Integer id;
    private String tipo_nacionalidade;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setTipo_nacionalidade(String tipo_nacionalidade) {
        this.tipo_nacionalidade = tipo_nacionalidade;
    }

    public String getTipo_nacionalidade() {
        return tipo_nacionalidade;
    }
}
