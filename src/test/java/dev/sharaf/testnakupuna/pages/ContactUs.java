package dev.sharaf.testnakupuna.pages;

import dev.sharaf.testnakupuna.bases.NakBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactUs extends NakBase {
    // locators
    By firstNameLocator = By.xpath("//input[@id='field_qh4icy']");
    By lastNameLocator = By.xpath("//input[@id='field_ocfup1']");
    By emailLocator = By.xpath("//input[@id='field_29yf4d']");
    By subjectLocator = By.xpath("//input[@id='field_e6lis6']");
    By messageLocator = By.xpath("//textarea[@id='field_9jv0r1']");
    By submitBtnLocator = By.xpath("//button[@type='submit']");


    public ContactUs(String browser) {
        super(browser);
    }

    private void typeText(String firstName, String lastName, String email, String subject, String message) {
        visit("https://nakupuna.com/contact-us/");
        getElement(firstNameLocator).sendKeys(firstName);
        getElement(lastNameLocator).sendKeys(lastName);
        getElement(emailLocator).sendKeys(email);
        getElement(subjectLocator).sendKeys(subject);
        getElement(messageLocator).sendKeys(message);
    }

    public String successfulFormSubmission(String firstName, String lastName, String email, String subject, String message) {
        typeText(firstName, lastName, email, subject, message);
        getElement(submitBtnLocator).click();
        By successMessageLocator = By.xpath("//div[normalize-space()='Success']");
        return getElement(successMessageLocator).toString();
    }

    public String failedFormSubmission(String firstName, String lastName, String email, String subject, String message) throws InterruptedException {
        typeText(firstName, lastName, email, subject, message);
        Thread.sleep(5_000);

        getElement(submitBtnLocator).click();
        // //*[@class='frm_error_style']
        By failedMessageLocator = By.xpath("//div[normalize-space()='There was a problem with your submission. Errors are marked below.']");
        return getElement(failedMessageLocator).toString();
    }

    // TODO: refactor the code to use wait.until(ExpectedConditions.visibilityOf()) @menhajswe
    public String getDcOfficeAddress() throws InterruptedException {
        // 251 18th Street South
        // Suite 1005
        // Arlington, VA 22202
        // 703.832.8042
        visit("https://nakupuna.com/contact-us/"); // update the base class start without a web page
        String defaultWindow = getDriver().getWindowHandle();
        Thread.sleep(5_000);
        scrollDownBy500Pixels();
        Thread.sleep(2000);
        WebElement mapElement = getElement(By.xpath("//div[contains(@class,'vc_col-sm-4 wpb_column column_container vc_column_container col has-animation padding-4-percent inherit_tablet inherit_phone instance-4 triggered-animation animated-in')]//a[contains(@role,'button')][normalize-space()='Map and Directions']"));
        moveToElement(mapElement);
        mapElement.click();
        Thread.sleep(2000);
        getElement(By.xpath("//img[contains(@alt,'Image showing map location of the Arlington office which links to Google Maps where you can find and follow directions to the office.')]")).click();
        // 251 18th St S #1005, Arlington, VA 22202
        // //span[contains(text(),'251 18th St S #1005, Arlington, VA 22202')]
        Thread.sleep(5000);
        System.out.println(getDriver().getWindowHandle());
        System.out.println(getDriver().getCurrentUrl());
        List<String> windowHandlers = getDriver().getWindowHandles().stream().toList();
        getDriver().switchTo().window(windowHandlers.get(windowHandlers.size() - 1));
        var googleResult = getElement(By.xpath("//span[contains(text(),'251 18th St S #1005, Arlington, VA 22202')]")).getText();
        return googleResult;


    }


}
