package com.gcw.testautomation.support.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class PracticePage {

    WebDriver webDriver;

    public PracticePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    public void clickRadioButton(int radioId) {
        String xPath = String.format("//*[@id=\"radio-btn-example\"]/fieldset/label[%d]/input", radioId);
        this.webDriver.findElement(By.xpath(xPath)).click();
    }

    public boolean IsRadioSelected(int radioId) {
        String xPath = String.format("//*[@id=\"radio-btn-example\"]/fieldset/label[%d]/input", radioId);
        return this.webDriver.findElement(By.xpath(xPath)).isSelected();
    }


    public void inputSuggestionClassCountries(String country) {
        webDriver.findElement(By.id("autocomplete")).sendKeys(country);
    }



    public void selectDropDownExample(int optionIndex) {
        String xPath = String.format("//select[@id=\"dropdown-class-example\"]/option[%d]", optionIndex);
        Select select = new Select(webDriver.findElement(By.id("dropdown-class-example")));
        Assert.assertFalse(select.isMultiple());
        if (optionIndex > 0 && optionIndex < 5) {
            webDriver.findElement(By.xpath(xPath)).click();
        }
        String currentOption = select.getFirstSelectedOption().getText();
        String expectedOption = webDriver.findElement(By.xpath(xPath)).getText();
        Assert.assertEquals(currentOption, expectedOption);
    }

    public void inputSwitchAlertName(String name) {
        webDriver.findElement(By.id("name")).sendKeys(name);
    }

    public String getSwitchAlertContent(String name) {
        webDriver.findElement(By.id("alertbtn")).click();
        webDriver.switchTo().alert().getText();
        String alertContent = webDriver.switchTo().alert().getText();
        webDriver.switchTo().alert().accept();
        return alertContent;
    }

    public String getConfirmAlertContent(String name) {
        webDriver.findElement(By.id("confirmbtn")).click();
        webDriver.switchTo().alert().getText();
        String confirmAlertContent = webDriver.switchTo().alert().getText();
        webDriver.switchTo().alert().accept();
        return confirmAlertContent;
    }

    public List<WebElement> getAllWebTableElementsList() {
        WebElement table = webDriver.findElement(By.name("courses"));
        return table.findElements(By.tagName("tr"));
    }

    public void clickHideButton() {
        webDriver.findElement(By.id("hide-textbox")).click();
    }

    public void clickShowButton() {
        webDriver.findElement(By.id("show-textbox")).click();
    }

    public boolean isElementInputBoxDisplayed() {
        return webDriver.findElement(By.id("displayed-text")).isDisplayed();
    }

    public void openNewWindow(){
        webDriver.findElement(By.id("openwindow")).click();
    }

    public void switchToIframeExample(){
        webDriver.switchTo().frame("courses-iframe");
    }

    public boolean isContentDisplayedInIframe(){
        String xPath = "//img[@src=\"assets/images/rs_logo.png\"]";
        return webDriver.findElement(By.xpath(xPath)).isDisplayed();
    }

    public List<WebElement> getAllFixedHeaderTable(){
        String xPath = "//div[@class=\"tableFixHead\"]/table";
        WebElement table = webDriver.findElement(By.xpath(xPath));
        return table.findElements(By.tagName("tr"));
    }


}
