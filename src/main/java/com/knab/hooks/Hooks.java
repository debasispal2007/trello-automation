package com.knab.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileInputStream;
import java.util.Properties;
import java.time.Duration;

public class Hooks {
    private static WebDriver driver;
    private static Properties properties;

    @Before("@ui")
    public void setupUI() {
        try {
            // Load properties
            properties = new Properties();
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties.load(file);
            
            // Setup ChromeDriver
            WebDriverManager.chromedriver().setup();
            
            
            ChromeOptions options = new ChromeOptions();
            
            // Check if running in Docker
            if (System.getenv("CHROME_OPTIONS") != null) {
                // Docker-specific options
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            } else {
                // Local-specific options
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("start-maximized");
            }
            
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            // Navigate to Trello
            driver.get(properties.getProperty("trello.ui.url"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("@ui")
    public void tearDownUI(Scenario scenario) {
        if (driver != null) {
            if (scenario.isFailed()) {
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            }
            driver.quit();
        }
    }


    @After("@api")
    public void tearDownAPI(Scenario scenario) {
        // Cleanup any test data created during API tests
    }

    public static WebDriver getDriver() {
        return driver;
    }
    
    public static Properties getProperties() {
        return properties;
    }
}