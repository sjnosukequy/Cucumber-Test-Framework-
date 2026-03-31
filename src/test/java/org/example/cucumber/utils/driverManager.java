package org.example.cucumber.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.example.cucumber.env.envManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class driverManager {
    private static int timeout = envManager.getBrowserTimeout();

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<List<String>> TAB_HANDLES = ThreadLocal.withInitial(ArrayList::new);
    private static final Set<WebDriver> ALL_DRIVERS = ConcurrentHashMap.newKeySet();

    /**
     * Returns current driver instance for this thread.
     * Initially null until initializeDriver() or openNewTab() is called.
     */
    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    /**
     * Initialize driver for current thread if not already created.
     */
    public static void initializeDriver() {
        if (DRIVER.get() != null) {
            return;
        }

        browserManger browserManager = new browserManger();
        WebDriver driver = browserManager.getBrowserDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

        DRIVER.set(driver);
        ALL_DRIVERS.add(driver);

        // Track the first/default tab handle
        TAB_HANDLES.get().clear();
        TAB_HANDLES.get().add(driver.getWindowHandle());
    }

    /**
     * Returns a copy of current tab/window handles for this thread.
     */
    public static List<String> getCurrentTabs() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            return Collections.emptyList();
        }

        syncTabHandlesWithBrowser();
        return new ArrayList<>(TAB_HANDLES.get());
    }

    /**
     * Open a new tab.
     * - If driver does not exist, initialize it first.
     * - Reuse existing driver otherwise.
     * - Return the new tab index.
     */
    public static int openNewTab() {
        boolean isNewDriver = false;
        if (DRIVER.get() == null) {
            initializeDriver();
            isNewDriver = true;
        }

        WebDriver driver = DRIVER.get();
        List<String> handles = TAB_HANDLES.get();

        if (!isNewDriver) {
            driver.switchTo().newWindow(WindowType.TAB);
            String newHandle = driver.getWindowHandle();
            handles.add(newHandle);
        }

        return handles.size() - 1;
    }

    /**
     * Switch to a tab by index.
     */
    public static void switchToTab(int tabIndex) {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized.");
        }

        syncTabHandlesWithBrowser();
        List<String> handles = TAB_HANDLES.get();

        validateTabIndex(tabIndex, handles);

        driver.switchTo().window(handles.get(tabIndex));
    }

    /**
     * Close a tab by index.
     * If it is the last tab, quit the driver completely.
     */
    public static void closeTab(int tabIndex) {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            return;
        }

        syncTabHandlesWithBrowser();
        List<String> handles = TAB_HANDLES.get();

        validateTabIndex(tabIndex, handles);

        String handleToClose = handles.get(tabIndex);
        driver.switchTo().window(handleToClose);
        driver.close();

        handles.remove(tabIndex);

        if (handles.isEmpty()) {
            quitCurrentThreadDriver();
            return;
        }

        // Switch to a safe remaining tab
        int nextIndex = Math.max(0, tabIndex - 1);
        driver.switchTo().window(handles.get(nextIndex));
    }


    private static void validateTabIndex(int tabIndex, List<String> handles) {
        if (tabIndex < 0 || tabIndex >= handles.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid tab index: " + tabIndex + ", total tabs: " + handles.size());
        }
    }

    /**
     * Keeps local tab list aligned with actual browser windows.
     * Useful if something closes a tab outside DriverManager methods.
     */
    private static void syncTabHandlesWithBrowser() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            return;
        }

        List<String> actualHandles = new ArrayList<>(driver.getWindowHandles());
        List<String> trackedHandles = TAB_HANDLES.get();

        // Keep only still-existing handles
        trackedHandles.removeIf(handle -> !actualHandles.contains(handle));

        // Add any newly found handles not yet tracked
        for (String handle : actualHandles) {
            if (!trackedHandles.contains(handle)) {
                trackedHandles.add(handle);
            }
        }
    }

    /**
     * Scenario reset, not full quit.
     */
    public static void resetForNextScenario() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            return;
        }

        List<String> handles = new ArrayList<>(driver.getWindowHandles());

        // Close all tabs except first
        for (int i = handles.size() - 1; i > 0; i--) {
            driver.switchTo().window(handles.get(i));
            driver.close();
        }

        List<String> remaining = new ArrayList<>(driver.getWindowHandles());
        if (!remaining.isEmpty()) {
            driver.switchTo().window(remaining.get(0));
        }

        driver.manage().deleteAllCookies();

        try {
            driver.get("about:blank");
        } catch (Exception ignored) {
        }
    }

    /**
     * Quit only current thread driver, rarely needed in this model.
     */
    public static void quitCurrentThreadDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                ALL_DRIVERS.remove(driver);
                DRIVER.remove();
            }
        }
    }

    /**
     * Quit everything at suite end.
     */
    public static void quitAll() {
        for (WebDriver driver : ALL_DRIVERS) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
        }
        ALL_DRIVERS.clear();
        DRIVER.remove();
    }

    
}
