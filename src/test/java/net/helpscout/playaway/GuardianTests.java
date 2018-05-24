package net.helpscout.playaway;

import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static net.helpscout.playaway.FunctionalTest.COMPANY_WITH_MISSING_PAYMENT;

public class GuardianTests {

    @Test
    public void errorIsGloballyHandled() {
        Assert.fail();
    }

    @Test
    public void guardianVetoesResourceAccess_ForReadingOpreation() {
        given().header("CompanyID", COMPANY_WITH_MISSING_PAYMENT).when().get("/customers/1").then().statusCode(402);
    }

    @Test
    public void guardianVetoesResourceAccess_ForWritingOpreation() {
        Assert.fail();
    }

}
