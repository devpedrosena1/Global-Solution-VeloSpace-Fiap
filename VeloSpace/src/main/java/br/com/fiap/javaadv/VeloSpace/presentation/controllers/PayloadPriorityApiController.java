package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadPriority.PayloadPriorityResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.PayloadPriority.PayloadPriorityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/payload-priorities")
@RequiredArgsConstructor
@Tag(name = "Payload Priority API", description = "Endpoints para gerenciamento de Prioridades de Payload")
public class PayloadPriorityApiController {

    private final PayloadPriorityService payloadPriorityService;

    @GetMapping
    @Operation(summary = "Listar todas as prioridades de payload", description = "Retorna uma lista de todas as prioridades de payload disponíveis no sistema.")
    public ResponseEntity<List<PayloadPriorityResponseDTO>> findAll() {
        List<PayloadPriorityResponseDTO> priorities = payloadPriorityService.findAll()
                .stream()
                .map(PayloadPriorityResponseDTO::from)
                .toList();
        return ResponseEntity.ok(priorities);
    }

}