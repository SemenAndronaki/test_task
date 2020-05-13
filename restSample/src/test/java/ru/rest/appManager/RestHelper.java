package ru.rest.appManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import ru.rest.model.User;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class RestHelper {
    private final ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<User> getUsers() {
        String json = RestAssured.get(app.getProperty("url.base") + "/users").asString();
        if (json.equals("[]")) {
            return new HashSet<>();
        }
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement users = parsed.getAsJsonObject();
        return new Gson().fromJson(users, new TypeToken<Set<User>>() {
        }.getType());
    }

    public void createUser(User user) {
        RestAssured.baseURI = app.getProperty("url.base");
        given().urlEncodingEnabled(true).contentType(ContentType.JSON)

                .queryParam("createdAt", user.getCreatedAt())
                .queryParam("email", user.getEmail())
                .queryParam("id", 1)
                .queryParam("firstName", user.getFirstName())
                .queryParam("secondName", user.getSecondName())
                .post("/users");
    }

    public User patchUser(User expected) {
        String json = given()
                .formParam("id", expected.getId())
                .formParam("firstName", expected.getFirstName())
                .formParam("secondName", expected.getSecondName())
                .formParam("createdAt", expected.getCreatedAt())
                .formParam("email", expected.getEmail())
                .patch(app.getProperty("url.base") + "/users/" + expected.getId()).asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement users = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(users, new TypeToken<Set<User>>() {
        }.getType());
    }
}