package com.jhs.animatomo.service;
import com.jhs.animatomo.exceptions.AnimeBadRequestException;
import com.jhs.animatomo.exceptions.AnimeException;
import com.jhs.animatomo.exceptions.AnimeNotFoundException;
import com.jhs.animatomo.model.Anime;
import com.jhs.animatomo.repository.CategoriaRepository;
import com.jhs.animatomo.repository.AnimeRepository;
import com.jhs.animatomo.util.ImageUtils;
import com.jhs.animatomo.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
@Service
public class AnimeService {
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    public List<Anime> getAllAnimes() {
        return animeRepository.findAll();
    }

    public Anime createAnime(String nombre, String sinopsis, Long categoria_id, MultipartFile imagenNombre) throws IOException, AnimeException {
        // Verificar si los campos obligatorios están presentes
        if (nombre == null || nombre.isEmpty())
            throw new AnimeBadRequestException("Debe introducirse el nombre");
        if (imagenNombre == null || imagenNombre.isEmpty())
            throw new AnimeBadRequestException("Debe introducirse la imagen");
        if (sinopsis == null || sinopsis.isEmpty())
            throw new AnimeBadRequestException("Debe introducirse la sinopsis");

        // Verificar si el ID de la categoria es válido
        if (categoria_id == null || categoria_id <= 0) {
            throw new AnimeException("El ID de la categoria proporcionado no es válido: " + categoria_id);
        }

        // Verificar si el ID de la categoria existe en la base de datos
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(categoria_id);
        if (!categoriaOptional.isPresent()) {
            throw new AnimeException("El ID de la categoria proporcionado no existe en la base de datos: " + categoria_id);
        }

        // Crear una instancia de Anime con los datos proporcionados y el ID de la categoria
        Anime anime = new Anime(nombre, sinopsis, null, null, categoria_id);

        // Verificar si se proporcionó una imagen
        if (imagenNombre != null && !imagenNombre.isEmpty()) {
            anime.setImagenNombre(imagenNombre.getOriginalFilename());
            anime.setImagenBlob(imagenNombre.getBytes()); // Guardar los bytes de la imagen directamente

            // Opcionalmente, también puedes guardar la imagen en el sistema de archivos
            Path dirImg = Paths.get("src//main//resources//static//img");
            String rutaAbsoluta = dirImg.toFile().getAbsolutePath();

            try {
                // Guardar la imagen en el sistema de archivos
                byte[] bytesImg = imagenNombre.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagenNombre.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException e) {
                // Manejar cualquier error de escritura
                throw new AnimeException("Error al escribir la imagen");
            }
        }

        // Guardar el anime en la base de datos
        return animeRepository.save(anime);
    }


    public Optional<Anime> getAnimeById(long id) {
        return Optional.ofNullable(animeRepository.findById(id).orElseThrow(()-> new AnimeNotFoundException("No se ha encontrado el anime con id:" + id)));
    }

    public Anime updateAnime(Anime anime, MultipartFile file) throws IOException, AnimeException {
        if (!file.isEmpty()) {
            anime.setImagenNombre(file.getOriginalFilename());
            anime.setImagenBlob(ImageUtils.compressImage(file.getBytes()));

            // Ruta donde se guardará la imagen
            Path dirImg = Paths.get("src//main//resources//static//img");
            String rutaAbsoluta = dirImg.toFile().getAbsolutePath();

            try {
                // Guardar la imagen en el sistema de archivos
                byte[] bytesImg = file.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException e) {
                // Manejar cualquier error de escritura
                throw new AnimeException("Error de escritura en el archivo");
            }
        }

        return animeRepository.save(anime);
    }

    public void deleteAnimeById(long id) {
        animeRepository.deleteById(id);
    }
    public byte[] descargarFoto(long id) throws DataFormatException, IOException {
        Anime anime = animeRepository.findById(id).orElse(null);
        return anime != null ? ImageUtils.decompressImage(anime.getImagenBlob()) : null;
    }
}