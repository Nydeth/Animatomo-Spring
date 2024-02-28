package com.jhs.animatomo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name="categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "fecha_modificacion", nullable = false, length = 255)
    private Integer fecha_modificacion;
    @Column(name = "fecha_creacion", nullable = false)
    private Integer fecha_creacion;
    //@OneToMany(targetEntity = Anime.class, fetch = FetchType.LAZY,mappedBy = "categoria")
    @OneToMany (mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Anime> animes;

    public Categoria() {

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

    public Integer getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Integer fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public Integer getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Integer fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public List<Anime> getAnimes() {
        return animes;
    }

    public void setAnimes(List<Anime> animes) {
        this.animes = animes;
    }

    public Categoria(String nombre, Integer fecha_modificacion, Integer fecha_creacion){
        this.nombre = nombre;
        this.fecha_modificacion = fecha_modificacion;
        this.fecha_creacion = fecha_creacion;
    }
    @Override
    public String toString(){
        return "Categoria{" + "id" + id +
                ", nombre='" + nombre +'\''
                + ",fecha_modificacion" + fecha_modificacion
                + "fecha_creacion" + fecha_creacion +'}';
    }

}