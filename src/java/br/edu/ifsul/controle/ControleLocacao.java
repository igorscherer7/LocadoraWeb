package br.edu.ifsul.controle;

import br.edu.ifsul.dao.LocacaoDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.modelo.Colaborador;
import br.edu.ifsul.modelo.Locacao;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;


@Named(value = "controleLocacao")
@SessionScoped
public class ControleLocacao implements Serializable {

    @EJB
    private LocacaoDAO<Locacao> dao;
    private Locacao objeto;
    private Boolean editando;
    
    @EJB
    private PessoaDAO<Pessoa> daoPessoa;
    private Boolean editandoColaborador;
    private Colaborador colaborador;
  
    public ControleLocacao() {
        editando = false;
        editandoColaborador = false;
      
    }

    public String listar() {
        editando = false;
        return "/privado/locacao/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        editandoColaborador = false;
        objeto = new Locacao();
    }

    public void alterar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoColaborador = false;
          
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
            editandoColaborador = false;
            
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir: " + Util.geMensagemErro(e));
        }
    }

    public void novoColaborador() {
        colaborador = new Colaborador();
        editandoColaborador = true;
    }

    public void salvarColaborador() {
        if (colaborador.getId() == null) {
            objeto.adicionarColaborador(colaborador);
        }
        editandoColaborador = false;
        Util.mensagemInformacao("Colaborador persistido com sucesso!");
    }

    public void alterarColaborador(int index) {
        colaborador = objeto.getColaboradores().get(index);
        editandoColaborador = true;
    }

    public void excluirColaborador(int index) {
        objeto.removerColaborador(index);
        Util.mensagemInformacao("Colaborador removido com sucesso!");
    }
       

    public Locacao getObjeto() {
        return objeto;
    }

    public void setObjeto(Locacao objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public LocacaoDAO<Locacao> getDao() {
        return dao;
    }

    public void setDao(LocacaoDAO<Locacao> dao) {
        this.dao = dao;
    }

    public PessoaDAO<Pessoa> getDaoPessoa() {
        return daoPessoa;
    }

    public void setDaoPessoa(PessoaDAO<Pessoa> daoPessoa) {
        this.daoPessoa = daoPessoa;
    }

   
    public Boolean getEditandoColaborador() {
        return editandoColaborador;
    }

    public void setEditandoColaborador(Boolean editandoColaborador) {
        this.editandoColaborador = editandoColaborador;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}
