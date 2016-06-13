package dao;

import java.util.List;
import model.Software;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author lber
 */
public class SoftwareDAO extends GenericDAO<Software>{
    
    public List<Software> buscaSoftwarePorUsuario(Integer idusuario){
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query consulta = sessao.createQuery("from Software s where s.usuario = :parametro");
        consulta.setString("parametro", ""+idusuario);
        List<Software> lista = (List<Software>) consulta.list();
        sessao.close();
        return lista;
    }
    
    public List<Software> buscaSoftwareComComentario(){
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query consulta = sessao.createQuery("select c.software FROM Software s, Comentario c WHERE s = c.software AND c.nota IS NOT NULL");
        
        List<Software> lista = (List<Software>) consulta.list();
        sessao.close();
        return lista;
    }
}