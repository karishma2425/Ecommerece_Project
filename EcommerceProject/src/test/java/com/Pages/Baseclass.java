
package com.Pages;

  import java.time.Duration;
  import org.openqa.selenium.WebDriver;
  import org.openqa.selenium.chrome.ChromeDriver;
  import org.testng.annotations.AfterClass;
  import org.testng.annotations.BeforeTest;

    public class Baseclass {
	public static WebDriver driver;
  	public Homepage hp;
  	public  Indexpage In;
    	
  	@BeforeTest
  	public void setup() {
  		 driver = new ChromeDriver();
  		  driver.get("https://www.demoblaze.com/");
  		  driver.manage().window().maximize();
  		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
  		hp=new Homepage(driver);
  		In = new Indexpage(driver);
  	   
  	}
  	
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
  	
  

  

