package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Carro;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;


@Stateful
public class CarroDAO<T> extends DAOGenerico<Carro> implements Serializable {

    public CarroDAO() {
        super();
        super.setClassePersistente(Carro.class);
        super.setOrdem("modelo");
    }
    
   public T getObjectById(String modelo) throws Exception {
        return (T) super.getEm().find(Carro.class,modelo);
    }  
   
    @Override
    public List<Carro> getListaObjetos() {
        String jpql = "from " + super.getClassePersistente().getSimpleName();
        String where = "";
        if (super.getFiltro().length() > 0) {
            if (super.getOrdem().equals("id")) {
                try {
                    Integer.parseInt(super.getFiltro());
                    where += " where " + super.getOrdem() + " = '" + super.getFiltro() + "' ";
                } catch (Exception e) {

                }
            } else {
                where += " where upper(" + super.getOrdem() + ") like '"
                        + super.getFiltro().toUpperCase() + "%' ";
            }
        }
        jpql += where;
        jpql += " order by " + super.getOrdem();
        super.setTotalObjetos(super.getEm().createQuery("select modelo from "+super.getClassePersistente().getSimpleName()+
                where+ " order by "+super.getOrdem()).getResultList().size());
        return super.getEm().createQuery(jpql).setFirstResult(super.getPosicaoAtual()).
                setMaxResults(super.getMaximoObjetos()).getResultList();
    }   
}
