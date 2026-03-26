# Test Suite Execution

By default, the framework is configured to run:

```xml
<test>org.example.cucumber.tests.suites.allSuites</test>
```

So when you run:
```bash
mvn test
```

👉 Only AllSuites will be executed.


# Customize Suite (POM)

To change the default suite, update the pom.xml:

```xml
<test>org.example.cucumber.tests.suites.AllSuites</test>
```

Example:
```xml
<test>org.example.cucumber.tests.suites.smokeSuite</test>
```

# Override via CLI (Recommended)

You can override the suite at runtime without modifying the POM:
```bash
mvn test -Dtest=org.example.cucumber.tests.suites.SmokeSuite
```

Other examples:

```bash
mvn test -Dtest=org.example.cucumber.tests.suites.RegressionSuite
```
```bash
mvn test -Dtest=org.example.cucumber.tests.suites.AllSuites
```

mvn io.qameta.allure:allure-maven:2.12.0:serve