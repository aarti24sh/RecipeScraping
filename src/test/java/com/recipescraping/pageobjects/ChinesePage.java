package com.recipescraping.pageobjects;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.recipescraping.testcases.BaseClass;


public class ChinesePage extends BaseClass{
	public void ChineseCusineName(String CuisineName) 
	{
		WebElement PunjabiLink = driver.findElement(By.xpath("(//a[contains(text(), '"+CuisineName+"')])[1]"));
		
		Actions action = new Actions(driver);
		action.moveToElement(PunjabiLink).perform();
		
		PunjabiLink.click();
	}
	





}
