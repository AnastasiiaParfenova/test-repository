package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.lang.String;



public class Task11 extends TestBase {


    /**
     * Ввод значения в поле ввода.
     * @param searchContext контекст поиска.
     * @param name имя поля
     * @param text текст для ввода
     */
    public void setInputField(WebElement searchContext, String name, String text){
        try {
            WebElement inputField = searchContext.findElement(By.name(name));
            inputField.clear();
            inputField.sendKeys(text);
        }
        catch (NoSuchElementException e){
            System.out.printf("Поле с именем %s не найдено \n", name);
        }
    }

    /**
     * Выбор значения из выпадающего списка
     * @param searchContext контекст поиска.
     * @param fieldName имя поля
     * @param selectValue значение для выбора из выпадающего списка
     */
    public void setSelect(WebElement searchContext, String fieldName, String selectValue){

        try {
            WebElement selectField = searchContext.findElement(By.name(fieldName));
            Select select = new Select(selectField);
            select.selectByValue(selectValue);
        }
        catch (NoSuchElementException e){
            System.out.printf("Поле с именем %s не найдено \n", fieldName);
        }
    }

    /**
     * Генерация уникального пароля
     */
    public String generatePassword() {
        return  UUID.randomUUID().toString();
    }

    /**
     * Генерация уникального E-mail
     */
    public String generateEmail() {
        return UUID.randomUUID().toString().concat("@test.ru");
    }

    /**
     * Логин в клиентской части магазина
     * @param email адрес электронной почты
     * @param password пароль
     */
    public void login(String email, String password){

        WebElement boxAccount = driver.findElement(By.id("box-account-login"));
        setInputField(boxAccount, "email", email);
        setInputField(boxAccount, "password", password);
        try{
            boxAccount.findElement(By.name("login")).click();
        }
        catch (NoSuchElementException e){
            System.out.println("Кнопка login не найдена");
        }
    }

    /**
     * Выходи из учетной записи в клиентской части маагзина
     */
    public void logout(){
        try{
            driver.findElement(By.linkText("Logout")).click();
        }
        catch (NoSuchElementException e){
            System.out.println("Кнопка Logout не найдена");
        }
    }

    /**
     * Логин в панели администрирования
     */
    public void adminLogin(){
        try {
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("admin", Keys.ENTER);
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
    }

    /**
     * Отмечает чекбокс с заданным именем и значением
     * @param name имя
     * @param value значение
     */
    public void setInput(String name, String value){
        String script = "$('input[value=\"" +value+ "\"][name=\"" +name+ "\"]').prop('checked', true)";
        ((JavascriptExecutor) driver).executeScript(script);
    }


    /**
     * Отключение капчи
     */
    public void disabledCaptcha(){
        try {
            driver.get("http://localhost/litecart/admin/?app=settings&doc=security&setting_group_key=store_info&page=1&key=captcha_enabled&action=edit");
            adminLogin();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement saveButton = wait.until((driver) -> driver.findElement(By.name("save")));
            setInput("value", "0");
            saveButton.click();
        }
        catch (TimeoutException e){
            System.out.println("Кнопка Save не найдена");
        }
    }

    /**
     * Создание новой учётной записи
     * @param email адрес электронной почты
     * @param password пароль
     */
    public void createAccount(String email, String password){
        driver.get("http://localhost/litecart/en/");

        try {
            driver.findElement(By.linkText("New customers click here")).click();
            WebElement searchContext = driver.findElement(By.cssSelector("main#content"));

            setInputField(searchContext, "tax_id", "12345");
            setInputField(searchContext, "company", "Test");
            setInputField(searchContext, "firstname", "Test");
            setInputField(searchContext, "lastname", "Test");
            setInputField(searchContext, "address1", "Test");
            setInputField(searchContext, "address2", "Test");
            setInputField(searchContext, "postcode", "12345");
            setInputField(searchContext, "city", "Test");
            setSelect(searchContext, "country_code", "US");
            setSelect(searchContext, "zone_code", "MA");
            setInputField(searchContext, "email", email);
            setInputField(searchContext, "phone", "+79000000000");
            setInputField(searchContext, "password", password);
            setInputField(searchContext, "confirmed_password", password);

            driver.findElement(By.name("create_account")).click();
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }

    }

    /**
     * Создание новой учётной записи, выход, повторный вход в только что созданную учетную запись и выход
     */
    @Test
    public void Test(){
        String email = generateEmail();
        String password = generatePassword();

        disabledCaptcha();
        createAccount(email, password);
        logout();
        login(email, password);
        logout();
    }

}
