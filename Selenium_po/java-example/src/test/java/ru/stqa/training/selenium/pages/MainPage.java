package ru.stqa.training.selenium.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

public class MainPage extends Page{

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Открывает главную страницу
     */
    public MainPage open(){
        driver.get("http://localhost/litecart/en/");
        return this;
    }

    @FindBy(linkText = "Popular Products")
    private WebElement tab;

    /**
     * Переход на вкладку "Popular Products"
     */
    public MainPage openTab(){
        try{
            tab.click();
        }
        catch (NoSuchElementException e){
            System.out.println("\"Элемент Popular Products не найден\"");
        }
        return this;
    }

    @FindBy(css = ".tab-pane.fade.in:not([style*=none])")
    public WebElement contentForm;

    /**
     * Поиск товара на странице.
     * @param i номер товара.
     * @return возвращает i-ый товар.
     */
    public WebElement getProduct(int i) {
        List<WebElement> products = contentForm.findElements(By.cssSelector(".col-lg-fifths"));
        if (i < products.size()) {
            return products.get(i);
        }
        else return products.get(0);
    }

    /**
     * Ждет, пока счетчик товаров в корзине обновится
     * @param numberOfProducts ожидаемое количество товара в корзине
     */
    public void waitForQuantityRefresh(int numberOfProducts) {
        try {
            WebElement cart = driver.findElement(By.id("cart"));
            WebElement quantity = cart.findElement(By.className("quantity"));
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(textToBePresentInElement(quantity, Integer.toString(numberOfProducts)));
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
        catch (TimeoutException ex){
            System.out.println("Счетчик товара не обновился \n");
        }
    }
}
