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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchForMovieTest {

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
    public void testSearchForMovie() {
        driver.get("https://www.imdb.com/");

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("suggestion-search"))
        );
        searchBox.sendKeys("The Shawshank Redemption");



        wait.until(ExpectedConditions.elementToBeClickable(By.id("suggestion-search-button"))).click();

        WebElement firstResult = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//ul[contains(@class,'ipc-metadata-list')]//h3)[1]")
                )
        );

        String firstMovieName = firstResult.getText();

        assertEquals("The Shawshank Redemption", firstMovieName,
                "First search result does not match");


        System.out.println("Search result matches: " + firstMovieName);
    }
}
