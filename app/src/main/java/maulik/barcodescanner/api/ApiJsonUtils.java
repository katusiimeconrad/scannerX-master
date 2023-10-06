package maulik.barcodescanner.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ApiJsonUtils {

    private static final Gson gson = new GsonBuilder().create();

    // Create a custom deserializer for the Date type
    // Define your custom date format
    static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    static JsonDeserializer<Date> dateDeserializer = (json, typeOfT, context) -> {
        try {
            String dateString = json.getAsString();
            DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date: " + json.getAsString(), e);
        }
    };

    static JsonSerializer<Date> dateSerializer = (date, typeOfSrc, context) -> {
        DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return gson.toJsonTree(formatter.format(date));
    };

}
