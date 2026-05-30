# Selenium Automation F inal Project

## Project Overview
This project is a Selenium WebDriver automation framework built for [AutomationExercise.com](https://www.automationexercise.com) as part of the Software Testing Diploma final project at EDGES For Training.

## Tech Stack
- Java 17
- Selenium WebDriver 4.44.0
- TestNG 7.12.0
- Allure Report 2.34.0
- Maven
- Page Object Model (POM)

## Project Structure
```
src/
└── test/
    ├── java/
    │   ├── pages/
    │   │   ├── BasePage.java
    │   │   ├── HomePage.java
    │   │   ├── LoginPage.java
    │   │   ├── RegisterPage.java
    │   │   ├── ProductsPage.java
    │   │   ├── ProductDetailPage.java
    │   │   ├── CartPage.java
    │   │   ├── CheckoutPage.java
    │   │   ├── PaymentPage.java
    │   │   └── ContactUsPage.java
    │   ├── tests/
    │   │   ├── BaseTest.java
    │   │   ├── UserRegistrationTests.java
    │   │   ├── LoginLogoutTests.java
    │   │   ├── ContactUsTests.java
    │   │   ├── NavigationTests.java
    │   │   ├── ProductTests.java
    │   │   ├── CartCheckoutTests.java
    │   │   └── SubscriptionTests.java
    │   └── utils/
    │       ├── DriverFactory.java
    │       ├── ConfigReader.java
    │       ├── JsonReader.java
    │       └── SeleniumHelper.java
    └── resources/
        ├── config/
        │   └── config.properties
        ├── testdata/
        │   ├── users.json
        │   ├── products.json
        │   ├── payment.json
        │   └── contact.json
        └── testng-suite.xml
```

## Test Cases Covered
| TC | Title |
|----|-------|
| TC1 | Register User |
| TC2 | Login with correct credentials |
| TC3 | Login with incorrect credentials |
| TC4 | Logout User |
| TC5 | Register with existing email |
| TC6 | Contact Us Form |
| TC7 | Verify Test Cases Page |
| TC8 | All Products and product detail page |
| TC9 | Search Product |
| TC10 | Subscription on Home page |
| TC11 | Subscription on Cart page |
| TC12 | Add Products in Cart |
| TC13 | Verify Product quantity in Cart |
| TC14 | Place Order — Register while Checkout |
| TC15 | Place Order — Register before Checkout |
| TC16 | Place Order — Login before Checkout |
| TC17 | Remove Products From Cart |
| TC18 | View Category Products |
| TC19 | View and Cart Brand Products |
| TC20 | Search Products and Verify Cart After Login |
| TC21 | Add review on product |
| TC22 | Add to cart from Recommended items |
| TC23 | Verify address details in checkout page |
| TC24 | Download Invoice after purchasing order |
| TC25 | Scroll Up using Arrow button |
| TC26 | Scroll Up without Arrow button |

## Prerequisites
- Java 17+
- Maven 3.8+
- Google Chrome browser

## How to Run

### Run all tests
```
mvn clean test
```

### Run a specific test class
```
mvn clean test -Dtest=LoginLogoutTests
```

### Run a specific test method
```
mvn clean test -Dtest=LoginLogoutTests#TC2_loginWithCorrectCredentials
```

## Allure Report
```
mvn allure:serve
```

## Configuration
You can change browser and settings in:
```
src/test/resources/config/config.properties
```

```properties
browser=chrome
headless=false
base.url=https://www.automationexercise.com
implicit.wait=10
explicit.wait=15
```
