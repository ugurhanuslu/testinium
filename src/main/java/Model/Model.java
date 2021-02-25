package Model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Random;
//Targetted xpaths
public class Model {
    public static WebDriver driver;
    public double listPrice, basketPrice;
    public Model(WebDriver webDriver) {
        driver = webDriver;
    }
    public WebElement logInArea() {
        return driver.findElement(By.cssSelector(".gekhq4-5 > .gekhq4-6 path"));
    }
    public WebElement logInArea2() {return driver.findElement(By.xpath("//header[@id=\'main-header\']/div[3]/div/div/div/div[3]/div/div/div[2]/div/div/div/a"));}
    public WebElement username() {
        return driver.findElement(By.id("L-UserNameField"));
    }
    public WebElement password() {return driver.findElement(By.id("L-PasswordField"));}
    public WebElement logIn() {
        return driver.findElement(By.xpath("//*[@id=\"gg-login-enter\"]"));
    }
    public WebElement searchBar() {
        return driver.findElement(By.name("k"));
    }
    public WebElement searchButton() {
        return driver.findElement(By.cssSelector(".hKfdXF > span"));
    }
    public WebElement pageTwo() {
        return driver.findElement(By.xpath("//a[contains(text(),'2')]"));
    }
    public WebElement pageSelector() {
        return driver.findElement(By.cssSelector(".pt30"));
    }
    public WebElement listingPrice() {
        return driver.findElement(By.xpath("//*[@id=\"sp-price-highPrice\"]"));
    }
    public WebElement basketPrice() {return driver.findElement(By.xpath("//*[@id=\"submit-cart\"]/div/div[2]/div[3]/div/div[1]/div/div[5]/div[1]/div/ul/li[1]/div[2]"));}
    public WebElement addToBasket() {return driver.findElement(By.cssSelector("#add-to-basket"));}
    public WebElement addToBasket2(){return driver.findElement(By.cssSelector(".gg-d-12 > .gg-ui-btn-default")); }
    public WebElement deleteItem(){return driver.findElement(By.cssSelector(".row > .btn-delete > .gg-icon")); }
    public WebElement emptyText(){return driver.findElement(By.cssSelector("#empty-cart-container > div.gg-d-24 > div:nth-child(1) > div > div.gg-w-22.gg-d-22.gg-t-21.gg-m-18 > h2"));}
    public WebElement pieceTwo(){return driver.findElement(By.cssSelector("option:nth-child(2)"));}
    public WebElement pieceCount(){return driver.findElement(By.xpath("//*[@id=\"submit-cart\"]/div/div[2]/div[3]/div/div[1]/div/div[5]/div[1]/div/ul/li[1]/div[1]"));}
    public WebElement logInCheck(){return driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div/div[2]/text()"));}
//Generate random product id
    public WebElement product() {
        Random rand = new Random();
        int upperbound = 47;
        //generate random values from 1-48
        int random = rand.nextInt(upperbound) + 1;
        String key = String.valueOf(random);
        String randomXpath = "//li[" + key + "]/a/div/div/div/div/h3/span";

        return driver.findElement(By.xpath(randomXpath));
    }

    public double getListPrice() {
        return listPrice;
    }
    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }
    public double getBasketPrice() {
        return basketPrice;
    }
    public void setBasketPrice(double basketPrice) {
        this.basketPrice = basketPrice;
    }

}