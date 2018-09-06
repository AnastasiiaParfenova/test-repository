package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.UUID;
import java.lang.String;
import java.io.File;
import org.openqa.selenium.interactions.Actions;
import static org.junit.Assert.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Task12 extends TestBase {

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
     * Генерирует уникальный номер
     * @return возвращает уникальный номер
     */
    public String generateUniqueName() {
        return  UUID.randomUUID().toString();
    }

    /**
     * Ввод занчения в файловое поле ввода поле ввода
     * @param searchContext номер товара
     * @param name имя поля
     * @param text текст для ввода
     */
    public void setField(WebElement searchContext, String name, String text){
        try {
            WebElement field = searchContext.findElement(By.name(name));
            field.sendKeys(text);
        }
        catch (NoSuchElementException e){
            System.out.printf("Поле с именем %s не найдено \n", name);
        }
    }

    /**
     * Ввод занчения в текстовое поле ввода
     * @param searchContext номер товара
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
     * @param searchContext номер товара
     * @param fieldName имя поля
     * @param selectValue выбираемое занчение из выпадающего списка
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
     * Вввод описания товара с установкой цвета текста и жирного шрифта
     * @param text текст описания
     */
    public void writeDescription(String text){
        try {
            WebElement descriptionField = driver.findElement(By.className("trumbowyg-editor"));
            WebElement button = driver.findElement(By.cssSelector(".trumbowyg-foreColor-button.trumbowyg-open-dropdown"));
            WebElement color = driver.findElement(By.className("trumbowyg-foreColor92cddc-dropdown-button"));

            StringSelection stringSelection = new StringSelection(text);
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);

            new Actions(driver)
                    .moveToElement(button)
                    .clickAndHold()
                    .moveToElement(color)
                    .clickAndHold()
                    .moveToElement(descriptionField)
                    .clickAndHold()
                    .keyDown(Keys.CONTROL)
                    .sendKeys("v")
                    .sendKeys("a")
                    .sendKeys("b")
                    .keyUp(Keys.CONTROL)
                    .perform();
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
    }

    /**
     * Проверка появления товара а каталоге
     * @param prodName название товара
     * @return true, если товар есть в каталоге, false, если нет
     */
    public boolean isProductExists(String prodName){
        boolean flag = false;
        List<WebElement> massName = driver.findElements(By.cssSelector("tbody tr"));
        if (massName.size() !=0){
            for (WebElement i:massName) {
                List<WebElement> str = i.findElements(By.cssSelector("td"));
                if (str.size() != 0){
                    String name = str.get(2).findElement(By.cssSelector("a")).getAttribute("text");
                    if (name.equals(prodName)) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
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
     * Добавление нового тавара с заданным названием
     * @param uniqName название товара
     */
    public void addNewProduct(String uniqName){
        try {
            driver.findElement(By.linkText("Catalog")).click();
            driver.findElement(By.linkText("Add New Product")).click();

            WebElement searchContext1 = driver.findElement(By.id("tab-general"));

            setInput("status", "1");
            setInput("categories[]", "1");
            setInput("product_groups[]", "1-3");

            setField(searchContext1, "date_valid_from", "01.01.2011");
            setField(searchContext1, "date_valid_to", "02.02.2022");

            setInputField(searchContext1, "code", "123");
            setInputField(searchContext1, "name[en]", uniqName);
            setInputField(searchContext1, "sku", generateUniqueName());
            setInputField(searchContext1, "gtin", generateUniqueName());
            setInputField(searchContext1, "taric", "Test");
            setInputField(searchContext1, "quantity", "1.23456");
            setSelect(searchContext1, "quantity_unit_id", "1");
            setInputField(searchContext1, "weight", "1.23456");
            setSelect(searchContext1, "weight_class", "g");
            setInputField(searchContext1, "dim_x", "1.23456");
            setInputField(searchContext1, "dim_y", "1.23456");
            setInputField(searchContext1, "dim_z", "1.23456");
            setSelect(searchContext1, "dim_class", "mm");
            setSelect(searchContext1, "delivery_status_id", "1");
            setSelect(searchContext1, "sold_out_status_id", "2");

            File image = new File("src\\images\\newDuck.jpg");
            String pathToImage = image.getAbsolutePath();
            setField(searchContext1, "new_images[]", pathToImage);

            WebDriverWait wait = new WebDriverWait(driver, 10);

            // Переход на вкладку Information
            driver.findElement(By.linkText("Information")).click();
            WebElement searchContext2 = wait.until((driver) -> driver.findElement(By.id("tab-information")));

            setSelect(searchContext2, "manufacturer_id", "1");
            setSelect(searchContext2, "supplier_id", "");

            setInputField(searchContext2, "keywords", "Test1");
            setInputField(searchContext2, "short_description[en]", "Test2");
            setInputField(searchContext2, "attributes[en]", "Test3");
            writeDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue. Cras scelerisque dui non consequat sollicitudin. Sed pretium tortor ac auctor molestie. Nulla facilisi. Maecenas pulvinar nibh vitae lectus vehicula semper. Donec et aliquet velit. Curabitur non ullamcorper mauris. In hac habitasse platea dictumst. Phasellus ut pretium justo, sit amet bibendum urna. Maecenas sit amet arcu pulvinar, facilisis quam at, viverra nisi. Morbi sit amet adipiscing ante. Integer imperdiet volutpat ante, sed venenatis urna volutpat a. Proin justo massa, convallis vitae consectetur sit amet, facilisis id libero.  ");

            // Переход на вкладку Prices
            driver.findElement(By.linkText("Prices")).click();
            WebElement searchContext3 = wait.until((driver) -> driver.findElement(By.id("tab-prices")));
            setInputField(searchContext3, "purchase_price", "1.2345");
            setSelect(searchContext3, "purchase_price_currency_code", "EUR");
            setInputField(searchContext3, "prices[USD]", "1.2334");
            setInputField(searchContext3, "prices[EUR]", "1.2334");

            driver.findElement(By.name("save")).click();
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
    }

    /**
     * Добавление нового товара с заданным названием и проверка, что он появился а каталоге
     */
    @Test
    public void Test() {
        String uniqName = generateUniqueName();
        adminLogin();
        addNewProduct(uniqName);
        assertTrue(isProductExists(uniqName));
    }
}
