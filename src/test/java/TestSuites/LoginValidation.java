package TestSuites;

import DataProvider.DataReader;
import PageObjects.LandingPage;
import TestComponents.TestBase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoginValidation extends TestBase {

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> loginData = DataReader.readData("/src/test/java/DataProvider/LoginData.json");
        return new Object[][] {{loginData.get(0)}, {loginData.get(1)}};
    }

    @Test
    public void landingPageValidation(){
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo("https://rahulshettyacademy.com/client");
        Assert.assertEquals(driver.getTitle(),"Let's Shop");
    }

    @Test (dataProvider = "getData")
    public void loginValidation(HashMap<String, String> loginData){
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo("https://rahulshettyacademy.com/client");
        landingPage.loginApp(loginData.get("email"), loginData.get("password"));
        Assert.assertEquals(driver.getTitle(),"Let's Shop");
    }
}
