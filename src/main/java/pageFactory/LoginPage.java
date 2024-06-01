package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import automationUtils.PageObject;

public class LoginPage extends PageObject{

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	// Replace your login code from here.
	@FindBy(id="user-name")
	WebElement usernameTextBox;

	@FindBy(id="password")
	WebElement passwordTextBox;
	
	@FindBy(id="login-button")
	WebElement signIn;
	
	public void setUserName(String strUserName) {
		usernameTextBox.sendKeys(strUserName);
	}
	
	public void setPassword(String strPassword) {
		passwordTextBox.sendKeys(strPassword);
	}
	
	public void clickSignInButton() {
		signIn.click();
	}
}
