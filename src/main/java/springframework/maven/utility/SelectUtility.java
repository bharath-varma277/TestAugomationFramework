package springframework.maven.utility;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectUtility {

    Select dropDown;

    public SelectUtility(WebElement element) {
        dropDown = new Select(element);
    }

    public void selectByText(String dropDownValue) {
        dropDown.selectByVisibleText(dropDownValue);
    }

    public void selectByValue(String dropDownValue) {
        dropDown.selectByValue(dropDownValue);
    }

    public void selectByIndex(int dropDownValue) {
        dropDown.selectByIndex(dropDownValue);
    }

}
