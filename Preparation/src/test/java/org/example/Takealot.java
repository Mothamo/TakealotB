package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Takealot {

    WebDriver Driver;
    WebElement webElement;
    WebDriverWait wait;

    Entities ent = new Entities();
    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        Driver = new ChromeDriver();
        Driver.get(ent.URL);
    }
    @Test
    public void getPlaystation() throws InterruptedException {
        //Spark report saved on the c drive local machine
        ExtentReports reports= new ExtentReports();
        ExtentSparkReporter rpt = new ExtentSparkReporter(ent.Path);
        reports.attachReporter(rpt);
        ExtentTest test = reports.createTest("order placed");

        wait = new WebDriverWait(Driver, Duration.ofSeconds(45));
        Driver.manage().window().maximize();
        //Already on take a lot site
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ent.searchBar)));
        Driver.findElement(By.xpath(ent.searchBar)).sendKeys(ent.SearchInput);
        Driver.findElement(By.xpath(ent.searchButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ent.addCart)));

        JavascriptExecutor js = (JavascriptExecutor) Driver;
        js.executeScript("window.scrollBy(0,500)");

        //add playstation to cart
        Driver.findElement(By.xpath(ent.addCart)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ent.Trolley)));
        Driver.findElement(By.xpath(ent.Trolley)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ent.checkOut)));
        Driver.findElement(By.xpath(ent.checkOut)).click();

        Thread.sleep(1500);
        Driver.quit();
        test.log(Status.INFO,"Order has been placed");
        reports.flush();
    }

}
