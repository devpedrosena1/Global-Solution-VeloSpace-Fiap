package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandler.CreatePayloadHandlerDTO;
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

    @PostMapping
    public ResponseEntity<PayloadHandlerResponseDTO> save(
            @Valid @RequestBody CreatePayloadHandlerDTO createPayloadHandlerDTO) {
        PayloadHandler newPayloadHandler = payloadHandlerService
                .create(CreatePayloadHandlerDTO.toEntity(createPayloadHandlerDTO));
        return new ResponseEntity<>(PayloadHandlerResponseDTO.from(newPayloadHandler), HttpStatus.CREATED);
    }

}
