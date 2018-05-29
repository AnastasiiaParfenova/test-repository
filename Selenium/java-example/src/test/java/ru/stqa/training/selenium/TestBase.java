package ru.stqa.training.selenium;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            return;
        }

        driver = new ChromeDriver();
//        driver = new InternetExplorerDriver();
//        driver = new FirefoxDriver();
        tlDriver.set(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);


        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }

}
