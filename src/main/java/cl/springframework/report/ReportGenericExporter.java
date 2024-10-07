package cl.springframework.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import java.io.ByteArrayOutputStream;

public abstract class ReportGenericExporter {
    public abstract void generateExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) throws JRException;
}