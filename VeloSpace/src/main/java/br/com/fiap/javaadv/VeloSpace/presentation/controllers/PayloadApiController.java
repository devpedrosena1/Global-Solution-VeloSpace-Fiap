package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Payload.CreatePayloadDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Payload.PayloadResponseDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadApprovalDTO;
import br.com.fiap.javaadv.VeloSpace.service.Payload.PayloadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payloads")
@RequiredArgsConstructor
public class PayloadApiController {

    private final PayloadService<Payload, Long> payloadService;

    @GetMapping("/{id}")
    public ResponseEntity<PayloadResponseDTO> findById(@PathVariable Long id) {
        Payload payload = payloadService.findById(id);
        return ResponseEntity.ok(PayloadResponseDTO.from(payload));
    }

    @PostMapping
    public ResponseEntity<PayloadResponseDTO> create(@Valid @RequestBody CreatePayloadDTO dto) {
        Payload newPayload = payloadService.create(CreatePayloadDTO.toEntity(dto));
        return new ResponseEntity<>(PayloadResponseDTO.from(newPayload), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayloadResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CreatePayloadDTO dto) {
        Payload updated = payloadService.updateById(id, CreatePayloadDTO.toEntity(dto));
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

    @PatchMapping("/{id}/approval")
    public ResponseEntity<PayloadResponseDTO> patchApprovalById(@PathVariable Long id, @Valid @RequestBody PayloadApprovalDTO dto) {
        Payload updated = payloadService.patchApprovalById(id, dto);
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        payloadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PayloadResponseDTO> patchById(@PathVariable Long id, @Valid @RequestBody CreatePayloadDTO dto) {
        Payload updated = payloadService.patchById(id, CreatePayloadDTO.toEntity(dto));
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

    @PatchMapping("/{id}/tracking")
    public ResponseEntity<PayloadResponseDTO> patchTrackingById(@PathVariable Long id, @RequestBody CreatePayloadDTO dto) {
        Payload updated = payloadService.patchTrackingById(id, CreatePayloadDTO.toEntity(dto));
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

}