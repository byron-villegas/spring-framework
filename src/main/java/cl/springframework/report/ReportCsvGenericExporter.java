package cl.springframework.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import java.io.ByteArrayOutputStream;

public class ReportCsvGenericExporter extends ReportGenericExporter {
    @Override
    public void generateExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) throws JRException {
        JRCsvExporter jrCsvExporter = new JRCsvExporter();
        jrCsvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        jrCsvExporter.setExporterOutput(new SimpleWriterExporterOutput(byteArrayOutputStream));
        jrCsvExporter.exportReport();
    }
}