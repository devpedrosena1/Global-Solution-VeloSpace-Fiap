package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.LaunchProvider.CreateLaunchProviderDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.LaunchProvider.LaunchProviderResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.LaunchProvider.LaunchProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/launch-providers")
public class LaunchProviderApiController {

    private final LaunchProviderService<LaunchProvider, Long> launchProviderService;

    @PostMapping
    public ResponseEntity<LaunchProviderResponseDTO> save(
            @Valid @RequestBody CreateLaunchProviderDTO createLaunchProviderDTO) {
        LaunchProvider newLaunchProvider = launchProviderService
                .create(CreateLaunchProviderDTO.toEntity(createLaunchProviderDTO));
        return new ResponseEntity<>(LaunchProviderResponseDTO.from(newLaunchProvider), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaunchProviderResponseDTO> findById(@PathVariable Long id) {
        LaunchProvider launchProvider = launchProviderService.findById(id);
        return ResponseEntity.ok(LaunchProviderResponseDTO.from(launchProvider));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaunchProviderResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CreateLaunchProviderDTO dto) {
        LaunchProvider updated = launchProviderService.updateById(id, CreateLaunchProviderDTO.toEntity(dto));
        return ResponseEntity.ok(LaunchProviderResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        launchProviderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
