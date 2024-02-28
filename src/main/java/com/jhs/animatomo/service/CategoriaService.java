package com.jhs.animatomo.service;

import com.jhs.animatomo.exceptions.AnimeNotFoundException;
import com.jhs.animatomo.model.Categoria;
import com.jhs.animatomo.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return  categoriaRepository.findAll();
    }

    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> getCategoriaById(long id) {

        return Optional.ofNullable(categoriaRepository.findById(id).orElseThrow(() -> new AnimeNotFoundException("No se ha encontrado a la Categoria con id:" + id)));
    }

    public Categoria updateCategoria(Categoria categoria) {

        return categoriaRepository.save(categoria);
    }

    public void deleteCategoriaById(long id) {

        categoriaRepository.deleteById(id);
    }

    public List<Categoria> getEjemplosByNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }
}