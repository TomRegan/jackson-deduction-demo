package io.tomregan.jackson;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;

interface AnimalsControllerTestFixture {

    default MockMvcRequestSpecification givenJson(String input) {
        return given().standaloneSetup(new Application.AnimalsController())
                .contentType(ContentType.JSON)
                .body(input);
    }
}
