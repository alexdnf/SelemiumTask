package ru.netology.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardFormTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendCorrectForm() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Иванов Александр");
        elements.get(1).sendKeys("+79999999999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void wrongNameLatinTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Ivanov Alexandr");
        elements.get(1).sendKeys("+79999999999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void wrongNameNumbersTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("15456 88789");
        elements.get(1).sendKeys("+79999999999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void wrongNameSymbolsTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Иванов Алексан:др");
        elements.get(1).sendKeys("+79999999999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void DoubleSurnameTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Иванов-Петров Александр");
        elements.get(1).sendKeys("+79999999999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    @Test
    public void wrongPhoneNumberLettersTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Иванов Александр");
        elements.get(1).sendKeys("Иванов Александр");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();


        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void wrongPhoneNumberLessCountTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Иванов Александр");
        elements.get(1).sendKeys("+7999999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();


        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void wrongPhoneNumberSymbolsTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Иванов Александр");
        elements.get(1).sendKeys("+7999999,999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();


        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void nameIsNullTest() {
    List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(1).sendKeys("+79999999999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
    String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();

    assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void phoneIsNullTest()  {
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Иванов Александр");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }
}


