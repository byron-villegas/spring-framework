package cl.springframework.service;

import cl.springframework.constants.Constants;
import cl.springframework.dto.TemplateRequestDTO;
import cl.springframework.dto.TemplateResponseDTO;
import cl.springframework.exception.ErrorNegocioException;
import cl.springframework.util.FileUtil;
import cl.springframework.util.TemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateUtil templateUtil;

    public TemplateResponseDTO generateTemplate(TemplateRequestDTO templateRequestDTO) {

        final String filename = templateRequestDTO.getName() + Constants.DOT + Constants.HTML.toLowerCase();

        if(!FileUtil.isFileExistsInResources(filename, Constants.FREEMARKER_TEMPLATES_FOLDER)) {
            throw new ErrorNegocioException("ETNE", "Error template no encontrado");
        }

        final String html = templateUtil.generateTemplateByNameTypeAndParameters(templateRequestDTO.getName(), Constants.HTML, templateRequestDTO.getParameters());

        final TemplateResponseDTO templateResponseDTO = TemplateResponseDTO
                .builder()
                .html(html)
                .build();

        return templateResponseDTO;
    }
}