import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.junit.Assert.*;
public class BaseTest {
    WebDriver driver;
    WebDriverWait wait;
    //    WebDriverWait explicitWait;

    public void highlightElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", element);
    }
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String devToUrl = "https://dev.to/";
        driver.get(devToUrl);
        wait = new WebDriverWait(driver, 20);
//        explicitWait = new WebDriverWait(driver, 20);
    }
    @Test
    public void selectFirstPostFromWeek(){
        WebElement week = driver.findElement(By.xpath("//a[@href='/top/week']"));
        week.click();
        wait.until(ExpectedConditions.urlToBe("https://dev.to/top/week")); //zanim zaczniesz szukać elementu, poczekaj aż url będzie miał wartość https://dev.to/top/week
        WebElement firstPostOnWeek = driver.findElement(By.cssSelector("h2.crayons-story__title > a"));
        String firstPostOnWeekText = firstPostOnWeek.getText();
        String firstPostLink = firstPostOnWeek.getAttribute("href");
        firstPostOnWeek.click();
        wait.until(ExpectedConditions.urlToBe(firstPostLink));
        WebElement postTitle = driver.findElement(By.cssSelector("div.crayons-article__header__meta > h1"));
        String postUrl = driver.getCurrentUrl();
        String postTitleText = postTitle.getText();
        assertEquals("Urls aren't the same", postUrl, firstPostLink);
        assertEquals("Titles aren't the same",postTitleText, firstPostOnWeekText);
    }
    @Test
    public void searchBarTesting() {
        WebElement searchBox = driver.findElement(By.id("nav-search"));
        String searchText = "testing";
        String searchUrl = "https://dev.to/search?q=";
        String cssSelector = "h2.crayons-story__title a";
        searchBox.sendKeys(searchText + Keys.ENTER);
        wait.until(ExpectedConditions.urlToBe(searchUrl + searchText));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
        List<WebElement> postTilesList = driver.findElements(By.cssSelector(cssSelector));
        for (int i = 0; i < 3; i++) {
            String message = String.format("Header %d doesn't contain text %s", i, searchText);
            Assert.assertTrue(message, postTilesList.get(i).getText().toLowerCase().contains(searchText));
        }
    }
    @Test
    public void findJavaInNavBar(){
        WebElement java = driver.findElement(By.cssSelector("div#default-sidebar-element-java > a"));
        highlightElement(driver, java);
        java.click();
    }
    @Test
    public void PodcastTest(){
       WebElement podcast = driver.findElement(By.xpath("//a[@href='/pod']"));
       podcast.click();
       wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));
       wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h3")));
       List<WebElement> podcasts = driver.findElements(By.tagName("h3"));
       podcasts.get(3).click();
       wait.until(ExpectedConditions.urlContains("stackpodcast"));
       WebElement playArea = driver.findElement(By.className("record-wrapper"));
       playArea.click();
       WebElement initializing = driver.findElement(By.className("status-message"));

               String playArenaClassAttribute = playArea.getAttribute("class");
        boolean isPlaying = playArenaClassAttribute.contains("playing");


        assertTrue("Podcast isn't playing", isPlaying);
        }

        private void waitForElements(String targetUrl, String cssSelector) {
            wait.until(ExpectedConditions.urlContains(targetUrl));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
        }

    @After
    public void cleanUp() {
//        driver.quit();
    }

}