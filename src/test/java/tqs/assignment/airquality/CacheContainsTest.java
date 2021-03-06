package tqs.assignment.airquality;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class CacheContainsTest {
  private WebDriver driver;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void cacheContains() {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1443, 903));
    driver.findElement(By.id("city")).click();
    driver.findElement(By.id("city")).sendKeys("Lisbon");
    driver.findElement(By.id("country")).click();
    driver.findElement(By.id("country")).sendKeys("Portugal");
    driver.findElement(By.cssSelector("p:nth-child(3) > input:nth-child(1)")).click();
    driver.findElement(By.linkText("Search another location")).click();
    driver.findElement(By.id("city")).click();
    driver.findElement(By.id("city")).sendKeys("Lisbon");
    driver.findElement(By.id("country")).click();
    driver.findElement(By.id("country")).sendKeys("Portugal");
    driver.findElement(By.cssSelector("p:nth-child(3) > input:nth-child(1)")).click();
    {
      List<WebElement> elements = driver.findElements(By.xpath("//p[contains(.,\'Location: Lisbon, Portugal (got from cache!)\')]"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.linkText("Search another location"));
      assert(elements.size() > 0);
    }
  }
}
