package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import br.com.fiap.javaadv.VeloSpace.model.Screening;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening.CreateScreeningDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Screening.ScreeningResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.Screening.ScreeningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/screenings")
@RequiredArgsConstructor
public class ScreeningApiController {

    private final ScreeningService<Screening, Long> screeningService;

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningResponseDTO> findById(@PathVariable Long id) {
        Screening screening = screeningService.findById(id);
        return ResponseEntity.ok(ScreeningResponseDTO.from(screening));
    }

    @PostMapping
    public ResponseEntity<ScreeningResponseDTO> create(@Valid @RequestBody CreateScreeningDTO dto) {
        Screening newScreening = screeningService.create(CreateScreeningDTO.toEntity(dto));
        return new ResponseEntity<>(ScreeningResponseDTO.from(newScreening), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreeningResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CreateScreeningDTO dto) {
        Screening updated = screeningService.updateById(id, CreateScreeningDTO.toEntity(dto));
        return ResponseEntity.ok(ScreeningResponseDTO.from(updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScreeningResponseDTO> patchById(@PathVariable Long id, @RequestBody CreateScreeningDTO dto) {
        Screening updated = screeningService.patchById(id, CreateScreeningDTO.toEntity(dto));
        return ResponseEntity.ok(ScreeningResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        screeningService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}