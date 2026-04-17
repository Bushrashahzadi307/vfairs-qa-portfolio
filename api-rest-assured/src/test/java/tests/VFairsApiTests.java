package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ApiConfig;

import static io.restassured.RestAssured.given;

/**
 * WebinarApiTests - vFairs Webinar/Auditorium API endpoints.
 *
 * Endpoints (from vFairs Mobile App APIs collection — Webinars folder):
 *   GET  /webinar/webinars             - list all webinars
 *   POST /webinar/webinars-groups      - fetch webinar groups
 *   POST /webinar/get-webinar          - fetch single webinar detail
 *   POST /webinar/add-remove-user-webinars - add/remove from schedule
 *   POST /webinar/my-schedule          - fetch user's personal schedule
 *   POST /webinar/webinar-tabs         - fetch webinar tab config
 *   GET  /webinar/question-answer      - fetch Q&A for a webinar
 *   POST /webinar/post-message         - post a Q&A question
 */
public class WebinarApiTests extends BaseApiTest {

    private final String webinarId = ApiConfig.getWebinarId();

    @Test(description = "GET /webinar/webinars - returns webinar list with status 200")
    public void testFetchWebinarListing() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("limit", 30)
                .when()
                .get("/webinar/webinars")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Webinar listing should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "Webinar data should not be null");
    }

    @Test(description = "GET /webinar/webinars - pagination works with limit and page")
    public void testWebinarListingPagination() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("limit", 10)
                .queryParam("page", 1)
                .when()
                .get("/webinar/webinars")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Paginated webinar listing should return 200");
    }

    @Test(description = "POST /webinar/get-webinar - returns webinar detail")
    public void testFetchSingleWebinarDetail() {
        String requestBody = "{\"webinar_id\": " + webinarId + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/webinar/get-webinar")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Webinar detail should return 200");
    }

    @Test(description = "GET /webinar/question-answer - returns Q&A for a webinar")
    public void testFetchWebinarQandA() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("webinar_id", webinarId)
                .when()
                .get("/webinar/question-answer")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Webinar Q&A fetch should return 200");
    }

    @Test(description = "POST /webinar/post-message - empty body returns error")
    public void testPostWebinarMessageEmptyBody() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
                .when()
                .post("/webinar/post-message")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(response.getStatusCode() >= 400,
                "Empty message post should return error");
    }

    @Test(description = "POST /webinar/my-schedule - returns user personal schedule")
    public void testFetchMySchedule() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
                .queryParam("limit", 10)
                .when()
                .post("/webinar/my-schedule")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "My schedule should return 200");
    }

    @Test(description = "POST /webinar/webinar-tabs - returns tab config for webinar")
    public void testFetchWebinarTabs() {
        String requestBody = "{\"webinar_id\": " + webinarId + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/webinar/webinar-tabs")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Webinar tabs should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "Tab config data should not be null");
    }
}


// ─── Swag Bag API Tests ──────────────────────────────────────────────────────

class SwagBagApiTests extends BaseApiTest {

    /**
     * Endpoints (from vFairs Mobile App APIs — Swag-Bag folder):
     *   GET  /booth/user-swag-bag-docs        - fetch swag bag items
     *   POST /booth/add-to-briefcase          - add document to swag bag
     *   POST /booth/remove-from-briefcase     - remove document from swag bag
     *   POST /booth/send-swag-bag-docs-email  - email swag bag to attendee
     */

    @Test(description = "GET /booth/user-swag-bag-docs - returns swag bag contents")
    public void testFetchSwagBagDocs() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("limit", 10)
                .queryParam("page", 1)
                .when()
                .get("/booth/user-swag-bag-docs")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Swag bag docs should return 200");
    }

    @Test(description = "POST /booth/add-to-briefcase - missing doc_id returns error")
    public void testAddToBriefcaseMissingDocId() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
                .when()
                .post("/booth/add-to-briefcase")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(response.getStatusCode() >= 400,
                "Add to briefcase without doc_id should fail");
    }

    @Test(description = "POST /booth/remove-from-briefcase - missing doc_id returns error")
    public void testRemoveFromBriefcaseMissingDocId() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
                .when()
                .post("/booth/remove-from-briefcase")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(response.getStatusCode() >= 400,
                "Remove from briefcase without doc_id should fail");
    }

    @Test(description = "POST /booth/send-swag-bag-docs-email - triggers email to attendee")
    public void testSendSwagBagEmail() {
        String requestBody = "{"
                + "\"email\": \"" + ApiConfig.get("api.test.email") + "\""
                + "}";

        Response response = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/booth/send-swag-bag-docs-email")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(
                response.getStatusCode() == 200 || response.getStatusCode() == 201,
                "Send swag bag email should succeed");
    }
}


// ─── User Profile API Tests ──────────────────────────────────────────────────

class UserProfileApiTests extends BaseApiTest {

    /**
     * Endpoints (from vFairs Mobile App APIs — User-Profile folder):
     *   GET  /user/fetch-user            - fetch current user profile
     *   GET  /user/generate-qrcode       - generate QR code for profile
     *   POST /user/scan-qrcode           - scan another user's QR code
     *   GET  /user/fetch-contacts        - fetch user's contact list
     *   POST /user/update-notes          - update notes on a contact
     *   GET  /contacts/export            - export favourite contacts
     */

    @Test(description = "GET /user/fetch-user - returns authenticated user profile")
    public void testFetchUserProfile() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/user/fetch-user")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch user should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "User data should not be null");
    }

    @Test(description = "GET /user/generate-qrcode - returns QR code data")
    public void testGenerateQrCode() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/user/generate-qrcode")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Generate QR code should return 200");
    }

    @Test(description = "GET /user/fetch-contacts - returns attendee contacts list")
    public void testFetchContacts() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/user/fetch-contacts")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fetch contacts should return 200");
    }

    @Test(description = "GET /contacts/export - exports favourite contacts")
    public void testExportFavouriteContacts() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("type", "favourite")
                .queryParam("user_type", "attendee")
                .when()
                .get("/contacts/export")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Contact export should return 200");
    }
}


// ─── Leaderboard API Tests ───────────────────────────────────────────────────

class LeaderboardApiTests extends BaseApiTest {

    /**
     * Endpoints (from vFairs Mobile App APIs — Leaderboard folder):
     *   GET /leaderboard/users    - top users on leaderboard
     *   GET /leaderboard/details  - current user's leaderboard position
     */

    @Test(description = "GET /leaderboard/users - returns top 100 leaderboard users")
    public void testFetchLeaderboardUsers() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("limit", 100)
                .when()
                .get("/leaderboard/users")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Leaderboard users should return 200");
        Assert.assertNotNull(response.jsonPath().get("data"),
                "Leaderboard data should not be null");
    }

    @Test(description = "GET /leaderboard/details - returns current user rank details")
    public void testFetchLeaderboardDetails() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/leaderboard/details")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Leaderboard details should return 200");
    }
}


// ─── Heartbeat API Tests ─────────────────────────────────────────────────────

class HeartbeatApiTests extends BaseApiTest {

    /**
     * Endpoints (from vFairs Mobile App APIs — HeartBeat folder):
     *   GET  /heartbeat            - check app is alive
     *   POST /heartbeat/add-heart-beat - record user activity heartbeat
     */

    @Test(description = "GET /heartbeat - health check returns 200")
    public void testHeartbeatHealthCheck() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/heartbeat")
                .then()
                .log().body()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200,
                "Heartbeat should return 200 - API is alive");
    }

    @Test(description = "POST /heartbeat/add-heart-beat - records user activity")
    public void testAddHeartbeat() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
                .when()
                .post("/heartbeat/add-heart-beat")
                .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(
                response.getStatusCode() == 200 || response.getStatusCode() == 201,
                "Add heartbeat should succeed");
    }
}
