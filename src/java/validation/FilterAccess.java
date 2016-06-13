package validation;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

@WebFilter(urlPatterns = "/logado/*")//vai filtrar tudo o que estiver dentro da pasta expecificada
public class FilterAccess implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        Usuario u = null; 
        //recupera a sessão, caso exista
        HttpSession sesison = ((HttpServletRequest) req).getSession(false);

        //se houver uma sessão válida, reucpera da sessão o objeto usuário-logado
        //que, pode ter sido setado no loginbean, se ouver uma sessão válida.
        if (sesison != null) {
            u = (Usuario) sesison.getAttribute("usuario-logado");
        }

        // se o usuário for nulo, o mesmo será redirécionado para login
        if (u == null) { 
            String contextPath = ((HttpServletRequest) req).getContextPath();
            ((HttpServletResponse) res).sendRedirect(contextPath + "/login.xhtml");
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

}