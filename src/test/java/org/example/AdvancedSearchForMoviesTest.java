package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvancedSearchForMoviesTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAdvancedSearchForMovies() throws InterruptedException {

        driver.get("https://www.imdb.com/");

        //press the button All to open the menu
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='nav-search-form']/div[1]/div")
        )).click();

        // Click Advanced Search
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='nav-search-form']/div[1]/div/div/div/div/ul/a")
        )).click();

        //choose Movie from Title type
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"accordion-item-titleTypeAccordion\"]/div/section/button[1]"))
        ).click();
        Thread.sleep(500);

        //scroll and open release Date section
        WebElement releaseDate = driver.findElement(By.id("releaseDateAccordion"));
        new Actions(driver)
                .scrollToElement(releaseDate)
                .perform();
        Thread.sleep(300);

        releaseDate.click();

        //enter release year  from and to
        WebElement releaseFrom = driver.findElement(By.name("release-yearmonth-start-input"));
        new Actions(driver)
                .scrollToElement(releaseFrom)
                .perform();

        releaseFrom.sendKeys("2010");

        WebElement releaseTo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("release-yearmonth-end-input")));

        releaseTo.sendKeys("2020");

        Thread.sleep(3000);

        WebElement genre = driver.findElement(By.id("genreAccordion"));
        new Actions(driver)
                .scrollToElement(genre)
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"accordion-item-genreAccordion\"]/div/section/button[1]"))
        ).click();
        Thread.sleep(3000);

        WebElement searchButton =wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-testid='adv-search-get-results']")));
        searchButton.click();

        Thread.sleep(7000);

        WebElement firstResult = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//ul[contains(@class,'ipc-metadata-list')]//h3)[1]")
                )
        );

        String firstMovieName = firstResult.getText();

        assertEquals("1. Inception", firstMovieName,
                "First result does not match");

        System.out.println("First Action movie from 2010-2020: " + firstMovieName);



    }
}
