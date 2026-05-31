package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @Operation(summary = "Criar um novo Shipper", description = "Recebe os dados de um Shipper e o cria no sistema.")
    public ResponseEntity<ShipperResponseDTO> save(@Valid @RequestBody CreateShipperDTO createShipperDTO) {
        Shipper newShipper = shipperService.create(CreateShipperDTO.toEntity(createShipperDTO));
        return new ResponseEntity<>(ShipperResponseDTO.from(newShipper), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Shipper por ID", description = "Retorna os dados de um Shipper específico, identificado pelo seu ID.")
    public ResponseEntity<ShipperResponseDTO> findById(@PathVariable Long id) {
        return shipperService.findById(id)
                .map(shipper -> ResponseEntity.ok(ShipperResponseDTO.from(shipper)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Shipper por ID", description = "Recebe os dados atualizados de um Shipper e o ID do Shipper a ser atualizado, e realiza a atualização no sistema.")
    public ResponseEntity<ShipperResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CreateShipperDTO dto) {
        Shipper updated = shipperService.updateById(id, CreateShipperDTO.toEntity(dto));
        return ResponseEntity.ok(ShipperResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Shipper por ID", description = "Recebe o ID de um Shipper e o deleta do sistema.")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        shipperService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
