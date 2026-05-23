package br.com.fiap.porquinho.presentation.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.porquinho.service.Ml.MlService;

@RestController
@RequestMapping("/test/ml")
public class TestMlController {

    private final MlService mlService;

    public TestMlController(MlService mlService) {
        this.mlService = mlService;
    }

    @GetMapping
    public String test(
            @RequestParam String description
    ) {

        return mlService.categorize(description);
    }
}