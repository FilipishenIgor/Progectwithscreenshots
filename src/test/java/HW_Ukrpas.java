import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertEquals;

public class HW_Ukrpas {
    public static WebDriver driver;
    String ukrpas = "https://ukrpas.com.ua/uk";


    @BeforeSuite
    public void driverSetUp () {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.out.println("The setup process is completed");
    }

    @BeforeTest
    public void ukrpasSetup() {
        driver.manage().window().maximize();
        System.out.println("The Ukrpas process opening is completed");
    }

    @BeforeClass
    public void autoriaSetup() {
        driver.get(ukrpas);
        System.out.println("Ukrpas is opened");
    }

    @AfterSuite
    public void driverClose () {
        driver.quit();

    }

    @BeforeMethod
    public void mainPage() {
        System.out.println("Search of tickets - Main page opened");

    }

    @AfterMethod
    public static void myScreenshot() throws IOException {
        Date d = new Date();
        String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(".//screenshot//" + fileName));
        System.out.println("Screenshot is taken");
    }


    @AfterClass
    public void closeUp() {
        driver.close();
        System.out.println("The close_up process is completed");
    }

    @AfterTest
    public void report() {
        System.out.println("Report is ready");
    }

    @AfterSuite
    public void cleanUp() {
        System.out.println("All close up activities completed");
    }

    @BeforeGroups("SearchValidation")
    public void SearchSecurity() {
        System.out.println("Search validation test starting");
    }
    @AfterGroups("SearchValidation")
    public void tearDownSecurity() {
        System.out.println("Search validation test finished");
    }


    @Test (groups = "SearchValidation")
    public void Search_with_filter() throws InterruptedException, IOException {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("input[name='from']")).click();
        driver.findElement(By.cssSelector("input[name='from']")).sendKeys("Одес");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/form/div[1]/div[1]/ul/li[1]/div[1]")).click(); //выбор Одессы
        driver.findElement(By.cssSelector("input[name='to']")).click();
        driver.findElement(By.cssSelector("input[name='to']")).sendKeys("Киї");
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/form/div[1]/div[2]/ul/li[1]")).click(); //выбор Киева
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/form/div[2]/div[1]/input[2]")).click(); //нажимаем на поле с датой

        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div[2]/div/span[29]")).click(); //выбор даты
        driver.findElement(By.xpath("//option[contains(text(),'2 пасажира')]")).click(); //вібираем 2 пассажира
        driver.findElement(By.xpath("//button[contains(text(),'Знайти квитки')]")).click(); //нажимем кнопку найти билеты
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,600)", "");
        driver.findElement(By.xpath("//div[@id='search-results']//div[1]//div[1]//div[2]//div[1]//div[4]//button[1]")).click(); //нажимаем кнопку выбрать
        jse.executeScript("window.scrollBy(0,300)", "");
        driver.findElement(By.xpath("//button[contains(text(),'Далі')]")).click(); //нажимаем далее
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@id='firstname0']")).sendKeys("Igor");
        driver.findElement(By.xpath("//input[@id='lastname0']")).sendKeys("Filipishen");
        driver.findElement(By.xpath("//input[@id='firstname1']")).sendKeys("Valera");
        driver.findElement(By.xpath("//input[@id='lastname1']")).sendKeys("Na avtobus pora");
        driver.findElement(By.xpath("//input[@placeholder='Промокод']")).sendKeys("777888999");
        //driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("940000000"); //поля специально закоментил что была ошибка валидации обязательных полей
        //driver.findElement(By.xpath("//input[@id='email']")).sendKeys("filipishen@gmail.com"); //поля специально закоментил что была ошибка валидации обязательных полей
        driver.findElement(By.xpath("//button[contains(text(),'Далі')]")).click();
        myScreenshot();


    }

}
