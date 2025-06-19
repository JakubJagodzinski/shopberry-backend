package com.example.shopberry.config;

import com.example.shopberry.common.constants.ApiConstants;
import com.example.shopberry.common.constants.HttpStatusConstants;
import com.example.shopberry.common.constants.SecurityConstants;
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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(ApiConstants.API_INFO_TITLE)
                                .version(ApiConstants.API_INFO_VERSION)
                                .description(ApiConstants.API_INFO_DESCRIPTION)
                )
                .addSecurityItem(new SecurityRequirement().addList(SecurityConstants.SECURITY_SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SecurityConstants.SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(SecurityConstants.SECURITY_SCHEME_NAME)
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

                    if (!responses.containsKey(HttpStatusConstants.HTTP_STATUS_BAD_REQUEST)) {
                        responses.addApiResponse(
                                HttpStatusConstants.HTTP_STATUS_BAD_REQUEST,
                                new ApiResponse().description(HttpStatusConstants.HTTP_DESCRIPTION_BAD_REQUEST)
                        );
                    }

                    if (!responses.containsKey(HttpStatusConstants.HTTP_STATUS_FORBIDDEN)) {
                        responses.addApiResponse(
                                HttpStatusConstants.HTTP_STATUS_FORBIDDEN,
                                new ApiResponse().description(HttpStatusConstants.HTTP_DESCRIPTION_FORBIDDEN)
                        );
                    }

                    if (!responses.containsKey(HttpStatusConstants.HTTP_STATUS_NOT_FOUND)) {
                        responses.addApiResponse(
                                HttpStatusConstants.HTTP_STATUS_NOT_FOUND,
                                new ApiResponse().description(HttpStatusConstants.HTTP_DESCRIPTION_NOT_FOUND)
                        );
                    }
                })
        );
    }

}
