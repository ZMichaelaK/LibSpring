package com.qa.lib.selenium;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
//DO NOT CALL IT data.sql/schema.sql
@Sql(scripts = { "classpath:person-schema.sql", "classpath:person-data.sql","classpath:item-schema.sql", "classpath:item-data.sql"},

	executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)

// DO NOT CALL IT data.sql/schema.sql
@Sql(scripts = { "classpath:person-schema.sql",
		"classpath:person-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class SeleniumTest {

	private RemoteWebDriver driver;

	@LocalServerPort
	private int port;

	@BeforeEach
	void init() {
		this.driver = new ChromeDriver();
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

    @Test
    @Order(2)
    void testCreateCustomer() {
        this.driver.get("http://localhost:" + this.port);
        String customer = "Barry";
        WebElement name = this.driver.findElement(
                By.cssSelector("#root > main > div > section:nth-child(1) > div:nth-child(2) > form > div > input"));
        name.sendKeys(customer);

        WebElement register = this.driver.findElement(By.cssSelector("#button-addon2"));
        register.click();

        WebElement created = this.driver.findElement(
                By.cssSelector("#root > main > div > section:nth-child(1) > div:nth-child(3) > h3:nth-child(2)"));
        Assertions.assertEquals(customer, created.getText());
    }
	@Test
	@Order(1)
	void testGetCustomer() {
		this.driver.get("http://localhost:" + this.port);

		WebElement created = this.driver
				.findElement(By.cssSelector("#root > main > div > section:nth-child(1) > div:nth-child(3) > h3"));
		Assertions.assertEquals("Piers", created.getText());
	}
	@Test
	@Order(3)
	void testItemTypeAndItemName() {
	    this.driver.get("http://localhost:" + this.port);
	    
	    String itemType = "Harry Potter and the Philosophers Stone";
	    WebElement testItemType = this.driver.findElement(
	            By.cssSelector("#root > main > div > section:nth-child(2) > div:nth-child(2) > form > div > input:nth-child(1)"));
	    testItemType.sendKeys(itemType);
	    
	    String itemName = "Best Seller Book";
	    WebElement testItemName = this.driver.findElement(
	            By.cssSelector("#root > main > div > section:nth-child(2) > div:nth-child(2) > form > div > input:nth-child(2)"));
	    testItemName.sendKeys(itemName);
	    
	    WebElement register = this.driver.findElement(By.cssSelector("#root > main > div > section:nth-child(2) > div:nth-child(2) > form > div > button"));
	    register.click();
	    
	    WebElement created = this.driver.findElement(By.cssSelector("#root > main > div > section:nth-child(2) > div:nth-child(3) > h3"));
	    Assertions.assertEquals(itemName + " (" + itemType + ")", created.getText());
	}
}