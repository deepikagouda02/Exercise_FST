package fst_AssmntExercises;

import java.util.Arrays;
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

public class SauceLabTest{
	
	public WebDriver driver;
	
	@BeforeTest
	public void launchApp() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");


		//Verify the title as Swag Labs
		String title = driver.getTitle();
		System.out.println("Page Title is....."+title);
		Assert.assertEquals("Swag Labs", title);
		
		Thread.sleep(3000);
		
	}
	
	@AfterTest
	public void closeApp() throws Exception {

		Thread.sleep(4000);
		driver.close();
		System.out.println("closing app.....");
	}
	

		@Test
		public void sauceDemo() throws InterruptedException {
				
		//Verify the title as Swag Labs
		String title = driver.getTitle();
		System.out.println("Page Title is....."+title);
		Assert.assertEquals("Swag Labs", title);

		//Verify the login button text is capitalized
		WebElement btnLogin = driver.findElement(By.id("login-button"));
		String Txt_loginText = btnLogin.getAttribute("value");
		System.out.println("login btn text is : "+Txt_loginText.trim().toUpperCase());
		Assert.assertEquals(Txt_loginText.trim().toUpperCase(), "LOGIN");

		//Login with standard_user & secret_sauce
		driver.findElement(By.xpath("//input[@id = 'user-name']")).sendKeys("standard_user");
		driver.findElement(By.xpath("//input[@id = 'password']")).sendKeys("secret_sauce");

		btnLogin.click();
		Thread.sleep(5000);

		//Verify default filter dropdown is A-Z
		String defaultdrpdwnFilter = driver.findElement(By.xpath("//span[@class = 'active_option']")).getText();
		System.out.println("default drpdwn filter is - "+defaultdrpdwnFilter);
		Assert.assertEquals(defaultdrpdwnFilter, "NAME (A TO Z)");

		//Add the first product to the cart
		driver.findElement(By.xpath("(//button[@class = 'btn btn_primary btn_small btn_inventory'])[1]")).click();
		Thread.sleep(3000);

		//Verify the cart badge has 1 product
		String cartBadgeNo = driver.findElement(By.xpath("//span[@class = 'shopping_cart_badge']")).getText();
		System.out.println("Cart badge value after adding first item into cart is - "+cartBadgeNo);
		Assert.assertEquals(cartBadgeNo, "1");

		//Add the last product to the cart
		int size = driver.findElements(By.xpath("//button[@class = 'btn btn_primary btn_small btn_inventory']")).size();
		driver.findElement(By.xpath("(//button[@class = 'btn btn_primary btn_small btn_inventory'])[" + size + "]")).click();
		Thread.sleep(3000);

		//Verify the cart badge value is increased
		String cartBadgeNo1 = driver.findElement(By.xpath("//span[@class = 'shopping_cart_badge']")).getText();
		System.out.println("Cart badge value after adding second item into cart is - "+cartBadgeNo1);
		Assert.assertEquals(cartBadgeNo1, "2");

		//Remove the first product from the cart
		driver.findElement(By.xpath("(//button[@class = 'btn btn_secondary btn_small btn_inventory'])[1]")).click();
		Thread.sleep(3000);

		//Verify the cart badge has 1 product
		String cartBadge2 = driver.findElement(By.xpath("//span[@class = 'shopping_cart_badge']")).getText();
		System.out.println("Cart badge value after removing first item from cart is - "+cartBadge2);
		Assert.assertEquals(cartBadge2, "1");
		Thread.sleep(3000);
		
		//Click on the cart 
		driver.findElement(By.xpath("//a[@class = 'shopping_cart_link']")).click();
		Thread.sleep(3000);
		
		//Verify the added product is available
		String prodName = driver.findElement(By.xpath("//div[@class = 'inventory_item_name']")).getText();
		Assert.assertEquals(prodName, "Test.allTheThings() T-Shirt (Red)");
		Thread.sleep(3000);
		
		//Click on the continue shopping
		driver.findElement(By.xpath("//button[@name = 'continue-shopping']")).click();
		Thread.sleep(3000);
		
		//Change the price filter from low to high
		WebElement dropDown = driver.findElement(By.xpath("//select[@class = 'product_sort_container']"));
		
		Select s = new Select(dropDown);
		s.selectByVisibleText("Price (low to high)");
		
		
		//Verify the price sorted properly
		double[] arr = new double[size];
		
		for(int i=0; i<size; i++) {
			arr[i] = Double.parseDouble(driver.findElement(By.xpath("(//div[@class = 'inventory_item_price'])["+(i+1)+"]")).getText().replace("$", ""));
		}
		System.out.println(Arrays.toString(arr));
		
		double[] arr2 = new double[size];
		for(int j=0; j<size; j++) {
			arr2[j] = arr[j];
		}
		Arrays.sort(arr);
		
		System.out.println(Arrays.toString(arr));
		
		if(Arrays.equals(arr, arr2)) {
			System.out.println("Sorted as expected from low to high price");
		}else {
			System.out.println("Sorting is not as expected");
		}
		}
		

	}

