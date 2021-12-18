package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_03_Browser_Part_II {
    WebDriver driver;

    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browserDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_URL(){
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
        String urlLoginPage = driver.getCurrentUrl();
        Assert.assertEquals(urlLoginPage,"http://live.techpanda.org/index.php/customer/account/login/");
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        String urlRegisterPage = driver.getCurrentUrl();
        Assert.assertEquals(urlRegisterPage,"http://live.techpanda.org/index.php/customer/account/create/");
    }

    @Test
    public void TC_02_URL(){
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
        String titleLoginPage = driver.getTitle();
        Assert.assertEquals(titleLoginPage,"Customer Login");
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        String titleRegisterPage = driver.getTitle();
        Assert.assertEquals(titleRegisterPage,"Create New Customer Account");
    }

    @Test
    public void TC_03_Navigation(){
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        String urlRegisterPage = driver.getCurrentUrl();
        Assert.assertEquals(urlRegisterPage,"http://live.techpanda.org/index.php/customer/account/create/");
        driver.navigate().back();
        sleepInSec(3);
        String titleLoginPage = driver.getTitle();
        Assert.assertEquals(titleLoginPage,"Customer Login");
        driver.navigate().forward();
        sleepInSec(3);
        String titleRegisterPage = driver.getTitle();
        Assert.assertEquals(titleRegisterPage,"Create New Customer Account");
    }

    @Test
    public void TC_04_Page_Source(){
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
        String sourceLoginPage = driver.getPageSource();
        Assert.assertTrue(sourceLoginPage.contains("Login or Create an Account"));
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        String sourceRegisterPage = driver.getPageSource();
        Assert.assertTrue(sourceRegisterPage.contains("Create an Account"));

    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

    public void sleepInSec(int time){
        try {
            Thread.sleep(time*1000);
        }catch (Exception e){}
    }
}
