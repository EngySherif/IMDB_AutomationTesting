package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Top250MoviesTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testTop250MoviesFirstMovie() {

        driver.get("https://www.imdb.com/");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("imdbHeader-navDrawerOpen"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='ipc-list-item__text' and text()='Top 250 movies']"))).click();

        WebElement firstResult = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//ul[contains(@class,'ipc-metadata-list')]//h3)[1]"))

        );

        String firstMovieName = firstResult.getText();

        assertTrue(firstMovieName.equals("The Shawshank Redemption") ||
                        firstMovieName.equals("1.The Shawshank Redemption"),
                "The First of the Top 250 movie does not match");
        System.out.println("The First of the Top 250 movie matches: " + firstMovieName);
    }
}
