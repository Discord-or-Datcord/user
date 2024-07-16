package io.datcord.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for retrieving application configuration properties.
 * The class reads the properties from the "application.properties" file in the classpath.
 */
public class ApplicationConfigUtils {

    private static final Properties props = new Properties();
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigUtils.class);


    /*
     * Static initializer block that loads the properties from the "application.properties" file in the classpath.
     * Ensures that the properties are loaded once when the class is loaded.
     * In case of any error such as file not found or IO exceptions, it logs the error message.
     */
    static {
        try (InputStream input = ApplicationConfigUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(input);
        } catch (IOException ex) {
            logger.error("Unable loading application.properties {}", ex.getMessage());
        }
    }

    /**
     * Retrieves the value of the specified property key from the application configuration properties.
     *
     * @param key the property key to retrieve the value for
     * @return the value of the property if the key exists
     * @throws RuntimeException if the key does not exist in the properties file
     */
    public static String getProperty(String key) {
        if (props.containsKey(key)) {
            return props.getProperty(key);
        }
        logger.error("Key {} not found", key);
        throw new RuntimeException("Key " + key + " not found");
    }

    /**
     * Retrieves the boolean value of the specified property key from the application configuration properties.
     *
     * @param key the property key to retrieve the value for
     * @return the boolean value of the property if the key exists
     * @throws RuntimeException if the key does not exist in the properties file
     */
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * Retrieves the integer value of the specified property key from the application configuration properties.
     *
     * @param key the property key to retrieve the value for
     * @return the integer value of the property if the key exists
     * @throws RuntimeException if the key does not exist in the properties file or if the value cannot be parsed as an integer
     */
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
}
