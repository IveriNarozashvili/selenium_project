import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestScenario {
    private static final String targetURL = "http://www.rgs.ru";
    private static WebDriver driver;
    private static Wait<WebDriver> wait;

    @Before
    public void startUp(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.get(targetURL);
    }

    @Test
    public void startTest() throws InterruptedException {
        WebElement menuButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='main-navbar']//a[@data-toggle='dropdown' and contains(text(),'Меню')]")));
        menuButton.click();

        WebElement dmsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'ДМС')]")));
        dmsButton.click();

        WebElement headerDms = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//h1[@class='content-document-header']")));
        Assert.assertEquals("Заголовок не соответствует",
                "ДМС — добровольное медицинское страхование", headerDms.getText());

        WebElement sendRequestButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Отправить заявку')]")));
        sendRequestButton.click();


        WebElement lastNameInput = driver.findElement(By.xpath("//input[@name='LastName']"));
        WebElement firstNameInput = driver.findElement(By.xpath("//input[@name='FirstName']"));
        WebElement middleNameInput = driver.findElement(By.xpath("//input[@name='MiddleName']"));
        WebElement selectRegion = driver.findElement(By.xpath("//select[@name='Region']"));
        WebElement telephoneNumber = driver.findElement(By.xpath("//label[text()='Телефон']//..//input"));
        WebElement email = driver.findElement(By.xpath("//input[@name='Email']"));
        WebElement date = driver.findElement(By.xpath("//label[text()='Предпочитаемая дата контакта']//..//input"));
        WebElement comment = driver.findElement(By.xpath("//textarea[@name='Comment']"));
        WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
        WebElement sendButton = driver.findElement(By.xpath("//button[@id='button-m']"));

        lastNameInput.sendKeys("Иванов");
        firstNameInput.sendKeys("Василий");
        middleNameInput.sendKeys("Станиславович");
        Thread.sleep(500);
        new Select(selectRegion).selectByIndex(1);

        telephoneNumber.click();
        int[] telNumbsSeq = {9,7,7,5,4,6,5,1,9,7};
        for (int s : telNumbsSeq) {
            telephoneNumber.sendKeys(String.valueOf(s));
            Thread.sleep(50);
        }

        email.sendKeys("qwertyqwerty");

        date.click();
        int[] dateNumbsSeq = {2, 1, 1, 0, 2, 0, 2, 0};
        for (int s : dateNumbsSeq) {
            date.sendKeys(String.valueOf(s));
            Thread.sleep(500);
        }

        comment.sendKeys("йфячсм");
        checkbox.click();

        Assert.assertEquals("Введите корректный email", "qwertyqwerty", email.getAttribute("value"));


        sendButton.click();


        Thread.sleep(5000);
    }

        @After
        public void endTest(){driver.quit();}
    }

