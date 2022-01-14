package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;


public class BaseTest {
    protected static AppiumDriver driver;
    protected static Properties props;
    protected static HashMap<String, String> strings = new HashMap<String, String>();
    protected static String platform;
    InputStream inputStream;
    InputStream stringsis;
    TestUtils utils;
    URL url;
    
    public BaseTest(){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    
    @Parameters({"simulator, platformName", "platformVersion", "udid", "deviceName"})
    @BeforeTest
    public void beforeTest(String emulator, String platformName, String platformVersion, String udid, String deviceName) throws Exception {
        platform = platformName;
        try {
            props = new Properties();
            String propFileName = "config.properties";
            String xmlFileName = "strings/strings.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            utils = new TestUtils();
            strings = utils.parseStringXML(stringsis);


            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "platformName");


            switch (platformName){
                case "Android":
                    caps.setCapability("automationName", "androidAutomationName");
                    caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
                    caps.setCapability("appActivity", props.getProperty("androidAppActivity"));

                    if (emulator.equalsIgnoreCase("true")){
                        caps.setCapability("platformVersion", "platformVersion");
                        caps.setCapability("avd", deviceName);
                    } else {
                        caps.setCapability("udid", udid);
                    }
                   // caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
                    // URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
                    String androidAppUrl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();
                    caps.setCapability("app",androidAppUrl);

                    url = new URL(props.getProperty("appiumURL"));

                    driver = new AndroidDriver(url, caps);
                    break;
                   // String sessionId = driver.getSessionId().toString();
                case "IOS":
                    caps.setCapability("automationName", "IOSAutomationName");
                    caps.setCapability("platformVersion", "platformVersion");
                    String IOSappUrl = getClass().getResource(props.getProperty("IOSAppLocation")).getFile();
                    System.out.println("appUrl is" +IOSappUrl);
                    caps.setCapability("bundleId", props.getProperty("iOSBundleId"));
                   // caps.setCapability("app",IOSappUrl);
                    //caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
                    // URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));

                    url = new URL(props.getProperty("appiumURL"));

                    driver = new IOSDriver(url, caps);
                   // String sessionId = driver.getSessionId().toString();
                    break;
                default:
                    throw new Exception("Invalid platform! -" + platformName);
            }


           
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if (inputStream !=null);
            inputStream.close();
        }
        if (strings !=null);
            stringsis.close();

    }
    public void waitForVisibility(MobileElement e){
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void clear(MobileElement e){
        waitForVisibility(e);
        e.clear();
    }

    public void click(MobileElement e, String press_logout_button) {
        waitForVisibility(e);
        e.click();
    }
    public void sendKeys(MobileElement e, String txt){
        waitForVisibility(e);
        e.sendKeys(txt);

        }
        public String getAttribute(MobileElement e, String attribute){
        waitForVisibility(e);
       return e.getAttribute(attribute);
    }

    public String getText(MobileElement e) {
        switch (platform) {
            case "Android":
                return getAttribute(e, "text");
            case "IOS":
                return getAttribute(e, "label");
        }
        return null;
    }

    @AfterTest
    public void afterTest(){
        driver.quit();

    }
}
