package cl.springframework.service;

import cl.springframework.constants.Constants;
import cl.springframework.dto.ReportRequestDTO;
import cl.springframework.exception.ErrorNegocioException;
import cl.springframework.util.FileUtil;
import cl.springframework.util.ReportUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportUtil reportUtil;

    public byte[] generateReport(ReportRequestDTO reportRequestDTO) {

        final String filename = reportRequestDTO.getName() + Constants.DOT + Constants.JRXML.toLowerCase();

        if (!FileUtil.isFileExistsInResources(filename, Constants.JASPER_REPORTS_FOLDER)) {
            throw new ErrorNegocioException("ERNE", "Error reporte no encontrado");
        }

        return reportUtil.generateReportByNameAndTypeAndParameters(reportRequestDTO.getName(), reportRequestDTO.getType(), reportRequestDTO.getParameters());
    }
}