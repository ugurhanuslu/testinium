import Model.Model;
import Model.Variables;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import static Model.Variables.log4jPath;
import static java.util.concurrent.TimeUnit.SECONDS;

public class GittiGidiyorAutomation {
    public static WebDriver driver;
    private static String baseUrl;
    private static Logger log = Logger.getLogger(GittiGidiyorAutomation.class);
    public static Model elementPage; //Modelimizin bulunduğu paket dosyası


    @Before
    public void setUp() throws Exception {
        PropertyConfigurator.configure(log4jPath);
        System.setProperty("webdriver.chrome.driver", Variables.chromePath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();


//Timeouts periods
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.manage().timeouts().pageLoadTimeout(200, SECONDS);
        elementPage = new Model(driver);
    }

    @Test
    public void gittiGidiyorAutomation() throws Exception {
        driver.get(Variables.baseUrl);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //check if page is loaded
        if (!js.executeScript("return document.readyState").toString().equals("complete")) {
            log.error("Page can not be loaded");
            return;
        } else {
            log.info("Main page loaded successfully");
        }
//Running test cases
        if (!logIn())
            return;

        if (!findProduct(js))
            return;

        if (!addToBasket())
            return;
    }

//Log In function
    public static boolean logIn() {
        try {
            elementPage.logInArea().click();
            elementPage.logInArea2().click();
            elementPage.username().clear();
            elementPage.username().sendKeys(Variables.email);
            elementPage.password().clear();
            elementPage.password().sendKeys(Variables.userPassword);
            elementPage.logIn().click();
            log.info("Log In Success");
            return true;
        } catch (Exception e) {
            log.error("Log In Failed");
            return false;
        }
    }

//Product search and moving next page function
    public static boolean findProduct(JavascriptExecutor js) {

        try {
            elementPage.searchBar().click();
            elementPage.searchBar().sendKeys("bilgisayar");
            elementPage.searchButton().click();
        } catch (Exception e) {
            log.error("Search page can't loading");
            return false;
        }

        js.executeScript("window.scrollBy(0,10000)");
        try {
            elementPage.pageSelector().click();
        } catch (Exception e) {
            log.error("Next pages bar can't loading");
            return false;
        }

        try {
            elementPage.pageTwo().click();
        } catch (Exception e) {
            log.error("Next page error");
            return false;
        }

        try {
            js.executeScript("window.scrollBy(0,10000)");
            Thread.sleep(2000);
            elementPage.product().click();
            elementPage.setListPrice(getDoublePrice(elementPage.listingPrice().getText()));
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        js.executeScript("window.scrollBy(0,300)");
        return true;
    }
//Add to basket, increasing the number of product and delete product function
    public static boolean addToBasket() throws InterruptedException, ParseException {
        try {
            elementPage.addToBasket().click();
            Thread.sleep(1000);
            elementPage.addToBasket2().click();
            driver.findElement(By.cssSelector(".gg-input-select > .amount")).click();
            elementPage.setBasketPrice(getDoublePrice(elementPage.basketPrice().getText()));
            log.info("Add basket success");
            try {
                checkPriceDiff();
                {
                    WebElement dropdown = driver.findElement(By.cssSelector(".gg-input-select > .amount"));
                    Thread.sleep(1000);
                    dropdown.findElements(By.xpath("//option[@value='2']"));
                    Thread.sleep(1000);
                }
            }catch (Exception e){
                log.error("There is no 2 piece");
            }
            elementPage.pieceTwo().click();
            Thread.sleep(1000);

            String pieceCheck = elementPage.pieceCount().getText();

            if (pieceCheck.equals("Ürün Toplamı (2 Adet)")) {
                log.info("Piece is correct");
            } else {
                log.info("Piece error");
            }
        } catch (Exception e) {
            log.error("Add basket failed.");
            return false;
        }

        elementPage.deleteItem().click();
        Thread.sleep(1000);
        boolean isEmpty = elementPage.emptyText().isDisplayed();
        if (isEmpty) {
            log.info("Basket is empty");
        } else {
            log.error("Basket is full");
        }
        return true;
    }
//Comparing product prices
    public static void checkPriceDiff() {
        double basketPrice, listPrice;
        listPrice = elementPage.getListPrice();
        basketPrice = elementPage.getBasketPrice();
        log.info("List Price = " + listPrice);
        log.info("Basket Price = " + basketPrice);
        if (basketPrice != listPrice) {
            System.out.println("Price different with basket = " + (listPrice - basketPrice));
            log.info("Price different with basket = " + (listPrice - basketPrice));
        } else {
            log.info("There are no differences between prices");
        }
    }
//Editing the product price information from String as double
    public static double getDoublePrice(String price) throws ParseException {
        price = (price.substring(0, price.length() - 3)); //Only reaching numerical value by deleting 3 characters from the end
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        Number number = format.parse(price);
        double d = number.doubleValue();
        return d;
    }
}

