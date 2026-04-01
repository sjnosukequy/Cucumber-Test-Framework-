package org.example.cucumber.utils;

public class prodStringUtils {
    /**
     * Appends an incremental index to each element in a semicolon-delimited string.
     *
     * <p>
     * Each item in the input string is separated by a semicolon (';'). This method
     * splits the string, appends an index (starting from 1) to each element using
     * an underscore ('_'), and then joins them back into a single string.
     * </p>
     *
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * Input:  "Blue Top;Men Tshirt"
     * Output: "Blue Top_1;Men Tshirt_2"
     * </pre>
     *
     * @param input the semicolon-delimited input string
     * @return a string where each element has an appended index
     */
    public static String convertFormat(String input) {
        String[] parts = input.split(";");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            result.append(parts[i].trim()) // trim optional
                    .append("_")
                    .append(i + 1);

            if (i < parts.length - 1) {
                result.append(";");
            }
        }

        return result.toString();
    }

    /**
     * Extracts the base product name from a string by removing the suffix
     * after the first underscore ('_').
     *
     * <p>
     * This method splits the input string at the underscore character
     * and returns the first part. It is useful for retrieving the original
     * product name when an index or suffix has been appended.
     * </p>
     *
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * Input:  "Blue Top_1"
     * Output: "Blue Top"
     * </pre>
     *
     * @param input the input string containing a product name with an optional
     *              suffix
     * @return the substring before the first underscore, or the entire string
     *         if no underscore is present
     */
    public static String parseProd(String input) {
        return input.split("_")[0];
    }
}
