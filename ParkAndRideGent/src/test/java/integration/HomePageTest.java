package integration;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.WebDriverRunner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomePageTest {

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
    public void WrongInputAddsErrorClass() throws Exception {
        open("/ParkAndRideGent");
        // Fill in wrong values in textfields and press enter
        $("#start-input").setValue("abc");
        $("#end-input").setValue("def").pressEnter();
        // Assert error class is added
        $$(".form-group").get(0).has(Condition.cssClass("has-error"));
        $$(".form-group").get(1).has(Condition.cssClass("has-error"));
        // Assert no redirect has happend
        Assert.assertEquals("http://localhost:8080/ParkAndRideGent", driver.getCurrentUrl());
    }

    @Test
    public void NoInputAddsErrorClass() throws Exception {
        open("/ParkAndRideGent");
        // Submit without values
        $("#submit").click();
        // Assert error class is added
        $$(".form-group").get(0).has(Condition.cssClass("has-error"));
        $$(".form-group").get(1).has(Condition.cssClass("has-error"));
        // Assert no redirect has happend
        Assert.assertEquals("http://localhost:8080/ParkAndRideGent", driver.getCurrentUrl());
    }

    @Test
    public void SearchRouteWorks() throws Exception {
        open("/ParkAndRideGent");
        // fill in start textfield
        $("#start-input").setValue("Antwerpen");
        // click first element in start dropdown
        $(".pac-container.pac-logo",0).$(".pac-item", 0).click();
        // fill in destination textfield
        $("#end-input").setValue("Gent-Sint-Pieters");
        // click first element in destination dropdown
        $(".pac-container.pac-logo", 1).$(".pac-item",0).click();
        // click submit button
        $("#submit").click();
        // result should be visible
        $(".result-routes").shouldBe(Condition.visible);
        // result should contain 3 routes
        Assert.assertEquals(3, $$(".route-choice").size());
        // Nearest P&R should be Sint-Pietersstation (should this be hardcoded?)
        $$(".park-ride-name").get(0).find("Sint-Pietersstation").exists();
        $$(".park-ride-name").get(1).shouldNotBe(Condition.visible);
        $$(".park-ride-name").get(2).shouldNotBe(Condition.visible);

        // Click on second route works
        $$(".route-choice").get(1).click();
        // Second P&R should be Bellevue, first one no longer visible
        $$(".park-ride-name").get(0).shouldNotBe(Condition.visible);
        $$(".park-ride-name").get(1).shouldBe(Condition.visible);
        $$(".park-ride-name").get(1).find("Bellevue").exists();

    }

    @Test
    public void usingHourInputWorksViaUrl() throws Exception {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String text = tomorrow.format(DateTimeFormatter.ISO_LOCAL_DATE);
        open("http://localhost:8080/ParkAndRideGent/route?start_name=Tielt%2C+Belgi%EB&start_id=ChIJASFfwR1Bw0cRYF1NL6uZAAQ&end_name=Gent-Sint-Pieters%2C+Gent%2C+Belgi%EB&end_id=ChIJHzU3k99zw0cR7Uoju6Gh7Uw&date-input="+text+"&hour-input=10%3A04&selected-pr=0");
        String start = $$(".start-time").get(0).getText();
        Assert.assertEquals("10:04", start);
        String end = $$(".end-time").get(0).getText();

        LocalTime startTime = LocalTime.parse(start);
        LocalTime endTime = LocalTime.parse(end);

        Assert.assertTrue(endTime.isAfter(startTime));
    }

    @Test
    public void usingHourInputWorks() throws Exception {
        open("http://localhost:8080/ParkAndRideGent");
        $("#hour-input").sendKeys("23:59");
        // fill in start textfield
        $("#start-input").setValue("Antwerpen");
        // click first element in start dropdown
        $(".pac-container.pac-logo",0).$(".pac-item", 0).click();
        // fill in destination textfield
        $("#end-input").setValue("Gent-Sint-Pieters");
        // click first element in destination dropdown
        $(".pac-container.pac-logo", 1).$(".pac-item",0).click();
        $("#submit").click();
        String start = $$(".start-time").get(0).getText();
        Assert.assertEquals("23:59", start);
    }

    @Test
    public void wrongHourInputShowsError() throws Exception {
        open("http://localhost:8080/ParkAndRideGent");
        // set hour input so it is in the past
        $("#hour-input").sendKeys("00:01");
        // fill in start textfield
        $("#start-input").setValue("Antwerpen");
        // click first element in start dropdown
        $(".pac-container.pac-logo",0).$(".pac-item", 0).click();
        // fill in destination textfield
        $("#end-input").setValue("Gent-Sint-Pieters");
        // click first element in destination dropdown
        $(".pac-container.pac-logo", 1).$(".pac-item",0).click();
        // click submit button
        $("#submit").click();
        $("#hour-input").has(Condition.cssClass("has-error"));
    }

    @Test
    public void selectSpecificPRWorks() throws Exception {
        open("http://localhost:8080/ParkAndRideGent");
        // fill in start textfield
        $("#start-input").setValue("Antwerpen");
        // click first element in start dropdown
        $(".pac-container.pac-logo",0).$(".pac-item", 0).click();
        // fill in destination textfield
        $("#end-input").setValue("Gent-Sint-Pieters");
        // click first element in destination dropdown
        $(".pac-container.pac-logo", 1).$(".pac-item",0).click();
        // select a p&r
        $("#more-options-button").click();
        $("#collapseExample").shouldBe(Condition.visible);

        WebElement selectedPR = $("#selected-pr").findElementsByTagName("option").get(3);
        selectedPR.click();
        String prName = selectedPR.getText();
        // click submit button
        $("#submit").click();
        $(".result-routes").shouldBe(Condition.visible);
        // result should contain 1 route
        Assert.assertEquals(1, $$(".route-choice").size());
        // route via correct p&r was calculated
        $$(".park-ride-name").get(0).find("P+R " + prName).exists();
    }

    @Test
    public void usingPastDateInUrlShowsError() throws Exception {
        open("http://localhost:8080/ParkAndRideGent/route?start_name=Tielt%2C+Belgi%EB&start_id=ChIJASFfwR1Bw0cRYF1NL6uZAAQ&end_name=Gent-Sint-Pieters%2C+Gent%2C+Belgi%EB&end_id=ChIJHzU3k99zw0cR7Uoju6Gh7Uw&date-input=2016-12-12&hour-input=10%3A04&selected-pr=0");
        Assert.assertEquals("Vetrekdatum en uur mag niet in het verleden liggen",$$(".error-messages").get(0).text());
    }

    @Test
    public void missingParametersInUrlShowsError() throws Exception {
        open("http://localhost:8080/ParkAndRideGent/route?start_id=ChIJASFfwR1Bw0cRYF1NL6uZAAQ&end_name=Gent-Sint-Pieters%2C+Gent%2C+Belgi%EB&end_id=ChIJHzU3k99zw0cR7Uoju6Gh7Uw&date-input=2016-12-12&hour-input=10%3A04&selected-pr=0");
        Assert.assertEquals("Gelieve vertrek- en aankomstlocatie correct in te vullen",$$(".error-messages").get(0).text());

    }
}
