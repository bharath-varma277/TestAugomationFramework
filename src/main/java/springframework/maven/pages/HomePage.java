package springframework.maven.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import springframework.maven.utility.SelectUtility;
import springframework.maven.utility.TakeScreenShot;
import springframework.maven.utility.WaitUtility;

public class HomePage {

    WebDriver driver;
    @FindBy(id = "popup-widget111379-close-icon")
    WebElement cancelPopUp;

    @FindBy(xpath = "//select[@id='tCounty']")
    WebElement selectCountry;

    @FindBy(xpath = ("(//span[@data-ux='Element']//iframe)[1]"))
    WebElement iframe;

    //    @FindBy(xpath = "(//input[@data-aid=\"CONTACT_FORM_NAME\"])[1]")
    @FindBy(xpath = "(//input[@role='textbox'])[1]")
    WebElement name;

    @FindBy(xpath = "(//input[@data-aid=\"CONTACT_FORM_EMAIL\"])[1]")
    WebElement email;

    @FindBy(xpath = "(//textarea[@data-aid=\"CONTACT_FORM_MESSAGE\"])[1]")
    WebElement message;

    @FindBy(xpath = "(//span[text()='Contact Us'])[1]")
    WebElement contactUs;

    @FindBy(xpath = "(//button[@type='submit'])[1]")
    WebElement send;

    JavascriptExecutor javascriptExecutor;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        javascriptExecutor = (JavascriptExecutor) driver;
    }

    public HomePage cancelPopUp() {
//        driver.findElement(By.id("popup-widget111379-close-icon")).click();
        cancelPopUp.click();
        return new HomePage(driver);
    }

    public HomePage selectCountry() {
        javascriptExecutor.executeScript("window.scrollBy(50,1112)", "");
        WaitUtility.waitFunctionality(driver, iframe);
        driver.switchTo().frame(iframe);

        WaitUtility.waitFunctionality(driver, selectCountry);
        new SelectUtility(selectCountry).selectByText("Berkshire");
        driver.switchTo().defaultContent();
        return new HomePage(driver);
    }

    public void contactUs(String nameUI, String emailUI, String messageUI) {
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", name);
//        javascriptExecutor.executeScript("window.scrollBy(50,1150)", "");
        Actions actions = new Actions(driver);
        actions.scrollToElement(message).perform();
        WaitUtility.waitFunctionality(driver, name);

        name.sendKeys(nameUI);
        email.sendKeys(emailUI);
        message.sendKeys(messageUI);
//        actions.scrollToElement(send).perform();
        TakeScreenShot.takeElementScreenShot(send, "send.jpeg");
        TakeScreenShot.takePageScreenShot(driver, "homepage.jpeg");
//        actions.doubleClick(send).perform();
    }

    public HallowenPartyPage hallowenParty() {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://candymapper.com/halloween-party");
        return new HallowenPartyPage(driver);
    }


}
