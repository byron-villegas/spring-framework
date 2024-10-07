package cl.springframework.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import java.io.ByteArrayOutputStream;

public class ReportHtmlGenericExporter extends ReportGenericExporter {
    @Override
    public void generateExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) throws JRException {
        HtmlExporter htmlExporter = new HtmlExporter();
        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArrayOutputStream));
        htmlExporter.exportReport();
    }
}