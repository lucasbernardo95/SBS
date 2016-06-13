package validation;

import dao.UsuarioDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import model.Usuario;
import util.SessionUtil;
import util.ErroSistema;
import util.MessageUtil;

/**
 * @author lber
 */
@RequestScoped
@ManagedBean(name = "validador")
public class ValidadorAcesso implements Serializable {

    UsuarioDAO usuDAO;
    private String login;
    private String senha;

    /*Esse método irá fazer a validação do login do usuario.
    Possui uma lista dos usuarios vindos do banco de dados
    para que sejam comparados todos os logins e senhas com o que foi informado
    nos campos da página de login. Se for forem válidos, o susuário será 
    redirecionado para o entendero 'restrita/bemvindo'. Caso contrário o mesmo 
    permanece na página de login.
    
    Quando o usuário é validado, ou seja, quando o login informado é válido a 
    requisisão pasará pelo método doFilter da classe ControleAceso e se o mesmo
    tiver uma sessão válida, só assim o mesmo será redirecionado.*/
    public String autenticar() throws ErroSistema {

        /**
         * instancia um novo objeto usuarioDao para que seja possível acessar
         * seus e chama o método buscar. esse retornará uma lista com todos os
         * usuários contidos no banco, então serão armazenado no objeto list,
         * chamado de usuario e então haverá uma iteração com esses valores para
         * comparar os logins e senhas.
         */
        usuDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuDAO.listar();

        for (Usuario u : usuarios) {

            if (senha.equals(u.getSenha()) && login.equals(u.getLogin())) {

                if ("desenvolvedor".equals(u.getTipo())) {

                    SessionUtil.setParamSession("usuario-logado", u);
                    MessageUtil.MensagemSucesso("Seja bem-vindo " + u.getNome());
                    return "/logado/developer/pageDeveloper.xhtml?faces-redirect=true";

                } else if ("administrador".equals(u.getTipo())) {
                    SessionUtil.setParamSession("usuario-logado", u);
                    MessageUtil.MensagemSucesso("Seja bem-vindo " + u.getNome());
                    return "/logado/admin/gerenciar.xhtml?faces-redirect=true";
                }
                //Seta o usuário na sessão
                SessionUtil.setParamSession("usuario-logado", u);

                /*seta uma mensagem de boas vindas com o nome do usuário.*/
                MessageUtil.MensagemSucesso("Seja bem-vindo " + u.getNome());

                /*quando for redirecionado para o filtro para fazer a verificação*/
                return "/logado/cliente/mypage.xhtml?faces-redirect=true";

            }
        }
        //Se percorrer todo o laço e não houver nenhum usuário com o login e senha informados informa uma mensagem de erro para o usuário e sai do método
        MessageUtil.MensagemErro("Usuário ou senha inválidos!");
        return null;
    }

    /**
     * Função para logar na página de busca por software. Para que um usuário
     * possa comentar e avaliar um softwareesse precisa estar logado no sistema.
     * A função irá verificar se o usuário que está tentando logar é válido, se
     * for, o mesmo será redirecionado para a página contento os dados do
     * software, se não, permanece na página atual.
     */
    
        private String url = "";

    public String baixar(){
        
        return url;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void logarComentar() throws ErroSistema {

        /**
         * instancia um novo objeto usuarioDao para que seja possível acessar
         * seus e chama o método buscar. esse retornará uma lista com todos os
         * usuários contidos no banco, então serão armazenado no objeto list,
         * chamado de usuario e então haverá uma iteração com esses valores para
         * comparar os logins e senhas.
         */
        usuDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuDAO.listar();

        for (Usuario u : usuarios) {

            if (senha.equals(u.getSenha()) && login.equals(u.getLogin())) {
                //Seta o usuário na sessão
                SessionUtil.setParamSession("usuario-logado", u);
                return;
            }
        }
        //Se percorrer todo o laço e não houver nenhum usuário com o login e senha informados informa uma mensagem de erro para o usuário e sai do método
        MessageUtil.MensagemErro("Usuário ou senha inválidos!");
    }

    //Remove o usuário da sessão e o redireciona para a página de login
    public String logout() {
        SessionUtil.invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
    
    public void sair(){
        SessionUtil.invalidateSession();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
