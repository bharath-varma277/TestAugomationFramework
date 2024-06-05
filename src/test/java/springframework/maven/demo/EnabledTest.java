package springframework.maven.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EnabledTest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void isEnabledTest() {
        driver.get("https://demo.nopcommerce.com/register");
        WebElement search = driver.findElement(By.id("small-searchterms"));
        System.out.println("is Enabled : " + search.isEnabled());
        System.out.println("is Displayed : " + search.isDisplayed());

        WebElement maleGender = driver.findElement(By.id("gender-male"));
        System.out.println("is Selected:" + maleGender.isSelected());

        maleGender.click();
        System.out.println("is Selected:" + maleGender.isSelected());
//        driver.close();
    }

    @Test
    public void isDisplayedTest() {
        driver.get("http://omayo.blogspot.com/");
        WebElement displayed = driver.findElement(By.id("but1"));
        Actions actions = new Actions(driver);
        actions.scrollToElement(displayed).perform();
        System.out.println("is displayed: " + displayed.isEnabled());

        WebElement enabled = driver.findElement(By.id("but2"));
        System.out.println("is enabled: " + enabled.isEnabled());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
