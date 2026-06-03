package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.LaunchProvider.CreateLaunchProviderDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.LaunchProvider.LaunchProviderResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.LaunchProvider.LaunchProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/launch-providers")
@Tag(name = "Launch Providers API", description = "Endpoints para gerenciamento de Launch Providers")
public class LaunchProviderApiController {

    private final LaunchProviderService<LaunchProvider, Long> launchProviderService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Launch Provider por ID", description = "Retorna os dados de um Launch Provider específico, identificado pelo seu ID.")
    public ResponseEntity<LaunchProviderResponseDTO> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        LaunchProvider launchProvider = launchProviderService.findById(id, authUser);
        return ResponseEntity.ok(LaunchProviderResponseDTO.from(launchProvider));
    }

    @GetMapping
    @Operation(summary = "Listar todos os Launch Providers", description = "Retorna os dados de todos os Launch Providers cadastrados.")
    public ResponseEntity<List<LaunchProviderResponseDTO>> findAll() {
        List<LaunchProvider> launchProviders = launchProviderService.findAll();
        return ResponseEntity.ok(launchProviders
                .stream()
                .map(LaunchProviderResponseDTO::from)
                .toList());
    }

    @PostMapping
    @Operation(summary = "Criar um novo Launch Provider", description = "Recebe os dados de um Launch Provider e o cria no sistema.")
    public ResponseEntity<LaunchProviderResponseDTO> save(
            @Valid @RequestBody CreateLaunchProviderDTO dto) {

        LaunchProvider newLaunchProvider = launchProviderService.create(CreateLaunchProviderDTO.toEntity(dto));
        return new ResponseEntity<>(LaunchProviderResponseDTO.from(newLaunchProvider), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Launch Provider por ID", description = "Recebe os dados atualizados de um Launch Provider e o ID do Launch Provider a ser atualizado, e realiza a atualização no sistema.")
    public ResponseEntity<LaunchProviderResponseDTO> updateById(
            @PathVariable Long id,
            @Valid @RequestBody CreateLaunchProviderDTO dto,
            @AuthenticationPrincipal JwtUserData authUser) {

        LaunchProvider updated = launchProviderService.updateById(id, CreateLaunchProviderDTO.toEntity(dto), authUser);
        return ResponseEntity.ok(LaunchProviderResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Launch Provider por ID", description = "Recebe o ID de um Launch Provider e o deleta do sistema.")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        launchProviderService.deleteById(id, authUser);
        return ResponseEntity.noContent().build();
    }

}
