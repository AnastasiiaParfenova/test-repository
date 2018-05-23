package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task1 {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test1() {
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.className("sbsb_g")).click();
        wait.until(titleIs("webdriver - Поиск в Google"));
    }

    @Test
    public void test2() {
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver", Keys.ENTER);
        wait.until(titleIs("webdriver - Поиск в Google"));
    }

    @Test
    public void test3() {
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.id("lga")).click();
        driver.findElement(By.name("btnK")).click();
        wait.until(titleIs("webdriver - Поиск в Google"));
    }

    @Test
    public void test4() {
        driver.get("https://yandex.ru");
        driver.findElement(By.name("text")).sendKeys("webdriver");
        driver.findElement(By.className("search2__button")).click();
        wait.until(titleIs("webdriver — Яндекс: нашлось 5 млн результатов"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
