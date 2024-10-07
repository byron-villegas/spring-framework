package cl.springframework.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import java.io.ByteArrayOutputStream;

public class ReportXlsxGenericExporter extends ReportGenericExporter {

    @Override
    public void generateExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) throws JRException {
        JRXlsxExporter jrXlsxExporter = new JRXlsxExporter();
        jrXlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        jrXlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

        SimpleXlsxReportConfiguration simpleXlsxReportConfiguration = new SimpleXlsxReportConfiguration();
        simpleXlsxReportConfiguration.setRemoveEmptySpaceBetweenRows(true);

        jrXlsxExporter.setConfiguration(simpleXlsxReportConfiguration);

        jrXlsxExporter.exportReport();
    }
}