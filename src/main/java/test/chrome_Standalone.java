package test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import automationUtils.BaseTest;

public class chrome_Standalone extends BaseTest{

	@BeforeClass
	public void setPrerequisite() {
//		initializeObjects();
//		
//		try {
//			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//			commonFunc.LogIn(BaseTest.username, BaseTest.password);
//			driver.get(Consts.Main_Url + BaseTest.inventoryScreen);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	private void initializeObjects() {
		// add and initialize Object of the pageObjects
	}
	
	@Test(priority = 1) 
	public void test_verification() {
		driver.get("http://google.com");
		System.out.println(driver.getTitle());
	}
}
