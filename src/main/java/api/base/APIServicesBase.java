package api.base;

import api.exception.RestAssuredFailException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;

@Slf4j
public class APIServicesBase {

  public APIServicesBase() {}

  public Response getRequest(String relativeURI) {
    RestAssured.baseURI = Constants.baseURI;
    RestAssured.basePath = relativeURI;
    try {

      return given().log().all().when().get();

    } catch (Exception ex) {
      log.error("Failed due to ", ex);
      throw new RestAssuredFailException();
    }
  }
}
