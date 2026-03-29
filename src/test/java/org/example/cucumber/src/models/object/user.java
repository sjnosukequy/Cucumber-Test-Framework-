package org.example.cucumber.src.models.object;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class user {
    public String name;
    public String email;
    public String password;
    public String title;
    public String birth_date;
    public String birth_month;
    public String birth_year;
    public String firstname;
    public String lastname;
    public String company;
    public String address1;
    public String address2;
    public String country;
    public String zipcode;
    public String state;
    public String city;
    public String mobile_number;

    public user(String name, String email, String password, String title, String birth_date, String birth_month,
            String birth_year, String firstname, String lastname, String company, String address1, String address2,
            String country, String zipcode, String state, String city, String mobile_number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.title = title;
        this.birth_date = birth_date;
        this.birth_month = birth_month;
        this.birth_year = birth_year;
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.address1 = address1;
        this.address2 = address2;
        this.country = country;
        this.zipcode = zipcode;
        this.state = state;
        this.city = city;
        this.mobile_number = mobile_number;
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new java.util.HashMap<>();
        map.put("name", this.name);
        map.put("email", this.email);
        map.put("password", this.password);
        map.put("title", this.title);
        map.put("birth_date", this.birth_date);
        map.put("birth_month", this.birth_month);
        map.put("birth_year", this.birth_year);
        map.put("firstname", this.firstname);
        map.put("lastname", this.lastname);
        map.put("company", this.company);
        map.put("address1", this.address1);
        map.put("address2", this.address2);
        map.put("country", this.country);
        map.put("zipcode", this.zipcode);
        map.put("state", this.state);
        map.put("city", this.city);
        map.put("mobile_number", this.mobile_number);
        return map;
    }

    public static user toUser(String userString) {
        Map<String, String> map = Arrays.stream(userString.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty()) // remove empty entries
                .map(pair -> {
                    String[] parts = pair.split("=", 2);
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid pair: [" + pair + "]");
                    }
                    return parts;
                })
                .collect(Collectors.toMap(
                        parts -> parts[0].trim(),
                        parts -> parts[1].trim()));

        return new user(
                map.get("name"),
                map.get("email"),
                map.get("password"),
                map.get("title"),
                map.get("birth_date"),
                map.get("birth_month"),
                map.get("birth_year"),
                map.get("firstname"),
                map.get("lastname"),
                map.get("company"),
                map.get("address1"),
                map.get("address2"),
                map.get("country"),
                map.get("zipcode"),
                map.get("state"),
                map.get("city"),
                map.get("mobile_number"));

    }

}
