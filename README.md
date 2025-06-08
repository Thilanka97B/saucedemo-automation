# SauceDemo Test Automation Framework

This project is a Selenium-based automated test framework for the SauceDemo e-commerce site using **Java**, **TestNG**, and **Allure Reports**.

## 📁 Project Structure

```
src
├── main
│   └── java
│       ├── pages         # Page Object Model classes
│       └── utils         # DriverFactory, ConfigReader, TestUtil helpers
├── test
│   └── java
│       └── tests         # TestNG test classes
├── resources             # Resource files (config, data, etc.)
allure-results/           # Allure raw test results
screenshots/              # Screenshots taken during test runs
```

---

## ✅ Prerequisites

- Java 8 or above
- Maven 3.6+
- Chrome browser installed
- IntelliJ IDEA (or any Java IDE)
- Allure CLI (for report generation)

### 📦 Install Allure CLI

#### On Windows (via Scoop)
```bash
scoop install allure
```

#### On macOS (via Homebrew)
```bash
brew install allure
```

---

## ⚙️ Setup Instructions


1.**Install Dependencies**
```bash
mvn clean install
```

2.**Update Config (if applicable)**  
   Place any required configuration files in the `src/main/resources` directory.

---

## 🚀 Run Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn -Dtest=CheckoutTest test
```

---

## 📊 Generate Allure Report

After running tests:

```bash
allure serve target/allure-results
```

To generate static report:

```bash
allure generate allure-results --clean -o target/allure-report
allure open target/allure-report
```

---

## 📸 Screenshots

Screenshots are saved in the `/screenshots` folder automatically during test steps using `TestUtil`.

---

## 🤝 Contributing

Feel free to fork this repo and contribute with pull requests. Suggestions and improvements are always welcome.

---


