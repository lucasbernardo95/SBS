package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lber
 */
@Entity
@Table (name = "Comentario")
public class Comentario implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id_comentario")
    private Integer id;

    @Column(name = "comentario", length = 300, nullable = true)
    private String comentario;
    
    @Column(name = "nota", nullable = true)
    private Integer nota;
    
    @ManyToOne (optional = true) 
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @ManyToOne(optional = true) 
    @JoinColumn(name = "id_software")
    private Software software;

    public Comentario() {
    }

    public Comentario(Integer id, String comentario, Integer nota, Usuario usuario, Software software) {
        this.id = id;
        this.comentario = comentario;
        this.nota = nota;
        this.usuario = usuario;
        this.software = software;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Comentario other = (Comentario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}