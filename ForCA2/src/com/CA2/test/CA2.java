package com.CA2.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;


public class CA2 {

	static String formURL = "file:///D:/Devops%20CAs/CA2/index.html";

    static WebDriver driver;
    static final int DELAY = 3000; // 3 seconds

    public static void main(String[] args) throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        Thread.sleep(DELAY);

        int passed = 0;
        int failed = 0;

        System.out.println("========================================");
        System.out.println("   CA2 - Selenium Test Cases Running    ");
        System.out.println("========================================\n");

        if (testPageOpens())           passed++; else failed++;
        if (testValidSubmission())     passed++; else failed++;
        if (testEmptyFieldsErrors())   passed++; else failed++;
        if (testInvalidEmail())        passed++; else failed++;
        if (testInvalidMobile())       passed++; else failed++;
        if (testDepartmentDropdown())  passed++; else failed++;
        if (testResetButton())         passed++; else failed++;

        System.out.println("\n========================================");
        System.out.println("   RESULTS: " + passed + " Passed | " + failed + " Failed");
        Thread.sleep(DELAY);
        driver.quit();
    }

    // Helper method
    public static void setReactValue(JavascriptExecutor js, WebElement element, String value) {
        js.executeScript("arguments[0].value = arguments[1];", element, value);
    }


    // Test 1: Check whether the form page opens
    static boolean testPageOpens() throws InterruptedException {
        try {
            System.out.println("[TC1] Opening form page...");
            driver.get(formURL);
            Thread.sleep(DELAY);

            String title = driver.getTitle();
            System.out.println("[TC1] Page Title: " + title);
            Thread.sleep(DELAY);

            if (title.contains("Student Feedback")) {
                System.out.println("[TC1] PASSED - Page opened successfully.\n");
                return true;
            } else {
                System.out.println("[TC1] FAILED - Unexpected page title.\n");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[TC1] FAILED - Exception: " + e.getMessage() + "\n");
            return false;
        }
    }


    // Test 2: Enter valid data - verify successful submission
    static boolean testValidSubmission() throws InterruptedException {
        try {
            System.out.println("[TC2] Testing valid form submission...");
            driver.get(formURL);
            Thread.sleep(DELAY);

            System.out.println("[TC2] Entering Student Name...");
            driver.findElement(By.id("studentName")).sendKeys("Rahul Sharma");
            Thread.sleep(DELAY);

            System.out.println("[TC2] Entering Email...");
            driver.findElement(By.id("email")).sendKeys("rahul@gmail.com");
            Thread.sleep(DELAY);

            System.out.println("[TC2] Entering Mobile Number...");
            driver.findElement(By.id("mobile")).sendKeys("9876543210");
            Thread.sleep(DELAY);

            System.out.println("[TC2] Selecting Department...");
            Select dept = new Select(driver.findElement(By.id("department")));
            dept.selectByValue("CSE");
            Thread.sleep(DELAY);

            System.out.println("[TC2] Selecting Gender...");
            driver.findElement(By.xpath("//input[@name='gender' and @value='Male']")).click();
            Thread.sleep(DELAY);

            System.out.println("[TC2] Entering Feedback Comments...");
            driver.findElement(By.id("feedback")).sendKeys(
                "The teaching quality and college infrastructure is excellent and very useful for students learning.");
            Thread.sleep(DELAY);

            System.out.println("[TC2] Clicking Submit...");
            driver.findElement(By.id("submitBtn")).click();
            Thread.sleep(DELAY);

            WebElement successMsg = driver.findElement(By.id("successMsg"));
            if (successMsg.isDisplayed()) {
                System.out.println("[TC2] PASSED - Valid form submitted successfully.\n");
                return true;
            } else {
                System.out.println("[TC2] FAILED - Success message not displayed.\n");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[TC2] FAILED - Exception: " + e.getMessage() + "\n");
            return false;
        }
    }

    // Test 3: Leave mandatory fields blank - check errors
    static boolean testEmptyFieldsErrors() throws InterruptedException {
        try {
            System.out.println("[TC3] Testing empty fields validation...");
            driver.get(formURL);
            Thread.sleep(DELAY);

            System.out.println("[TC3] Clicking Submit without filling anything...");
            driver.findElement(By.id("submitBtn")).click();
            Thread.sleep(DELAY);

            String nameError   = driver.findElement(By.id("nameError")).getText();
            String emailError  = driver.findElement(By.id("emailError")).getText();
            String mobileError = driver.findElement(By.id("mobileError")).getText();

            System.out.println("[TC3] Name Error: " + nameError);
            System.out.println("[TC3] Email Error: " + emailError);
            System.out.println("[TC3] Mobile Error: " + mobileError);
            Thread.sleep(DELAY);

            if (!nameError.isEmpty() && !emailError.isEmpty() && !mobileError.isEmpty()) {
                System.out.println("[TC3] PASSED - Empty field errors shown correctly.\n");
                return true;
            } else {
                System.out.println("[TC3] FAILED - Some errors not displayed.\n");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[TC3] FAILED - Exception: " + e.getMessage() + "\n");
            return false;
        }
    }

    // Test 4: Invalid email format - verify validation
    static boolean testInvalidEmail() throws InterruptedException {
        try {
            System.out.println("[TC4] Testing invalid email format...");
            driver.get(formURL);
            Thread.sleep(DELAY);

            System.out.println("[TC4] Entering Name...");
            driver.findElement(By.id("studentName")).sendKeys("Test User");
            Thread.sleep(DELAY);

            System.out.println("[TC4] Entering invalid email: invalidemail@@wrong");
            driver.findElement(By.id("email")).sendKeys("invalidemail@@wrong");
            Thread.sleep(DELAY);

            driver.findElement(By.id("mobile")).sendKeys("9876543210");
            Thread.sleep(DELAY);

            System.out.println("[TC4] Clicking Submit...");
            driver.findElement(By.id("submitBtn")).click();
            Thread.sleep(DELAY);

            String emailError = driver.findElement(By.id("emailError")).getText();
            System.out.println("[TC4] Email Error Shown: " + emailError);
            Thread.sleep(DELAY);

            if (!emailError.isEmpty()) {
                System.out.println("[TC4] PASSED - Invalid email caught.\n");
                return true;
            } else {
                System.out.println("[TC4] FAILED - Email error not shown.\n");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[TC4] FAILED - Exception: " + e.getMessage() + "\n");
            return false;
        }
    }

    // Test 5: Invalid mobile number - verify validation
    static boolean testInvalidMobile() throws InterruptedException {
        try {
            System.out.println("[TC5] Testing invalid mobile number...");
            driver.get(formURL);
            Thread.sleep(DELAY);

            driver.findElement(By.id("studentName")).sendKeys("Test User");
            Thread.sleep(DELAY);

            driver.findElement(By.id("email")).sendKeys("test@gmail.com");
            Thread.sleep(DELAY);

            System.out.println("[TC5] Entering invalid mobile: 12345");
            driver.findElement(By.id("mobile")).sendKeys("12345");
            Thread.sleep(DELAY);

            System.out.println("[TC5] Clicking Submit...");
            driver.findElement(By.id("submitBtn")).click();
            Thread.sleep(DELAY);

            String mobileError = driver.findElement(By.id("mobileError")).getText();
            System.out.println("[TC5] Mobile Error Shown: " + mobileError);
            Thread.sleep(DELAY);

            if (!mobileError.isEmpty()) {
                System.out.println("[TC5] PASSED - Invalid mobile caught.\n");
                return true;
            } else {
                System.out.println("[TC5] FAILED - Mobile error not shown.\n");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[TC5] FAILED - Exception: " + e.getMessage() + "\n");
            return false;
        }
    }

    // Test 6: Check drop down selection works
    static boolean testDepartmentDropdown() throws InterruptedException {
        try {
            System.out.println("[TC6] Testing department dropdown...");
            driver.get(formURL);
            Thread.sleep(DELAY);

            System.out.println("[TC6] Selecting IT from dropdown...");
            Select dept = new Select(driver.findElement(By.id("department")));
            dept.selectByValue("IT");
            Thread.sleep(DELAY);

            String selected = dept.getFirstSelectedOption().getText();
            System.out.println("[TC6] Selected: " + selected);
            Thread.sleep(DELAY);

            if (selected.contains("Information Technology")) {
                System.out.println("[TC6] PASSED - Dropdown works correctly.\n");
                return true;
            } else {
                System.out.println("[TC6] FAILED - Wrong option selected.\n");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[TC6] FAILED - Exception: " + e.getMessage() + "\n");
            return false;
        }
    }

    // Test 7: Check Submit and Reset buttons work
    static boolean testResetButton() throws InterruptedException {
        try {
            System.out.println("[TC7] Testing Reset button...");
            driver.get(formURL);
            Thread.sleep(DELAY);

            System.out.println("[TC7] Filling form fields...");
            driver.findElement(By.id("studentName")).sendKeys("John Doe");
            Thread.sleep(DELAY);

            driver.findElement(By.id("email")).sendKeys("john@test.com");
            Thread.sleep(DELAY);

            driver.findElement(By.id("mobile")).sendKeys("9876543210");
            Thread.sleep(DELAY);

            System.out.println("[TC7] Clicking Reset...");
            driver.findElement(By.id("resetBtn")).click();
            Thread.sleep(DELAY);

            String nameValue = driver.findElement(By.id("studentName")).getAttribute("value");
            System.out.println("[TC7] Name field after reset: '" + nameValue + "'");
            Thread.sleep(DELAY);

            if (nameValue.equals("")) {
                System.out.println("[TC7] PASSED - Reset button cleared the form.\n");
                return true;
            } else {
                System.out.println("[TC7] FAILED - Fields not cleared after reset.\n");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[TC7] FAILED - Exception: " + e.getMessage() + "\n");
            return false;
        }
    }
}