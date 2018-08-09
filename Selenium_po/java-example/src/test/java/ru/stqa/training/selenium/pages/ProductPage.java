package ru.stqa.training.selenium.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class ProductPage extends Page {

    public ProductPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".featherlight-content")
    public WebElement productPage;

    /**
     * Нажимает кнопку "Add To Cart"
     */
    public ProductPage addButtonClick(){
        try {
            productPage.findElement(By.cssSelector(".btn.btn-success")).click();
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
        return this;
    }

    /**
     * Нажимает кнопку "x"
     */
    public void closeButtonClick(){
        try {
            productPage.findElement(By.cssSelector(".featherlight-close-icon.featherlight-close")).click();
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
    }


    /**
     * Выбор размера товара
     */
    public ProductPage selectSize(){
        if (isElementExist(productPage, By.name("options[Size]"))) {
            WebElement size = productPage.findElement(By.name("options[Size]"));
            Select select = new Select(size);
            select.selectByValue("Large");
        }
        return this;
    }


}





