package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import  java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import  static org.junit.Assert.*;


public class Task3 {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    //Проверка успешной авторизации при нажатии enter
    @Test
   public void test1(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin", Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(visibilityOf(driver.findElement(By.className("alert-success"))));
    }

    //Проверка успешной авторизации при нажатии кнопки "Login"
    @Test
    public void test2(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.className("btn-default")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(visibilityOf(driver.findElement(By.className("alert-success"))));
    }

    //Проверка появления предупреждения при вводе несуществующего логина
    @Test
    public void test3(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("aadmin");
        driver.findElement(By.name("password")).sendKeys("admin", Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(visibilityOf(driver.findElement(By.xpath("//*[contains(text(), 'The user could not be found in our database')]"))));
    }

    //Проверка успешной авторизации при нажатии кнопки "Remember me"
    @Test
    public void test4(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("remember_me"));
        driver.findElement(By.className("btn-default")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(visibilityOf(driver.findElement(By.className("alert-success"))));
    }

    //Проверка logout
    @Test
    public void test5(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.className("btn-default")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(".fa-sign-out.fa-lg")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals(driver.getCurrentUrl(), "http://localhost/litecart/admin/login.php");
    }

    //Проверка появления предупреждения при неверном вводе пароля
    @Test
    public void test6(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("hhh", Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(visibilityOf(driver.findElement(By.xpath("//*[contains(text(), 'Wrong combination of username and password or the account does not exist.')]"))));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
