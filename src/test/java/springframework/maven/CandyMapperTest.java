package springframework.maven;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.Test;
import springframework.maven.pages.HomePage;

import java.io.IOException;
import java.util.logging.Logger;

// Using this Framework in the Jenkins Also
public class CandyMapperTest extends TestDataProvider {

    Logger logger = Logger.getLogger(getClass().getName());
    ExtentReports extent = new ExtentReports();
    ExtentSparkReporter spark = new ExtentSparkReporter("extent_report.html");


    //    System.setProperty("log4j.configurationFile","./path_to_the_log4j2_config_file/log4j2.xml");
//
//    Logger log = (Logger) LogManager.getLogger(CandyMapperTest.class);

    @Test(priority = 1, dataProvider = "homepage")
//    @Test
    public void seleniumUITest(String name, String email, String message) throws IOException {
//    public void seleniumUITest() throws IOException {
        extent.attachReporter(spark);
        ExtentTest report = extent.createTest("CandMapper HomePage")
                .info("This Test checks the UI of the candyMapper Site Using Windows");
        HomePage homePage = new HomePage(driver);
        homePage.cancelPopUp().selectCountry().
                contactUs(name, email, message);
        report.pass("Selecting the country and entering the contact us details correctly");
        homePage.hallowenParty().attendingParty();

        report.pass("Everything is working fine");
//        ReadExcel readExcel = new ReadExcel();
//        readExcel.getData("sheet1");
        extent.flush();
    }
}
