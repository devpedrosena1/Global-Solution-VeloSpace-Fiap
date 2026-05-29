package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.LaunchProvider.CreateLaunchProviderDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.LaunchProvider.LaunchProviderResponseDTO;
import br.com.fiap.javaadv.VeloSpace.service.LaunchProviderService;
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

}
