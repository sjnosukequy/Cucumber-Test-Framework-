# Cucumber Test Framework

## 1. Short Overview

This project is a Java-based automation framework built on Cucumber + JUnit Platform, covering both UI and API testing for Automation Exercise.

It is designed for:

- Behavior-driven test authoring with Gherkin feature files
- Parallel-safe execution (thread-isolated browser/session state)
- Flexible configuration with override precedence
- Allure-based reporting (Allure 2 and Allure 3 style report outputs)

---

## 2. Folder Structure

```text
.
|-- pom.xml
|-- package.json
|-- allurerc.js
|-- .env
|-- src/
|   `-- test/
|       |-- java/org/example/cucumber/
|       |   |-- env/
|       |   |   `-- envManager.java
|       |   |-- properties/
|       |   |   `-- propertiesManager.java
|       |   |-- plugins/
|       |   |   `-- CucumberSummaryPlugin.java
|       |   |-- src/
|       |   |   |-- api/
|       |   |   |-- models/
|       |   |   `-- ...
|       |   |-- tests/
|       |   |   |-- globalHooks.java
|       |   |   |-- suites/
|       |   |   |   |-- AllSuites.java
|       |   |   |   |-- UiSuite.java
|       |   |   |   `-- ApiSuite.java
|       |   |   |-- ui/
|       |   |   `-- api/
|       |   `-- utils/
|       |       |-- driverManager.java
|       |       |-- browserManger.java
|       |       |-- accountRotation.java
|       |       `-- ...
|       `-- resources/
|           |-- config.properties
|           |-- cucumber.properties
|           |-- junit-platform.properties
|           |-- allure.properties
|           `-- tests/
|               |-- ui/
|               `-- api/
|-- target/
|   `-- allure-results/
`-- reports/
		`-- allure/
```

---

## 3. Technology / Framework Used

- Java 21
- Maven (build/test lifecycle)
- Cucumber JVM 7 (BDD)
- JUnit Platform Suite Engine
- Selenium WebDriver 4 (UI testing)
- REST Assured 6 (API testing)
- Allure (Cucumber + JUnit integrations)
- Log4j2 + SLF4J
- Dotenv (java-dotenv) for local secret/config loading
- Node.js scripts for report/log cleanup and Allure CLI control

## Installation Guide
- [JDK 21](https://www.oracle.com/java/technologies/downloads/)
- [allure reporpt](https://allurereport.org/docs/v3/install/)
- [maven](https://maven.apache.org/install.html)


---

## 4. Key Features

- Parallel execution support:
	- Enabled via `cucumber.execution.parallel.enabled=true` and fixed thread pool strategy in `cucumber.properties`.
	- Driver/session isolation uses `ThreadLocal<WebDriver>` and thread-local buffers/state for safer concurrent runs.
	- Shared summary metrics use thread-safe types (`AtomicInteger`, `CopyOnWriteArrayList`, concurrent collections).

- Thread/state safety design:
	- Browser instance is managed per thread in `driverManager`.
	- Scenario logs are isolated with thread-local logging buffers.
	- Test account assignment supports concurrent access with atomic cursor + per-thread current account context.

- Configuration support through `.env` and `config.properties`:
	- Centralized property access is handled by `propertiesManager.get(key)`.
	- `envManager` exposes typed getters consumed by test/runtime classes.

- Sensitive configuration in `.env` with override precedence:
	- Runtime lookup priority is:
		1) System environment variables
		2) `.env`
		3) `config.properties`
	- This allows CI/CD or local shell env vars to override local `.env`, and `.env` to override repository defaults.

- Unified UI + API framework:
	- UI and API scenarios live in the same Cucumber project and can be run independently or together through suite classes.

- Reporting:
	- Allure result generation through Maven output (`target/allure-results`).
	- Additional report composition via `allurerc.js` plugins (`awesome`, `dashboard`, `allure2`).

---

## 5. Configuration Guidelines (.env, config.properties, envManager)

### A. config.properties (shared defaults, versioned)

Use this file for non-sensitive defaults used by all developers, for example:

- `baseURI`
- `browser_type`
- `browser_timeout`
- headless/maximize flags
- custom driver/binary paths
- log behavior flags

Keep values stable and team-safe.

### B. .env (local overrides + sensitive values)

Use `.env` for:

- Credentials and account pools (`email_1/password_1`, `email_2/password_2`, ...)
- Machine/user-specific values
- Sensitive data you do not want in shared defaults

Notes:

- The project loads `.env` if present (`ignoreIfMissing`), so local setup is optional but recommended.
- Keys must match what the framework reads (for example `baseURI`, `browser_type`, `email_1`, `password_1`).

### C. envManager (typed configuration facade)

`envManager` reads values from `propertiesManager` and exposes typed accessors used by tests and utilities.

Guidelines:

- Add new config keys in `config.properties` (default), optionally override in `.env`.
- Read them through `propertiesManager.get(...)` and surface typed getters in `envManager`.
- Keep key naming consistent across all three layers: system env, `.env`, and `config.properties`.

### D. Effective precedence

For any key `X`, the effective value is resolved as:

1. System environment variable `X`
2. `.env` value `X`
3. `config.properties` value `X`

If key `X` does not exist in any source, framework throws a runtime configuration error.

### E. Available configuration reference

The table below lists currently supported runtime keys used by `envManager`.

| Key | Type | Example | What it controls |
|---|---|---|---|
| `baseURI` | String | `https://automationexercise.com` | Base URL for UI page objects and API requests. |
| `browser_timeout` | Integer (seconds) | `4` | Default Selenium implicit wait timeout used by `driverManager`. |
| `browser_type` | String | `edge` | Browser engine to start (`chrome`, `edge`, `firefox`). |
| `browser_headless` | Boolean | `false` | Runs browser in headless mode when `true`. |
| `browser_window_maximize` | Boolean | `true` | Adds maximize/start-maximized behavior when supported. |
| `use_custom_driver_path` | Boolean | `false` | Enables explicit WebDriver executable paths from custom driver keys. |
| `use_custom_binary_path` | Boolean | `false` | Enables explicit browser binary paths from custom binary keys. |
| `custom_chrome_driver_path` | String (path) | `src/test/resources/drivers/chrome/chromedriver.exe` | ChromeDriver path when `use_custom_driver_path=true`. |
| `custom_edge_driver_path` | String (path) | `src/test/resources/drivers/edge/msedgedriver.exe` | EdgeDriver path when `use_custom_driver_path=true`. |
| `custom_firefox_driver_path` | String (path) | `src/test/resources/drivers/firefox/geckodriver.exe` | GeckoDriver path when `use_custom_driver_path=true`. |
| `custom_chrome_binary_path` | String (path) | *(empty by default)* | Custom Chrome binary location when `use_custom_binary_path=true`. |
| `custom_edge_binary_path` | String (path) | *(empty by default)* | Custom Edge binary location when `use_custom_binary_path=true`. |
| `custom_firefox_binary_path` | String (path) | *(empty by default)* | Custom Firefox binary location when `use_custom_binary_path=true`. |
| `log_images_on_failure` | Boolean | `false` | Saves failed-scenario screenshots to local logs in addition to Allure attachment. |

Account pool keys used by `accountRotation`:

| Key pattern | Type | Example | What it controls |
|---|---|---|---|
| `email_1 ... email_N` | String | `email_1=test1_uni@gmail.com` | Login email pool for UI/API flows that require account rotation. |
| `password_1 ... password_N` | String | `password_1=a` | Password paired to each indexed email. |

Rules for account pool:

1. Keep indexes continuous (`1..N`) without gaps.
2. Each `email_i` must have matching `password_i`.
3. Put these keys in `.env` (recommended), or system env, or `config.properties` if needed.

---

## 6. Test Suite Execution Management

By default, the framework is configured in `pom.xml` to run:

```xml
<test>org.example.cucumber.tests.suites.AllSuites</test>
```

So when you run:

```bash
mvn test
```

Only `AllSuites` is executed.

### Customize default suite in pom.xml

To change the default suite, update:

```xml
<test>org.example.cucumber.tests.suites.AllSuites</test>
```

Replace with another suite class if needed (for example `UiSuite` or `ApiSuite`).

### Override via CLI (recommended)

Override the suite at runtime without modifying the POM:

```bash
mvn test -Dtest=org.example.cucumber.tests.suites.UiSuite
```

or

```bash
mvn test -Dtest=org.example.cucumber.tests.suites.ApiSuite
```

### Allure serve via Maven Plugin

```bash
mvn io.qameta.allure:allure-maven:2.15.0:serve
```

### NPM helper scripts

```bash
npm run test
npm run allure:generate
npm run allure:serve
```