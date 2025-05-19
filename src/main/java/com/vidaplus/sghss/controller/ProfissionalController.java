package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Profissional;
import com.vidaplus.sghss.repository.ProfissionalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @GetMapping
    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscarPorId(@PathVariable("id") Long id) {
        Optional<Profissional> profissional = profissionalRepository.findById(id);
        return profissional.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profissional> criar(@RequestBody @Valid Profissional profissional) {
        return ResponseEntity.ok(profissionalRepository.save(profissional));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profissional> atualizar(@PathVariable("id") Long id, @RequestBody Profissional profissionalAtualizado) {
        return profissionalRepository.findById(id)
                .map(profissional -> {
                    profissional.setNome(profissionalAtualizado.getNome());
                    profissional.setEmail(profissionalAtualizado.getEmail());
                    profissional.setSenha(profissionalAtualizado.getSenha());
                    profissional.setCrmOuRegistro(profissionalAtualizado.getCrmOuRegistro());
                    profissional.setTelefone(profissionalAtualizado.getTelefone());
                    profissional.setEspecialidade(profissionalAtualizado.getEspecialidade());
                    return ResponseEntity.ok(profissionalRepository.save(profissional));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        return profissionalRepository.findById(id)
                .map(profissional -> {
                    profissionalRepository.delete(profissional);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
