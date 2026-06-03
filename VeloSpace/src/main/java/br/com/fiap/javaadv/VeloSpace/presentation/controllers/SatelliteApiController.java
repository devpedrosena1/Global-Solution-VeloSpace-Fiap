package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.Satellite;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Satellite.CreateSatelliteDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Satellite.SatelliteResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.Satellite.SatelliteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/satellites")
@RequiredArgsConstructor
@Tag(name = "Satellite API", description = "Endpoints para gerenciamento de Satellites")
public class SatelliteApiController {

    private final SatelliteService<Satellite, Long> satelliteService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Satellite por ID", description = "Retorna os dados de um Satellite específico, identificado pelo seu ID.")
    public ResponseEntity<SatelliteResponseDTO> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        Satellite satellite = satelliteService.findById(id, authUser);
        return ResponseEntity.ok(SatelliteResponseDTO.from(satellite));
    }

    @PostMapping
    @Operation(summary = "Criar um novo Satellite", description = "Recebe os dados de um Satellite e o cria no sistema.")
    public ResponseEntity<SatelliteResponseDTO> save(
            @Valid @RequestBody CreateSatelliteDTO dto,
            @AuthenticationPrincipal JwtUserData authUser) {

        Satellite newSatellite = satelliteService.create(CreateSatelliteDTO.toEntity(dto), authUser);
        return new ResponseEntity<>(SatelliteResponseDTO.from(newSatellite), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Satellite por ID", description = "Recebe os dados atualizados de um Satellite e o ID do Satellite a ser atualizado, e realiza a atualização no sistema.")
    public ResponseEntity<SatelliteResponseDTO> updateById(
            @PathVariable Long id,
            @Valid @RequestBody CreateSatelliteDTO dto,
            @AuthenticationPrincipal JwtUserData authUser) {

        Satellite updated = satelliteService.updateById(id, CreateSatelliteDTO.toEntity(dto), authUser);
        return ResponseEntity.ok(SatelliteResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Satellite por ID", description = "Recebe o ID de um Satellite e o deleta do sistema.")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        satelliteService.deleteById(id, authUser);
        return ResponseEntity.noContent().build();
    }

}
