package api.exception;

public class RestAssuredFailException extends RuntimeException {

  public RestAssuredFailException(String message, Throwable cause) {
    super(message, cause);
  }

  public RestAssuredFailException() {
    super("Rest assured initialization failed");
  }
}
