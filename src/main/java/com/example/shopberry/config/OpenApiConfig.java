package com.example.shopberry.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    public static final String API_TITLE = "Shopberry API";
    public static final String API_VERSION = "1.0";
    public static final String API_DESCRIPTION = "Shopberry API documentation";

    public static final String FORBIDDEN_CODE = "403";
    public static final String NOT_FOUND_CODE = "404";

    public static final String FORBIDDEN_DESCRIPTION = "Forbidden";
    public static final String NOT_FOUND_DESCRIPTION = "Not Found";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(API_TITLE)
                                .version(API_VERSION)
                                .description(API_DESCRIPTION)
                )
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }

    @Bean
    public OpenApiCustomizer globalResponsesCustomizer() {
        return openApi -> openApi.getPaths().forEach((path, pathItem) ->
                pathItem.readOperations().forEach(operation -> {
                    ApiResponses responses = operation.getResponses();

                    if (!responses.containsKey(FORBIDDEN_CODE)) {
                        responses.addApiResponse(
                                FORBIDDEN_CODE,
                                new ApiResponse().description(FORBIDDEN_DESCRIPTION)
                        );
                    }

                    if (!responses.containsKey(NOT_FOUND_CODE)) {
                        responses.addApiResponse(
                                NOT_FOUND_CODE,
                                new ApiResponse().description(NOT_FOUND_DESCRIPTION)
                        );
                    }
                })
        );
    }

}
