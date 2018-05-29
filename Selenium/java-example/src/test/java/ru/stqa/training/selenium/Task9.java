package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;


public class Task9 {

    public WebDriver driver;
    public WebDriverWait wait;

    /**
     * Проверяет расположение строк в алфавитном порядке.
     * @param str1 первая строка.
     * @param str2 вторая строка.
     * @return true, если str1 следует за str2 по алфавиту, иначе false.
     */
    boolean isAlphabetOrder(String  str1, String str2){
        return str1.compareTo(str2) < 0;
    }

    /**
     * Находит строки таблицы.
     * @return массив строк таблицы.
     */
    List<WebElement> getStrings(){
        return driver.findElements(By.cssSelector("tbody tr"));
    }

    /**
     * Находит в массиве строк i-ую строку.
     * @param stringsMass - массив строк таблицы.
     * @param i - номер строки.
     * @return содержимое i-ой строки.
     */
    List<WebElement> getCurrentString(List<WebElement> stringsMass, int i){
        return stringsMass.get(i).findElements(By.tagName("td"));
            }

    /**
     * Находит название страны в таблице по указанным строке и столбцу.
     * @param stringsMass - массив строк таблицы.
     * @param i - номер строки.
     * @param colNum - номер столбца.
     * @return название страны.
     */
    String getCountryName(List<WebElement> stringsMass, int i, int colNum){
        return getCurrentString(stringsMass,i).get(colNum).getAttribute("textContent");
    }

    /**
     * Находит название геозоны в таблице по указанной строке.
     * @param zonesStringMass таблица геозон.
     * @param j номер строки.
     * @return название геозоны.
     */
    String getZoneName(List<WebElement> zonesStringMass, int j){
        return getCurrentString(zonesStringMass,j).get(2).findElement(By.tagName("input")).getAttribute("value");
    }


    /**
     * Логин в панель администрирования.
     */
    public void login(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin", Keys.ENTER);
    }

    /**
     * Запуск браузера.
     */
    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    /**
     * Проверка расположения стран в алфавитном порядке в разделе Contries.
     */
    @Test
    public void test1() {
        login();
        driver.findElement(By.linkText("Countries")).click();
        List<WebElement> stringsMass = getStrings();
        for (int i = 0; i < stringsMass.size()-1; i++) {
            String countryName1 = getCountryName(stringsMass,i,4);
            String countryName2 = getCountryName(stringsMass,i+1,4);
            assertTrue(isAlphabetOrder(countryName1, countryName2));
        }
    }

    /**
     * Проверка расположения зон в алфавитном порядке в разделе "Contries" для стран, у которых количество зон отлично от 0.
     */
    @Test
    public void test2() {
        login();
        driver.findElement(By.linkText("Countries")).click();

        int numberOfStrings = getStrings().size();
        for (int i = 0; i < numberOfStrings; i++) {
            List<WebElement> stringsMass = getStrings();
            List<WebElement> currString = getCurrentString(stringsMass,i);
            String numberOfZones = currString.get(5).getAttribute("textContent");
            if (!numberOfZones.equals("0")){
                currString.get(4).findElement(By.tagName("a")).click();
                List<WebElement> zonesStringMass = getStrings();
                for (int j = 0; j < zonesStringMass.size()-1; j++) {
                    String countryName1 = getZoneName(zonesStringMass, j);
                    String countryName2 = getZoneName(zonesStringMass, j+1);
                    assertTrue(isAlphabetOrder(countryName1, countryName2));
                }
                driver.findElement(By.name("cancel")).click();
            }
        }
    }

    /**
     * Проверка расположения зон в алфавитном порядке для каждой страны в разделе "Geo Zones".
     */
    @Test
    public void test3() {
        login();
        driver.findElement(By.linkText("Geo Zones")).click();

        int numberOfStrings = getStrings().size();
        for (int i = 0; i < numberOfStrings; i++) {
            List<WebElement> stringsMass = getStrings();
            List<WebElement> currString = getCurrentString(stringsMass,i);
            currString.get(2).findElement(By.tagName("a")).click();
            List<WebElement> zonesStringMass = getStrings();
            for (int j = 0; j < zonesStringMass.size()-1; j++) {
                String countryName1 = getCountryName(zonesStringMass, j,2);
                String countryName2 = getCountryName(zonesStringMass, j+1,2);
                assertTrue(isAlphabetOrder(countryName1, countryName2));
                }
                driver.findElement(By.name("cancel")).click();
            }
        }

    /**
     * Закрывает браузер.
     */
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
