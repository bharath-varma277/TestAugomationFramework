package saucelab;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SauceLabDemoRunningTest {

    public final String sauceLabAccessURL = "https://oauth-bharath.varma.vanaparthi-efc2d:45bc542e-61e2-4360-827d-2ef03f6ab36d@ondemand.eu-central-1.saucelabs.com:443/wd/hub";
    WebDriver driver;

    @Parameters({"browser", "version", "platform"})
    public void setUp(String br, String vr, String pf) throws MalformedURLException {
//				DesiredCapabilities dc = new DesiredCapabilities();
//				dc.setCapability("browserName", br );
//				dc.setCapability("version", vr);
//				dc.setCapability("platform", pf);

        // As the DesiredCapabilities are depreciated now we are using this AbstractDriverOptions

        AbstractDriverOptions<?> dc = null;
        switch (br.toLowerCase()) {
            case "chrome":

                dc = new ChromeOptions();
                dc.setBrowserVersion(vr);
                dc.setPlatformName(pf);
                break;
            case "firefox":
                dc = new FirefoxOptions();
                dc.setBrowserVersion(vr);
                break;
            case "safari":
                dc = new SafariOptions();
                dc.setBrowserVersion(vr);
                break;
            default:
                break;
        }

        driver = new RemoteWebDriver(new URL(sauceLabAccessURL), dc);
    }

    @Test
    public void verifyLogin() throws InterruptedException {
//        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));

        WebElement userName = driver.findElement(By.xpath("//input[@name='username']"));
        userName.sendKeys("Admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.quit();
    }
}
