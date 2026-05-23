package br.com.fiap.porquinho.service.Ml;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.fiap.porquinho.presentation.transferObjects.Ml.CategoryRequestDTO;
import br.com.fiap.porquinho.presentation.transferObjects.Ml.CategoryResponseDTO;

@Service
public class MlService {

    private final WebClient webClient;

    public MlService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String categorize(String description) {

        CategoryRequestDTO request =
                new CategoryRequestDTO(description);

        CategoryResponseDTO response = webClient.post()
                .uri("/predict")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CategoryResponseDTO.class)
                .block();

        return response.getCategory();
    }
}