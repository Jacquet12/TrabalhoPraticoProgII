package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.ClienteDAO;
import br.edu.projeto.model.Cliente;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;



//Escopo do objeto da classe (Bean)
//ApplicationScoped é usado quando o objeto é único na aplicação (compartilhado entre usuários), permanece ativo enquanto a aplicação estiver ativa
//SessionScoped é usado quando o objeto é único por usuário, permanece ativo enquanto a sessão for ativa
//ViewScoped é usado quando o objeto permanece ativo enquanto não houver um redirect (acesso a nova página)
//RequestScoped é usado quando o objeto só existe naquela requisição específica
//Quanto maior o escopo, maior o uso de memória no lado do servidor (objeto permanece ativo por mais tempo)
//Escopos maiores que Request exigem que classes sejam serializáveis assim como todos os seus atributos (recurso de segurança)
@ViewScoped
//Torna classe disponível na camada de visão (html) - são chamados de Beans ou ManagedBeans (gerenciados pelo JSF/EJB)
@Named
public class ClienteController implements Serializable {
	private static final long serialVersionUID = 1L;

	//Anotação que marca atributo para ser gerenciado pelo CDI
	//O CDI criará uma instância do objeto automaticamente quando necessário
	@Inject
	private FacesContext facesContext;
	
	@Inject
    private ClienteDAO clienteDAO;
	
	private Cliente cliente;

	private String nomeFiltrado;
	private String generoFiltrado;
	
	private List<Cliente> listaCliente;

	private List<Cliente> clientesFiltrados;
	private List<Cliente> clientesFiltradoPorGenero;

	
	//Anotação que força execução do método após o construtor da classe ser executado
    @PostConstruct
    public void init() {
    	//Inicializa elementos importantes
    	this.listaCliente = clienteDAO.listAll();
    }
	
    //Chamado pelo botão novo
	public void novoCadastro() {
        this.cliente = new Cliente();
    }
	
	//Chamado pelo botão remover da tabela
	public void remover() {
		if (this.clienteDAO.delete(this.cliente))
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário Removido", null));
		else 
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover Usuário", null));
		//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
		this.listaCliente = clienteDAO.listAll();
		//Limpa seleção de usuário
		this.cliente = null;
		PrimeFaces.current().ajax().update("form:messages", "form:dt-clientes");
	}
	
	
	
	//Chamado ao salvar cadastro de usuário (novo)
	public void salvarNovo() {
		if (this.clienteDAO.insert(this.cliente)) {
            this.listaCliente.add(this.cliente);
            PrimeFaces.current().executeScript("PF('clienteDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-cliente");
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Criado", null));
		} else{
        	this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Criar Cliente", null));
        }
	}
	
	//Chamado ao salvar cadastro de usuário (alteracao)
	public void salvarAlteracao() {
		if (this.cliente.getGenero().equals("Homem") || this.cliente.getGenero().equals("Mulher") || this.cliente.getGenero().equals("Neutro"))
		{
			if (this.clienteDAO.update(this.cliente)) {
				PrimeFaces.current().executeScript("PF('clienteDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-cliente");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Atualizado", null));
			} else
        		this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Cliente", null));
		} else {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Genero do cliente inválido, deve ser Homem, Mulher ou Neutro!", null));
    	}   
		PrimeFaces.current().ajax().update("form:messages");
	}
	
	

	public void validarTelefone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String telefone = (String) value;
		if (!telefone.matches("^\\(\\d{2}\\)\\d{5}-\\d{4}$")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Telefone inválido", null));
		}
	}

	public void validarCPF(FacesContext context, UIComponent component, Object value) {
        String cpf = (String) value;

        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new ValidatorException(new FacesMessage("CPF inválido. Preencha no formato 000.000.000-00"));
        }
    }

	public double getAlturaDouble() {
        return cliente.getAltura() != null ? cliente.getAltura().doubleValue() : 0.0;
    }
	

	public void validarAlturaMaiorQueZero() {
		if (cliente.getAltura() <= 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Altura deve ser maior que zero.", null);
			FacesContext.getCurrentInstance().addMessage("altura", message);
		}
	}

	public void verificarMassa() {
		if (cliente.getMassa() <= 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Massa inválida", "Massa deve ser maior do que zero.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			cliente.setMassa(null);// Limpar o valor da altura para que o usuário digite novamente
		}
	}

	public void filtrar() {
		this.clientesFiltrados = clienteDAO.ClienteFiltrado(nomeFiltrado);
		
		PrimeFaces.current().executeScript("PF('productDialog').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:tabelaClientes");
	}

	public void filtrarPorGenero(){
		this.clientesFiltradoPorGenero = clienteDAO.ClienteFiltradoPorgenero(generoFiltrado);
		PrimeFaces.current().ajax().update("form:messages", "form:tabelasClientes");
	}



	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}
	
	public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
		this.clientesFiltrados = clientesFiltrados;
	}

	//filtro de busca por genero
	public List<Cliente> getClientesFiltradoPorGenero() {
		return clientesFiltradoPorGenero;
	}
	
	public void gsetClientesFiltradoPorGenero(List<Cliente> clientesFiltradoPorGenero) {
		this.clientesFiltradoPorGenero = clientesFiltradoPorGenero;
	}

	//get and para filtro por nome

	public String getNomeFiltrado() {
		return nomeFiltrado;
	}
	
	public void setNomeFiltrado(String nomeFiltrado) {
		this.nomeFiltrado = nomeFiltrado;
	}

	public String getGeneroFiltrado() {
		return generoFiltrado;
	}
	
	public void setGeneroFiltrado(String generoFiltrado) {
		this.generoFiltrado = generoFiltrado;
	}

}