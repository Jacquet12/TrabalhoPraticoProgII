package br.edu.projeto.model;
import java.math.BigDecimal;

   


public class Cliente {
    private String nome;

    private String nomeSocial;
    
    private String cpf;
    
    private Double altura;

	private Integer massa;
    
    private String genero;
    
    private Integer idade;
    
    private String email;
    
    private String telefone;
    
    private String celular;

	private String endereco;
	private TipoNacionalidade nacionalidade;


    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getMassa() {
		return massa;
	}

	public void setMassa(Integer massa) {
		this.massa = massa;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
		this.endereco = endereco;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

	public TipoNacionalidade getTipoNacionalidade() {
		return nacionalidade;
	}
	
	public void setNacionalidade(TipoNacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
    
    public Double getAlturaAsDouble() {
        if (altura != null) {
            return altura.doubleValue();
        } else {
            return null;
        }
    }

	public TipoNacionalidade getTipoNacionalidadeSelecionada() {
		return null;
	}

	public void setTipoNacionalidade(TipoNacionalidade tipoNacionalidade) {
	}
}

