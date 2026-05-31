package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandler.CreatePayloadHandlerDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandler.ApprovalPayloadHandlerDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandler.PayloadHandlerResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.PayloadHandler.PayloadHandlerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payload-handlers")
@Tag(name = "Payload Handler API", description = "Endpoints para gerenciamento de Payload Handlers")
public class PayloadHandlerApiController {

    private final PayloadHandlerService<PayloadHandler, Long> payloadHandlerService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Payload Handler por ID", description = "Retorna os dados de um Payload Handler específico, identificado pelo seu ID.")
    public ResponseEntity<PayloadHandlerResponseDTO> findById(@PathVariable Long id) {
        PayloadHandler payloadHandler = payloadHandlerService.findById(id);
        return ResponseEntity.ok(PayloadHandlerResponseDTO.from(payloadHandler));
    }

    @PostMapping
    @Operation(summary = "Criar um novo Payload Handler", description = "Recebe os dados de um Payload Handler e o cria no sistema.")
    public ResponseEntity<PayloadHandlerResponseDTO> save(
            @Valid @RequestBody CreatePayloadHandlerDTO createPayloadHandlerDTO) {
        PayloadHandler newPayloadHandler = payloadHandlerService
                .create(CreatePayloadHandlerDTO.toEntity(createPayloadHandlerDTO));
        return new ResponseEntity<>(PayloadHandlerResponseDTO.from(newPayloadHandler), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/approval")
    @Operation(summary = "Aprovar ou rejeitar um Payload Handler", description = "Recebe o ID de um Payload Handler e os dados de aprovação, e atualiza o status de aprovação do Payload Handler no sistema.")
    public ResponseEntity<Void> approval(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalPayloadHandlerDTO approvalPayloadHandlerDTO,
            @AuthenticationPrincipal JwtUserData authUser) {

        payloadHandlerService.approval(authUser, id, approvalPayloadHandlerDTO.approval());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reapply")
    @Operation(summary = "Reaplicar um Payload Handler", description = "Recebe o ID de um Payload Handler e reaplica o processo de aprovação, permitindo que um Payload Handler rejeitado seja avaliado novamente.")
    public ResponseEntity<Void> reapply(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        payloadHandlerService.reapply(authUser, id);
        return ResponseEntity.noContent().build();
    };

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Payload Handler por ID", description = "Recebe os dados atualizados de um Payload Handler e o ID do Payload Handler a ser atualizado, e realiza a atualização no sistema.")
    public ResponseEntity<PayloadHandlerResponseDTO> updateById(@PathVariable Long id,
            @Valid @RequestBody CreatePayloadHandlerDTO dto) {
        PayloadHandler updated = payloadHandlerService.updateById(id, CreatePayloadHandlerDTO.toEntity(dto));
        return ResponseEntity.ok(PayloadHandlerResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Payload Handler por ID", description = "Recebe o ID de um Payload Handler e o deleta do sistema.")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        payloadHandlerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
