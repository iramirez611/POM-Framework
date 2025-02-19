package TestComponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    public WebDriver driver;

    public WebDriver initializeDriver() throws IOException {

        Properties property = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/Resources/GlobalData.properties");
        property.load(fis);

        String browserName = System.getProperty("broswer") != null ? System.getProperty("broswer") : property.getProperty("browser");

        if (browserName.toLowerCase().contains("chrome")) { //Chrome browser
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            if (browserName.contains("headless")) {
                options.addArguments("--headless");
            }
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
        return driver;
    }

    @BeforeMethod (alwaysRun = true)
    public void launchApplication() throws IOException {
        driver = initializeDriver();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(){
        driver.close();
        driver.quit();
    }

    public String getScreenShot(WebDriver driver, String testName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "/Reports/Screenshots/" + testName + ".png");
        FileUtils.copyFile(source, file);

        return System.getProperty("user.dir") + "/Reports/Screenshots/" + testName + ".png";
    }
}
