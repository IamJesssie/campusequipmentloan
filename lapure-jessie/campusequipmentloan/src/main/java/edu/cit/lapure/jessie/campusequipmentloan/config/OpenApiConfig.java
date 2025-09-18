package edu.cit.lapure.jessie.campusequipmentloan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI campusEquipmentLoanOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Campus Equipment Loan API")
                        .description("API for managing equipment loans in a campus setting")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Campus IT Department")
                                .email("it@campus.edu")));
    }
}
