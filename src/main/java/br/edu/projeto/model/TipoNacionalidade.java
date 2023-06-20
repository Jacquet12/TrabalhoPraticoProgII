package br.edu.projeto.model;

public class TipoNacionalidade {
    private int id;
    private String tipo_nacionalidade;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTipo_nacionalidade(String tipo_nacionalidade) {
        this.tipo_nacionalidade = tipo_nacionalidade;
    }

    public String getTipo_nacionalidade() {
        return tipo_nacionalidade;
    }
}
