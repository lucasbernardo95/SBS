package beans;

import dao.ComentarioDAO;
import dao.SoftwareDAO;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Comentario;
import model.Software;
import model.Usuario;
import util.ErroSistema;
import util.MessageUtil;
import util.SessionUtil;

/**
 *
 * @author lber
 */
@SuppressWarnings("serial")
@ManagedBean(name = "comentarioB")
@ViewScoped
public class ComentarioBean implements Serializable {

    private ComentarioDAO dao;
    private Comentario entidade;
    private List<Comentario> listaComentarios;
    private Integer nota;
    private String campoComentario;
    private Software software;
    private Usuario usuario;

    public void novo() {
        entidade = new Comentario();
    }

    public void novoDAO() {
        if (dao == null) {
            dao = new ComentarioDAO();
        }
    }

    public void recuperaSoftware(ActionEvent evento) {
        this.software = (Software) evento.getComponent().getAttributes().get("softwareParaComentar");
        this.usuario = (Usuario) SessionUtil.getParamSession("usuario-logado");
    }

    public void comentar() {

        if (this.software == null || this.usuario == null) {
            MessageUtil.MensagemPerigo("Operação inválida!\nUsuário ou Software podem ser nulos!");
            return;
        } else {

            novo();//intancia o objeto comentário
            if (this.usuario.getTipo().equals("desenvolvedor")) {
                nota = null;
            }

            entidade.setComentario(campoComentario);//seta o comentário feito ao objeto comentário
            entidade.setUsuario(usuario);//seta o usuário que fez o comentário
            entidade.setSoftware(software);//seta o software ao qual o comentário se refere
            entidade.setNota(nota);
            novoDAO();
            try {
                dao.salvar(entidade);
                MessageUtil.MensagemSucesso("Seu comentário agora pode ser visto na página de comentários!");
                calculaMedia();//sempre que surgir um novo comentário a média é recalculada
            } catch (ErroSistema ex) {
                MessageUtil.MensagemErro("Erro ao tentar salvar o comentário!\n" + ex);
            }
        }
    }

    /**
     * Calcula a média para todos os software que tenham comentários
     */
    private void calculaMedia() {
        novoDAO();
        SoftwareDAO fdao = new SoftwareDAO();
        List<Software> sof = fdao.buscaSoftwareComComentario();
        List<Comentario> lista = dao.buscarComentariosComNota();
        Integer tt = 0;//armazena a média
        Integer cont = 0;//total de comentários de um software
        for (Software s : sof) {
            cont = 0;
            for (Comentario c : lista) {
                if (s.getId() == c.getSoftware().getId()) {
                    tt += c.getNota();
                    cont++;
                }
            }
            if (cont > 0) {
                tt = tt / cont;
                s.setMedia(tt);
                s.setTotalVotos(cont);
                try {
                    fdao.merge(s);
                } catch (ErroSistema ex) {
                    MessageUtil.MensagemErro("Erro inesperado!\nCausa: " + ex);
                }
            } else {
                s.setMedia(null);
                s.setTotalVotos(null);
                try {
                    fdao.merge(s);
                } catch (ErroSistema ex) {
                    MessageUtil.MensagemErro("Erro inesperado!\nCausa: " + ex);
                }
            }
            tt = 0;
        }
    }

    /**
     * Esse método recupera o software selecionado na tabela e passa o id desse
     * para o método buscaComentariosPorSoftware que retorna uma lista dos
     * comentários do software selecionado. Esse retorno será atribuído a
     * listaComentarios que será usado em uma tabela para exibir os comentários
     * do software selecionado.
     *
     * @param evento evento gerado quando o usuário selecionar um software na
     * tabela
     */
    public void mostrarComentariosDoSoftware(ActionEvent evento) {
        this.software = (Software) evento.getComponent().getAttributes().get("softwareParaComentar");
        novoDAO();
        listaComentarios = dao.buscaComentariosPorSoftware(software.getId());
    }
    
    /**
     * REcalcula a média dos software cadastrados, porém percorrendo a lista com todos os software que tenham ou não comentários
     */

    private void calculaMediaPosExclusao() {
        try {
            novoDAO();
            SoftwareDAO fdao = new SoftwareDAO();
            List<Software> sof = fdao.listar();

            List<Comentario> lista = dao.buscarComentariosComNota();
            Integer tt = 0;
            Integer cont = 0;
            for (Software s : sof) {
                cont = 0;
                for (Comentario c : lista) {

                    if (s.getId() == c.getSoftware().getId()) {
                        tt += c.getNota();
                        cont++;
                    }

                }
                if (cont > 0) {
                    tt = tt / cont;
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
        } catch (ErroSistema ex) {
            Logger.getLogger(ComentarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirComentario(ActionEvent evento) {
        try {
            Comentario comentario = (Comentario) evento.getComponent().getAttributes().get("comentarioSelecionado");
            novoDAO();
            //quando for excluir um comentário, o software ao qual ele se refere deve ter a média recalculada
            Software s = comentario.getSoftware();
            //deleta o comentário
            dao.deletar(comentario);
            
            if (s.getTotalVotos() != null || s.getTotalVotos() > 1) {
                calculaMediaPosExclusao(); 
            } else {
                s.setMedia(null);
                s.setTotalVotos(null);
                SoftwareDAO fdao = new SoftwareDAO();
                fdao.merge(s);
            }
            
            MessageUtil.MensagemSucesso("Comentário excluído com sucesso\n");
        } catch (ErroSistema ex) {
            MessageUtil.MensagemErro("Erro ao tentar apagar o comentário\nCausa:\n" + ex);
        }
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Comentario getEntidade() {
        return entidade;
    }

    public void setEntidade(Comentario entidade) {
        this.entidade = entidade;
    }

    public String getCampoComentario() {
        return campoComentario;
    }

    public void setCampoComentario(String campoComentario) {
        this.campoComentario = campoComentario;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public Software getSoftware() {
        return software;
    }

}
