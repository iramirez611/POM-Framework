package TestComponents;

import Resources.ExtentReportNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends TestBase implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentReportNG.getReportObject();
    //Thread safe for test when executing parallel classes or methods
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); //Unique thread ID
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        extentTest.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        //Reference image saved to local disk
        //test.fail(MediaEntityBuilder.createScreenCaptureFromPath("/Reports/Screenshots/" + result.getMethod().getMethodName() + ".png").build());
        //base 64
        //test.fail(MediaEntityBuilder.createScreenCaptureFromBase64String("/Reports/Screenshots/based64").build());
        extentTest.get().fail(result.getThrowable());
        //Get the driver from the failed test
        try{
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        }catch (Exception e){
            e.printStackTrace();
        }

        String filePath = null;
        try{
            filePath = getScreenShot(driver, result.getMethod().getMethodName());
        }catch (IOException e){
            e.printStackTrace();
        }
        extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        //ITestListener.super.onFinish(context);
        extent.flush();
    }
}
