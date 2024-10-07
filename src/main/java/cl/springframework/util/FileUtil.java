package cl.springframework.util;

import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
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
            Path path = Paths
                    .get(FileUtil.class.getClassLoader().getResource(filePath).toURI());

            Stream<String> lines = Files.lines(path);

            String data = lines.collect(Collectors.joining("\n"));

            lines.close();

            return data;
        } catch(Exception ex) {
            throw new FileSystemNotFoundException("Archivo no encontrado");
        }
    }
}