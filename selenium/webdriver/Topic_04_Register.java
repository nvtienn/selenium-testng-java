package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_04_Register {
    WebDriver driver;
    By registerButton = By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']");
    By firstNameErrorTxt = By.id("txtFirstname-error");
    By emailErrorTxt = By.id("txtEmail-error");
    By cEmailErrorTxt = By.id("txtCEmail-error");
    By passwordErrorTxt = By.id("txtPassword-error");
    By cPasswordErrorTxt = By.id("txtCPassword-error");
    By phoneErrorTxt = By.id("txtPhone-error");
    By firstNameTextbox = By.id("txtFirstname");
    By emailTextbox = By.id("txtEmail");
    By cEmailTextbox = By.id("txtCEmail");
    By passwordTextbox = By.id("txtPassword");
    By cPasswordTextbox= By.id("txtCPassword");
    By phoneTextbox= By.id("txtPhone");

    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browserDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
    }

    @Test
    public void Register_TC_01_Invalid_Email(){

        driver.findElement(registerButton).click();
        Assert.assertEquals( driver.findElement(firstNameErrorTxt).getText(),"Vui lòng nhập họ tên");
        Assert.assertEquals( driver.findElement(emailErrorTxt).getText(),"Vui lòng nhập email");
        Assert.assertEquals( driver.findElement(cEmailErrorTxt).getText(),"Vui lòng nhập lại địa chỉ email");
        Assert.assertEquals( driver.findElement(passwordErrorTxt).getText(),"Vui lòng nhập mật khẩu");
        Assert.assertEquals( driver.findElement(cPasswordErrorTxt).getText(),"Vui lòng nhập lại mật khẩu");
        Assert.assertEquals( driver.findElement(phoneErrorTxt).getText(),"Vui lòng nhập số điện thoại.");
    }

    @Test
    public void Register_TC_02_Empty_Data(){
        driver.findElement(firstNameTextbox).sendKeys("Automation");
        driver.findElement(emailTextbox).sendKeys("123@456@789");
        driver.findElement(cEmailTextbox).sendKeys("123@456@789");
        driver.findElement(passwordTextbox).sendKeys("123456");
        driver.findElement(cPasswordTextbox).sendKeys("123456");
        driver.findElement(phoneTextbox).sendKeys("0987654321");
        driver.findElement(registerButton).click();
        Assert.assertEquals( driver.findElement(emailErrorTxt).getText(),"Vui lòng nhập email hợp lệ");
        Assert.assertEquals( driver.findElement(cEmailErrorTxt).getText(),"Vui lòng nhập email hợp lệ");
    }

    @Test
    public void Register_TC_03_Incorrect_Confirm_Email(){
        driver.findElement(firstNameTextbox).sendKeys("Automation");
        driver.findElement(emailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(cEmailTextbox).sendKeys("automation@gmail.com");
        driver.findElement(passwordTextbox).sendKeys("123456");
        driver.findElement(cPasswordTextbox).sendKeys("123456");
        driver.findElement(phoneTextbox).sendKeys("0987654321");
        driver.findElement(registerButton).click();
        Assert.assertEquals( driver.findElement(cEmailErrorTxt).getText(),"Email nhập lại không đúng");
    }

    @Test
    public void Register_TC_04_Password_Less_Than_6_Characters(){
        driver.findElement(firstNameTextbox).sendKeys("Automation");
        driver.findElement(emailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(cEmailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(passwordTextbox).sendKeys("12345");
        driver.findElement(cPasswordTextbox).sendKeys("12345");
        driver.findElement(phoneTextbox).sendKeys("0987654321");
        driver.findElement(registerButton).click();
        Assert.assertEquals( driver.findElement(passwordErrorTxt).getText(),"Mật khẩu phải có ít nhất 6 ký tự");
    }

    @Test
    public void Register_TC_05_Incorrect_Confirm_Password(){
        driver.findElement(firstNameTextbox).sendKeys("Automation");
        driver.findElement(emailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(cEmailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(passwordTextbox).sendKeys("123456");
        driver.findElement(cPasswordTextbox).sendKeys("1234567");
        driver.findElement(phoneTextbox).sendKeys("0987654321");
        driver.findElement(registerButton).click();
        Assert.assertEquals( driver.findElement(cPasswordErrorTxt).getText(),"Mật khẩu bạn nhập không khớp");
    }

    @Test
    public void Register_TC_06_Invalid_Phone_Number(){
        driver.findElement(firstNameTextbox).sendKeys("Automation");
        driver.findElement(emailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(cEmailTextbox).sendKeys("automation@gmail.net");
        driver.findElement(passwordTextbox).sendKeys("123456");
        driver.findElement(cPasswordTextbox).sendKeys("123456");
        driver.findElement(phoneTextbox).sendKeys("098765432100");
        driver.findElement(registerButton).click();
        Assert.assertEquals( driver.findElement(phoneErrorTxt).getText(),"Số điện thoại phải từ 10-11 số.");
        driver.findElement(phoneTextbox).clear();
        driver.findElement(phoneTextbox).sendKeys("123456789");
        driver.findElement(registerButton).click();
        Assert.assertEquals( driver.findElement(phoneErrorTxt).getText(),"Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019");
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }
}
