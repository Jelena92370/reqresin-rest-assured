package tests;

import dto.UserDataResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetListUsers {

    @Test
    public void getListUserPage2() {
        List<UserDataResponse> users = given().baseUri("https://reqres.in")
                .when().log().all()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all().statusCode(200)
                .extract().body().jsonPath()
                .getList("data", UserDataResponse.class);

        assertEquals(6, users.size());

        for (UserDataResponse user : users) {
            assertTrue(user.getId() > 0);
            assertTrue(user.getEmail().endsWith("@reqres.in"));
            assertTrue(user.getAvatar().endsWith(user.getId() + "-image.jpg"));

        }
        users.forEach(user -> assertTrue(user.getId() > 0));
        users.forEach(user -> assertTrue(user.getEmail().endsWith("@reqres.in")));
        users.forEach(user -> assertTrue(user.getAvatar().endsWith(user.getId() + "-image.jpg")));

    }
}
