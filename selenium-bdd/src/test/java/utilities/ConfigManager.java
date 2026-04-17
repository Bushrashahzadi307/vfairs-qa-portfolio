package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigManager - loads test configuration from config.properties.
 * All URLs, credentials and env settings are externalised here.
 * NEVER hardcode credentials in test code.
 */
public class ConfigManager {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(
                "src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing config key: " + key);
        }
        return value.trim();
    }

    public static String getBaseUrl()    { return get("base.url"); }
    public static String getBrowser()    { return get("browser"); }
    public static String getAppiumUrl()  { return get("appium.server.url"); }
    public static String getPlatform()   { return get("platform.name"); }
    public static String getAppPath()    { return get("app.path"); }
}
