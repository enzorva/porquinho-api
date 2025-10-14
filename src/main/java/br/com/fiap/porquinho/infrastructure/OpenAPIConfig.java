package br.com.fiap.porquinho.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return new ModelResolver(objectMapper);
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Porquinho API")
                        .version("1.0.0")
                        .description("""
                                O Porquinho é um sistema de gestão financeira pessoal que ajuda os usuários
                                a organizar carteiras, contas e transações em um só lugar.
                                Ele oferece uma visão clara sobre gastos, receitas e saldo geral,
                                com relatórios intuitivos e análises inteligentes.
                                """));
    }

}
