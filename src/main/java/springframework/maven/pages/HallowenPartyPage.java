package springframework.maven.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.Iterator;
import java.util.Set;

public class HallowenPartyPage {

    WebDriver driver;

    public HallowenPartyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public HallowenPartyPage attendingParty() {
        Set<String> set = driver.getWindowHandles();
        Iterator<String> iterator = set.iterator();
        String parent = iterator.next();
        driver.switchTo().window(parent);
        return new HallowenPartyPage(driver);
    }
}
