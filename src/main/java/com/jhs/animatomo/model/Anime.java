package com.jhs.animatomo.model;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Animes")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "sinopsis", nullable = false, length = 255)
    private String sinopsis;
    @Column(name = "imagen_nombre", nullable = false, length = 50)
    private String imagen_nombre;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fecha_creacion;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "imagen_blob", columnDefinition = "LONGBLOB")
    private byte [] imagen_blob;

    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn (name = "categoria_id")
    @JsonBackReference
    private Categoria categoria;

    public Anime() {

    }
    public Anime(String nombre, String sinopsis, String imagen_nombre, LocalDateTime fecha_creacion, Long categoria_id) {
    this.nombre = nombre;
    this.sinopsis = sinopsis;
    this.imagen_nombre = imagen_nombre;
    this. fecha_creacion = fecha_creacion;
        if (categoria == null) {
            this.categoria = new Categoria();
        }
        this.categoria.setId(categoria_id);
    }

    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", imagen_nombre='" + imagen_nombre + '\'' +
                ", fecha_creacion='" + fecha_creacion + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getImagen_nombre() {
        return imagen_nombre;
    }
    public void setImagenNombre(String imagen_nombre) {
        this.imagen_nombre = imagen_nombre;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    public byte[] getImagenBlob() {
        return imagen_blob;
    }
    public void setImagenBlob(byte[] imagen_blob) {
        this.imagen_blob = imagen_blob;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
