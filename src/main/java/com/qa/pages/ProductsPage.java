package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.FindBy;

public class ProductsPage extends BaseTest {

    @AndroidFindBy (xpath = "//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/android.widget.TextView")
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Toggle\"]/parent::[1]/preceding-sibling::*[1]")
    private MobileElement productTitleTxt;

    public String getTitle(){
        return getText(productTitleTxt);

        // fixme
    }






}
