package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Payload.CreatePayloadDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Payload.PayloadResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.Payload.PayloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payloads")
@RequiredArgsConstructor
@Tag(name = "Payload API", description = "Endpoints para gerenciamento de Payloads")
public class PayloadApiController {

    private final PayloadService<Payload, Long> payloadService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Payload por ID", description = "Retorna os dados de um Payload específico, identificado pelo seu ID.")
    public ResponseEntity<PayloadResponseDTO> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        Payload payload = payloadService.findById(id, authUser);
        return ResponseEntity.ok(PayloadResponseDTO.from(payload));
    }

    @PostMapping
    @Operation(summary = "Criar um novo Payload", description = "Recebe os dados de um Payload e o cria no sistema.")
    public ResponseEntity<PayloadResponseDTO> save(
            @Valid @RequestBody CreatePayloadDTO dto,
            @AuthenticationPrincipal JwtUserData authUser) {

        Payload newPayload = payloadService.create(CreatePayloadDTO.toEntity(dto), authUser);
        return new ResponseEntity<>(PayloadResponseDTO.from(newPayload), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Payload por ID", description = "Recebe os dados atualizados de um Payload e o ID do Payload a ser atualizado, e realiza a atualização no sistema.")
    public ResponseEntity<PayloadResponseDTO> updateById(
            @PathVariable Long id,
            @Valid @RequestBody CreatePayloadDTO dto,
            @AuthenticationPrincipal JwtUserData authUser) {

        Payload updated = payloadService.updateById(id, CreatePayloadDTO.toEntity(dto), authUser);
        return ResponseEntity.ok(PayloadResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Payload por ID", description = "Recebe o ID de um Payload e o deleta do sistema.")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        payloadService.deleteById(id, authUser);
        return ResponseEntity.noContent().build();
    }

}
