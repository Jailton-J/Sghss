package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.PacienteRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable("id") Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> criar(@RequestBody @Valid Paciente paciente) {
        return ResponseEntity.ok(pacienteRepository.save(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable("id") Long id, @RequestBody Paciente pacienteAtualizado) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setNome(pacienteAtualizado.getNome());
                    paciente.setCpf(pacienteAtualizado.getCpf());
                    paciente.setEmail(pacienteAtualizado.getEmail());
                    paciente.setSenha(pacienteAtualizado.getSenha());
                    paciente.setEndereco(pacienteAtualizado.getEndereco()); 
                    paciente.setTelefone(pacienteAtualizado.getTelefone());
                    paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());
                    return ResponseEntity.ok(pacienteRepository.save(paciente));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    pacienteRepository.delete(paciente);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}

