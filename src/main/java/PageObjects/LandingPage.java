package PageObjects;

import Common.CommonComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends CommonComponents {

    WebDriver driver;

    public LandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Landing Page Factory locators
    @FindBy (id = "userEmail")
    WebElement userEmailInput;

    @FindBy (id = "userPassword")
    WebElement userPasswordInput;

    @FindBy (id = "login")
    WebElement loginButton;

    @FindBy (css = "div.invalid-feedback")
    WebElement invalidFeedback;

    public void goTo(String url){
        driver.get(url);
    }

    public void loginApp(String email, String password){
        waitForWebElementToAppear(userEmailInput);
        userEmailInput.sendKeys(email);
        userPasswordInput.sendKeys(password);
        loginButton.click();
    }

    public String getInvalidFeedback(){
        return invalidFeedback.getText();
    }

}
