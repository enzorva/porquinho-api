package br.com.fiap.porquinho.presentation.transferObjects.Ml;

public class CategoryRequestDTO {
    
    private String description;

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
