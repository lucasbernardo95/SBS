/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import beans.SoftwareBean;
import dao.ComentarioDAO;
import dao.SoftwareDAO;
import dao.UsuarioDAO;
import java.util.List;
import model.Comentario;
import model.Software;
import model.Usuario;

/**
 *
 * @author lber
 */
public class ClasseParaTeste {

    public static void main(String[] args) throws ErroSistema {
        ComentarioDAO cdao = new ComentarioDAO();
        SoftwareDAO fdao = new SoftwareDAO();
//        UsuarioDAO dao = new UsuarioDAO();
//        Usuario usu = dao.buscar(3);
        List<Software> sof = fdao.listar();

        List<Comentario> lista = cdao.buscarComentariosComNota();
        Integer tt = 0;
        Integer cont = 0;
        for (Software s : sof) {
            cont = 0;
            for (Comentario c : lista) {
                
                if(s.getId() == c.getSoftware().getId()){
                    tt += c.getNota();
                    cont++;
                }
                
            }
            if(cont > 0){
                tt = tt/cont;
                s.setMedia(tt);
                s.setTotalVotos(cont);
                try {
                    fdao.merge(s);
                } catch (ErroSistema ex) {
                    throw new ErroSistema("Erro\nFalha ao tentar calcular a média");
                }
            } else {
            s.setMedia(null);
            s.setTotalVotos(null);
            try {
                    fdao.merge(s);
                } catch (ErroSistema ex) {
                    throw new ErroSistema("Erro\nFalha ao tentar calcular a média");
                }
            }
            System.out.println(cont);
            
            tt = 0;
        }
        
        for (Software d : sof) {
            System.out.println("Software: "+d.getNome() + " média "+d.getMedia()+" qtdVotos " + d.getTotalVotos());
        }
        
//        SoftwareDAO fdao = new SoftwareDAO();
//        List<Software> lf = fdao.buscaSoftwarePorUsuario(2);
//        for (Software f : lf) {
//            System.out.println("nome: "+f.getNome() + " id: "+ f.getUsuario().getId());
//        }
//        int i = 0;
//        UsuarioDAO dao = new UsuarioDAO();
//        do {
//            Usuario usu = new Usuario();
//            usu.setNome("lucas romao" + i);
//            usu.getNome();
//
//            dao.salvar(usu);
//            i++;
//        } while (i < 6);
        /* exemplo de inserção de dados
        do {
            Usuario usu = new Usuario();
            usu.setNome("lucas romao" + i);
            usu.getNome();
            
            dao.salvar(usu);
            i++;
        } while (i < 6);
        
        //Exemplo de listagem dos dados de uma tabela
        List<Usuario> result = dao.listar();
        for (Usuario usu : result) {
            System.out.println(usu.getNome() + " codigo = "+ usu.getCodigo());
        }
        //=============buscando um usuário específico===========
        Usuario usu = dao.buscar(23);
        System.out.println(usu.getNome() + " codigo = "+ usu.getCodigo());
         
        //=============testando o delete=======
        //primeiro busca o objeto que quer remover
        Usuario usu = dao.buscar(3);

        if (usu == null) {
            System.out.println("não achou");
        } else {

            System.out.println(usu.getNome() + " codigo = " + usu.getCodigo());
            dao.deletar(usu);//se o mesmo for encontrado, apaga
            System.out.println("apagou");
        }
        Usuario usu1 = dao.buscar(2);
        System.out.println(usu1.getNome() + " codigo = " + usu1.getCodigo());
        
        
        //Testando o update
        //Assim como o método deletar, é necessário buscar o dado que quero editar antes
        Usuario usu3 = dao.buscar(4);

        if (usu3 == null) {
            System.out.println("não achou");
        } else {

            System.out.println(usu3.getNome() + " codigo = " + usu3.getCodigo());
            //altera os dados desejados
            usu3.setNome("Rei da cacimbinha 2");
            dao.editar(usu3);//manda executar o update
            System.out.println("alterou");
            System.out.println(usu3.getNome() + " codigo = " + usu3.getCodigo());
        }Usuario usu = dao.buscar(2);

            System.out.println(usu.getNome() + " codigo = " + usu.getCodigo());
         */

    }

}
