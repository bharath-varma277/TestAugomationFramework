package springframework.maven;

import org.testng.annotations.DataProvider;

public class TestDataProvider extends StartAndQuitBrowser {
    @DataProvider(name = "homepage")
    public Object[][] dataProvider() {
        return new Object[][]
                {
//                        {"User1", "TestUser1@gmail.com", "Send the input from the User1"},
                        {"User2", "TestUser2@gmail.com", "Send the input from the User2"}
                };

    }
}
