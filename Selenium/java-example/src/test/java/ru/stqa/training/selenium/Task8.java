package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.concurrent.TimeUnit;
import  static org.junit.Assert.*;


public class Task8 {

    public WebDriver driver;
    public WebDriverWait wait;

    /**
     * Проверяет наличие ровно одного элемента по заданному критерию поиска.
     * @param element элемент.
     * @param locator критерий поиска элемента.
     * @return возвращает true, если найден ровно один элемент, и false, если не найден.
     */
    boolean isOneElementPresent(WebElement element, By locator) {
        return element.findElements(locator).size() == 1;
    }

    /**
     * Логин в панель администрирования
     */
    public void login(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin", Keys.ENTER);
    }

    /**
     * Запуск браузера
     */
    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    /**
     * Проверяет,что у каждого товара имеется ровно один стикер.
     */
    @Test
    public void test() {
        login();
        driver.findElement(By.cssSelector("#shortcuts [title=Catalog]")).click();

        List<WebElement> menuItems = driver.findElements(By.cssSelector(".nav-justified li"));
        for (int item = 0; item < menuItems.size(); item++){
            menuItems.get(item).click();
            WebElement contentForm = driver.findElement(By.cssSelector(".tab-pane.fade.in:not([style*=none])"));
            List<WebElement> products = contentForm.findElements(By.cssSelector(".col-lg-fifths"));
            for (int prod = 0; prod < products.size(); prod++){
                assertTrue(isOneElementPresent(products.get(prod), By.cssSelector("[class*=sticker]")));
            }
        }
    }

    /**
     * Закрывает браузер.
     */
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
