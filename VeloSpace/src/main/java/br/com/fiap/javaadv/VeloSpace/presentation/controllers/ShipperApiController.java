package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Shipper.CreateShipperDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Shipper.ShipperResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.Shipper.ShipperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shippers")
@Tag(name = "Shipper API", description = "Endpoints para gerenciamento de Shippers")
public class ShipperApiController {

    private final ShipperService<Shipper, Long> shipperService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Shipper por ID", description = "Retorna os dados de um Shipper específico, identificado pelo seu ID.")
    public ResponseEntity<ShipperResponseDTO> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        Shipper shipper = shipperService.findById(id, authUser);
        return ResponseEntity.ok(ShipperResponseDTO.from(shipper));
    }

    @PostMapping
    @Operation(summary = "Criar um novo Shipper", description = "Recebe os dados de um Shipper e o cria no sistema.")
    public ResponseEntity<ShipperResponseDTO> save(
            @Valid @RequestBody CreateShipperDTO dto) {

        Shipper newShipper = shipperService.create(CreateShipperDTO.toEntity(dto));
        return new ResponseEntity<>(ShipperResponseDTO.from(newShipper), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Shipper por ID", description = "Recebe os dados atualizados de um Shipper e o ID do Shipper a ser atualizado, e realiza a atualização no sistema.")
    public ResponseEntity<ShipperResponseDTO> updateById(
            @PathVariable Long id,
            @Valid @RequestBody CreateShipperDTO dto,
            @AuthenticationPrincipal JwtUserData authUser) {

        Shipper updated = shipperService.updateById(id, CreateShipperDTO.toEntity(dto), authUser);
        return ResponseEntity.ok(ShipperResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Shipper por ID", description = "Recebe o ID de um Shipper e o deleta do sistema.")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        shipperService.deleteById(id, authUser);
        return ResponseEntity.noContent().build();
    }

}
