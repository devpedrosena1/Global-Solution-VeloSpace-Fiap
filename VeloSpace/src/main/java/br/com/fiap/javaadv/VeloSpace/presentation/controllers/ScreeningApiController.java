package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.Screening;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening.CreateScreeningDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening.ScreeningResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.Screening.ScreeningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/screenings")
@RequiredArgsConstructor
@Tag(name = "Screening API", description = "Endpoints para gerenciamento de Screenings")
public class ScreeningApiController {

    private final ScreeningService<Screening, Long> screeningService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Screening por ID", description = "Retorna os dados de um Screening específico, identificado pelo seu ID.")
    public ResponseEntity<ScreeningResponseDTO> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUserData authUser) {

        Screening screening = screeningService.findById(id, authUser);
        return ResponseEntity.ok(ScreeningResponseDTO.from(screening));
    }

    @PostMapping
    @Operation(summary = "Criar um novo Screening", description = "Recebe os dados de um Screening e o cria no sistema.")
    public ResponseEntity<ScreeningResponseDTO> save(
            @Valid @RequestBody CreateScreeningDTO dto,
            @AuthenticationPrincipal JwtUserData authUser) {

        Screening newScreening = screeningService.create(CreateScreeningDTO.toEntity(dto), authUser);
        return new ResponseEntity<>(ScreeningResponseDTO.from(newScreening), HttpStatus.CREATED);
    }

}
