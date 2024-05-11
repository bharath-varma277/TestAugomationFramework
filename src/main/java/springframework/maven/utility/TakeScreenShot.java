package springframework.maven.utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;

public class TakeScreenShot {
    static File file;
    static File destFile;

    public static void takeElementScreenShot(WebElement element, String filePath) {
        file = element.getScreenshotAs(OutputType.FILE);
        destFile = new File(filePath);
        try {
            FileUtils.copyFile(file, destFile);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public static void takePageScreenShot(WebDriver driver, String filePath) {
        file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        destFile = new File(filePath);
        try {
            FileUtils.copyFile(file, destFile);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
