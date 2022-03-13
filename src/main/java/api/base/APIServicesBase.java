package api.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class APIServicesBase {

    private Response response;


    public APIServicesBase() {
    }

    public Response getRequest(String relativeURI) {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        RestAssured.basePath = relativeURI;
        try {

            response = given().log().all().when().get();

            return response;

        } catch (Exception ex) {
            throw ex;
        }


    }
}
