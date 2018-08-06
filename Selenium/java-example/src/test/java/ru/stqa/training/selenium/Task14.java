package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Task14 extends TestBase {

    /**
     * Ожидание появления нового окна
     * @param oldWindows множество идентификаторов уже открытых окон
     * @return ожидание появления нового идентификатора окна
     */
    public ExpectedCondition<String> anyWindowOterThan(Set<String> oldWindows){
        return new ExpectedCondition<String>(){
            @Override
            //Возвращает идентификатор нового окна, если оно появилось, иначе null
            public String apply(WebDriver driver){
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0 ? handles.iterator().next():null;
            }
        };
    }

    /**
     * Логин в панели администрирования
     */
    public void adminLogin(){
        driver.get("http://localhost/litecart/admin");
        try {
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("admin", Keys.ENTER);
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
    }

    /**
     * Проверяет, что ссылки на странице создания новой страны открываются в новом окне.
     */
    @Test
    public void Test() {
        adminLogin();
        try{
            driver.findElement(By.linkText("Countries")).click();
            driver.findElement(By.linkText("Add New Country")).click();
            List<WebElement> links = driver.findElements(By.cssSelector("i.fa.fa-external-link"));
            for (WebElement link : links) {
                String mainWindow = driver.getWindowHandle();
                Set<String> oldWindows = driver.getWindowHandles();
                link.click();
                WebDriverWait wait = new WebDriverWait(driver, 10);
                String newWindow = wait.until(anyWindowOterThan(oldWindows));
                driver.switchTo().window(newWindow);
                driver.close();
                driver.switchTo().window(mainWindow);
            }
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
    }
}
