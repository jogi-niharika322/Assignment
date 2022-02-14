package FirstAsesment.FirstAsesment;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;



public class loginClass {
	WebDriver driver;
	public static ExtentReports extent;
	@BeforeMethod
	@BeforeSuite
	public void setUp()
	{
	extent = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter("target/Reports/extentReports" + DateAndTime.getCurrentDateTime()+ ".html");
	extent.attachReporter(spark);
	}
	@SuppressWarnings("deprecation")
	@Parameters("browser")
	@BeforeClass
	void setup(String browser) throws Exception
	{
	if(browser.equalsIgnoreCase("chrome")) {
	//System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\Downloads\\chromedriver_win32\\chromedriver.exe");//Local path
		WebDriverManager.chromedriver().driverVersion("97").setup();
	driver = new ChromeDriver();
	}
	else if(browser.equalsIgnoreCase("Edge")){
	//System.setProperty("webdriver.edge.driver","C:\\Driverserver\\edgedriver_win64\\msedgedriver.exe"); //create Edge instance
		WebDriverManager.edgedriver().driverVersion("97").setup();
		driver = new EdgeDriver();
	}
	else{
	throw new Exception("browser is not correct");
	}
	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.MILLISECONDS);
	driver.manage().window().maximize();



	}



	@AfterClass
	void close() throws InterruptedException
	{
	Thread.sleep(2000);
	driver.close();
	}
	@AfterSuite
	public void gettingReports() {
	extent.flush();//used to erase the prevoius data and create new report

	}



	@Test(dataProvider="niharika")
	void login(String data)
	{
	String users[] = data.split(",");
	driver.get("https://opensource-demo.orangehrmlive.com/");
	driver.findElement(By.name("txtUsername")).sendKeys(users[0]); //username
	driver.findElement(By.name("txtPassword")).sendKeys(users[1]); //password
	driver.findElement(By.name("btnLogin")).click();
	String act_title = driver.getTitle();
	String exp_title = "GTPL Bank Manager HomePage";
	Assert.assertEquals(act_title, exp_title);
	ExtentTest test = extent.createTest("crossbrowsertesting");
	test.pass("Passed");
	test.log(Status.PASS, "Passed");
	extent.createTest("Exception! <i class='fa fa-frown-o'></i>").fail(new RuntimeException("A runtime exception occurred!"));

	}
	@AfterMethod
	public void name(ITestResult result) {



	long a = result.getEndMillis()-result.getStartMillis();
	System.out.println("Time taken to run test is :" +(a/1000)+"seconds");
	}



	@DataProvider(name="niharika")
	public String[] readJSON() throws IOException, org.json.simple.parser.ParseException
	{
	JSONParser jsonParser = new JSONParser();
	FileReader reader = new FileReader(".\\jsonfiles\\login.json");



	Object obj = jsonParser.parse(reader);



	JSONObject userloginsJsonobj = (JSONObject) obj;
	JSONArray userloginsArray = (JSONArray)userloginsJsonobj.get("userlogins");




	String arr[] = new String[userloginsArray.size()];



	for(int i=0;i<userloginsArray.size();i++)
	{
	JSONObject users = (JSONObject)userloginsArray.get(i);
	String user = (String) users.get("username");
	String pwd = (String) users.get("password");



	arr[i]=user+","+pwd;
	}



	return arr;



	}





	}



	
	


