package org.vfl.vintago.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vfl.vintago.entity.Artikel;
import org.vfl.vintago.repository.ArtikelRepository;

import java.util.List;

@RestController
@RequestMapping("api/artikel")
public class ArtikelController {

    private final ArtikelRepository artikelRepository;

    public ArtikelController(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @GetMapping
    public ResponseEntity<List<Artikel>> getAll() {
        return ResponseEntity.ok(artikelRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Artikel> createArtikel(@RequestBody Artikel artikel) {
        Artikel savedArtikel = artikelRepository.save(artikel);
        return ResponseEntity.ok(savedArtikel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtikelById(@PathVariable Long id) {
        artikelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artikel> updateArtikel(@PathVariable Long id, @RequestBody Artikel artikelDetails) {
        return artikelRepository.findById(id)
                .map(existingArtikel -> {
                    existingArtikel.setNaam(artikelDetails.getNaam());
                    existingArtikel.setBeschrijving(artikelDetails.getBeschrijving());
                    existingArtikel.setStatus(artikelDetails.getStatus());
                    existingArtikel.setGewicht(artikelDetails.getGewicht());
                    existingArtikel.setGrootte(artikelDetails.getGrootte());
                    Artikel updatedArtikel = artikelRepository.save(existingArtikel);
                    return ResponseEntity.ok(updatedArtikel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
