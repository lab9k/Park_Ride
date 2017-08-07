package integration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class OverviewPageTest {
private ChromeDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void overwiewShowsListOfPr() throws Exception {
        open("/ParkAndRideGent/overview");
        $(".table").exists();
    }

}
