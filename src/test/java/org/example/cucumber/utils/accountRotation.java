package org.example.cucumber.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.cucumber.src.models.object.credential;
import org.example.cucumber.properties.propertiesManager;

public class accountRotation {
    private static int maxAccounts = getMaxAccounts();
    private static AtomicInteger cursor = new AtomicInteger(0);
    private static List<credential> accounts = loadAccounts();
    private static final ThreadLocal<Integer> threadCursor = ThreadLocal.withInitial(() -> 0);

    private static List<credential> loadAccounts() {
        List<credential> loadedAccounts = new ArrayList<>();

        for (int i = 1; i <= maxAccounts; i++) {
            String email = propertiesManager.get("email_" + i);
            String password = propertiesManager.get("password_" + i);
            loadedAccounts.add(new credential(email, password));
        }

        return loadedAccounts;
    }

    public static credential get() {
        int index = Math.floorMod(cursor.getAndIncrement(), accounts.size());
        threadCursor.set(index);
        return accounts.get(index);
    }

    public static credential getCurrent() {
        int index = threadCursor.get();
        return accounts.get(index);
    }

    private static int getMaxAccounts() {
        int count = 1;
        try {
            while (true) {
                String email = propertiesManager.get("email_" + count);
                String password = propertiesManager.get("password_" + count);
                if (email == null || password == null) {
                    break;
                } else {
                    count++;
                }
            }
        } catch (Exception e) {

        }
        System.out.println("Max accounts found: " + (count - 1));
        return count - 1;
    }

}
