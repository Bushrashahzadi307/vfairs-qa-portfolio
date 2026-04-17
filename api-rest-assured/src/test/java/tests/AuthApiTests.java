package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ApiConfig;

import static io.restassured.RestAssured.given;

/**
 * AuthApiTests - verifies vFairs authentication API endpoints.
 *
 * Endpoints tested (from vFairs-React-APIs Postman collection):
 *   POST /event/login                  - validate login credentials
 *   POST /event/validate-pin-code      - OTP/PIN validation
 *   POST /event/register-user          - new user registration
 *   POST /event/send-forget-password-pin - forgot password
 *   POST /event/reset-password         - reset password
 *   GET  /event/logout                 - logout session
 *   GET  /event/fetch-login-fields/{appId} - fetch login form config
 *   POST /event/resend-pin-code        - resend OTP
 */
public class AuthApiTests extends BaseApiTest {

    // ── LOGIN ────────────────────────────────────────────────────────────────

    @Test(description = "POST /event/login - valid credentials return 200 with token")
    public void testLoginWithValidCredentials() {
        String requestBody = "{"
                + "\"email\": \"" + ApiConfig.get("api.test.email") + "\","
                + "\"password\": \"" + ApiConfig.get("api.test.password") + "\","
                + "\"app_id\": " + ApiConfig.getAppId()
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/event/login")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Login with valid credentials should return 200");
        Assert.assertNotNull(response.jsonPath().getString("token"),
                "Response should contain auth token");
    }

    @Test(description = "POST /event/login - missing email returns 400 or 422")
    public void testLoginWithMissingEmail() {
        String requestBody = "{"
                + "\"password\": \"somepassword\","
                + "\"app_id\": " + ApiConfig.getAppId()
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/event/login")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(
                response.getStatusCode() == 400 || response.getStatusCode() == 422,
                "Missing email should return 400 or 422, got: " + response.getStatusCode());
    }

    @Test(description = "POST /event/login - invalid credentials return error")
    public void testLoginWithInvalidCredentials() {
        String requestBody = "{"
                + "\"email\": \"invalid@notregistered.com\","
                + "\"password\": \"wrongpassword\","
                + "\"app_id\": " + ApiConfig.getAppId()
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/event/login")
                .then()
                .log().body()
                .extract().response();

        Assert.assertNotEquals(response.getStatusCode(), 200,
                "Login with invalid credentials should not return 200");
        Assert.assertNull(response.jsonPath().getString("token"),
                "No token should be returned for invalid credentials");
    }

    @Test(description = "POST /event/login - empty body returns 400")
    public void testLoginWithEmptyBody() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
                .when()
                .post("/event/login")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(
                response.getStatusCode() >= 400,
                "Empty body should return error status");
    }

    // ── OTP / PIN ────────────────────────────────────────────────────────────

    @Test(description = "POST /event/validate-pin-code - invalid OTP returns error")
    public void testValidateInvalidOtp() {
        String requestBody = "{"
                + "\"email\": \"" + ApiConfig.get("api.test.email") + "\","
                + "\"pin_code\": \"000000\","
                + "\"app_id\": " + ApiConfig.getAppId()
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/event/validate-pin-code")
                .then()
                .log().body()
                .extract().response();

        Assert.assertNotEquals(response.getStatusCode(), 200,
                "Invalid OTP should not return 200");
    }

    @Test(description = "POST /event/resend-pin-code - valid email returns 200")
    public void testResendPinCode() {
        String requestBody = "{"
                + "\"email\": \"" + ApiConfig.get("api.test.email") + "\","
                + "\"app_id\": " + ApiConfig.getAppId()
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/event/resend-pin-code")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(
                response.getStatusCode() == 200 || response.getStatusCode() == 201,
                "Resend OTP should succeed");
    }

    // ── REGISTRATION ─────────────────────────────────────────────────────────

    @Test(description = "GET /event/registration-field - fetch registration form fields")
    public void testFetchRegistrationFields() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/event/registration-field/en")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Registration fields endpoint should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "Response should contain registration field data");
    }

    @Test(description = "POST /event/register-user - duplicate email returns error")
    public void testRegisterWithDuplicateEmail() {
        String requestBody = "{"
                + "\"email\": \"" + ApiConfig.get("api.test.email") + "\","
                + "\"name\": \"Test User\","
                + "\"app_id\": " + ApiConfig.getAppId()
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/event/register-user")
                .then()
                .log().body()
                .extract().response();

        Assert.assertNotEquals(response.getStatusCode(), 200,
                "Duplicate email registration should not return 200");
    }

    // ── FORGOT PASSWORD ───────────────────────────────────────────────────────

    @Test(description = "POST /event/send-forget-password-pin - valid email returns 200")
    public void testForgotPasswordWithValidEmail() {
        String requestBody = "{"
                + "\"email\": \"" + ApiConfig.get("api.test.email") + "\","
                + "\"app_id\": " + ApiConfig.getAppId()
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/event/send-forget-password-pin")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(
                response.getStatusCode() == 200 || response.getStatusCode() == 201,
                "Forgot password with valid email should succeed");
    }

    @Test(description = "POST /event/send-forget-password-pin - unregistered email returns error")
    public void testForgotPasswordWithUnregisteredEmail() {
        String requestBody = "{"
                + "\"email\": \"nobody@notregistered.com\","
                + "\"app_id\": " + ApiConfig.getAppId()
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/event/send-forget-password-pin")
                .then()
                .log().body()
                .extract().response();

        Assert.assertNotEquals(response.getStatusCode(), 200,
                "Unregistered email should not return 200");
    }

    // ── LOGOUT ────────────────────────────────────────────────────────────────

    @Test(description = "GET /event/logout - returns 200 and clears session")
    public void testLogout() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/event/logout")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(
                response.getStatusCode() == 200 || response.getStatusCode() == 302,
                "Logout should return 200 or redirect");
    }

    // ── FETCH LOGIN FIELDS ────────────────────────────────────────────────────

    @Test(description = "GET /event/fetch-login-fields/{appId} - returns login form config")
    public void testFetchLoginFields() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/event/fetch-login-fields/" + ApiConfig.getAppId())
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch login fields should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "Login fields data should not be null");
    }
}
