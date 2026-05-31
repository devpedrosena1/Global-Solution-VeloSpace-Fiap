package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Payload.CreatePayloadDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Payload.PayloadResponseDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadApprovalDTO;
import br.com.fiap.javaadv.VeloSpace.service.Payload.PayloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payloads")
@RequiredArgsConstructor
@Tag(name = "Payload API", description = "Endpoints para gerenciamento de Payloads")
public class PayloadApiController {

    private final PayloadService<Payload, Long> payloadService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Payload por ID", description = "Retorna os dados de um Payload específico, identificado pelo seu ID.")
    public ResponseEntity<PayloadResponseDTO> findById(@PathVariable Long id) {
        Payload payload = payloadService.findById(id);
        return ResponseEntity.ok(PayloadResponseDTO.from(payload));
    }

    @PostMapping
    @Operation(summary = "Criar um novo Payload", description = "Recebe os dados de um Payload e o cria no sistema.")
    public ResponseEntity<PayloadResponseDTO> save(@Valid @RequestBody CreatePayloadDTO dto) {
        Payload newPayload = payloadService.create(CreatePayloadDTO.toEntity(dto));
        return new ResponseEntity<>(PayloadResponseDTO.from(newPayload), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Payload por ID", description = "Recebe os dados atualizados de um Payload e o ID do Payload a ser atualizado, e realiza a atualização no sistema.")
    public ResponseEntity<PayloadResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CreatePayloadDTO dto) {
        Payload updated = payloadService.updateById(id, CreatePayloadDTO.toEntity(dto));
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

    @PatchMapping("/{id}/approval")
    @Operation(summary = "Atualizar o status de aprovação de um Payload por ID", description = "Recebe o ID de um Payload parcialmente e os dados de aprovação, e atualiza o status de aprovação do Payload no sistema.")
    public ResponseEntity<PayloadResponseDTO> patchApprovalById(@PathVariable Long id, @Valid @RequestBody PayloadApprovalDTO dto) {
        Payload updated = payloadService.patchApprovalById(id, dto);
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Payload por ID", description = "Recebe o ID de um Payload e o deleta do sistema.")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        payloadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente um Payload por ID", description = "Recebe o ID de um Payload e os dados parciais para atualização, e atualiza o Payload no sistema.")
    public ResponseEntity<PayloadResponseDTO> patchById(@PathVariable Long id, @Valid @RequestBody CreatePayloadDTO dto) {
        Payload updated = payloadService.patchById(id, CreatePayloadDTO.toEntity(dto));
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

    @PatchMapping("/{id}/tracking")
    @Operation(summary = "Atualizar o status de rastreamento de um Payload por ID", description = "Recebe o ID de um Payload e os dados de rastreamento, e atualiza o status de rastreamento do Payload no sistema.")
    public ResponseEntity<PayloadResponseDTO> patchTrackingById(@PathVariable Long id, @RequestBody CreatePayloadDTO dto) {
        Payload updated = payloadService.patchTrackingById(id, CreatePayloadDTO.toEntity(dto));
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

}