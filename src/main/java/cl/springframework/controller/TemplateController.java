package cl.springframework.controller;

import cl.springframework.dto.TemplateRequestDTO;
import cl.springframework.dto.TemplateResponseDTO;
import cl.springframework.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TemplateResponseDTO> generateTemplate(@RequestBody TemplateRequestDTO templateRequestDTO) {
        return ResponseEntity.ok(templateService.generateTemplate(templateRequestDTO));
    }
}