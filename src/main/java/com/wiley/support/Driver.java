package com.wiley.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.wiley.support.Log.LOGGER;

public class Driver {
    private static boolean isInitialized = false;
    private static WebDriverWait wait;
    private static WebDriver instance;

    private Driver() {
    }

    public static WebDriver getInstance() {
        return instance;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static void initialize() {
        if (!isInitialized) {
            LOGGER.info("Driver is not initialized. Starting to initialize webdriver");
            System.setProperty("webdriver.chrome.driver", Driver.class.getResource("/chromedriver.exe").getPath());
            instance = new ChromeDriver();
            wait = new WebDriverWait(instance, 10);
            instance.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            instance.manage().window().maximize();
            LOGGER.info("Driver has been successfully initialized");
            isInitialized = true;
        }
    }

    public static void close() {
        LOGGER.info("Closing webdriver instance...");
        instance.quit();
        LOGGER.info("Webdriver has been closed");
        isInitialized = false;
    }

    public static Set<String> getCurrentTabs() {
        return instance.getWindowHandles();
    }

    public static String getCurrentTab() {
        return instance.getWindowHandle();
    }

    public static void switchToTab(String tabHandle) {
        instance.switchTo().window(tabHandle);
    }
}
