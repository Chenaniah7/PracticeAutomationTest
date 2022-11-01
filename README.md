# Automation Test Practice
*Author: Guan Chuan Wen*  
*Language: Java*  
*TestFrameWork: Cucumber and TestNG*


### 1. API automation test framework Instructions  :  
1. Init a BaseStep instance use BaseStepFactory.getBaseStep(), this method will init Entity and Config instance by default
2. Assemble the http request use the method, for example: baseStep.setBaseUri(),baseStep.updatePathParam() etc.
3. Call the baseStep.getResource() to submit the http request
4. All of the response info will save in the baseStep's Entity variable
5. Get the request info from entity and verify it, for example: base.getEntity.getResponseCode()  

   #### sample:
        BaseStep baseStep = BaseStepFactory.getBaseStep();
        baseStep.setBaseUri(ConfigProvider.getConfig(ConfigKeys.API).getString(ConfigKeys.BASE_URI));
        baseStep.setEndPoint(Endpoint.USERS);
        baseStep.getResource();
        Assert.assertEquals(baseStep.getResourceCode(), HttpStatus.SC_OK);
        String resContent =  baseStep.getResponseJson();
    

### 2. UI automation test instructions :
1. Init webdriver and PracticePage instance in setUp
2. Find elements in PracticePage class
3. Write testng test cases in UiAutomationTest class
    #### sample:
       @BeforeSuite
       public void setUp(){
           String projectPath = System.getProperty("user.dir");
           System.setProperty("webdriver.chrome.driver",projectPath+"\\src\\test\\resources\\drivers\\chromedriver.exe");
           chromeDriver  = new ChromeDriver();
           chromeDriver.manage().window().maximize();
           String practiceUrl = config.getConfig(ConfigKeys.UI).getString(ConfigKeys.PRACTICE_PAGE_URI);
           practicePage = new PracticePage(chromeDriver);
           chromeDriver.get(practiceUrl);
           String title = new WebDriverWait(chromeDriver,Duration.ofSeconds(15)).until(webDriver -> webDriver.findElement(By.xpath("//h1")).getText());
           Assert.assertEquals(title, ElementKeys.PRACTICE_PAGE);
       }
       @AfterSuite
           public void tearDown(){
           chromeDriver.quit();
       }
       
       @Test(dataProvider = "radioIdProvider",dataProviderClass = PracticeDataProvider.class)
           public void testSelectFromRadioButtonExample(int radioId){
           practicePage.clickRadioButton(radioId);
           practicePage.IsRadioSelected(radioId);
       }

### 3. How to run test case  
Execute this command **mvn test -Dentity=autotest** in the project directory, it will run all test cases including ui and api 


#### Project Tree
    ├───src
    │   ├───main
    │   │   └───java
    │   │       └───com
    │   │           └───gcw
    │   │               └───apiautomation                       ------ api automation framework
    │   │                   ├───core
    │   │                   │   │   BaseStep.java
    │   │                   │   │   BaseStepFactory.java
    │   │                   │   │   
    │   │                   │   ├───api
    │   │                   │   │   │   ApiJob.java
    │   │                   │   │   │   ApiJobHelper.java
    │   │                   │   │   │   
    │   │                   │   │   ├───rest
    │   │                   │   │   │       AbstractRestJob.java
    │   │                   │   │   │       RestJobProvider.java
    │   │                   │   │   │       
    │   │                   │   │   └───restimpl                ------ Http method operation
    │   │                   │   │           RestCreateJob.java  ------ POST method
    │   │                   │   │           RestReadJob.java    ------ GET method
    │   │                   │   │          
    │   │                   │   ├───config
    │   │                   │   │       ConfigProvider.java     ------ get the test data from config file
    │   │                   │   │       
    │   │                   │   ├───domain
    │   │                   │   │   └───entity                --------- save all api request data
    │   │                   │   │           Entity.java
    │   │                   │   │           EntityBuilder.java
    │   │                   │   │           
    │   │                   │   ├───enums
    │   │                   │   │       ConfigKeys.java
    │   │                   │   │       
    │   │                   │   ├───request
    │   │                   │   │   └───headers              ---------- generate default headers from config file
    │   │                   │   │           Headers.java
    │   │                   │   │           HeadersAssembler.java
    │   │                   │   │           
    │   │                   │   └───utility
    │   │                   │           Constants.java
    │   │                   │           
    │   │                   └───runner
    │   │                           FeatureTestRunner.java    ---------- cucumber configuration
    │   │                           
    │   └───test
    │       ├───java
    │       │   └───com
    │       │       └───gcw
    │       │           └───testautomation
    │       │               ├───steps                      ------------- cucumber step definitions
    │       │               │       ApiTestSteps.java
    │       │               ├───support
    │       │               │   ├───beans
    │       │               │   │       Post.java
    │       │               │   │       User.java
    │       │               │   │       
    │       │               │   ├───constants
    │       │               │   │       ConfigKeys.java
    │       │               │   │       ElementKeys.java
    │       │               │   │       Endpoint.java
    │       │               │   │       Params.java
    │       │               │   │       
    │       │               │   ├───dataproviders          ------------ testNG's data providers
    │       │               │   │       PracticeDataProvider.java
    │       │               │   │       
    │       │               │   └───pages
    │       │               │           PracticePage.java  ------------ define the element selector for practice page
    │       │               │           
    │       │               └───tests
    │       │                   ├───apiTestcases
    │       │                   │       AutomationTestForAPI.feature  --- api automation test cases                                 
    │       │                   │       
    │       │                   └───uiTestcases
    │       │                           UiAutomationTest.java   --------- ui automation test cases
    │       │                           
    │       └───resources
    │           │   cucumber.properties                
    │           │   
    │           ├───config                          --------- test data config file 
    │           │       application-autotest.conf
    │           │       application.conf
    │           │       
    │           ├───drivers
    │           │       chromedriver.exe
    │           │       
    │           ├───payload
    │           │       create_post_resource.json
    │           │       
    │           ├───reports
    │           │       emailable-report.html
    │           │       report.html
    │           │       
    │           └───suites
    │                   automation-test-suite.xml  ------ test suite xml for maven-surefile plugin             
    │

