package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

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
public class ShipperApiController {

    private final ShipperService<Shipper, Long> shipperService;

    @PostMapping
    public ResponseEntity<ShipperResponseDTO> save(@Valid @RequestBody CreateShipperDTO createShipperDTO) {
        Shipper newShipper = shipperService.create(CreateShipperDTO.toEntity(createShipperDTO));
        return new ResponseEntity<>(ShipperResponseDTO.from(newShipper), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipperResponseDTO> findById(@PathVariable Long id) {
        return shipperService.findById(id)
                .map(shipper -> ResponseEntity.ok(ShipperResponseDTO.from(shipper)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShipperResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CreateShipperDTO dto) {
        Shipper updated = shipperService.updateById(id, CreateShipperDTO.toEntity(dto));
        return ResponseEntity.ok(ShipperResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        shipperService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
