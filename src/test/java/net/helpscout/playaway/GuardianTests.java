package net.helpscout.playaway;

import com.google.common.collect.ImmutableMap;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class GuardianTests extends FunctionalTest {

    @Test
    public void guardianVetoesResourceAccess_ForReadingOpreation() {
        given()
            .header("CompanyID", COMPANY_WITH_MISSING_PAYMENT)
        .when()
            .get("/customers/1")
        .then()
            .statusCode(402);
    }

    @Test
    public void guardianVetoesResourceAccess_ForWritingOpreation() {
        given()
            .contentType(ContentType.JSON)
            .header("CompanyID", COMPANY_WITH_MISSING_PAYMENT)
            .body(ImmutableMap.of("first", "New", "last", "Custie"))
        .when()
            .post("/customers")
        .then()
            .statusCode(402);
    }

    @Test
    public void shouldReportInternalServerError() {
        get("/internal-server-error").then()
            .statusCode(INTERNAL_SERVER_ERROR.value())
            .body("code", equalTo(500))
            .body("status", equalTo("Internal-Server-Error"))
            .body("message", equalTo("Oops! Something went wrong!"));
    }

    @Test
    public void shouldReportUnknownPath() {
        get("/bogus-path").then()
            .statusCode(NOT_FOUND.value())
            .body("code", equalTo(404))
            .body("status", equalTo("Not-Found"))
            .body("message", equalTo("Unknown path: /bogus-path"));
    }

    @Test
    public void shouldReportWrongPayload_OnInvalidMarshalling() {
        given()
            .contentType(ContentType.JSON)
            .header("CompanyID", COMPANY_ALL_SET)
            .body("{\"age\": \"error\"}") // age should be an integer
        .when()
            .post("/customers")
        .then()
            .statusCode(BAD_REQUEST.value())
            .body("code", equalTo(400))
            .body("status", equalTo("Bad-Request"))
            .body("message", equalTo("Failure parsing request body!"));
    }

}
