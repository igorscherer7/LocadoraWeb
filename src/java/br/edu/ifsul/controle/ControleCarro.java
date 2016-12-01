package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CarroDAO;
import br.edu.ifsul.modelo.Carro;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;


@Named(value = "controleCarro")
@SessionScoped
public class ControleCarro implements Serializable {
    @EJB
    private CarroDAO<Carro> dao;
    private Carro objeto;
    private Boolean editando;
    
    public ControleCarro(){        
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/carro/listar?faces-redirect=true";
    }
    
    public void novo(){
        editando = true;
        objeto = new Carro();
    }
    
    public void alterar(String modelo){
      try {
            objeto = dao.getObjectById(modelo); 
            editando = true;
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: "+Util.geMensagemErro(e));            
        }                
        
    }
    
    public void excluir(String modelo){
       try {
            objeto = dao.getObjectById(modelo);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");            
        } catch(Exception e){
            Util.mensagemErro("Erro a remover objeto: "+Util.geMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {            
            dao.persist(objeto);            
            Util.mensagemInformacao("Sucesso ao persistir objeto");  
            editando = false;        
        } catch(Exception e){
            Util.mensagemErro("Erro ao persistir: "+Util.geMensagemErro(e));            
        }                
    }
    
    public Carro getObjeto() {
        return objeto;
    }

    public void setObjeto(Carro objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public CarroDAO<Carro> getDao() {
        return dao;
    }

    public void setDao(CarroDAO<Carro> dao) {
        this.dao = dao;
    }
}
