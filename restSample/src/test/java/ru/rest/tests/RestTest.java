package ru.rest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.rest.model.User;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        User expected = new User().setEmail("new email").setFirstName("new firstName")
                .setSecondName("new secondName").setCreatedAt(getDate())
                .setId(app.rest().getUsers().iterator().next().getId());

        User actual = app.rest().patchUser(expected);
        assertThat(expected, equalTo(actual));
    }

    private String getDate() {
        Date date = new Timestamp(System.currentTimeMillis());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

        String dateAsString = dateFormatter.format(date);
        String timeAsString = timeFormatter.format(date);
        return dateAsString +"T"+timeAsString;
    }
}