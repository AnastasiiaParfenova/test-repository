package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.List;
import static org.junit.Assert.*;


public class Task10 extends TestBase {

    /**
     * Поиск товара на странице.
     * @param i номер товара.
     * @return возвращает i-ый товар.
     */
    WebElement getProduct(int i) {
        WebElement contentForm = driver.findElement(By.cssSelector(".tab-pane.fade.in:not([style*=none])"));
        List<WebElement> products = contentForm.findElements(By.cssSelector(".col-lg-fifths"));
        return products.get(i);
    }

    /**
     * Поиск страницы товара.
     * @return возвращает страницу товара.
     */
    WebElement getProductPage() {
        return driver.findElement(By.cssSelector(".featherlight-content"));
    }

    /**
     * Поиск обычной цены товара.
     * @param searchContext контекст поиска
     * @return возвращает обычную цену товара.
     */
    WebElement getRegularPrice(WebElement searchContext) {
        return searchContext.findElement(By.className("regular-price"));
    }

    /**
     * Поиск акционной цены товара.
     * @param searchContext контекст поиска
     * @return возвращает акционную цену товара.
     */
    WebElement getCampaignPrice(WebElement searchContext) {
        return searchContext.findElement(By.className("campaign-price"));
    }

    /**
     * Поиск текста элемента.
     * @param element элемент.
     * @return возвращает текст элемента.
     */
    String getTextContent(WebElement element) {
        return element.getAttribute("textContent");
    }

    /**
     * Проверяет, что на главной странице и на странице товара совпадает текст названия товара
     */
    @Test
    public void nameTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        String prodName1 = getTextContent(productOne.findElement(By.cssSelector(".info .name")));
        productOne.click();
        WebElement productPage = getProductPage();
        String prodName2 = getTextContent(productPage.findElement(By.tagName("h1")));
        assertTrue(prodName1.equals(prodName2));
    }

    /**
     * Проверяет, что на главной странице и на странице товара совпадают обычные цены
     */
    @Test
    public void priceTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        String regPrice = getTextContent(getRegularPrice(productOne));
        productOne.click();
        WebElement productPage = getProductPage();
        String prodPrice = getTextContent(getRegularPrice(productPage));
        assertTrue(regPrice.equals(prodPrice));
    }

    /**
     * Проверяет, что на главной странице и на странице товара совпадают акционные цены
     */
    @Test
    public void campaignPriceTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        String campPrice1 = getTextContent(getCampaignPrice(productOne));
        productOne.click();
        WebElement productPage = getProductPage();
        String campPrice2 = getTextContent(getCampaignPrice(productPage));
        assertTrue(campPrice1.equals(campPrice2));
    }

    /**
     * Проверяет, что на главной странице обычная цена серая
     */
    @Test
    public void priceColorTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        WebElement price = getRegularPrice(productOne);
        String priceColor = price.getCssValue("color");
        if (driver instanceof FirefoxDriver){
            assertEquals("rgb(51, 51, 51)", priceColor);
        }
        else assertEquals("rgba(51, 51, 51, 1)", priceColor);
    }

    /**
     * Проверяет, что на главной странице обычная цена зачеркнутая
     */
    @Test
    public void priceStyleTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        WebElement price = getRegularPrice(productOne);
        String priceTagName = price.getTagName();
        assertEquals("s", priceTagName);
    }

    /**
     * Проверяет, что на главной странице акционная цена красная
     */
    @Test
    public void CampPriceColorTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        WebElement campPrice = getCampaignPrice(productOne);
        String CampPriceColor = campPrice.getCssValue("color");
        if (driver instanceof FirefoxDriver){
            assertEquals("rgb(204, 0, 0)", CampPriceColor);
        }
        else assertEquals("rgba(204, 0, 0, 1)", CampPriceColor);
    }

    /**
     * Проверяет, что на главной странице акционная цена жирная
     */
    @Test
    public void campPriceStyleTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        WebElement campPrice = getCampaignPrice(productOne);
        String campPriceTagName = campPrice.getTagName();
        assertEquals("strong", campPriceTagName);
    }

    /**
     * Проверяет, что на главной странице акционная цена крупнее, чем обычная
     */
    @Test
    public void sizeTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        WebElement price = getRegularPrice(productOne);
        WebElement campPrice = getCampaignPrice(productOne);
        Dimension size = price.getSize();
        Dimension campSize = campPrice.getSize();
        assertTrue(campSize.getHeight() > size.getHeight() & campSize.getWidth() > size.getWidth());
    }

    /**
     * Проверяет, что на странице товара обычная цена серая
     */
    @Test
    public void prodPagePriceColorTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        productOne.click();
        WebElement productPage = getProductPage();
        WebElement price = getRegularPrice(productPage);
        String priceColor = price.getCssValue("color");
        if (driver instanceof FirefoxDriver){
            assertEquals("rgb(51, 51, 51)", priceColor);
        }
        else assertEquals("rgba(51, 51, 51, 1)", priceColor);
    }

    /**
     * Проверяет, что на странице товара обычная цена зачеркнутая
     */
    @Test
    public void prodPagePriceStyleTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        productOne.click();
        WebElement productPage = getProductPage();
        WebElement price = getRegularPrice(productPage);
        String priceTagName = price.getTagName();
        assertEquals("del", priceTagName);
    }

    /**
     * Проверяет, что на странице товара акционная цена красная
     */
    @Test
    public void prodPageCampPriceColorTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        productOne.click();
        WebElement productPage = getProductPage();
        WebElement campPrice = getCampaignPrice(productPage);
        String CampPriceColor = campPrice.getCssValue("color");
        if (driver instanceof FirefoxDriver){
            assertEquals("rgb(204, 0, 0)", CampPriceColor);
        }
        else assertEquals("rgba(204, 0, 0, 1)", CampPriceColor);
    }

    /**
     * Проверяет, что на странице товара акционная цена жирная
     */
    @Test
    public void prodPageCampPriceStyleTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        productOne.click();
        WebElement productPage = getProductPage();
        WebElement campPrice = getCampaignPrice(productPage);
        String campPriceTagName = campPrice.getTagName();
        assertEquals("strong", campPriceTagName);
    }

    /**
     * Проверяет, что на странице товара акционная цена крупнее, чем обычная
     */
    @Test
    public void prodPageSizeTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        productOne.click();
        WebElement productPage = getProductPage();
        WebElement price = getRegularPrice(productPage);
        WebElement campPrice = getCampaignPrice(productPage);
        Dimension size = price.getSize();
        Dimension campSize = campPrice.getSize();
        assertTrue(campSize.getHeight() > size.getHeight() & campSize.getWidth() > size.getWidth());
    }
}
