package com.jhs.animatomo.controller;
import com.jhs.animatomo.model.Categoria;
import com.jhs.animatomo.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;
    @GetMapping("/Categorias")
    public List<Categoria> getAllCategorias(){
        return categoriaService.getAllCategorias();
    }
    @PostMapping("/Categorias")
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria){
        Categoria createdCategoria = categoriaService.createCategoria(categoria);
        return new ResponseEntity<>(createdCategoria, HttpStatus.CREATED);
    }
    @Operation(description = "Obtienes una categoria", tags = {"categorias"})
    @Parameter(description = "ID del categoria", required = true, example = "1")
    @ApiResponse(description = "Categoria encontrado", responseCode = "200")
    @ApiResponse(description = "Categoria no encontrado", responseCode = "404")
    @GetMapping("/Categorias/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable long id){
        Optional<Categoria> optionalCategoria = categoriaService.getCategoriaById(id);

        if (optionalCategoria.isPresent()){
            optionalCategoria = categoriaService.getCategoriaById(id);
            return new ResponseEntity<>(optionalCategoria.get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/Categorias/{id}")
    public ResponseEntity<Categoria> UpdateCategoria(@PathVariable long id, @RequestBody Categoria categoria){
        Optional<Categoria> optionalCategoria = categoriaService.getCategoriaById(id);
        if (((Optional<?>) optionalCategoria).isPresent()){
            Categoria existingCategoria = optionalCategoria.get();
            existingCategoria.setNombre(categoria.getNombre());
            existingCategoria.setFecha_creacion(categoria.getFecha_creacion());
            existingCategoria.setFecha_modificacion(categoria.getFecha_modificacion());

            Categoria updateCategoria = categoriaService.updateCategoria(existingCategoria);
            return new ResponseEntity<>(updateCategoria,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Categorias/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable long id){
        Optional<Categoria> optionalCategoria = categoriaService.getCategoriaById(id);
        if (optionalCategoria.isPresent()){
            categoriaService.deleteCategoriaById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/Categorias/nom")
    public ResponseEntity<List<Categoria>> getEjemplosPorNombre(@RequestParam String nombre){
        List<Categoria> categorias = categoriaService.getEjemplosByNombre(nombre);
        if (!categorias.isEmpty()){
            return new ResponseEntity<>(categorias,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}