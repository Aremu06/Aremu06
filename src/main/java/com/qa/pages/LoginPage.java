package com.qa.pages;


import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BaseTest {

    @AndroidFindBy(accessibility = "test-Username")
    @FindBy(id = "test-Username")
    private MobileElement usernameTxtFld;

    @AndroidFindBy(accessibility = "test-Password")
    @FindBy(id = "test-Password")
    private MobileElement passwordTxtFld;

    @AndroidFindBy(accessibility = "test-LOGIN")
    @FindBy(id = "test-LOGIN")
    private MobileElement loginBtn;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
    private MobileElement errTxt;

    public LoginPage enterUserName(String username) {
        clear(usernameTxtFld);
        sendKeys(usernameTxtFld, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        clear(passwordTxtFld);
        sendKeys(passwordTxtFld, password);
        return this;

    }
    public LoginPage pressLoginBtn(String password) {
        click(loginBtn, "press Logout button");
        return this;
  }
  public String getErrTxt(){
        return getText(errTxt);
  }

    public ProductsPage pressLoginBtn() {
        return null;
    }
}