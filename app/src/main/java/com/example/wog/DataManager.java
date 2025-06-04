package com.example.wog;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataManager {
    public static String userName;
    public static String cardNumber;
    public static double bonusBalance;
    public static double fuelBalance;
    public static int coffeeCount;
    public static final double BONUS_RATE_FUEL = 2.5;
    public static final double BONUS_RATE_GAS = 1.5;
    public static Map<String, Double> fuelPrices = new LinkedHashMap<>();

    // Ініціалізація всіх даних
    public static void init(Context context) {
        loadUserData(context);
        loadFuelPrices(context);
    }

    // Завантаження даних користувача
    public static void loadUserData(Context context) {
        try {
            InputStream is = context.getAssets().open("user_data.json");
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len;
            while ((len = is.read(data)) != -1) {
                buffer.write(data, 0, len);
            }
            String jsonStr = buffer.toString("UTF-8");
            JSONObject json = new JSONObject(jsonStr);
            userName = json.optString("ім'я");
            cardNumber = json.optString("карта");
            bonusBalance = json.optDouble("баланс");
            fuelBalance = json.optDouble("залишок_пального");
            coffeeCount = json.optInt("кава");
            is.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    // Завантаження цін на пальне
    public static void loadFuelPrices(Context context) {
        try {
            InputStream is = context.getAssets().open("fuel_prices.json");
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len;
            while ((len = is.read(data)) != -1) {
                buffer.write(data, 0, len);
            }
            String jsonStr = buffer.toString("UTF-8");
            JSONArray arr = new JSONArray(jsonStr);
            fuelPrices.clear();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String name = obj.getString("назва");
                double price = obj.getDouble("ціна");
                fuelPrices.put(name, price);
            }
            is.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    // Емуляція відповіді від WOG PAY API
    public static String simulatePayment(Context context) {
        try {
            InputStream is = context.getAssets().open("pay_response.json");
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[512];
            int len;
            while ((len = is.read(data)) != -1) {
                buffer.write(data, 0, len);
            }
            String jsonStr = buffer.toString("UTF-8");
            JSONObject json = new JSONObject(jsonStr);
            String status = json.optString("статус");
            String message = json.optString("повідомлення");
            is.close();
            if ("успішно".equalsIgnoreCase(status)) {
                return message;
            } else {
                return "Помилка оплати";
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return "Помилка оплати";
        }
    }
}
