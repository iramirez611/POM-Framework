package Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportNG {

    public static com.aventstack.extentreports.ExtentReports getReportObject(){
        String path = System.getProperty("user.dir") + "/Reports/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("POM-Framework-Test Report");
        reporter.config().setDocumentTitle("Test report: " + getTimestamp());

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("User-SQA", System.getProperty("user.name"));

        return extent;
    }

    public static String getTimestamp(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);

    }
}
