package constants;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class ResponseSpec {
    private ResponseSpec(){

    }

    public static ResponseSpecification checkStatusCodeOk() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification checkStatusCodeBadRequest() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

    public static ResponseSpecification checkStatusCodeCreated() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(HttpStatus.SC_CREATED)
                .build();
    }

    public static ResponseSpecification checkStatusCodeContentTypeBinary() {
        return new ResponseSpecBuilder()
                .expectContentType("application/zip")
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }
}
