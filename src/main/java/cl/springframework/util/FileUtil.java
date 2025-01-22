package cl.springframework.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            throw new FileSystemNotFoundException("Archivo no encontrado");
        }
    }
}