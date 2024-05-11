package springframework.maven.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtility {

    public static WebElement waitFunctionality(WebDriver driver, WebElement element) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return explicitWait.until(ExpectedConditions.visibilityOf(element));
    }
}
