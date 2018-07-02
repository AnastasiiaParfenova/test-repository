package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class Task11 extends TestBase {

    @Test
    public void Test() {
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.linkText("New customers click here")).click();
        WebElement taxId = driver.findElement(By.name("tax_id"));
        taxId.sendKeys("12345");
        WebElement company = driver.findElement(By.name("company"));
        company.sendKeys("Test");
        WebElement firstname = driver.findElement(By.name("firstname"));
        firstname.sendKeys("Test");
        WebElement lastname = driver.findElement(By.name("lastname"));
        lastname.sendKeys("Test");
        WebElement address1 = driver.findElement(By.name("address1"));
        address1.sendKeys("Test");
        WebElement address2 = driver.findElement(By.name("address2"));
        address2.sendKeys("Test");
        WebElement postcode = driver.findElement(By.name("postcode"));
        postcode.sendKeys("12345");
        WebElement city = driver.findElement(By.name("city"));
        city.sendKeys("Test");
        WebElement email = driver.findElement(By.cssSelector("main#content")).findElement(By.name("email"));
        email.sendKeys("Test@Test.com");
        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys("89000000000");
        WebElement password = driver.findElement(By.cssSelector("main#content")).findElement(By.name("password"));
        password.sendKeys("test1");
        WebElement confirmedPassword = driver.findElement(By.name("confirmed_password"));
        confirmedPassword.sendKeys("test1");
    }
}
