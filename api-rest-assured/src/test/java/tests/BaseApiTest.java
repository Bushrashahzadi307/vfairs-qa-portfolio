package tests;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import utilities.ApiConfig;

/**
 * BaseApiTest - parent class for all vFairs API test classes.
 * Sets up shared RequestSpecification and common assertions.
 */
public class BaseApiTest {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = ApiConfig.buildRequestSpec();
    }

    /**
     * Helper: returns full endpoint path.
     * Endpoint examples match real vFairs API collection structure.
     */
    protected String endpoint(String path) {
        return path;
    }
}
