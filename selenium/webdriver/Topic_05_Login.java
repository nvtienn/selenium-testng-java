package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_05_Login {
    WebDriver driver;
    String validEmail = "automation" +getRandomNumber()+ "@gmail.com";
    String validPassword = "123456";
    String firstName = "Baby";
    String lastName = "Shark";
    String fullName = firstName+" "+lastName;
    String welcomeMsg = "Hello, "+fullName+"!";
    By myAccountLink = By.xpath("//div[@class='footer-container']//a[@title='My Account']");
    By emailTextbox = By.id("email");
    By passwordTextbox = By.id("pass");
    By loginButton = By.xpath("//button[@title='Login']");


    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browserDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.get("http://live.techpanda.org/");
        driver.findElement(myAccountLink).click();
    }

    @Test
    public void Login_TC_01_Empty_Email_Password(){
        driver.findElement(emailTextbox).clear();
        driver.findElement(passwordTextbox).clear();
        driver.findElement(loginButton).click();
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),"This is a required field.");
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),"This is a required field.");

    }

    @Test
    public void Login_TC_02_Invalid_Email(){
        driver.findElement(emailTextbox).sendKeys("123@456.789");
        driver.findElement(passwordTextbox).sendKeys("123456");
        driver.findElement(loginButton).click();
        Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),"Please enter a valid email address. For example johndoe@domain.com.");

    }

    @Test
    public void Login_TC_03_Password_Less_Than_6_Characters(){
        driver.findElement(emailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(passwordTextbox).sendKeys("12345");
        driver.findElement(loginButton).click();
        Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(),"Please enter 6 or more characters without leading or trailing spaces.");

    }

    @Test
    public void Login_TC_04_Incorrect_Email_Password(){
        driver.findElement(emailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(passwordTextbox).sendKeys("123456");
        driver.findElement(loginButton).click();
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(),"Invalid login or password.");

    }

    @Test
    public void Login_TC_05_Create_Account(){
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).sendKeys(validEmail);
        driver.findElement(By.id("password")).sendKeys(validPassword);
        driver.findElement(By.id("confirmation")).sendKeys(validPassword);
        driver.findElement(By.xpath("//button[@title='Register']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(),"Thank you for registering with Main Website Store.");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page-title']/h1")).getText().toUpperCase(),"MY DASHBOARD");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='welcome-msg']//strong")).getText(),welcomeMsg);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']/p")).getText().contains(fullName));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']/p")).getText().contains(validEmail));
        driver.findElement(By.xpath("//div[@class='account-cart-wrapper']/a")).click();
        driver.findElement(By.xpath("//a[@title='Log Out']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='page-title']//img")).isDisplayed());

    }

    @Test
    public void Login_TC_06_Valid_Account(){
        driver.findElement(emailTextbox).sendKeys(validEmail);
        driver.findElement(passwordTextbox).sendKeys(validPassword);
        driver.findElement(loginButton).click();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page-title']/h1")).getText().toUpperCase(),"MY DASHBOARD");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='welcome-msg']//strong")).getText(),welcomeMsg);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']/p")).getText().contains(fullName));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']/p")).getText().contains(validEmail));

    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

    public int getRandomNumber(){
        Random rd = new Random();
        return rd.nextInt(999999);
    }
}
