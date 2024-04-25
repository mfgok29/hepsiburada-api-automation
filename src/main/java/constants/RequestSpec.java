package constants;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class RequestSpec {

    private RequestSpec() {

    }

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder().build()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter(LogDetail.ALL))
                .baseUri(Url.GENERATOR_API_URL);
    }

    public static RequestSpecification requestSpecificationFileDownload() {
        return new RequestSpecBuilder().build()
                .accept(ContentType.BINARY)
                .contentType(ContentType.BINARY)
                .filter(new RequestLoggingFilter(LogDetail.ALL))
                .baseUri(Url.GENERATOR_API_URL);
    }



}
