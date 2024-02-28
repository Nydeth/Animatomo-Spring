package com.jhs.animatomo.controller;

import com.jhs.animatomo.exceptions.AnimeException;
import com.jhs.animatomo.model.Categoria;
import com.jhs.animatomo.model.Anime;
import com.jhs.animatomo.service.AnimeService;
import com.jhs.animatomo.util.ImageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/api")
public class AnimeController {
    @Autowired
    private AnimeService animeService;


    @GetMapping("/Animes")
    public List<Anime> getAllAnimes() {
        return animeService.getAllAnimes();
    }

    @PostMapping(value = "/Animes", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Anime> createAnime(@RequestParam String nombre, @RequestParam String sinopsis, @RequestParam Long categoria_id, @RequestPart(name = "imagen_nombre", required = false) MultipartFile imagenNombre) throws IOException, AnimeException {
        // Verificar si el ID de la categoría es nulo o no válido
        if (categoria_id == null || categoria_id <= 0) {
            throw new AnimeException("Debe proporcionarse un ID de categoría válido");
        }

        // Llama al método createAnime del servicio pasando el ID de la categoría
        Anime createdAnime = animeService.createAnime(nombre, sinopsis, categoria_id, imagenNombre);

        return new ResponseEntity<>(createdAnime, HttpStatus.CREATED);
    }
    @Operation(description = "Obtienes un anime", tags = {"animes"})
    @Parameter(description = "ID del anime", required = true, example = "1")
    @ApiResponse(description = "Anime encontrado", responseCode = "200")
    @ApiResponse(description = "Anime no encontrado", responseCode = "404")
    @GetMapping("/Animes/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable long id) {
        Optional<Anime> optionalAnime = animeService.getAnimeById(id);

        if (optionalAnime.isPresent()) {
            return new ResponseEntity<>(optionalAnime.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/Animes/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Anime> updateAnime(@PathVariable long id, @RequestParam String nombre, @RequestParam String sinopsis, @RequestPart(name = "imagen_nombre", required = false) MultipartFile imagen_nombre) throws IOException, AnimeException {
        Optional<Anime> optionalAnime = animeService.getAnimeById(id);
        if (((Optional<?>) optionalAnime).isPresent()) {
            Anime existingAnime = optionalAnime.get();
            existingAnime.setNombre(nombre);
            existingAnime.setSinopsis(sinopsis);
            existingAnime.setImagenBlob(ImageUtils.compressImage(imagen_nombre.getBytes()));

            Anime updateAnime = animeService.updateAnime(existingAnime, imagen_nombre);
            return new ResponseEntity<>(updateAnime, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/Animes/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable long id) {
        animeService.deleteAnimeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/{id}/foto",produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> descargarFoto(@PathVariable Long id) throws DataFormatException, IOException {
        byte[] foto = animeService.descargarFoto(id);
        if (foto != null){
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(foto);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}