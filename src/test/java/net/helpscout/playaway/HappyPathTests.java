package net.helpscout.playaway;

import com.google.common.collect.ImmutableMap;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.helpscout.playaway.customer.CustomerRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class HappyPathTests extends FunctionalTest {

	@Autowired
	CustomerRepository customerRepository;

	@Test
	public void readingControllerReturnsDataFromSlave() {
		// master script is empty, so if any data is returned it must have used slave

		given()
			.header("CompanyID", 1)
		.when()
			.get("/customers/1")
		.then()
			.statusCode(200)
			.body("id", equalTo(1))
			.body("first", equalTo("The"))
			.body("last", equalTo("Dude"));
	}

	@Test
	public void writingControllerSavesDataToMaster() {
		assertThat(customerRepository.findAll().size()).isEqualTo(0);

	    given()
			.contentType(ContentType.JSON)
			.header("CompanyID", 1)
			.body(ImmutableMap.of("first", "New", "last", "Custie"))
		.when()
			.post("/customers")
		.then()
			.statusCode(201)
			.header("Location", "www.the-internet.com");

		assertThat(customerRepository.findAll().size()).isEqualTo(1);
	}

}
