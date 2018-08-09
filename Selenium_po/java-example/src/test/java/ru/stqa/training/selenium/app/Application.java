package ru.stqa.training.selenium.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.pages.MainPage;
import ru.stqa.training.selenium.pages.ProductPage;
import ru.stqa.training.selenium.pages.CartPage;


public class Application {

    private WebDriver driver;

    private MainPage mainPage;
    private ProductPage productPage;
    private CartPage cartPage;

    public Application(){
    driver = new ChromeDriver();
    mainPage = new MainPage(driver);
    productPage = new ProductPage(driver);
    cartPage = new CartPage(driver);
    }

    /**
     * Закрывает браузер
     */
    public void quit() {
        driver.quit();
    }


    /**
     * Добавление указанного числа продуктов в корзину
     * @param numberOfProducts необходимое количество товара в корзине
     */
    public void addProductToCart(int numberOfProducts) {
        mainPage.open().openTab();
        for (int count = 0; count < numberOfProducts; count++) {
            mainPage.getProduct(count).click();
            productPage.selectSize().addButtonClick();
            mainPage.waitForQuantityRefresh(count + 1);
            productPage.closeButtonClick();
        }
    }

    /**
     * Удаление указанного числа продуктов из корзины
     * @param numberOfProducts количество товара в корзине
     */
    public void removeProductFromCart(int numberOfProducts){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        cartPage.open().waitForLoaderDisappear(wait);
        for (int count = 0; count < numberOfProducts; count++) {
            cartPage.removeProductFromCart().waitForLoaderDisappear(wait);
        }
    }
}

