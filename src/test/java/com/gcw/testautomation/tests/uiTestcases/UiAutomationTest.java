package com.gcw.testautomation.tests.uiTestcases;

import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.testautomation.support.constants.ConfigKeys;
import com.gcw.testautomation.support.constants.ElementKeys;
import com.gcw.testautomation.support.dataproviders.PracticeDataProvider;
import com.gcw.testautomation.support.pages.PracticePage;
import com.typesafe.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Test(groups = "ui")
public class UiAutomationTest {
    private  ChromeDriver chromeDriver;
    private PracticePage practicePage;

    Config config = ConfigProvider.config(System.getProperty(ConfigKeys.ENTITY));


    @BeforeSuite
    public void setUp(){
        String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver",projectPath+"\\src\\test\\resources\\drivers\\chromedriver.exe");
        chromeDriver  = new ChromeDriver();
        chromeDriver.manage().window().maximize();
         String practiceUrl = config.getConfig(ConfigKeys.UI).getString(ConfigKeys.PRACTICE_PAGE_URI);
        practicePage = new PracticePage(chromeDriver);
        chromeDriver.get(practiceUrl);
        String title = new WebDriverWait(chromeDriver,Duration.ofSeconds(15)).until(webDriver -> webDriver.findElement(By.xpath("//h1")).getText());
        Assert.assertEquals(title, ElementKeys.PRACTICE_PAGE);
    }

    @AfterSuite
    public void tearDown(){
        chromeDriver.quit();
    }

    @Test(dataProvider = "radioIdProvider",dataProviderClass = PracticeDataProvider.class)
    public void testSelectFromRadioButtonExample(int radioId){
        practicePage.clickRadioButton(radioId);
        practicePage.IsRadioSelected(radioId);
    }

    @Test
    public void testInputContentInSuggestionClassExample(){
        String country = "China";
        practicePage.inputSuggestionClassCountries(country);
    }

    @Test(dataProvider = "optionIndexProvider",dataProviderClass = PracticeDataProvider.class)
    public void testSelectOptionsFromDropdownExample(int optionIndex){
        practicePage.selectDropDownExample(optionIndex);
    }

    @Test
    public void testGetAlertContentForSwitchAlertExample(){
        String name = "gcw";
        //verify alert button
        String expectedAlertContent = String.format("Hello %s, share this practice page and share your knowledge",name);
        practicePage.inputSwitchAlertName(name);
        String actualAlertContent = practicePage.getSwitchAlertContent(name);
        Assert.assertEquals(actualAlertContent,expectedAlertContent);

        //verify confirm button
        String expectedConfirmAlertContent = String.format("Hello %s, Are you sure you want to confirm?",name);
        practicePage.inputSwitchAlertName(name);
        String actualConfirmAlertContent = practicePage.getConfirmAlertContent(name);
        Assert.assertEquals(actualConfirmAlertContent,expectedConfirmAlertContent);
    }

    @Test
    public void testSumAllPriceInWebTableExample(){
        double expectedPriceSum = 235;
        List<WebElement> webElementList = practicePage.getAllWebTableElementsList();
        List<String> priceList = new ArrayList<>();
        webElementList.remove(0);
        for (WebElement element : webElementList) {
            List<WebElement> rowList = element.findElements(By.tagName("td"));
            String price = rowList.get(rowList.size()-1).getText();
            priceList.add(price);
        }
        double actualPriceSum = priceList.stream().mapToDouble(Double::parseDouble).sum();
        Assert.assertEquals(actualPriceSum,expectedPriceSum);
    }

    @Test
    public void testHideAndShowElementDisPlayedExample(){
        //verify hide button
        practicePage.clickHideButton();
        Assert.assertFalse(practicePage.isElementInputBoxDisplayed());

        //verify show button
        practicePage.clickShowButton();
        Assert.assertTrue(practicePage.isElementInputBoxDisplayed());
    }

    @Test
    public void testSwitchWindowExample(){
        String expectedNewWindowTitle = "QA Click Academy | Selenium,Jmeter,SoapUI,Appium,Database testing,QA Training Academy";
        String practicePageWindow = chromeDriver.getWindowHandle();
        practicePage.openNewWindow();
        for (String window : chromeDriver.getWindowHandles()){
            if (!window.equals(practicePageWindow)){
                chromeDriver.switchTo().window(window);
            }
        }
        Assert.assertEquals(chromeDriver.getTitle(),expectedNewWindowTitle);
        chromeDriver.close();
        chromeDriver.switchTo().window(practicePageWindow);
    }

    @Test
    public void testIframeExample(){
        practicePage.switchToIframeExample();
        practicePage.isContentDisplayedInIframe();
        chromeDriver.switchTo().defaultContent();
    }

    @Test
    public void testWebTableFixedHeaderExample(){
        double expectedTotalAmount = 296;
        List<WebElement> webElementList = practicePage.getAllFixedHeaderTable();
        webElementList.remove(0);
        List<String> amountList = new ArrayList<>();
        for (WebElement element : webElementList) {
            List<WebElement> rowList = element.findElements(By.tagName("td"));
            String amount = rowList.get(rowList.size()-1).getText();
            amountList.add(amount);
        }
        double actualTotalAmount = amountList.stream().mapToDouble(Double::parseDouble).sum();
        Assert.assertEquals(actualTotalAmount,expectedTotalAmount);
    }

}
