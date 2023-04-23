package com.recipescraping.testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.recipescraping.utilities.ReadConfig;

public class BaseClass {
	ReadConfig readconfig= new ReadConfig();
	public String baseURL=readconfig.getURL();
	public static WebDriver driver;
	public static int counter=1;
	public HashMap<Integer , String> ReceipeIdList= new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeNameList = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeIngredient = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipePreparationTime = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeCookingTime = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeMethod = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeNutrition = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeUrlList = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeMorbidCondition = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeCategory = new HashMap<Integer , String>();
	public HashMap<Integer , String> DiabeticsFilterList = new HashMap<Integer , String>();
	public HashMap<Integer , String> DiabeticsAddList = new HashMap<Integer , String>();
	public HashMap<Integer , String> ReceipeCategoryList = new HashMap<Integer , String>();
	public HashMap<Integer , String> HypothyroidismElimninate = new HashMap<Integer , String>();
	 public HashMap<Integer , String> HypothyroidismAdd = new HashMap<Integer , String>();
	
	
	
	
	
		
	@Parameters("browser")
	@BeforeClass
	public void setup(String chrome) {
		
		//System.setProperty("webdriver.chrome.driver", "RecipeScrapingHackathon\\Driver\\chromedriver.exe");
		ChromeOptions options= new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
	    WebDriver driver=new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(baseURL);
			
	}
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	public void ReceipeList() throws InterruptedException 
	{
		
				
		int ArrayListCounter = 0;
		
		List<WebElement> NoOfPages = driver.findElements(By.xpath("//div[@id='pagination']//a"));
		int TotalPages = NoOfPages.size();
		
		WebElement LastPageNoString = driver.findElement(By.xpath("//div[@id='pagination']//a["+TotalPages+"]"));
		int LastPageNo = Integer.parseInt(LastPageNoString.getText());
		System.out.println(LastPageNo);
		
		for (int j=1;j<=1;j++)
		{
			WebElement NextPageLink = driver.findElement(By.xpath("//div[@id='pagination']//a[text()='"+j+"']"));
			NextPageLink.click();
			
			List<WebElement> ReceipeList = driver.findElements(By.xpath("//div[@class='recipelist']//article"));
			int ReceipeListCount = ReceipeList.size();
			
			for (int i=counter; i<=2; i++)
			{				
				WebElement ReceipeName = driver.findElement(By.xpath("(//div[@class='recipelist']//article)["+i+"]/div[@class='rcc_rcpcore']/span/a"));
				ReceipeNameList.put(i, ReceipeName.getText());
				
				WebElement ReceipeId = driver.findElement(By.xpath("(//div[@class='recipelist']//article)["+i+"]/div[@class='rcc_rcpno']/span"));
				String ReceiptIdSubstring = ReceipeId.getText();
				String FinalReceiptId = ReceiptIdSubstring.split("\\n")[0].split(" ")[1];
				ReceipeIdList.put(i, FinalReceiptId);
				
				ReceipeName.click();
				
				String ReceipeUrl = driver.getCurrentUrl();
				ReceipeUrlList.put(i, ReceipeUrl);
				
				List<WebElement> IngredientsList = driver.findElements(By.xpath("//span[contains(text(),'Ingredients')]/following::div[@id='rcpinglist']/div/span"));
				int IngredientListcount = IngredientsList.size();
				
				String FinalIngredient = "";
				String FinalMethod = "";
				String FinalNutrition = "";
				
				for (int k=1;k<=IngredientListcount;k++)
				{
					WebElement IngredientListOnebyOne = driver.findElement(By.xpath("//span[contains(text(),'Ingredients')]/following::div[@id='rcpinglist']/div/span["+k+"]"));
					FinalIngredient += IngredientListOnebyOne.getText();					
				}
				ReceipeIngredient.put(i, FinalIngredient);
				
String Morbid="";
				
				for (int k=1;k<=DiabeticsFilterList.size();k++)
				{
					System.out.println(DiabeticsFilterList.get(k));
					if (FinalIngredient.contains(DiabeticsFilterList.get(k)))
					{
						Morbid="Restricted for Diabetics";
					}					
				}
				
				for (int k=1;k<=DiabeticsAddList.size();k++)
				{
					if (FinalIngredient.contains(DiabeticsAddList.get(k)))
					{
						Morbid  = Morbid + "/" + "No Restriction for Diabetics";
					}					
				}
				
				
				
				if (Morbid == "")
				{
					Morbid = "Nil";
				}
				
				ReceipeMorbidCondition.put(i,  Morbid);
				
				WebElement ReceipeTags = driver.findElement(By.xpath("//div[@id='recipe_tags']/following::p[1]"));
				String PrepartionTime = ReceipeTags.getText().split("Preparation Time:")[1].split("Cooking Time:")[0];
				String CookingTime = ReceipeTags.getText().split("Preparation Time:")[1].split("Cooking Time:")[1].split("Total")[0];
				
				ReceipePreparationTime.put(i, PrepartionTime);
				ReceipeCookingTime.put(i, CookingTime);
				
//				List<WebElement> Method = driver.findElements(By.xpath("(//ol[@class='rcpprocsteps'])[1]/li"));
//				int MethodCount = Method.size();
//				
//				for (int k=1;k<=MethodCount;k++)
//				{
//					WebElement MethodOneByOne = driver.findElement(By.xpath("(//ol[@class='rcpprocsteps'])[1]/li["+k+"]"));
//					FinalMethod += MethodOneByOne.getText();					
//				}
				
				WebElement Method = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
	        	ReceipeMethod.put(i, Method.getText());
				
				List<WebElement> NutritionTableRows = driver.findElements(By.xpath("//table[@id='rcpnutrients']/tbody/tr"));
				int NutritionRowsCount = NutritionTableRows.size();
				
				for (int k=1;k<=NutritionRowsCount;k++)
				{
					List<WebElement> NutritionTableColumns = driver.findElements(By.xpath("//table[@id='rcpnutrients']/tbody/tr["+k+"]/td"));
					int NutritionColumnsCount = NutritionTableColumns.size();
					for (int c=1;c<=NutritionColumnsCount;c++)
					{
						if (c==1)
						{
							WebElement NutritionTableData1=driver.findElement(By.xpath("//table[@id='rcpnutrients']/tbody/tr["+k+"]/td["+c+"]"));
							FinalNutrition += NutritionTableData1.getText();
						}
						else if (c==2)
						{
							WebElement NutritionTableData2;
							List<WebElement> NutritionTableDataSpanCheck = driver.findElements(By.xpath("//table[@id='rcpnutrients']/tbody/tr["+k+"]/td["+c+"]/span"));
							if (NutritionTableDataSpanCheck.size()!=0)
							{
								NutritionTableData2=driver.findElement(By.xpath("//table[@id='rcpnutrients']/tbody/tr["+k+"]/td["+c+"]/span"));
								FinalNutrition = FinalNutrition + " " + NutritionTableData2.getText();	
							}
							else
							{
								NutritionTableData2=driver.findElement(By.xpath("//table[@id='rcpnutrients']/tbody/tr["+k+"]/td["+c+"]"));
								FinalNutrition = FinalNutrition + " " + NutritionTableData2.getText();
							}							
						}						
					}					
				}
				
				ReceipeNutrition.put(i, FinalNutrition);
				
				//WebElement ReceipeCategoryTags = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
				//String ReceipeTagsCollection = ReceipeCategoryTags.getText();
				
				List<WebElement> ReceipeCategoryTags = driver.findElements(By.xpath("//div[@id='recipe_tags']/a"));
				int ReceipeCategoryTagsCount = ReceipeCategoryTags.size();
				
				String FinalReceipeCategory = "";
				int ReceipeCategoryPosition = 0;
				
				for (int k=1;k<=ReceipeCategoryTagsCount;k++)
				{
					WebElement ReceipeCategoryOnebyOne = driver.findElement(By.xpath("//div[@id='recipe_tags']/a["+k+"]"));
					
					for (int l=1; l<=ReceipeCategoryList.size();l++)
					{
						if (ReceipeCategoryOnebyOne.getText().contains(ReceipeCategoryList.get(l)))
						{
							ReceipeCategoryPosition = l;							
						}
					}					
				}
				
				if (ReceipeCategoryPosition==0)
				{
					ReceipeCategory.put(i, "Other");
				}
				else
				{
					ReceipeCategory.put(i, ReceipeCategoryList.get(ReceipeCategoryPosition));
				}
				
				ArrayListCounter+=1;	
				
				driver.navigate().back();
				Thread.sleep(1000);
			}			
			
			
			
		}		
		
	}
	public void ReceipeCategory()
	{
		ReceipeCategoryList.put(1, "Breakfast");
		ReceipeCategoryList.put(2, "Lunch");
		ReceipeCategoryList.put(3, "Snack");
		ReceipeCategoryList.put(4, "Dinner");
	}
	public void DiabeticsFilter()
	{
		
		DiabeticsFilterList.put(1, "Cream");
		DiabeticsFilterList.put(2, "Sugar");
		DiabeticsFilterList.put(3, "White Rice");
		DiabeticsFilterList.put(4, "White Bread");
		DiabeticsFilterList.put(5, "Sugar");
		DiabeticsFilterList.put(6, "Pasta");
		DiabeticsFilterList.put(7, "Milk");
		DiabeticsFilterList.put(8, "Rice Flour");
		DiabeticsFilterList.put(9, "Rice rava");
		DiabeticsFilterList.put(10, "corn");
		DiabeticsFilterList.put(11, "soda");
		DiabeticsFilterList.put(12, "flavoured water");
		DiabeticsFilterList.put(13, "Gatorade");
		DiabeticsFilterList.put(14, "Apple Juice");
		DiabeticsFilterList.put(15, "Orange Juice");
		DiabeticsFilterList.put(16, "Pomegranate Juice");
		DiabeticsFilterList.put(17, "Peanut butter");
		DiabeticsFilterList.put(18, "spreads");
		DiabeticsFilterList.put(19, "Flavoured curd");
		DiabeticsFilterList.put(20, "Corn flakes");
		DiabeticsFilterList.put(21, "puffed rice");
		DiabeticsFilterList.put(22, "Honey");
		DiabeticsFilterList.put(23, "Maple syrup"); 
		DiabeticsFilterList.put(24, "Jaggery");
		DiabeticsFilterList.put(25, "choclates");
		DiabeticsFilterList.put(26, "Alcoholic");
		DiabeticsFilterList.put(27, "All purpose flour");
		DiabeticsFilterList.put(28, "chicken nuggets");
		DiabeticsFilterList.put(29, "chicken patties");
		DiabeticsFilterList.put(30, "bacon");
		DiabeticsFilterList.put(31, "jams");
		DiabeticsFilterList.put(32, "jelly");
		DiabeticsFilterList.put(33, "mango");
		DiabeticsFilterList.put(34, "cucumber");
		DiabeticsFilterList.put(35, "Canned fruits/vegetables -  pineapple, peaches, mangos, pear, mixed fruit, mandarine oranges, cherries");
		DiabeticsFilterList.put(36, "Mayonnaise");
		DiabeticsFilterList.put(37, "butter");		 		
	}
	public void HypothyroidismEliminate()
	{
		HypothyroidismElimninate.put(1, "Tofu");
		HypothyroidismElimninate.put(2, "Edamame");
		HypothyroidismElimninate.put(3, "Cauliflower");
		HypothyroidismElimninate.put(4, "Cabbage");
		HypothyroidismElimninate.put(5, "Broccoli");
		HypothyroidismElimninate.put(6, "Kale");
		HypothyroidismElimninate.put(7, "Spinach");
		HypothyroidismElimninate.put(8, "Sweet Potatoes");
		HypothyroidismElimninate.put(9, "Strawberries");
		HypothyroidismElimninate.put(10, "Pine nuts");
		HypothyroidismElimninate.put(11, "Peanuts");
		HypothyroidismElimninate.put(12, "Peaches");
		HypothyroidismElimninate.put(13, "Green Tea");
		HypothyroidismElimninate.put(14, "Coffee");
		HypothyroidismElimninate.put(15, "Alcohol");
		HypothyroidismElimninate.put(16, "Soy milk");
		HypothyroidismElimninate.put(17, "White Bread");
		HypothyroidismElimninate.put(18, "Cakes Pastries");
		HypothyroidismElimninate.put(19, "Fried Food");
		HypothyroidismElimninate.put(20, "Sugar");
		HypothyroidismElimninate.put(21, "Processed food-ham, Bacon, Salami, Sausages");
		HypothyroidismElimninate.put(22, "Frozen Food");
		HypothyroidismElimninate.put(23, "Gluten");
		HypothyroidismElimninate.put(24, "Sodas");
		HypothyroidismElimninate.put(25, "Noodles");
		HypothyroidismElimninate.put(26, "Salad Dressing");
		HypothyroidismElimninate.put(27, "Candies");
	}
	public void MouseHoverReceipes() 
	{
		
		WebElement ReceipesLink = driver.findElement(By.xpath("//div[contains(text(), 'RECIPES')]"));
		
		Actions action = new Actions(driver);
		action.moveToElement(ReceipesLink).perform();
	}
	
	public void MouseHoveCusine() 
	{	
		//Thread.sleep(1000);
		WebElement CuisineLink = driver.findElement(By.xpath("//a[contains(text(), 'Cuisine')]"));
		
		Actions action = new Actions(driver);
		action.moveToElement(CuisineLink).perform();
	
	

	}
	public void DiabeticsAdd()
	{
		DiabeticsAddList.put(1, "Brocoli");
		DiabeticsAddList.put(2, "Pumpkin");
		DiabeticsAddList.put(3, "Pumpkin Seeds");
		DiabeticsAddList.put(4, "Apples");
		DiabeticsAddList.put(5, "Nuts");
		DiabeticsAddList.put(6, "Ladies Finger");
		DiabeticsAddList.put(7, "Okra");
		DiabeticsAddList.put(8, "Beans");
		DiabeticsAddList.put(9, "Raspberries");
		DiabeticsAddList.put(10, "Strawberries");
		DiabeticsAddList.put(11, "Blueberries");
		DiabeticsAddList.put(12, "Blackberries");
		DiabeticsAddList.put(13, "Eggs");
		DiabeticsAddList.put(14, "Bitter Guard");
		DiabeticsAddList.put(15, "Rolled Oats");
		DiabeticsAddList.put(16, "Chicken");
		DiabeticsAddList.put(17, "Fish");
		DiabeticsAddList.put(18, "Quinoa");
		DiabeticsAddList.put(19, "Mushroom");
	}


public void HypothyroidismAdd() 
	{
		HypothyroidismAdd.put(1, "Oyesters");
		HypothyroidismAdd.put(2, "Eggs");
		HypothyroidismAdd.put(3, "Dairy");
		HypothyroidismAdd.put(4, "Nuts");
		HypothyroidismAdd.put(5, "Chicken");
		HypothyroidismAdd.put(6, "Pumpkin Seeds");
		HypothyroidismAdd.put(7, "Seaweed");
		HypothyroidismAdd.put(8, "Iodized salt");
		HypothyroidismAdd.put(9, "Brazil nuts");
		HypothyroidismAdd.put(10, "Blueberries");
		HypothyroidismAdd.put(11, "Quinoa");
		HypothyroidismAdd.put(12, "Mushroom");
		HypothyroidismAdd.put(13, "Celery");
		HypothyroidismAdd.put(14, "Kiwi");
		HypothyroidismAdd.put(15, "Dark Choclate");
		HypothyroidismAdd.put(16, "Watermelon");
		HypothyroidismAdd.put(17, "Spinach");
		HypothyroidismAdd.put(18, "Cabbage");
		HypothyroidismAdd.put(19, "Romaine Lettuce");
		HypothyroidismAdd.put(20, "Mustard Greens");
		HypothyroidismAdd.put(21, "Broccoli");
		HypothyroidismAdd.put(22, "Argula");
		HypothyroidismAdd.put(23, "Garlic");
		HypothyroidismAdd.put(24, "Pomegranate");
		HypothyroidismAdd.put(25, "Cinnamon");
		HypothyroidismAdd.put(26, "Pistachios");
		HypothyroidismAdd.put(27, "Chia seeds");
		HypothyroidismAdd.put(28, "Yogurt");
		HypothyroidismAdd.put(29, "Unsalted nuts");
		HypothyroidismAdd.put(30, "Fish, Turkey");		
	}
}
