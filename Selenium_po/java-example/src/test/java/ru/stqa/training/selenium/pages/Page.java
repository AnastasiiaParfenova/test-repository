package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Page {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    /**
     * Проверка наличия элемента
     * @param searchContext контекст поиска
     * @param locator локатор
     * @return true, если элемент найден, false - если нет
     */
    public boolean isElementExist(WebElement searchContext, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            driver.findElement(locator);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
        finally {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
    }
}
