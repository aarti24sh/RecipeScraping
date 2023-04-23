package com.recipescraping.utilities;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.recipescraping.testcases.BaseClass;

public class ExcelRead extends BaseClass {

	public void DatasToExcel(String ExcelSheetName,int ArrayListCounter) throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(ExcelSheetName);
		
		XSSFFont font= workbook.createFont();
		CellStyle style=null;
		font.setBold(true);
		
		sheet.createRow(0);
		sheet.getColumnStyle(0).setFont(font);
		sheet.getRow(0).createCell(0).setCellValue("S.No");
		sheet.getRow(0).createCell(1).setCellValue("RecipeId");
		sheet.getRow(0).createCell(2).setCellValue("Recipe Name");
		sheet.getRow(0).createCell(3).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
		sheet.getRow(0).createCell(4).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
		sheet.getRow(0).createCell(5).setCellValue("Ingredients");
		sheet.getRow(0).createCell(6).setCellValue("Preparation Time");
		sheet.getRow(0).createCell(7).setCellValue("Cooking Time");
		sheet.getRow(0).createCell(8).setCellValue("Preparation method");
		sheet.getRow(0).createCell(9).setCellValue("Nutrient values");
		sheet.getRow(0).createCell(10).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		sheet.getRow(0).createCell(11).setCellValue("Recipe URL");
		
		int rowno=1;

		for(int i = 1;i<ArrayListCounter; i++)
		{
			XSSFRow row=sheet.createRow(rowno++);
			
			
					row.createCell(0).setCellValue(i);
					row.createCell(1).setCellValue(ReceipeIdList.get(i));
					row.createCell(2).setCellValue(ReceipeNameList.get(i));
					row.createCell(3).setCellValue(ReceipeCategory.get(i));
					row.createCell(4).setCellValue("Veg");
					row.createCell(5).setCellValue(ReceipeIngredient.get(i));
					row.createCell(6).setCellValue(ReceipePreparationTime.get(i));
					row.createCell(7).setCellValue(ReceipeCookingTime.get(i));
					row.createCell(8).setCellValue(ReceipeMethod.get(i));
					row.createCell(9).setCellValue(ReceipeNutrition.get(i));
					row.createCell(10).setCellValue(ReceipeMorbidCondition.get(i));
					row.createCell(11).setCellValue(ReceipeUrlList.get(i));
					
			
			
			
			
		}
		
		FileOutputStream FOS = new FileOutputStream(".\\src\\test\\resources\\Punjabi_ReceipeDetails.xlsx");
		workbook.write(FOS);
		FOS.close();
		
	}

}
