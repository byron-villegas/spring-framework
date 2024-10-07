package cl.springframework.util;

import cl.springframework.constants.Constants;
import cl.springframework.exception.ErrorTecnicoException;
import cl.springframework.report.ReportExporterFactory;
import cl.springframework.report.ReportGenericExporter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Component
public class ReportUtil {

    public byte[] generateReportByNameAndTypeAndParameters(String name, String type, Map<String, Object> parameters) {
        try {
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(new ArrayList<>());

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = getClass().getResourceAsStream(Constants.JASPER_REPORTS_FOLDER + name + Constants.DOT + Constants.JRXML.toLowerCase());

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            ReportGenericExporter reportGenericExporter = ReportExporterFactory.getReportExporter(type);

            reportGenericExporter.generateExporter(jasperPrint, byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (JRException ex) {
            log.error(ex.getMessage());
            throw new ErrorTecnicoException("EAGR", "Error al generar reporte");
        }
    }
}