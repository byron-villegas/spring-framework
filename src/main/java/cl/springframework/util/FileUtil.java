package cl.springframework.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.FileSystemNotFoundException;

@Slf4j
public class FileUtil {

    private FileUtil() {

    }

    public static boolean isFileExistsInResources(String filename, String path) {
        return new ClassPathResource(path + filename).exists();
    }

    public static String getFileContent(String filePath) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath,
                    FileUtil.class.getClassLoader());

            StringBuilder fileContents = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContents
                            .append(line)
                            .append("\n");
                }
            }

            return fileContents.toString();
        } catch(Exception ex) {
            log.error("Archivo no encontrado [{}]", ex.getMessage());
            throw new FileSystemNotFoundException("Archivo no encontrado");
        }
    }
}