package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author lber
 */
//Classe respoável por setar mensagens no contexto da aplicação
public class MessageUtil {

    public static void MensagemErro(String mensagem) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public static void MensagemSucesso(String mensagem) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public static void MensagemPerigo(String mensagem) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

}
