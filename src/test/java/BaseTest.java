import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class BaseTest {

    WebDriver driver;

    public void highlightElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", element);
    }


    @Before
public void setUp(){
    System.setProperty("webdriver.chrome.driver","C:\\chromedriver\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    }

    @Test
    public void firsTest(){
       String devToUrl = "https://dev.to";
        driver.get(devToUrl);
        WebElement week = driver.findElement(By.xpath("//a[@href='/top/week']"));
        week.click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe("https://dev.to/top/week"));
        WebElement firstPostOnWeek = driver.findElement(By.cssSelector("h2.crayons-story__title > a"));
        String firstPostOnWeektext = firstPostOnWeek.getText();
        String firstPostLink = firstPostOnWeek.getAttribute("href");

        firstPostOnWeek.click();
        wait.until(ExpectedConditions.urlToBe(firstPostLink));
        WebElement postTitle = driver.findElement(By.cssSelector("div.crayons-article__header__meta > h1"));

        String postUrl = driver.getCurrentUrl();
        String postTitleText = postTitle.getText();

        assertEquals("Urls don't maches", postUrl, firstPostLink);
        assertEquals("Titles aren't the same", postTitleText, firstPostOnWeektext);







    }
}

