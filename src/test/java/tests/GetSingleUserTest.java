package tests;



import dto.UserDataResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class GetSingleUserTest {
    @Test
    public void getValidUserTest() {
        int request_id = 7;
        UserDataResponse user = given().baseUri("https://reqres.in")
                .when().log().all()
                .get("/api/users/" + request_id)
                .then().log().all().statusCode(200)
                .extract().body().jsonPath().
                getObject("data", UserDataResponse.class);

        assertEquals(request_id, user.getId());
        assertFalse(user.getEmail().isEmpty());
        assertTrue(user.getEmail().endsWith("@reqres.in"));

        assertTrue(user.getAvatar().endsWith(request_id + "-image.jpg"));

    }


}
