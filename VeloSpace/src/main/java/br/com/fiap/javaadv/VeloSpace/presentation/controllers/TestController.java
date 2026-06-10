package br.com.fiap.javaadv.VeloSpace.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Test Controller", description = "Controller de teste para verificar se a aplicação está funcionando corretamente")
public class TestController {

    @GetMapping("/test")
    @Operation(summary = "Teste Ataulizado pela Pipeline", description = "Endpoint de teste para verificar se a aplicação está funcionando corretamente")
    public String test() {
        return "Testando o controller e pipeline junto!!!";
    }

}
