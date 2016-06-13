package beans;

import dao.SoftwareDAO;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import model.Software;
import model.Usuario;
import util.ErroSistema;
import util.MessageUtil;
import util.SessionUtil;

/**
 *
 * @author lber
 */
@SuppressWarnings("serialS")
@ManagedBean
@ViewScoped
public class SoftwareBean implements Serializable {

    private SoftwareDAO dao;
    private Software entidade;
    private List<Software> lista;
    private Usuario usuarioLogado;
    private boolean habilitaCadastro = false;
    
    @PostConstruct
    public void iniciar(){
        try {
            novoDAO();
            lista = dao.listar();
            if(lista == null || lista.size() <= 0){
                return;
            }
        } catch (ErroSistema ex) {
            MessageUtil.MensagemErro("Erro ao tentar preencher a tabelas!\nCausa: " + ex);
        }
    }
    public void init() throws ErroSistema {
        try {
            usuarioLogado = (Usuario) SessionUtil.getParamSession("usuario-logado");
            novoDAO();
            lista = dao.buscaSoftwarePorUsuario(usuarioLogado.getId());//Recebe a lista de softwares do banco
        } catch (RuntimeException ex) {
            MessageUtil.MensagemPerigo("Erro ao tentar exibir os dados na tabela");
        }
    }

    public void novo() {
        entidade = new Software();
        habilitaCadastro = true;
    }

    private void novoDAO() {
        if (dao == null) {
            dao = new SoftwareDAO();
        }
    }

    public void cadastrar() throws ErroSistema {
        novoDAO();
        try {
            List<Software> listaSoftware = dao.listar();
            if (!listaSoftware.isEmpty()) {
                for (Software u : listaSoftware) {
                    if (u.getNome().equals(entidade.getNome()) && u.getDescricao().equals(entidade.getDescricao()) && u.getCategoria().equals(entidade.getCategoria())) {
                        MessageUtil.MensagemPerigo("Erro!\nO software já foi cadastrado ou nenhuma alteração foi feita!");
                        return;
                    }
                }
            }
            //recupera o usuári oda sessão para saber quem o está cadastrando e seta ao software para salvá-lo
            Usuario usuario = (Usuario) SessionUtil.getParamSession("usuario-logado");
            entidade.setUsuario(usuario);
            dao.merge(entidade);//O método merge serve para alterar, se o objeto existir, caso contrário insere 
            MessageUtil.MensagemSucesso("Salvo com sucesso!");
            //lista = dao.listar();//sempre que salvar um novo software a lista será atualizada
            init();
        } catch (RuntimeException ex) {
            MessageUtil.MensagemErro("Erro ao tentar cadastrar\nCausa: "+ex);
        }
    }

    /**
     * @param evento é o evento que será gerado quando o usuário clicar em
     * excluir na tabela que exibe os software. Quando esse evento ocorrer o
     * f:attribute irá seta um atributo no contexto da aplicação que será o
     * software que foi clicado na tabela, e será setado com o nome de
     * softwareSelecionado. Então o método irá pegar o evento, desse evento pega
     * componente clicado e de todos os seus atributos, ele pega o elemento
     * softwareSelecionado.
     */
    public void excluir(ActionEvent evento) throws ErroSistema {
        try {
            entidade = (Software) evento.getComponent().getAttributes().get("softwareSelecionado");
            novoDAO();
            dao.deletar(entidade);
            MessageUtil.MensagemSucesso("Excluído con sucesso!");
            init();
        } catch (RuntimeException ex) {
            MessageUtil.MensagemErro("Erro ao tentar excluir o software\nCausa: " +ex);
        }
    }

    public void editar(ActionEvent evento) {
        habilitaCadastro = true;
        entidade = (Software) evento.getComponent().getAttributes().get("softwareSelecionado");
    }
    
    public void baixar(Software software){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(software.getUrl());
        } catch (IOException ex) {
            MessageUtil.MensagemPerigo("Link quebrado!");
        }
    }
    
    public void enviaUsuarioParaAtualizar(){
        SessionUtil.setParamSession("usuario-update", usuarioLogado);
    }
    
    public List<Software> getLista() {
        return lista;
    }

    public Software getEntidade() {
        return entidade;
    }

    public void setEntidade(Software entidade) {
        this.entidade = entidade;
    }

    public boolean isHabilitaCadastro() {
        return habilitaCadastro;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
