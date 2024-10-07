package cl.springframework.util;

import cl.springframework.constants.Constants;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemplateUtil {
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public String generateTemplateByNameTypeAndParameters(String name, String type, Map<String, Object> parameters) {
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(name + Constants.DOT + type.toLowerCase());
            Writer writer = new StringWriter();
            template.process(parameters, writer);
            return writer.toString();
        } catch (TemplateException | IOException ex) {
            log.error(ex.getMessage());
            return "";
        }
    }
}
