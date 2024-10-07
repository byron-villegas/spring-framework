package cl.springframework.controller;

import cl.springframework.dto.ReportRequestDTO;
import cl.springframework.service.ReportService;
import cl.springframework.util.HttpHeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportRequestDTO reportRequestDTO) {
        log.info(String.format("Reporte %s Generado en Formato %s", reportRequestDTO.getName(), reportRequestDTO.getType()));

        byte[] archivo = reportService.generateReport(reportRequestDTO);
        HttpHeaders httpHeaders = HttpHeaderUtil.getHttpHeadersByFileType(reportRequestDTO.getName(), reportRequestDTO.getType());

        return ResponseEntity.ok().headers(httpHeaders).body(archivo);
    }
}