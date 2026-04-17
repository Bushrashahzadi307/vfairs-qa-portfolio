package utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ApiConfig - centralised REST Assured configuration.
 * All base URLs and auth tokens are loaded from api-config.properties.
 * NEVER hardcode credentials or internal URLs in test code.
 */
public class ApiConfig {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(
                "src/test/resources/api-config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load api-config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException("Missing key: " + key);
        return value.trim();
    }

    public static String getBaseUrl()  { return get("api.base.url"); }
    public static String getAuthToken() { return get("api.auth.token"); }
    public static String getAppId()    { return get("api.app.id"); }
    public static String getBoothId()  { return get("api.booth.id"); }
    public static String getWebinarId(){ return get("api.webinar.id"); }

    /**
     * Build shared RequestSpecification used by all test classes.
     * Includes base URL, content type and auth header.
     */
    public static RequestSpecification buildRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + getAuthToken())
                .addHeader("Accept", "application/json")
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .build();
    }

    public static ResponseSpecification buildResponseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
