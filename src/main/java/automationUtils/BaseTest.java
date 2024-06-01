package automationUtils;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class BaseTest {
	public static WebDriver driver = null;
//	public static RemoteWebDriver driver = null;
	
	public static String baseURL;
	public static String username;
	public static String password;
	public static String browser;
	public static boolean runInDockerFlag;
	
	public static Dimension screensize;
	
	public static Properties propScreenID = null;
	public static Properties propSetup = null;
	
	// All Screen properties declare here
	public static String inventoryScreen;
	
	static String setUpPath = System.getProperty("user.dir") + "\\resources\\setUp.properties";
	
	static {
		try {
			propSetup = readPropertiesFile(setUpPath);
			
			browser = propSetup.getProperty("browser").trim();
			baseURL = propSetup.getProperty("baseURL").trim();
			username = propSetup.getProperty("username").trim();
			password = propSetup.getProperty("password").trim();
			
			String flagValue = propSetup.getProperty("runInDocker").trim();
		    runInDockerFlag = Boolean.parseBoolean(flagValue);
			
			String screenIDPath = System.getProperty("user.dir") + "\\resources\\screenID.properties";
			propScreenID = readPropertiesFile(screenIDPath);

			inventoryScreen = propScreenID.getProperty("inventory").trim();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeClass
	public static void initialize() {
		String chromeDriverPath = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
		ChromeOptions options = new ChromeOptions();
		URL new_url;
		try {
			if(runInDockerFlag) {
				new_url = new URL("http://localhost:4444/wd/hub");
				driver = new RemoteWebDriver(new_url, options);
			}else {
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				driver = new ChromeDriver();
				setHighResolutionScreen();
				driver.manage().window().maximize();
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.get(baseURL);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
//	@BeforeClass
//	public static void initialize() {
//		String chromeDriverPath = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
//		
//		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//		driver = new ChromeDriver();
//
//		// To Run on Higher Resolution Screen
//		setHighResolutionScreen();
//
//		// To Maximize browser
//		driver.manage().window().maximize();
//
//		// Implicit wait
////		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//
//		// To open base URL
//		driver.get(baseURL);
//	}
	
	@AfterClass
	public void testWrite() {
		BaseTest.driver.quit();
	}

	private static Properties readPropertiesFile(String fileName) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}
	
	private static void setHighResolutionScreen() {
		// TODO Auto-generated method stub
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();

		if (width <= 1920) {
			Point point = new Point((int) width, 0);
			driver.manage().window().setPosition(point);
			driver.manage().window().maximize();
		} else {
			Point point = new Point(0, 0);
			driver.manage().window().setPosition(point);
			Dimension targetWindowSize = new Dimension(1920, 1080);
			driver.manage().window().setSize(targetWindowSize);
			driver.manage().window().maximize();
		}
	}
	
	
//	All commonly used functions related to configurations are as follows
	public static void waitForElementIsPresent(WebElement elementPresent, WebDriver driver) {
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(elementPresent));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Boolean waitForElementToBeClickable(WebElement clickableElement, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println("Element not clickable " + clickableElement);
			return false;
		}
		return true;
	}

	public static void waitForElementHasText(final WebElement element) {
		try {
			(new WebDriverWait(driver, Duration.ofSeconds(30))).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver input) {
					if (element.getText().length() != 0) {
						System.out.println("Element has text present " + element.getText());
						return true;
					} else {
						return false;
					}
				}

			});
		} catch (Exception e) {
			System.out.println("Element has no text present");
		}
	}

	public static boolean isAlertPresent() {
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			if (wait.until(ExpectedConditions.alertIsPresent()) == null) {
				foundAlert = false;
			} else {
				foundAlert = true;
			}

		} catch (TimeoutException eTO) {
			foundAlert = false;
		} catch (UnhandledAlertException f) {
			foundAlert = true;
		}

		return foundAlert;
	}

	public static void toDoubleClick(WebElement element) {
		Actions actions = new Actions(driver);
		try {
			waitForElementIsPresent(element, driver);
			if (BaseTest.browser.equalsIgnoreCase("chrome")) {
				actions.doubleClick(element).perform();
				actions.click(element).perform();
			} else {
				actions.doubleClick(element).perform();
			}

		} catch (Exception e) {
			System.out.println("Exception is " + e.getCause());
			e.printStackTrace();
		}
	}

	public static boolean isWebElementIsDisplayed(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.visibilityOf(element));
			Thread.sleep(500);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}