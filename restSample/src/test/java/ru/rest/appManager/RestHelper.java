package ru.rest.appManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
        JsonElement users = parsed.getAsJsonArray();
        return new Gson().fromJson(users, new TypeToken<Set<User>>() {}.getType());
    }

    public void createUser(User user) {
        RestAssured.baseURI = app.getProperty("url.base");
        JsonObject json1 = new JsonObject();
        json1.addProperty("createdAt", user.getCreatedAt());
        json1.addProperty("email", user.getEmail());
        json1.addProperty("id", 1);
        json1.addProperty("firstName", user.getFirstName());
        json1.addProperty("secondName", user.getSecondName());

        given().log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(json1.toString())
                .post("/users");
    }

    public User patchUser(User expected) {
        RestAssured.baseURI = app.getProperty("url.base");
        JsonObject json1 = new JsonObject();
        json1.addProperty("createdAt", expected.getCreatedAt());
        json1.addProperty("email", expected.getEmail());
        json1.addProperty("id", expected.getId());
        json1.addProperty("firstName", expected.getFirstName());
        json1.addProperty("secondName", expected.getSecondName());

        String json =  given().log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(json1.toString())
                .patch(app.getProperty("url.base") + "/users/" + expected.getId()).asString();

        JsonElement parsed = JsonParser.parseString(json);
        JsonElement users = parsed.getAsJsonObject();
        return new Gson().fromJson(users, User.class);
    }
}