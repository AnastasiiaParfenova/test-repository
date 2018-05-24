package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
import  static org.junit.Assert.*;


public class Task7 {

    public WebDriver driver;

    /**
     * Проверяет наличие элемента по заданному критерию поиска.
     * @param driver драйвер.
     * @param locator критерий поиска элемента на странице.
     * @return возвращает true, если элемент найден, и false, если не найден.
     */
    boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
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
     * Запускает браузер
     */
    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    /**
     * Прокликивает последовательно все пункты меню слева, включая вложенные пункты, для каждой страницы проверяет наличие заголовка.
     */
    @Test
    public void test() {
        login();
        int numberOfMenuItems = driver.findElements(By.cssSelector("#box-apps-menu > li")).size(); //считаем количество элементов меню
        for (int number = 0; number < numberOfMenuItems; number++) { //цикл по элементам меню
            WebElement item = driver.findElements(By.cssSelector("#box-apps-menu > li")).get(number);
            item.click(); //кликаем на нужный элемент
            if (areElementsPresent(driver, By.cssSelector("li.selected li"))) { // если есть вложенные элементы
                int numberOfItems = driver.findElements(By.cssSelector("li.selected li")).size();//считаем их количество
                for (int num = 0; num < numberOfItems; num++) { //цикл по элементам вложенного меню
                    WebElement insertedItem = driver.findElements(By.cssSelector("li.selected li")).get(num);
                    insertedItem.click();//кликаем на нужный элемент
                    assertTrue(areElementsPresent(driver, By.tagName("h1")));// проверяем наличие заголовка
                }
            }
            else {assertTrue(areElementsPresent(driver, By.tagName("h1")));}// проверяем наличие заголовка, если нет вложенного меню
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
