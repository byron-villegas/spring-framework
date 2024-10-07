package cl.springframework.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import java.io.ByteArrayOutputStream;

public class ReportPdfGenericExporter extends ReportGenericExporter {
    @Override
    public void generateExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) throws JRException {
        JRPdfExporter jrPdfExporter = new JRPdfExporter();
        jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        jrPdfExporter.exportReport();
    }
}
