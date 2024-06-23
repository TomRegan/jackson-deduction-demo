package io.tomregan.sorrymsjackson;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.tomregan.sorrymsjackson.Application.AnimalsController;

final class AnimalsControllerTest {

    @Test
    void intergalactic_animal_is_valid() {
        // language=JSON
        var input = """
                {
                  "created": "1977-05-25T12:00:00Z",
                  "animal": {
                    "galaxy": "Far Far Away",
                    "name": "Womp Rat"
                  }
                }""";

        given().standaloneSetup(new AnimalsController())
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/animals")
                .then()
                .statusCode(is(equalTo(200)));
    }

    @Test
    void provincial_animal_is_valid() {
        // language=JSON
        var input = """
                {
                  "created": "1835-09-15T06:00:00Z",
                  "animal": {
                    "province": "Galápagos Islands",
                    "name": "Galápagos tortoise"
                  }
                }""";

        given().standaloneSetup(new AnimalsController())
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/animals")
                .then()
                .statusCode(is(equalTo(200)));
    }

    @Test
    void lunar_animal_is_invalid() {
        // language=JSON
        var input = """
                {
                  "created": "1969-07-20T20:17:40Z",
                  "animal": {
                    "region": "Mare Tranquillitatis",
                    "name": "Lunar Tranquillux"
                  }
                }""";

        given().standaloneSetup(new AnimalsController())
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/animals")
                .then()
                .statusCode(is(equalTo(400)));
    }
}

