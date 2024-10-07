package cl.springframework.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

    // Characters
    public static final String LINE_BREAK = "\n";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String SPACE = " ";
    public static final String EQUAL = "=";
    public static final String DOT = ".";

    // Parameters
    public static final String STRING_PARAMETER = "%s";

    // Titles
    public static final String BEGIN_TITLE_ENVIRONMENT_AND_CONFIGURATION = "Environment and configuration";

    public static final List<String> PROPERTIES_TO_EXCLUDE = Arrays.asList("credentials", "password", "token", "secret");

    // Token
    public static final String BASIC = "Basic";
    public static final String BEARER = "Bearer";

    // Http Headers
    public static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String INLINE = "inline";
    public static final String FILENAME = "filename";
    public static final String HEADER_FILENAME_VALUE = INLINE + SEMICOLON + SPACE + FILENAME + EQUAL + STRING_PARAMETER + DOT + STRING_PARAMETER;

    // Files
    public static final Map<String, String> FILE_CONTENT_TYPES = new HashMap<String, String>() {
        {
            put("XLSX", "application/vnd.ms-excel");
            put("PDF", "application/pdf");
            put("CSV", "text/csv");
            put("HTML", "text/html");
        }
    };

    public static final String XLSX = "XLSX";
    public static final String PDF = "PDF";
    public static final String CSV = "CSV";
    public static final String HTML = "HTML";
    public static final String JRXML = "JRXML";

    // Jaspers Reports
    public static final String JASPER_REPORTS_FOLDER = "/reports/";

    // Templates
    public static final String FREEMARKER_TEMPLATES_FOLDER = "/templates/";
}