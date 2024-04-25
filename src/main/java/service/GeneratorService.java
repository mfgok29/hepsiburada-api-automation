package service;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.request.GeneratorInputRequest;

import static io.restassured.RestAssured.given;

public class GeneratorService {

    public static final String CLIENTS_BASE_PATH = "/gen";


    public Response getClients(RequestSpecification requestSpec, ResponseSpecification responseSpec) {

        return given()
                .spec(requestSpec)
                .basePath(CLIENTS_BASE_PATH)
                .when()
                .get("/clients")
                .then()
                .assertThat()
                .spec(responseSpec)
                .log().ifValidationFails()
                .extract().response();
    }

    public Response generateClientLibrary(GeneratorInputRequest generatorInputRequest, String language, RequestSpecification requestSpec, ResponseSpecification responseSpec) {

        return given()
                .spec(requestSpec)
                .basePath(CLIENTS_BASE_PATH)
                .body(generatorInputRequest)
                .when()
                .pathParam("language", language)
                .post("/clients/{language}")
                .then()
                .assertThat()
                .spec(responseSpec)
                .log().ifValidationFails()
                .extract().response();
    }

    public Response getDownloadFile(String fileId, RequestSpecification requestSpec, ResponseSpecification responseSpec) {

        return given()
                .spec(requestSpec)
                .basePath(CLIENTS_BASE_PATH)
                .when()
                .pathParam("fileId", fileId)
                .get("/download/{fileId}")
                .then()
                .assertThat()
                .spec(responseSpec)
                .log().ifValidationFails()
                .extract().response();
    }


}
