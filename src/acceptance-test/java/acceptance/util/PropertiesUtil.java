package acceptance.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties getPropertiesByFile(String file) {
        Properties properties = new Properties();
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }
}