package tests;

import dto.NotSuccessfullResponse;
import dto.RegisterRequest;
import dto.SuccessRegisterResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    @Test
    public void successRegistration() {
        RegisterRequest requestBody = new RegisterRequest("eve.holt@reqres.in", "pistol");
        //RegisterRequest request = RegisterRequest.builder(). .email("eve.holt@reqres.in").password("pistol").build();
   SuccessRegisterResponse response = given().baseUri("https://reqres.in")
            .body(requestBody)
            .when().log().all()
            .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(200)
            .extract().body().jsonPath().getObject("", SuccessRegisterResponse.class);

        assertNotNull(response.getId());
        assertFalse(response.getToken().isEmpty());
        assertEquals(4, response.getId());
}

    @Test
    public void registrationWithoutPassword() {
        RegisterRequest request = RegisterRequest.builder().email("eve.holt@reqres.in").build();
        NotSuccessfullResponse response = given().baseUri("https://reqres.in")
                .body(request)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400)
                .extract().body().jsonPath().getObject("", NotSuccessfullResponse.class);

        assertTrue(response.getError().contains("Missing password"));

}

    @Test
    public void registrationWithoutEmail() {
        RegisterRequest request = RegisterRequest.builder().password("pistol").build();
        NotSuccessfullResponse response = given().baseUri("https://reqres.in")
                .body(request)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400)
                .extract().body().jsonPath().getObject("", NotSuccessfullResponse.class);

        assertEquals("Missing email or username", response.getError());

    }

    @Test
    public void registrationWithEmptyFields() {
        RegisterRequest request = RegisterRequest.builder().build();
        NotSuccessfullResponse response = given().baseUri("https://reqres.in")
                .body(request)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400)
                .extract().body().jsonPath().getObject("", NotSuccessfullResponse.class);

        assertEquals("Missing email or username", response.getError());

    }

    @Test
    public void registrationWithNotDefinedUser() {
        RegisterRequest request = RegisterRequest.builder().email("eva@reqres.in").password("rtgre").build();
        NotSuccessfullResponse response = given().baseUri("https://reqres.in")
                .body(request)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400)
                .extract().body().jsonPath().getObject("", NotSuccessfullResponse.class);

        assertEquals("Note: Only defined users succeed registration", response.getError());

    }
}
