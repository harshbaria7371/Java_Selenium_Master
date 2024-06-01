package test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationUtils.BaseTest;
import automationUtils.Common_Functions;
import automationUtils.Consts;

import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class NewTest extends BaseTest{
	
	private static Common_Functions commonFunc = new Common_Functions() ;
	
	private SoftAssert assertion;

	@BeforeClass
	public void setPrerequisite() {
		initializeObjects();
		
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			commonFunc.LogIn(BaseTest.username, BaseTest.password);
			driver.get(Consts.Main_Url + BaseTest.inventoryScreen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initializeObjects() {
		// add and initialize Object of the pageObjects
	}
	
	@Test(priority = 1) 
	public void test_verification() {
		System.out.println("Test 1: Test verification");
		
		assertion = new SoftAssert();
		
//		assertion.assertEquals(abc.getPageTitle(), "Cases", "User is not on Cases page and Title mismatched");
	}
	
}