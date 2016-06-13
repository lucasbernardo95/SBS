package util;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Classe responsável por mandar, pegar e remover objetos da a sessão
 * @author lber
 */
public class SessionUtil implements Serializable{
    
    /*Recupera a sessão do contexto, se existir e retorna.*/
    public static HttpSession getSession(){
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return sessao;
    }
    
    /*Método responsável por setar um objeto na sessão atual do usuário.
    Recebe o primeiro parâmetro, que refere-se ao nome do atributo a ser setado
    e o segundo trata-se do objeto que deseja-se setar na sessão.*/
    public static void setParamSession(String param, Object value){
        getSession().setAttribute(param, value);
    }

    public SessionUtil() {
    }
    /**Método responsável por retornar um objeto contindo na sessão.
    * Recebe o nome do atributo a ser recuperado e retorna o tal, se existir.
    */
    public static Object getParamSession(String param){
        return getSession().getAttribute(param);
    }
    
    /**
     * Método para remover um atributo da sessão. Recebe o nome do atributo 
     * e remove da sessão do usuário.
     */
    
    public static void removeParamSession(String param){
        getSession().removeAttribute(param);
    }
    
    /**
     * Função para invalidar a sessão do usuário. Usada para quando o usuário
     * desejar fazer logout do da sessão.
     */
    
    public static void invalidateSession(){
        getSession().invalidate();
    }
    
}