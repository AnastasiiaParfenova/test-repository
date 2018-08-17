package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task10 extends TestBase {


    /**
     * Поиск подстроки, заданной регулярным выражением.
     * @param str строка для поиска.
     * @param pattern регулярное выражение.
     * @param groupNumber номер группы
     * @return возвращает i-ый товар.
     */
    public String parseString(String str, String pattern, int groupNumber) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        if (m.find()) {
            return m.group(groupNumber);

        } else {
            return "";
        }
    }

    /**
     * Получение значений r, g, b из строки в формате rgb.
     * @param rgbColor в формате rgb.
     * @return массив со значениями r, g, b.
     */
    public HashMap<String, Integer> parseRgbString(String rgbColor){
        String pattern = "rgba*\\((.*)\\)";
        String str = parseString(rgbColor, pattern, 1);
        String[] colorsMass = str.split(", ");
        HashMap<String, Integer> colors = new HashMap<>();
        colors.put("r", Integer.parseInt(colorsMass[0]));
        colors.put("g", Integer.parseInt(colorsMass[1]));
        colors.put("b", Integer.parseInt(colorsMass[2]));
        return colors;
    }

    /**
     * Получение размера цены из строки.
     * @param sizeStr, строка, содержащая размер цен.
     * @return размер цены.
     */
    public float parseSize(String sizeStr){
        String pattern = "(.*)px";
        String str = parseString(sizeStr, pattern, 1);
        Float size = Float.parseFloat(str);
        return size;
    }

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
        assertTrue("Названия товаров не совпадают", prodName1.equals(prodName2));
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
        assertTrue("Цены не совпадают", regPrice.equals(prodPrice));
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
        assertTrue("Акционные цены не совпадают", campPrice1.equals(campPrice2));
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
        HashMap<String, Integer> colors = parseRgbString(priceColor);
        int r = colors.get("r");
        int g = colors.get("g");
        int b = colors.get("b");
        assertTrue("Цена не серая",(r == g ) && (g == b) && (r > 0));
    }

    /**
     * Проверяет, что на главной странице обычная цена зачеркнутая
     */
    @Test
    public void priceStyleTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        WebElement price = getRegularPrice(productOne);
        String priceStyle = price.getCssValue("text-decoration-line");
        String priceStyleIE = price.getCssValue("text-decoration");
        if (driver instanceof InternetExplorerDriver)
            assertEquals("Цена не зачеркнутая", "line-through", priceStyleIE);
        else assertEquals("Цена не зачеркнутая", "line-through", priceStyle);
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
        HashMap<String, Integer> colors = parseRgbString(CampPriceColor);
        int g = colors.get("g");
        int b = colors.get("b");
        assertTrue("Цена не красная",(g == 0 ) && (b == 0));
    }

    /**
     * Проверяет, что на главной странице акционная цена жирная
     */
    @Test
    public void campPriceStyleTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement productOne = getProduct(0);
        WebElement campPrice = getCampaignPrice(productOne);
        String campPriceWeight = campPrice.getCssValue("font-weight");
        assertEquals("Акционная цена не жирная", "700", campPriceWeight);
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
        String priceStyle = price.getCssValue("font-size");
        String campPriceStyle = campPrice.getCssValue("font-size");
        float priceSize = parseSize(priceStyle);
        float campPriceSize = parseSize(campPriceStyle);
        assertTrue("Акционная цена не крупнее, чем обычная", campPriceSize - priceSize > 0);
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
        HashMap<String, Integer> colors = parseRgbString(priceColor);
        int r = colors.get("r");
        int g = colors.get("g");
        int b = colors.get("b");
        assertTrue("Цена не серая", (r == g ) && (g == b) && (r > 0));
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
        String priceStyle = price.getCssValue("text-decoration-line");
        String priceStyleIE = price.getCssValue("text-decoration");
        if (driver instanceof InternetExplorerDriver)
            assertEquals("Цена не зачеркнутая", "line-through", priceStyleIE);
        else assertEquals("Цена не зачеркнутая", "line-through", priceStyle);
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
        HashMap<String, Integer> colors = parseRgbString(CampPriceColor);
        int g = colors.get("g");
        int b = colors.get("b");
        assertTrue("Цена не красная",(g == 0 ) && (b == 0));
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
        String campPriceWeight = campPrice.getCssValue("font-weight");
        assertEquals("Цена не жирная", "700", campPriceWeight);
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
        String priceStyle = price.getCssValue("font-size");
        String campPriceStyle = campPrice.getCssValue("font-size");
        float priceSize = parseSize(priceStyle);
        float campPriceSize = parseSize(campPriceStyle);
        assertTrue("Акционная цена не крупнее, чем обычная",campPriceSize - priceSize > 0);
    }
}
