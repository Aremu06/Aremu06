package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;

public class LoginTests extends BaseTest {

    LoginPage loginPage;
    ProductsPage productsPage;
    InputStream datais;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws IOException {
        try {
            String dataFileName = "data/loginUser.json";
            datais = getClass().getResourceAsStream(dataFileName);
            JSONTokener tokener = new  JSONTokener(datais);
            loginUsers = new JSONObject(tokener);

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if (datais !=null){
                datais.close();
            }

        }

    }
    @AfterClass
    public void afterClass(){
    }

    @BeforeMethod
    public void beforeMethod(){
        loginPage = new LoginPage();
       // System.out.println("\n" + "8888 starting test:" + m.getName() +"****" + "\n");
    }
    @AfterMethod
    public void afterMethod(){


    }
    @Test
    public void invalidUserName(){
        loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
        loginPage.pressLoginBtn();

        String actualErrTxt = loginPage.getErrTxt();
        String expectedErrTxt = strings.get("err_invalid_user_or_password");
        System.out.println("actualErrTxt - " + actualErrTxt +"\n" + "expected error txt -" + expectedErrTxt );

        Assert.assertEquals(actualErrTxt, expectedErrTxt);

    }
    @Test
    public void invalidPassword(){
        loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
        loginPage.pressLoginBtn();

        String actualErrTxt = loginPage.getErrTxt();
        String expectedErrTxt = strings.get("err_invalid_user_or_password");
        System.out.println("actualErrTxt - " + actualErrTxt +"\n" + "expected error txt -" + expectedErrTxt );

        Assert.assertEquals(actualErrTxt, expectedErrTxt);
  }
    @Test
    public void successfulLogin(){
        loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
        loginPage.pressLoginBtn();
        productsPage = loginPage.pressLoginBtn();

        String actualProductTitle = productsPage.getTitle();
        String expectedProductTitle = strings.get("product_title");
        System.out.println("product title -" + actualProductTitle + "\n" + "expected title -" +expectedProductTitle);

        Assert.assertEquals(actualProductTitle, expectedProductTitle);

    }
}

