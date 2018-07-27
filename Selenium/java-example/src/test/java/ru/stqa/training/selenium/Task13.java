package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

public class Task13 extends TestBase {

    /**
     * Проверка наличия элемента
     * @param searchContext контекст поиска
     * @param locator локатор
     * @return true, если элемент найден, false - если нет
     */
    private boolean isElementExist(WebElement searchContext, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            driver.findElement(locator);
        }
        catch (NoSuchElementException e) {
            return false;
        }
        finally {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * Поиск товара на странице.
     * @param i номер товара.
     * @return возвращает i-ый товар.
     */
    public WebElement getProduct(int i) {
        WebElement contentForm = driver.findElement(By.cssSelector(".tab-pane.fade.in:not([style*=none])"));
        List<WebElement> products = contentForm.findElements(By.cssSelector(".col-lg-fifths"));
        return products.get(i);
    }

    /**
     * Поиск страницы товара.
     * @return возвращает страницу товара.
     */
    public WebElement getProductPage() {
        return driver.findElement(By.cssSelector(".featherlight-content"));
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

    /**
     * Добавление указанного числа продуктов в корзину
     * @param numberOfProducts необходимое количество товара в корзине
     */
    public void addProductToCart(int numberOfProducts){
        try {
            for (int count = 0; count < numberOfProducts; count++) {
                getProduct(count).click();
                WebElement page = getProductPage();
                if (isElementExist(page, By.name("options[Size]"))){
                    WebElement size = page.findElement(By.name("options[Size]"));
                    Select select = new Select(size);
                    select.selectByValue("Large");
                }
                page.findElement(By.cssSelector(".btn.btn-success")).click();
                waitForQuantityRefresh(count + 1);
                page.findElement(By.cssSelector(".featherlight-close-icon.featherlight-close")).click();
            }
        }
        catch (NoSuchElementException e){
            System.out.println(e.toString());
        }
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

    /**
     * Удаление указанного числа продуктов из корзины
     * @param numberOfProducts количество товара в корзине
     */
    public void removeProductFromCart(int numberOfProducts){
        try {
            driver.findElement(By.id("cart")).click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            waitForLoaderDisappear(wait);
            for (int count = 0; count < numberOfProducts; count++) {
                List<WebElement> butonsDelete = driver.findElements(By.name("remove_cart_item"));
                if (butonsDelete.size() != 0) {
                    butonsDelete.get(0).click();
                    waitForLoaderDisappear(wait);

                }
            }
        }
        catch (NoSuchElementException e){
            System.out.println("Элемент cart не найден");
        }
    }

    /**
     * Добавление трех товаров со страницы "Popular Products" в корзину и удаление их из корзины
     */
    @Test
    public void Test() {
        try {
            driver.get("http://localhost/litecart/en/");
            driver.findElement(By.linkText("Popular Products")).click();
            int quantity = 3;
            addProductToCart(quantity);
            removeProductFromCart(quantity);
        }
        catch (NoSuchElementException e){
            System.out.println("Элемент Popular Products не найден");
        }
    }

}

