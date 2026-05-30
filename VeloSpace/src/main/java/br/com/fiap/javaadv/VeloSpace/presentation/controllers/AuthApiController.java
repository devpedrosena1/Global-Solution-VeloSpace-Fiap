package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.AuthUserDetails;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtHelper;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Auth.AuthRequestDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Auth.AuthResponseDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Auth.RefreshTokenRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @PostMapping
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.email(),
                        authRequestDTO.password()));

        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();
        String token = jwtHelper.generateToken(user);
        String refreshToken = jwtHelper.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponseDTO(token, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {

        Optional<JwtUserData> jwtData = jwtHelper.validateToken(
                refreshTokenRequestDTO.refreshToken());

        if (jwtData.isPresent()) {
            JwtUserData data = jwtData.get();
            AuthUserDetails user = AuthUserDetails.builder()
                    .userId(data.userId())
                    .email(data.email())
                    .build();

            String newToken = jwtHelper.generateToken(user);
            String newRefreshToken = jwtHelper.generateRefreshToken(user);

            return ResponseEntity.ok(new AuthResponseDTO(newToken, newRefreshToken));
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Refresh Token inválido ou expirado");
    }

}
