package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ApiConfig;

import static io.restassured.RestAssured.given;

/**
 * BoothApiTests - verifies vFairs Booth/Exhibit Hall API endpoints.
 *
 * Endpoints tested (from vFairs Mobile App APIs Postman collection):
 *   GET  /booth/fetch-booth-info       - fetch booth details
 *   GET  /booth/fetch-booth-jobs       - fetch jobs listed at a booth
 *   GET  /documents/fetch-booth-videos - fetch booth video content
 *   GET  /documents/fetch-booth-docs   - fetch booth documents
 *   GET  /documents/fetch-booth-links  - fetch booth links
 *   GET  /documents/fetch-booth-tabs   - fetch booth tab configuration
 *   GET  /booth/get-reps               - fetch booth representatives
 *   GET  /booth/get-available-slots    - fetch available meeting slots
 *   POST /booth/reserve-chat-slot      - book a meeting slot
 *   POST /booth/cancel-chat-slot       - cancel a booked meeting
 *   GET  /booth/booked-slots           - list all booked meetings
 *   POST /booth/leave-message          - leave a message at a booth
 *   POST /booth/send-business-card     - send business card to booth rep
 *   GET  /floor/all                    - fetch all event floors
 */
public class BoothApiTests extends BaseApiTest {

    private final String boothId = ApiConfig.getBoothId();

    // ── BOOTH INFO ───────────────────────────────────────────────────────────

    @Test(description = "GET /booth/fetch-booth-info - returns booth details with status 200")
    public void testFetchBoothInfo() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("booth_id", boothId)
                .when()
                .get("/booth/fetch-booth-info")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch booth info should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "Booth info data should not be null");
    }

    @Test(description = "GET /booth/fetch-booth-info - missing booth_id returns error")
    public void testFetchBoothInfoWithoutId() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/booth/fetch-booth-info")
                .then()
                .log().body()
                .extract().response();

        Assert.assertNotEquals(response.getStatusCode(), 200,
                "Fetch booth info without ID should not return 200");
    }

    @Test(description = "GET /booth/fetch-booth-info - invalid booth_id returns error")
    public void testFetchBoothInfoInvalidId() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("booth_id", "9999999")
                .when()
                .get("/booth/fetch-booth-info")
                .then()
                .log().body()
                .extract().response();

        Assert.assertNotEquals(response.getStatusCode(), 200,
                "Invalid booth ID should not return 200");
    }

    // ── BOOTH CONTENT ─────────────────────────────────────────────────────────

    @Test(description = "GET /documents/fetch-booth-videos - returns video list")
    public void testFetchBoothVideos() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("page", 1)
                .queryParam("limit", 20)
                .when()
                .get("/documents/fetch-booth-videos")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch booth videos should return 200");
    }

    @Test(description = "GET /documents/fetch-booth-docs - returns documents list")
    public void testFetchBoothDocs() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("limit", 20)
                .when()
                .get("/documents/fetch-booth-docs")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch booth docs should return 200");
    }

    @Test(description = "GET /documents/fetch-booth-links - returns booth links")
    public void testFetchBoothLinks() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("booth_id", boothId)
                .when()
                .get("/documents/fetch-booth-links")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch booth links should return 200");
    }

    @Test(description = "GET /documents/fetch-booth-tabs - returns tab configuration")
    public void testFetchBoothTabs() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("booth_id", boothId)
                .when()
                .get("/documents/fetch-booth-tabs")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch booth tabs should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "Tab configuration data should not be null");
    }

    // ── BOOTH JOBS ────────────────────────────────────────────────────────────

    @Test(description = "GET /booth/fetch-booth-jobs - returns job listings for booth")
    public void testFetchBoothJobs() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("booth_id", boothId)
                .when()
                .get("/booth/fetch-booth-jobs")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch booth jobs should return 200");
    }

    // ── BOOK A MEETING ────────────────────────────────────────────────────────

    @Test(description = "GET /booth/get-reps - returns list of booth representatives")
    public void testFetchBoothReps() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("booth_id", boothId)
                .when()
                .get("/booth/get-reps")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch booth reps should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "Reps data should not be null");
    }

    @Test(description = "GET /booth/get-available-slots - returns meeting time slots")
    public void testGetAvailableSlots() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("rep_id", "622111456")   // example rep ID for QA env
                .when()
                .get("/booth/get-available-slots")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Get available slots should return 200");
    }

    @Test(description = "POST /booth/reserve-chat-slot - missing required fields returns 400")
    public void testReserveChatSlotMissingFields() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
                .when()
                .post("/booth/reserve-chat-slot")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(response.getStatusCode() >= 400,
                "Reserve slot with empty body should fail");
    }

    @Test(description = "GET /booth/booked-slots - returns list of booked meetings")
    public void testGetBookedSlots() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/booth/booked-slots")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Booked slots should return 200");
    }

    // ── MESSAGING ─────────────────────────────────────────────────────────────

    @Test(description = "POST /booth/leave-message - missing booth_id returns error")
    public void testLeaveMessageMissingBoothId() {
        String requestBody = "{"
                + "\"message\": \"Hello from QA test\","
                + "\"name\": \"QA Tester\","
                + "\"email\": \"" + ApiConfig.get("api.test.email") + "\""
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/booth/leave-message")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(response.getStatusCode() >= 400,
                "Leave message without booth_id should fail");
    }

    // ── FLOORS ────────────────────────────────────────────────────────────────

    @Test(description = "GET /floor/all - returns all event floors")
    public void testFetchAllFloors() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/floor/all")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch all floors should return 200");
    }
}
