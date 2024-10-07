package cl.springframework.report;

import cl.springframework.constants.Constants;
import cl.springframework.exception.ErrorNegocioException;

public class ReportExporterFactory {

    private ReportExporterFactory() {

    }
    public static ReportGenericExporter getReportExporter(final String type) {
        switch (type.toUpperCase()) {
            case Constants.XLSX:
                return new ReportXlsxGenericExporter();
            case Constants.PDF:
                return new ReportPdfGenericExporter();
            case Constants.CSV:
                return new ReportCsvGenericExporter();
            case Constants.HTML:
                return new ReportHtmlGenericExporter();
            default:
                throw new ErrorNegocioException("ETRNE", "Error tipo de reporte no encontrado");
        }
    }
}