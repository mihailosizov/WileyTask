package com.wiley.utils;

import com.wiley.support.Driver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wiley.support.Log.LOGGER;

public class Common {

    public static List<String> getTextFor(List<WebElement> webElements) {
        List<String> texts = new ArrayList<>();
        for (WebElement element : webElements) {
            texts.add(element.getText());
        }
        return texts;
    }

    public static boolean clickLinkFromCollection(List<WebElement> collection, String linkText) {
        for (WebElement element : collection) {
            if (element.getText().equals(linkText)) {
                element.click();
                    return true;
            }
        }
        LOGGER.error("Unable to find link with text: " + linkText);
        return false;
    }

    public static String getSubstringByRegexPatternGroup(String source, String regexPattern) {
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            LOGGER.error(String.format("Unable to find substring from '%s' by regex pattern: $s", source, regexPattern));
            return "";
        }
    }

    public static boolean isAlertPresent() {
        try {
            Driver.getInstance().switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public static String getAlertTextAndAccept() {
        Alert alert = Driver.getInstance().switchTo().alert();
        String alertMessage = alert.getText();
        alert.accept();
        return alertMessage;
    }
}
