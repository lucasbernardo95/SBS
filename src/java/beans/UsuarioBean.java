package beans;

import dao.UsuarioDAO;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import model.Usuario;
import util.ErroSistema;
import util.MessageUtil;
import util.SessionUtil;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lber
 */
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

    private UsuarioDAO dao;
    private Usuario entidade;
    private Usuario usuarioSessao;
    private List<Usuario> listaUsuario;
    private boolean mostraCadastro = false;

    /**
     * quando essa variável for true, será renderizada, na pagina do admin a
     * tabela de gerêcia de usuários. O mesmo se aplica às variáveis booleanas
     * abaixo.
     */
    private boolean gerenciarUsuario = true;
    private boolean gerenciarSoftware = false;
    private boolean editarDados = false;
    private boolean cadastrarAdmin = false;

    /**
     * Quando o usuário clicar em cadastre-se, o objeto entidade, do tipo
     * usuário será instanciado para. Além disso, o atributo cadastrar será
     * setado como true, para que o painel com o formulário de cadastro seja
     * renderizado.
     */
    public void novo() {
        if (entidade == null) {
            entidade = new Usuario();
        }
        mostraCadastro = true;
    }

    public void novoDao() {
        if (dao == null) {
            dao = new UsuarioDAO();
        }
    }

    public void init() {
        usuarioSessao = (Usuario) SessionUtil.getParamSession("usuario-logado");
        MessageUtil.MensagemSucesso("Seja bem-vindo(a) " + usuarioSessao.getNome());
    }

    //Método para iniciar a lista dos usuários na página go amin
    public void iniciaListaUsuario() {
        try {
            usuarioSessao = (Usuario) SessionUtil.getParamSession("usuario-logado");
            MessageUtil.MensagemSucesso("Seja bem-vindo(a) " + usuarioSessao.getNome());
            novoDao();
            listaUsuario = dao.listar();
        } catch (ErroSistema ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cadastrar() throws ErroSistema {
        novoDao();
        try {
            //recebe a lista de usuários do banco de dados
            List<Usuario> usuarios = dao.listar();
            //só irá fazer a verificação se ouver algum usuário cadastrado
            if (!usuarios.isEmpty()) {
                for (Usuario u : usuarios) {
                    if (u.getLogin().equals(entidade.getNome())) {
                        MessageUtil.MensagemErro("Login já cadastrado!");
                        return;
                    } else if (u.getEmail().equals(entidade.getEmail())) {
                        MessageUtil.MensagemErro("E-mail já cadastrado!");
                        return;
                    } else if (entidade.getSenha().equals("")) {
                        MessageUtil.MensagemErro("A senha não pode ser nula");
                        return;
                    }
                }
            }
            dao.salvar(entidade);// salva o usuário no banco
            MessageUtil.MensagemSucesso(entidade.getNome() + " seu cadastro foi efetivado com sucesso!");
            mostraCadastro = false;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao tentar cadastrar", ex);
        }
    }

    public void atualiza() {
        try {
            entidade = (Usuario) SessionUtil.getParamSession("usuario-update");
            novoDao();
            dao.merge(entidade);
            MessageUtil.MensagemSucesso("Atualizado com sucesso!");
        } catch (ErroSistema ex) {
            MessageUtil.MensagemErro("Erro ao tentar salvar as alterações!");
        }
    }

    /**
     * Quando o admin editar um usuário na tabela o <p:ajax event="cellEdit"/>
     * irá gerar um evento 'cellEdit', esse irá enviar o objeto editado para o
     * contexto da aplicação. Então o objeto que foi capturado será salvo no
     * banco de dados para salvar as alterações.
     *
     * @param evento é gerado quando o admin alterar alguma célula na tabela.
     */
    public void linhaEditada(RowEditEvent evento) throws ErroSistema {
        Usuario usuario = (Usuario) evento.getObject();
        if (!usuario.getNome().equals("") || !usuario.getEmail().equals("") || !usuario.getLogin().equals("") || !usuario.getTipo().equals("")) {
            novoDao();
            entidade = usuario;
            dao.merge(entidade);
            MessageUtil.MensagemSucesso("Atualizado com sucesso!");

        } else {
            MessageUtil.MensagemPerigo("Os campos não podem ser nulos");
        }
    }

    public void cancelaAlteracao(RowEditEvent evento) {
        Usuario usuario = (Usuario) evento.getObject();
        MessageUtil.MensagemErro("cancelou" + usuario.getNome() + "\n" + usuario.getId());
    }

    public void excluir(ActionEvent evento) throws ErroSistema {
        try {
            novoDao();
            dao.deletar((Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado"));
            MessageUtil.MensagemSucesso("Excluído con sucesso!");
        } catch (RuntimeException ex) {
            MessageUtil.MensagemErro("Erro ao tentar excluir o usuário\nCausa: " + ex);
        }
    }

    /**
     * Método que recebe o valor da linha alterada
     *
     * @param evento      *
     *
     * public void teste(CellEditEvent evento){ Object anterior =
     * evento.getClass(); Object novo = evento.getNewValue();
     * //entidade.setNome(""+novo); MessageUtil.MensagemErro("anteriou: "+
     * anterior.getClass()+ "\nnovo " +novo);
    }
     */
    public void enviaUsuarioParaAtualizar() {
        SessionUtil.setParamSession("usuario-update", usuarioSessao);
    }

    public Object usuarioLogado() {
        return (SessionUtil.getParamSession("usuario-logado") != null);
    }

    public void exibeTabela(boolean usuario, boolean software, boolean dados, boolean admin) {
        gerenciarUsuario = usuario;
        gerenciarSoftware = software;
        editarDados = dados;
        cadastrarAdmin = admin;
    }

    public Usuario getEntidade() {
        return entidade;
    }

    public void setEntidade(Usuario entidade) {
        this.entidade = entidade;
    }

    public boolean isMostraCadastro() {
        return mostraCadastro;
    }

    public Usuario getUsuarioSessao() {
        return usuarioSessao;
    }

    public void setUsuarioSessao(Usuario usuarioSessao) {
        this.usuarioSessao = usuarioSessao;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public boolean isGerenciarUsuario() {
        return gerenciarUsuario;
    }

    public boolean isGerenciarSoftware() {
        return gerenciarSoftware;
    }

    public boolean isEditarDados() {
        return editarDados;
    }

    public boolean isCadastrarAdmin() {
        return cadastrarAdmin;
    }

}
