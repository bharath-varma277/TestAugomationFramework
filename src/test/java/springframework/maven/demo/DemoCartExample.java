package springframework.maven.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DemoCartExample {
    static WebDriver driver;

    public static void main(String[] args) {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.opencart.com/");
        List<WebElement> headerLists = driver.findElements(By.className("list-inline-item"));
        headerLists.forEach(System.out::println);
        System.out.println(headerLists.size());

        List<WebElement> desktop = driver.findElements(By.xpath("//a[@class='nav-link dropdown-toggle show']"));
        System.out.println(desktop.size());
//        System.out.println(desktop.get(0).getText());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement desk = driver.findElement(By.xpath("(//a[@class='nav-link dropdown-toggle'])[1]"));
        System.out.println(desk.getText());

        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Links size:" + links.size());

//        driver.switchTo().newWindow(WindowType.WINDOW);
//        driver.get("https://selenium.dev");
////        driver.close();
        driver.quit();
    }

    @Test
    public void cssSelector() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demo.nopcommerce.com/");
        driver.manage().window().maximize();

//        WebElement search = driver.findElement(By.cssSelector("input#small-searchterms"));
        WebElement search = driver.findElement(By.cssSelector("#small-searchterms"));
        search.sendKeys("Mobile Phones");
//        search.sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("button.button-1")).click();
        driver.findElement(By.cssSelector("input[class=search-text]")).clear();

        WebElement searchItem = driver.findElement(By.className("button-1"));
        String color = searchItem.getCssValue("background-color");
        System.out.println(color);
        driver.close();
    }

    @Test
    public void handlingActions() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");
        driver.manage().window().maximize();
        Actions actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.className("context-menu-one"))).perform();
//        actions.click(driver.findElement(By.className("context-menu-item context-menu-icon context-menu-icon-delete")));
        actions.click(driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon-delete"))).perform();
        driver.switchTo().alert().accept();
//        driver.close();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://www.youidraw.com/apps/painter/#google_vignette");

        actions.clickAndHold(driver.findElement(By.id("painter"))).moveByOffset(0, 100)
                .moveByOffset(100, 0)
                .moveByOffset(0, -100)
                .moveByOffset(-100, 0)
                .release().perform();

        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("https://demo.opencart.com/");
        WebElement mouseHoverOp = driver.findElement(By.xpath("//a[text()='Desktops']"));
        new Actions(driver).moveToElement(mouseHoverOp).click(driver.findElement(
                By.xpath("//a[text()='PC (0)']")
        )).perform();


        driver.quit();
    }

    @Test
    public void windowHandles() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://selenium.dev");
        String parentWindow = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https:sonarqube.org");


        System.out.println(parentWindow);
        driver.switchTo().window(parentWindow);

        Set<String> allWindows = driver.getWindowHandles();
        System.out.println("-".repeat(90));
        for (String allWindow : allWindows) {
            System.out.println(allWindow);
        }
        Iterator<String> iterator = allWindows.iterator();
//        String parent = iterator.next();
        String child = "";
        while (iterator.hasNext()) {
            child = iterator.next();
        }
        driver.switchTo().window(child);
    }

    @Test
    public void relativeLocators() {
        //Selenium 4 Feature
        // above, below, toLeftOf, toRightOf, near
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("https://www.browserstack.com/");
        driver.manage().window().maximize();
        WebElement appLive = driver.findElement(By.className("product-section__content-title"));
        WebElement getDemo = driver.findElement(RelativeLocator.with(By.tagName("button")).above(appLive));
        getDemo.click();
    }
}
