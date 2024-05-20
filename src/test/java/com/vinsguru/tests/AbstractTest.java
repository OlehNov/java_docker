package com.vinsguru.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vinsguru.util.Config;
import com.vinsguru.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


public abstract class AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(AbstractTest.class);
    protected WebDriver driver;

    @BeforeSuite
    public void setupConfig(){
        Config.initialize();
    }

    @BeforeTest
    public void setDriver() throws MalformedURLException {
        this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ? getRemoteDriver() : getLocalDriver();
    }

    private WebDriver getRemoteDriver() throws MalformedURLException {
        Capabilities capabilities = new ChromeOptions();
        if (Constants.CHROME.equalsIgnoreCase(Config.get(Constants.BROWSER))){
            capabilities = new ChromeOptions();
        }
        String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat, hubHost);
        log.info("grid url: {}", url);
        return new RemoteWebDriver(new URL(url), capabilities);
    }

    private WebDriver getLocalDriver(){
        System.setProperty("webdriver.chrome.driver", "/home/oleh/.cache/selenium/chromedriver/linux64/124.0.6367.201/chromedriver");
        ChromeOptions options = new ChromeOptions();
        final String[] args = { "--remote-debugging-port=9222" };
        options.addArguments(args);
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        return new ChromeDriver(options);
    }

    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }

    @AfterMethod
    public void sleep(){
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
    }
}


//public abstract class AbstractTest {
//
//    protected WebDriver driver;
//
//    @BeforeTest
//    public void setDriver(){
////        WebDriverManager.chromedriver().setup();
//        ChromeOptions options = new ChromeOptions();
//        System.setProperty("webdriver.chrome.driver", "/home/oleh/.cache/selenium/chromedriver/linux64/124.0.6367.201/chromedriver");
//        final String[] args = { "--remote-debugging-port=9222" };
//        options.addArguments(args);
//        options.addArguments("start-maximized"); // open Browser in maximized mode
//        options.addArguments("disable-infobars"); // disabling infobars
//        options.addArguments("--disable-extensions"); // disabling extensions
//        options.addArguments("--disable-gpu"); // applicable to windows os only
//        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//        options.addArguments("--no-sandbox"); // Bypass OS security model
//        this.driver = new ChromeDriver(options);
//    }
//
//    @AfterTest
//    public void quitDriver(){
//        this.driver.quit();
//    }
//
//}

//public abstract class AbstractTest {
//
//    protected WebDriver driver;
//
//    @BeforeTest
//    public void setDriver() throws MalformedURLException {
//        if(Boolean.getBoolean("selenium.grid.enabled")){
//            this.driver = getRemoteDriver();
//        }else {
//            this.driver = getLocalDriver();
//        }
//    }
//
//    private WebDriver getRemoteDriver() throws MalformedURLException {
//        Capabilities capabilities;
//        if (System.getProperty("browser").equalsIgnoreCase("chrome")){
//            capabilities = new ChromeOptions();
//        }else{
//            capabilities = new FirefoxOptions();
//        }
//        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
//    }
//
//    private WebDriver getLocalDriver(){
//        System.setProperty("webdriver.chrome.driver", "/home/oleh/.cache/selenium/chromedriver/linux64/124.0.6367.201/chromedriver");
//        ChromeOptions options = new ChromeOptions();
//        final String[] args = { "--remote-debugging-port=9222" };
//        options.addArguments(args);
//        options.addArguments("start-maximized"); // open Browser in maximized mode
//        options.addArguments("disable-infobars"); // disabling infobars
//        options.addArguments("--disable-extensions"); // disabling extensions
//        options.addArguments("--disable-gpu"); // applicable to windows os only
//        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//        options.addArguments("--no-sandbox"); // Bypass OS security model
//        return new ChromeDriver(options);
//    }
//
//    @AfterTest
//    public void quitDriver(){
//        this.driver.quit();
//    }
//}
