package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import br.com.fiap.javaadv.VeloSpace.model.Screening;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening.CreateScreeningDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening.ScreeningResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.Screening.ScreeningService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/screenings")
@RequiredArgsConstructor
@Tag(name = "Screening API", description = "Endpoints para gerenciamento de Screenings")
public class ScreeningApiController {

    private final ScreeningService<Screening, Long> screeningService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Screening por ID", description = "Retorna os dados de um Screening específico, identificado pelo seu ID.")
    public ResponseEntity<ScreeningResponseDTO> findById(@PathVariable Long id) {
        Screening screening = screeningService.findById(id);
        return ResponseEntity.ok(ScreeningResponseDTO.from(screening));
    }

    @PostMapping
    @Operation(summary = "Criar um novo Screening", description = "Recebe os dados de um Screening e o cria no sistema.")
    public ResponseEntity<ScreeningResponseDTO> save(@Valid @RequestBody CreateScreeningDTO dto) {
        Screening newScreening = screeningService.create(CreateScreeningDTO.toEntity(dto));
        return new ResponseEntity<>(ScreeningResponseDTO.from(newScreening), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Screening por ID", description = "Recebe os dados atualizados de um Screening e o ID do Screening a ser atualizado, e realiza a atualização no sistema.")
    public ResponseEntity<ScreeningResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CreateScreeningDTO dto) {
        Screening updated = screeningService.updateById(id, CreateScreeningDTO.toEntity(dto));
        return ResponseEntity.ok(ScreeningResponseDTO.from(updated));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente um Screening por ID", description = "Recebe o ID de um Screening e os dados parciais para atualização, e atualiza o Screening no sistema.")
    public ResponseEntity<ScreeningResponseDTO> patchById(@PathVariable Long id, @RequestBody CreateScreeningDTO dto) {
        Screening updated = screeningService.patchById(id, CreateScreeningDTO.toEntity(dto));
        return ResponseEntity.ok(ScreeningResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Screening por ID", description = "Recebe o ID de um Screening e o deleta do sistema.")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        screeningService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}