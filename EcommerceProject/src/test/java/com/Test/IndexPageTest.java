package com.Test;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
  
  
import com.Pages.Baseclass;
import com.Pages.Indexpage;
import com.aventstack.extentreports.*;
  import com.aventstack.extentreports.reporter.ExtentSparkReporter;
  //import com.aventstack.extentreports.reporter.configuration.Theme;
  import org.openqa.selenium.OutputType;
  import org.openqa.selenium.TakesScreenshot;
  //import org.apache.commons.io.FileUtils;

  import java.io.File;
  import java.io.IOException;
import java.nio.file.Files;


public class IndexPageTest extends Baseclass {
	 private ExtentReports extent;
     private ExtentTest test;

     @BeforeClass
     public void setupReport() {
         ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReports/IndexpageTestReport.html");
         extent = new ExtentReports();
         extent.attachReporter(spark);
     }

    
    
     @Test(priority = 1)
     public void Test1() {
         test = extent.createTest("Test1 - Validate URL");
         String url = hp.getAppUrl();
         Assert.assertEquals(url, "https://www.demoblaze.com/", "URL mismatch");
         test.info("URL Matched");
     }

     @Test(priority = 2)
     public void validateSignup() throws InterruptedException {
         test = extent.createTest("Test2 - Validate Signup");
         hp.signUp("sparklingvibes", "Vibes@9898");
         test.pass("SignUp successful");
     }

     @Test(priority = 3)
     public void validateLogin() throws InterruptedException {
         test = extent.createTest("Test3 - Validate Login");
         hp.login("sparklingvibes", "Vibes@9898");
         test.pass("Login Successful");
     }

     @Test(priority = 4)
     public void testCategoriesAndOptions() {
         test = extent.createTest("Test4 - Validate Categories and Options");
         Indexpage indexPage = new Indexpage(driver);
         Assert.assertTrue(indexPage.getCurrentUrl().contains("demoblaze"), "URL is incorrect!");
         test.info("Categories and options validated successfully");
     }

     @Test(priority = 5)
     public void testAddProductToCart() throws InterruptedException {
         test = extent.createTest("Test5 - Add Product to Cart");
         Indexpage indexPage = new Indexpage(driver);
         indexPage.selectProduct(0);
         indexPage.addToCart();
         test.pass("Product added to cart successfully");
     }

     @Test(priority = 6)
     public void clickOnCart() throws InterruptedException {
         test = extent.createTest("Test6 - Click on Cart");
         Indexpage indexPage = new Indexpage(driver);
         indexPage.clickOnCart();
         indexPage.DeleteItem();
         indexPage.placeOrderBtn();
         test.pass("Cart actions performed successfully");
     }

     @Test(priority = 7)
     public void OrderDetails() throws InterruptedException {
         test = extent.createTest("Test7 - Enter Order Details");
         Indexpage indexPage = new Indexpage(driver);
         indexPage.OrderDetails("Suman", "India", "Dehradun", "123456", "Jan", "2025");
         indexPage.logout();
         test.pass("Order details entered and user signed out successfully");
     }
 
 @AfterMethod
 public void captureScreenshotAfterTest(ITestResult result) {
     try {
         // Define the base directory for screenshots
         String baseDir = "D:\\karishma_SeleniumDemos\\EcommerceProject\\Screenshots";

         // Ensure the directory exists
         File screenshotDir = new File(baseDir);
         if (!screenshotDir.exists()) {
             screenshotDir.mkdirs(); // Create the directory if it doesn't exist
         }

         // Capture the screenshot
         File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

         // Define the screenshot file path
         String screenshotPath = baseDir + "\\" + result.getName() + "_" + (result.getStatus() == ITestResult.SUCCESS ? "PASS" : "FAIL") + ".png";
         File destination = new File(screenshotPath);
         Files.copy(screenshot.toPath(), destination.toPath());

         // Log the screenshot to ExtentReports
         if (result.getStatus() == ITestResult.FAILURE) {
             test.fail(result.getThrowable());
             test.addScreenCaptureFromPath(screenshotPath, "Failed Test Screenshot");
         } else if (result.getStatus() == ITestResult.SUCCESS) {
             test.pass("Test passed");
             test.addScreenCaptureFromPath(screenshotPath, "Passed Test Screenshot");
         }

         // Print confirmation
         System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
     } catch (IOException e) {
         System.err.println("Error capturing screenshot: " + e.getMessage());
     }
 }


 @AfterTest
 public void tearDownReport() {
     extent.flush();
 }
}
 	