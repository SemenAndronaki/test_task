package ru.rest.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.rest.model.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RestTest extends TestBase {
    @BeforeMethod
    public void createUserIfNotExist() {
        if (app.rest().getUsers().size() == 0) {
            app.rest().createUser(new User().setEmail("email").setFirstName("firstName")
                    .setSecondName("secondName").setCreatedAt(getDate()));
        }
    }

    @Test
    public void testPatchUser() throws IOException {
        Date date = new Timestamp(System.currentTimeMillis());

        User expected = new User().setEmail("new email").setFirstName("new firstName")
                .setSecondName("new secondName").setCreatedAt(date.toString())
                .setId(app.rest().getUsers().iterator().next().getId());

        User actual = app.rest().patchUser(expected);
        assertThat(expected, equalTo(actual));
    }

    @Test
    public void test(){
        User user = new User().setEmail("new email").setFirstName("new firstName")
                .setSecondName("new secondName").setCreatedAt(getDate());
        RestAssured.baseURI = app.getProperty("url.base");
        given().urlEncodingEnabled(true).contentType(ContentType.JSON)
                .queryParam("createdAt", user.getCreatedAt())
                .queryParam("email", user.getEmail())
                .queryParam("id", 1)
                .queryParam("firstName", user.getFirstName())
                .queryParam("secondName", user.getSecondName())
                .post("/users");
    }

    private String getDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

        String dateAsString = dateFormatter.format(new Date());
        String timeAsString = timeFormatter.format(new Date());


        return dateAsString +"T"+timeAsString;
    }
}