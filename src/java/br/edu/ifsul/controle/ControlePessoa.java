package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.dao.CarroDAO;
import br.edu.ifsul.modelo.Carro;
import br.edu.ifsul.modelo.Telefone;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

@Named(value = "controlePessoa")
@SessionScoped
public class ControlePessoa implements Serializable {

    @EJB
    private PessoaDAO<Pessoa> dao;
    private Pessoa objeto;
    private Boolean editando;
    
   
   @EJB
    private CarroDAO<Carro> daoCarro;
    private Boolean editandoCarro;
    private Carro carro;
    
     private Boolean editandoTelefone;
    private Telefone telefone;
  

    public ControlePessoa() {
        editando = false;
        editandoTelefone = false;
        editandoCarro =false;
       
    }

    public String listar() {
        editando = false;
        return "/privado/pessoa/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        editandoTelefone = false;
        editandoCarro =false;
        objeto = new Pessoa();
    }

    public void alterar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoTelefone = false;
            editandoCarro =false;
            } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.geMensagemErro(e));
        }

    }
    
    
    public void excluir(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemErro("Erro a remover objeto: " + Util.geMensagemErro(e));
        }
    }

    public void salvar() {
        try {
            if (objeto.getId() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Sucesso ao persistir objeto");
            editando = false;
            editandoTelefone = false;
            editandoCarro =false;
            } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir: " + Util.geMensagemErro(e));
        }
    }

    public void novoTelefone() {
        telefone = new Telefone();
        editandoTelefone = true;
    }

    public void salvarTelefone() {
        if (telefone.getId() == null) {
            objeto.adicionarTelefone(telefone);
        }
        editandoTelefone = false;
        Util.mensagemInformacao("Telefone persistido com sucesso!");
    }

    public void alterarTelefone(int index) {
        telefone = objeto.getTelefones().get(index);
        editandoTelefone = true;
    }

    public void excluirTelefone(int index) {
        objeto.removerTelefone(index);
        Util.mensagemInformacao("Telefone removido com sucesso!");
    }
 
    public void novoCarro() {
        editandoCarro = true;
    }

    public void salvarCarro() {
        objeto.getCarros().add(carro);
        editandoCarro = false;
        Util.mensagemInformacao("Carro adicionado com sucesso!");
    }

    public void excluirCarro(Carro obj) {
        objeto.getCarros().remove(obj);
        Util.mensagemInformacao("Carro removido com sucesso!");
    }

    public Pessoa getObjeto() {
        return objeto;
    }

    public void setObjeto(Pessoa objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public PessoaDAO<Pessoa> getDao() {
        return dao;
    }

    public void setDao(PessoaDAO<Pessoa> dao) {
        this.dao = dao;
    }

    public Boolean getEditandoTelefone() {
        return editandoTelefone;
    }

    public void setEditandoTelefone(Boolean editandoTelefone) {
        this.editandoTelefone = editandoTelefone;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public CarroDAO<Carro> getDaoCarro() {
        return daoCarro;
    }

    public void setDaoCarro(CarroDAO<Carro> daoCarro) {
        this.daoCarro = daoCarro;
    }

    public Boolean getEditandoCarro() {
        return editandoCarro;
    }

    public void setEditandoCarro(Boolean editandoCarro) {
        this.editandoCarro = editandoCarro;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    
}