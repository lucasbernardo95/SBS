package model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lber
 */
@Entity
@Table(name = "Software")
public class Software implements Serializable{

    private static final long serialVersionUID = 123456L;

    @Id
    @GeneratedValue
    @Column(name = "id_software")
    private Integer id;

    @Column(name = "nome", length = 20, nullable = false, unique = true)
    @NotNull(message = "Informe o nome do software!")
    @Size(min = 5, max = 20, message = "O nome deve conter entre 5 e 20 dígitos")
    private String nome;

    @Column(name = "descricao", length = 256, nullable = false, unique = true)
    @NotNull(message = "Informe uma descrição para software!")
    private String descricao;
    
    @Column(name = "categoria", length = 20, nullable = false)
    @NotNull(message = "Informe uma categoria para software!")
    private String categoria;
    
    @Column(name = "url", length = 400, nullable = true)
    private String url;
    
    @Column(name = "media", nullable = true)
    private Integer media;//alterado para integer para que o rating na view possa funcionar
    
    @Column(name = "total_votos", nullable = true)
    private Integer totalVotos;
    
    @ManyToOne(optional = true) 
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @OneToMany( fetch = FetchType.LAZY)
    private List<Comentario> listaComentario;

    public Software(Integer id, String nome, String descricao, String categoria, String url, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.url = url;
        this.usuario = usuario;
    }

    public Software() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getMedia() {
        return media;
    }

    public void setMedia(Integer media) {
        this.media = media;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final Software other = (Software) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Integer totalVotos) {
        this.totalVotos = totalVotos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
