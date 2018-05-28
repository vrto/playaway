package net.helpscout.playaway;

import com.google.common.collect.ImmutableMap;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PreconditionTests extends FunctionalTest {

    @Test
    public void urlPreconditionTerminatesReadingControllerOperation() {
        given()
            .header("CompanyID", 1)
        .when()
            .get("/customers/-1")
        .then()
            .statusCode(404)
            .body("message", equalTo("The customer doesn't exist!"));
    }

    @Test
    public void payloadPrecondition_WithDefaultMessage_TerminatesWritingControllerOperation() {
        given()
            .contentType(ContentType.JSON)
            .header("CompanyID", 1)
            .body(ImmutableMap.of("first", "New", "last", ""))
        .when()
            .post("/customers")
        .then()
            .statusCode(400)
            .body("message", equalTo("Payload invalid"));
    }

    @Test
    public void payloadPrecondition_WithCustomMessage_TerminatesWritingControllerOperation() {
        given()
            .contentType(ContentType.JSON)
            .header("CompanyID", 1)
            .body(ImmutableMap.of("first", "New", "last", "Kustomer"))
        .when()
            .post("/customers")
        .then()
            .statusCode(400)
            .body("message", equalTo("Kustomer already taken"));
    }

}
