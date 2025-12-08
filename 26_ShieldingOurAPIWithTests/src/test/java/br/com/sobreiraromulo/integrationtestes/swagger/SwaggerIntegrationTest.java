package br.com.sobreiraromulo.integrationtestes.swagger;

import br.com.sobreiraromulo.config.TestConfigs;
import br.com.sobreiraromulo.integrationtestes.testescontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("test")
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	void shouldDisplaySwaggerUIPage() {
        var content = given()
                    .basePath("/swagger-ui/index.html")
                    .port(TestConfigs.SERVER_PORT)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .asString();

        System.out.println(given().basePath("/swagger-ui/index.html#/").port(TestConfigs.SERVER_PORT));
        assertTrue(content.contains("Swagger UI"));
	}

}
