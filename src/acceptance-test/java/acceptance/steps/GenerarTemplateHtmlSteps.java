package acceptance.steps;

import acceptance.base.BaseSteps;
import acceptance.dto.GenerateTemplateRequestDTO;
import acceptance.dto.GenerateTemplateResponseDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import java.util.HashMap;
import java.util.Map;

public class GenerarTemplateHtmlSteps {
    @When("Genero el template {string} con parametro {string} y valor {string}")
    public void generoElTemplateConParametroYValor(String reporte, String parametro, String valor) {
        final String url = "/templates";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(parametro, valor);

        final GenerateTemplateRequestDTO generateTemplateRequestDTO = GenerateTemplateRequestDTO
                .builder()
                .name(reporte)
                .parameters(parameters)
                .build();

        BaseSteps.executePost(url, generateTemplateRequestDTO);
    }

    @And("Valido que se haya generado el reporte")
    public void validoQueSeHayaGeneradoElReporte() {
        GenerateTemplateResponseDTO generateTemplateResponseDTO = BaseSteps.LAST_RESPONSE
                .getBody()
                .as(GenerateTemplateResponseDTO.class);

        Assert.assertNotNull(generateTemplateResponseDTO.getHtml());
    }
}