/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Comentario;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author lber
 */
public class ComentarioDAO extends GenericDAO<Comentario>{
    public List<Comentario> buscaComentariosPorSoftware(Integer idsoftware){
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query consulta = sessao.createQuery("from Comentario c where c.software = :parametro");
        consulta.setString("parametro", ""+idsoftware);
        List<Comentario> lista = (List<Comentario>) consulta.list();
        sessao.close();
        return lista;
    }
    
    public List<Comentario> buscarComentariosComNota(){
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query consulta = sessao.createQuery("from Comentario c where c.nota is not null");
        List<Comentario> lista = (List<Comentario>) consulta.list();
        sessao.close();
        return lista;
    }
    
}
