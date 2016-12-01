package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Locacao;
import java.io.Serializable;
import javax.ejb.Stateful;


@Stateful
public class LocacaoDAO<T> extends DAOGenerico<Locacao> implements Serializable {

    public LocacaoDAO() {
        super();
        super.setClassePersistente(Locacao.class);
        super.setOrdem("carro");
    }
           
     @Override
    public Locacao getObjectById(Integer id) throws Exception {
        Locacao obj = (Locacao) super.getEm().find(super.getClassePersistente(), id);
        obj.getColaboradores().size();
        return obj;
    }       
}
