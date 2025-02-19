package TestSuites;

import DataProvider.DataReader;
import PageObjects.LandingPage;
import TestComponents.TestBase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * The type Login validation.
 */
public class LoginValidation extends TestBase {

    /**
     * Get data object [ ] [ ].
     *
     * @param m The method that calls the DataProvider
     * @return the object [ ] [ ]
     * @throws IOException the io exception
     */
    @DataProvider (name = "LoginData")
    public Object[][] getData(Method m) throws IOException {
        List<HashMap<String, String>> loginData = DataReader.readData("/src/test/java/DataProvider/LoginData.json");
        return switch (m.getName()) {
            case "loginValidation" -> new Object[][]{{loginData.get(0)}, {loginData.get(1)}};
            case "invalidLogin" -> new Object[][]{{loginData.get(2)}};
            default -> throw new IllegalArgumentException("Invalid Test case" + m.getName());
        };
    }

    /**
     * Landing page validation.
     */
    @Test
    public void landingPageValidation(){
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo("https://rahulshettyacademy.com/client");
        Assert.assertEquals(driver.getTitle(),"Let's Shop");
    }

    /**
     * Login validation.
     *
     * @param loginData The login data from the DataProvider
     */
    @Test (dataProvider = "LoginData")
    public void loginValidation(HashMap<String, String> loginData){
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo("https://rahulshettyacademy.com/client");
        landingPage.loginApp(loginData.get("email"), loginData.get("password"));
        Assert.assertEquals(driver.getTitle(),"Let's Shop");
    }

    /**
     * Invalid login.
     *
     * @param loginData The login data from the DataProvider
     */
    @Test (dataProvider = "LoginData")
    public void invalidLogin(HashMap<String, String> loginData){
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo("https://rahulshettyacademy.com/client");
        landingPage.loginApp(loginData.get("email"), loginData.get("password"));
        Assert.assertEquals(landingPage.getInvalidFeedback(),loginData.get("feedback"));
    }
}
