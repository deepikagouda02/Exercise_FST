package fst_AssmntExercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ComputerDatabase {

	public WebDriver driver;
	
	@BeforeTest
	public void launchApp() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("https://computer-database.gatling.io/computers");

		Thread.sleep(2000);
		}

					
		@AfterTest
		public void closeApp() throws Exception {

			Thread.sleep(4000);
			driver.close();
			System.out.println("closing app.....");
		}

		
		@Test
		public void computerDatabase() throws Exception {
			
			//Verify the title as Computers database
			String title = driver.getTitle();
			System.out.println("Title of the page is: " + title);
			Assert.assertEquals(title, "Computers database");

			//Verify the page header is the same as the page title
			String pageHeader = driver.findElement(By.xpath("//a[text() = 'Computer database']")).getText();
			System.out.println("Page Header is: " + pageHeader);
			Assert.assertEquals(pageHeader, "Computer database");
			

		//User must see the filter by computer name text box
		boolean btn_FilterFound = driver.findElement(By.xpath("//input[@id = 'searchbox']")).isDisplayed();
		System.out.println("Search filter is displayed: " + btn_FilterFound);
		Assert.assertEquals(btn_FilterFound, true);

		//User able to see add a new computer button
		boolean btn_AddFound = driver.findElement(By.xpath("//a[@id = 'add']")).isDisplayed();
		System.out.println("Add Computer button is displayed: " + btn_AddFound);
		Assert.assertEquals(btn_AddFound, true);

		//User able to see the filter by name button
		boolean btn_FilterByNameFound = driver.findElement(By.xpath("//input[@id = 'searchsubmit']")).isDisplayed();
		System.out.println("Filter by name button is displayed: " + btn_FilterByNameFound);
		Assert.assertEquals(btn_FilterByNameFound, true);

		//User able to see the table and the headers as follows
		// ---Computer name
		String str_Comp = driver.findElement(By.xpath("(//th//a)[1]")).getText();
		System.out.println("Column-1 is: " + str_Comp);
		Assert.assertEquals(str_Comp, "Computer name");
		
		// ---Introduced
		String str_Intro = driver.findElement(By.xpath("(//th//a)[2]")).getText();
		System.out.println("Column-2 is: " + str_Intro);
		Assert.assertEquals(str_Intro, "Introduced");
		
		// ----Discontinued
		String str_Disc = driver.findElement(By.xpath("(//th//a)[3]")).getText();
		System.out.println("Column-3 is: " + str_Disc);
		Assert.assertEquals(str_Disc, "Discontinued");
		
		// ----Company
		String str_Company = driver.findElement(By.xpath("(//th//a)[4]")).getText();
		System.out.println("Column-4 is: " + str_Company);
		Assert.assertEquals(str_Company, "Company");
		

		//The user should see the pagination
		boolean btn_Pagination = driver.findElement(By.xpath("//a[text() = 'Displaying 1 to 10 of 574']")).isDisplayed();
		System.out.println("Pagination Exists: " + btn_Pagination);
		Assert.assertEquals(btn_Pagination, true);
		
		//Add a new computer
		//Click on create this computer
		driver.findElement(By.xpath("//a[@id = 'add']")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@class = 'btn primary']")).click();
		Thread.sleep(3000);
		
		//User should see the red background on the computer name field
		//--Failed to refine type : Predicate isEmpty() did not fail.
		boolean blnErrorFound = driver.findElement(By.xpath("//span[text() = 'Failed to refine type : Predicate isEmpty() did not fail.']")).isDisplayed();
		System.out.println("error msg:'Failed to refine type : Predicate isEmpty() did not fail.'  Exists - " + blnErrorFound);
		Assert.assertEquals(blnErrorFound, true);
		
		//Enter the computer name
		driver.findElement(By.xpath("//input[@id = 'name']")).sendKeys("ABCTEST");
		
		//Select the company as Nokia
		WebElement dropDown = driver.findElement(By.xpath("//select[@id = 'company']"));
		Select s = new Select(dropDown);
		s.selectByVisibleText("Nokia");
		Thread.sleep(2000);
		//Submit the form
		driver.findElement(By.xpath("//input[@class = 'btn primary']")).click();
		Thread.sleep(3000);
		
		//Successful message should be displayed
		String successMsg = driver.findElement(By.xpath("//div[@class = 'alert-message warning']")).getText();
		System.out.println("Success message is: "+successMsg);
		Assert.assertEquals(successMsg, "Done ! Computer ABCTEST has been created");
		Thread.sleep(2000);
		
		//Search the created data
		//result should be visible (defect)
		driver.findElement(By.xpath("//input[@id = 'searchbox']")).sendKeys("ABCTEST");
		driver.findElement(By.xpath("//input[@id = 'searchsubmit']")).click();
		Thread.sleep(2000);
		
		if(driver.findElement(By.xpath("//em[text() = 'Nothing to display']")).isDisplayed()) {
			System.out.println("No results displayed - It is a defect");
		}else {
			System.out.println("Result displayed as expected");
		}
	}
		
}

	

