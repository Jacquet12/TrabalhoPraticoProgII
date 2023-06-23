package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.NacionalidadeDAO;
import br.edu.projeto.model.TipoNacionalidade;

@Named
@ViewScoped
public class NacionalidadeController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private NacionalidadeDAO nacionalidadeDAO;

    private TipoNacionalidade tipoNacionalidade;
    private List<TipoNacionalidade> listaNacionalidades;

    private boolean verificador;

    @PostConstruct
    public void init() {
        tipoNacionalidade = new TipoNacionalidade();
        listaNacionalidades = nacionalidadeDAO.obterTodos();
    }

    public void novoCadastro() {
        this.tipoNacionalidade = new TipoNacionalidade();
    }

    public void remover() {
        if (this.nacionalidadeDAO.delete(this.tipoNacionalidade)) {
            listaNacionalidades.remove(tipoNacionalidade);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de Nacionalidade Removido", null));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-nacionalidade");
        } else {
            this.verificador = false;
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover Tipo de Nacionalidade pois está em uso!", null));
        }
        tipoNacionalidade = null;
        PrimeFaces.current().ajax().update("form:messages", "form:dt-nacionalidade");
    }

    public void salvarNovo() {
        if (nacionalidadeDAO.insert(tipoNacionalidade)) {
            listaNacionalidades.add(tipoNacionalidade);
            PrimeFaces.current().executeScript("PF('nacionalidadeDialog').hide()");
            PrimeFaces.current().ajax().update(":form:dt-nacionalidade");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de Nacionalidade Criado", null));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Criar Tipo de Nacionalidade", null));
        }
    }


    public void salvarAlteracao() {
        if (nacionalidadeDAO.update(tipoNacionalidade)) {
            PrimeFaces.current().executeScript("PF('nacionalidadeDialog').hide()");
            PrimeFaces.current().ajax().update(":form:dt-nacionalidade");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de Nacionalidade Atualizado", null));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Nacionalidade", null));
        }
    }

    public TipoNacionalidade getTipoNacionalidade() {
        return tipoNacionalidade;
    }

    public void setTipoNacionalidade(TipoNacionalidade tipoNacionalidade) {
        this.tipoNacionalidade = tipoNacionalidade;
    }

    public List<TipoNacionalidade> getListaNacionalidades() {
        return listaNacionalidades;
    }

    public void setListaNacionalidades(List<TipoNacionalidade> listaNacionalidades) {
        this.listaNacionalidades = listaNacionalidades;
    }
}
