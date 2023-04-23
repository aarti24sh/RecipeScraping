package com.recipescraping.testcases;

import java.io.IOException;
import java.sql.Driver;

import org.testng.annotations.Test;

import com.recipescraping.pageobjects.ChinesePage;
import com.recipescraping.utilities.ExcelRead;

public class TC_Chineese extends BaseClass {
	
	   ChinesePage cp=new ChinesePage();
	   @Test
	   public void Finalsetup() throws InterruptedException, IOException  {
		   MouseHoverReceipes();
		   MouseHoveCusine();
		   cp.ChineseCusineName("Chinese");
		   DiabeticsAdd();
		   DiabeticsFilter();
		   ReceipeCategory();
		   HypothyroidismEliminate();
		   HypothyroidismAdd();
		   ReceipeList();
		   ExcelRead excel=new ExcelRead();
		   excel.DatasToExcel("ChineseName",1);
		   tearDown();
		   
		   
	   }
	   
	   
	   
	

}
