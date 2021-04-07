import org.asynchttpclient.util.Assertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class Qwerty {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/Java/Selenium/chromedriver.exe");
        WebDriver cd = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(cd, 10);
        cd.get("https://tempmail.plus/ru/"); //1

        cd.findElement(By.cssSelector("[data-tr=\"new_random_name\"]")).click(); //2


        cd.findElement(By.id("domain")).click();
        cd.findElement(By.xpath("/html/body/div[8]/div[1]/div[2]/div[1]/form/div/div[2]/div/button[6]")).click(); //3

        String part1 = cd.findElement(By.cssSelector("#pre_button")).getAttribute("value");
        String part2 = cd.findElement(By.cssSelector("#domain")).getAttribute("textContent");
        String email = part1 + part2;//4

        cd.findElement(By.id("pre_settings")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/form/div[3]/div[1]/div[2]/label[1]"))).click();
//        wd.findElement(By.xpath("//[@id=\"modal-settings\"]/div/form/div[3]/div[2]/input")).click();

//        wd.findElement(By.id("pre_settings")).click();
        String secretAddress = cd.findElement(By.cssSelector("#secret-address")).getAttribute("textContent");
        cd.findElement(By.cssSelector("#modal-settings > div > form > div.modal-header > div > button")).click(); // 6

        Assert.assertTrue(cd.findElement(By.cssSelector("#email > h2")).isDisplayed()); //7 покажи что отображается главная страница

        Assert.assertTrue(cd.getPageSource().contains("В ожидании новых писем...")); // 8

        cd.findElement(By.cssSelector("#compose")).click(); //9

        WebElement sendButton = cd.findElement(By.cssSelector("#submit"));
        wait.until(ExpectedConditions.visibilityOf(sendButton));
        Assert.assertTrue(sendButton.isDisplayed());
        cd.findElement(By.cssSelector("#to")).sendKeys(email);
        cd.findElement(By.cssSelector("#subject")).sendKeys("Test");
        cd.findElement(By.cssSelector("#text")).sendKeys(secretAddress); //10

        cd.findElement(By.cssSelector("#submit")).click(); //11

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#container-body > div > div.inbox > div.mail")));
        cd.findElement(By.cssSelector("#container-body > div > div.inbox > div.mail")).click();   //12
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#info > div.row.row-info.no-gutters > div.col.d-flex.mb-10")));
        String adrEmail = cd.findElement(By.cssSelector("#info > div.row.row-info.no-gutters > div.col.d-flex.mb-10")).getAttribute("textContent");
        Assert.assertEquals(email, adrEmail);
        String topic = cd.findElement(By.cssSelector("#info > div.subject.mb-20")).getAttribute("textContent");
        Assert.assertEquals("Test",topic);
        String secret = cd.findElement(By.cssSelector("#info > div.overflow-auto.mb-20")).getAttribute("textContent");
        Assert.assertEquals(secretAddress, secret); // 13
        cd.findElement(By.cssSelector("#reply")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#form > div.modal-header.shadow-sm > div.bar > span")));
        cd.findElement(By.cssSelector("#text")).sendKeys("Test2");
        cd.findElement(By.cssSelector("#submit")).click();//14
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#inbox-head > p")));//15 start
        cd.findElement(By.cssSelector("#back > span:nth-child(2)")).click();//15 finish
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#container-body > div > div.inbox > div:nth-child(2) > div > div.from.col-9.col-md-4 > img")));
        cd.findElement(By.cssSelector("#container-body > div > div.inbox > div:nth-child(2) > div > div.subj.col-12.col-md-7.px-md-3")).click();//16 finish
        String topic2=cd.findElement(By.cssSelector("#info > div.overflow-auto.mb-20")).getAttribute("textContent");//17start
        Assert.assertEquals("Test2", topic2);//17 fin
        cd.findElement(By.cssSelector("#delete_mail > span:nth-child(2)")).click();//18 start
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#modal-destroy-mail > div > div > div > h5")));
        cd.findElement(By.cssSelector("#confirm_mail")).click();//18fin
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#inbox-head > p")));
        Assert.assertFalse(cd.getPageSource().contains("Re: Test"));
        cd.quit();
    }
}
