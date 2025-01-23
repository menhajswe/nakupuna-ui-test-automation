package dev.sharaf.testnakupuna.pages;

import dev.sharaf.testnakupuna.bases.NakBase;
import org.openqa.selenium.By;

import java.util.List;

import static dev.sharaf.testnakupuna.utils.NakConstants.careersUrl;

public class Careers extends NakBase {
    private By viewAllOpeningsLocator = By.xpath("//div[contains(@class,'hover_color_edc77d border_radius_30px alignment_tablet_default alignment_phone_default display_tablet_inherit display_phone_inherit font_size_desktop_0-95em')]//a[@role='button'][normalize-space()='VIEW ALL CURRENT OPENINGS']");
    private By submitYourResumeBtnLocator = By.xpath("//img[@alt='Submit Your Resume Button-01']");
    private By searchBoxLocator = By.xpath("//input[@id='jsb_f_keywords_i']");

    private final String iframeId = "icims_content_iframe";
    private final String targetElement = "#jsb_f_keywords_i";
    private final String btnSelector = "#jsb_form_submit_i";

    public Careers(String browser) {
        super(browser);
        visit(careersUrl);
    }

    // WIP: Please don't review this class.
    public void viewAllOpenings() {
        getElement(viewAllOpeningsLocator).click();
        List<String> windowHandles = getDriver().getWindowHandles().stream().toList();
        getDriver().switchTo().window(windowHandles.get(windowHandles.size() - 1));
        // add logic for retrieving first three jobs.
    }

    public Boolean searchAJob(String searchValue) throws InterruptedException {
        isDisplayed(viewAllOpeningsLocator);
        getElement(viewAllOpeningsLocator).click();
        List<String> windowHandles = getDriver().getWindowHandles().stream().toList();
        getDriver().switchTo().window(windowHandles.get(windowHandles.size() - 1));
        scrollDownByHalfPage();
        Thread.sleep(2000);
        String scriptToSearch = """
                const iframe = document.getElementById('%s');
                const iframeDocument = iframe.contentDocument;
                const element = iframeDocument.querySelector('%s');
                element.value = '%s';
                const btn = iframeDocument.querySelector('%s');
                btn.click();
                """.formatted(iframeId, targetElement, searchValue, btnSelector).trim();
        executeJavaScript(scriptToSearch);
        scrollDownByHalfPage();
        Thread.sleep(2000);

        String scriptToVerify = """
                const iframe2 = document.getElementById('%s');
                return iframe2.contentDocument.body.textContent.includes('%s');
                """.formatted(iframeId, searchValue).trim();
        return (Boolean) executeJavaScript(scriptToVerify);
    }
}
