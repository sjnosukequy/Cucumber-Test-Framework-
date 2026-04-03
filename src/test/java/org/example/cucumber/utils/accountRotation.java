package org.example.cucumber.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.cucumber.src.models.object.credential;
import org.example.cucumber.properties.propertiesManager;

public class accountRotation {
    private static int maxAccounts = 6;
    private static AtomicInteger cursor = new AtomicInteger(0);
    private static List<credential> accounts = loadAccounts();

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
        return accounts.get(index);
    }

    public static credential getCurrent(){
        int index = Math.floorMod(cursor.get(), accounts.size());
        return accounts.get(index);
    }

}
