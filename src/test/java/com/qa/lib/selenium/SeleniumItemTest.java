package com.qa.lib.selenium;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
@Sql(scripts = { "classpath:library-schema.sql",
		"classpath:library-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class SeleniumItemTest {

	@LocalServerPort
	private int port;

	private RemoteWebDriver driver;

	@BeforeEach
	void init() {
		this.driver = new ChromeDriver();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test
	void testGet() {
		this.driver.get("http://localhost:" + this.port);

		WebElement item = this.driver
				.findElement(By.cssSelector("#root > main > div > section:nth-child(2) > div:nth-child(3) > h3"));

		Assertions.assertEquals("lotr (film)", item.getText());
	}

	@Test
	void testPost() {
		this.driver.get("http://localhost:" + this.port);

		WebElement type = this.driver.findElement(By.cssSelector(
				"#root > main > div > section:nth-child(2) > div:nth-child(2) > form > div > input:nth-child(1)"));

		type.sendKeys("book");

		WebElement name = this.driver.findElement(By.cssSelector(
				"#root > main > div > section:nth-child(2) > div:nth-child(2) > form > div > input:nth-child(2)"));

		name.sendKeys("lotr");

		WebElement submit = this.driver.findElement(
				By.cssSelector("#root > main > div > section:nth-child(2) > div:nth-child(2) > form > div > button"));
		submit.click();

		WebElement item = this.driver.findElement(
				By.cssSelector("#root > main > div > section:nth-child(2) > div:nth-child(3) > h3:nth-child(2)"));

		Assertions.assertEquals("lotr (book)", item.getText());
	}

	@AfterEach
	void tearDown() {
		this.driver.quit();
	}

}