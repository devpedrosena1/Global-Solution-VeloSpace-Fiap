package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

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
public class PayloadHandlerApiController {

    private final PayloadHandlerService<PayloadHandler, Long> payloadHandlerService;

    @GetMapping("/{id}")
    public ResponseEntity<PayloadHandlerResponseDTO> findById(@PathVariable Long id) {
        PayloadHandler payloadHandler = payloadHandlerService.findById(id);
        return ResponseEntity.ok(PayloadHandlerResponseDTO.from(payloadHandler));
    }

    @PostMapping
    public ResponseEntity<PayloadHandlerResponseDTO> save(
            @Valid @RequestBody CreatePayloadHandlerDTO createPayloadHandlerDTO) {
        PayloadHandler newPayloadHandler = payloadHandlerService
                .create(CreatePayloadHandlerDTO.toEntity(createPayloadHandlerDTO));
        return new ResponseEntity<>(PayloadHandlerResponseDTO.from(newPayloadHandler), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/approval")
    public ResponseEntity<Void> approval(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalPayloadHandlerDTO approvalPayloadHandlerDTO,
            @AuthenticationPrincipal JwtUserData authUser) {

        payloadHandlerService.approval(authUser, id, approvalPayloadHandlerDTO.approval());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reapply")
    public ResponseEntity<Void> reapply(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        payloadHandlerService.reapply(authUser, id);
        return ResponseEntity.noContent().build();
    };

    @PutMapping("/{id}")
    public ResponseEntity<PayloadHandlerResponseDTO> updateById(@PathVariable Long id,
            @Valid @RequestBody CreatePayloadHandlerDTO dto) {
        PayloadHandler updated = payloadHandlerService.updateById(id, CreatePayloadHandlerDTO.toEntity(dto));
        return ResponseEntity.ok(PayloadHandlerResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        payloadHandlerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
