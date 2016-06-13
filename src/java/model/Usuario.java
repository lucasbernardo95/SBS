package model;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author lber
 */
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nome", length = 30, nullable = true)
    @NotNull(message = "Insira seu nome!")
    @Size(min = 5, max = 30, message = "O nome deve conter entre 5 e 30 dígitos")
    private String nome;

    @Column(name = "login", length = 20, nullable = false, unique = true)
    @NotNull(message = "Informe um login!")
    @Size(min = 3, max = 20, message = "O nome deve conter entre 3 e 20 dígitos")
    private String login;

    @Column(name = "email", length = 30, nullable = false, unique = true)
    @NotNull(message = "O E-mail não pode ser nulo!")
    @Pattern(regexp ="[a-zA-Z0-9]+@+[a-zA-Z0-9]+[.]+com", message = "Informe um e-mail no formato xxx@xxx.com, ou xxx@xxx.com")
    private String email;

    @Column(name = "senha", length = 15, nullable = false)
    @NotNull(message = "A senha não pode ser nula!")
    @Size(min = 3, max = 15, message = "A senha deve conter entre 3 e 15 dígitos")
    private String senha;

    @Column(name = "tipo", length = 13, nullable = false)
    @NotNull(message = "Informe um tipo de usuário")
    private String tipo;
    
    @OneToMany( fetch = FetchType.LAZY)
    private List<Software> listaSoftware;
    
    @OneToMany( fetch = FetchType.LAZY)
    private List<Comentario> listaComentario;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String login, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Software> getListaSoftware() {
        return listaSoftware;
    }

    public List<Comentario> getListaComentario() {
        return listaComentario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
