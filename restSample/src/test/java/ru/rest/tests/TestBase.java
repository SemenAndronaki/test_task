package ru.rest.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import ru.rest.appManager.ApplicationManager;

import java.io.IOException;

public class TestBase {
    protected static final ApplicationManager app = new ApplicationManager();

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
    }
}