package test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import automationUtils.BaseTest;
import net.datafaker.Faker;

public class FakerDataLibraryExample extends BaseTest{
	
	Faker faker = new Faker();
	
	@BeforeClass
	public void setPrerequisite() {
		initializeObjects();
		
		try {
			driver.get("https://google.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initializeObjects() {
		// add and initialize Object of the pageObjects
	}
	
	@Test(priority = 1) 
	public void test_verification() {
		System.out.println("Test 1: Faker Class Data verification");
		
		// User information related Fake data
		System.out.println("Address: " + faker.address().fullAddress().toString());
		System.out.println("First Name: " + faker.name().firstName());
		System.out.println("Full Name: " + faker.name().fullName());
		System.out.println("Last Name: " + faker.name().lastName());
		System.out.println("Company Name: " + faker.company().name());
		System.out.println("Industry: " + faker.company().industry());
		System.out.println("Country Name:" + faker.country().name());
		System.out.println("Country Capital: " + faker.country().capital());
		System.out.println("Country Currency: " + faker.country().currency());
		System.out.println("Country Flag: " + faker.country().flag());
		System.out.println("Funny Name: " + faker.funnyName().name());

	}

}
