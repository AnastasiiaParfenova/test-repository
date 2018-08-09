package ru.stqa.training.selenium.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CartPage extends Page{

    public CartPage (WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Открывает корзину
     */
    public CartPage open(){
        driver.get("http://localhost/litecart/en/checkout");
        return this;
    }

    @FindBy(name = "remove_cart_item")
    public List<WebElement> butonsDelete;

    /**
     * Удаление продукта из корзины
     */
    public CartPage removeProductFromCart(){
        if (butonsDelete.size() != 0) {
            butonsDelete.get(0).click();
        }
        return this;
    }

    /**
     * Ожидание исчезновения прелоадера
     */
    public void waitForLoaderDisappear(WebDriverWait wait){
        try {
            WebElement loader = driver.findElement(By.className("loader-wrapper"));
            wait.until(stalenessOf(loader));
        }
        catch (NoSuchElementException e){
            System.out.println("Прелоадер не найден");
        }
        catch (TimeoutException e){
            System.out.println("Прелоадер не исчез");
        }
    }

}
