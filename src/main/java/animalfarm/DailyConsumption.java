/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalfarm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyConsumption {
    private String date;
    private double amount;

    public DailyConsumption(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public DailyConsumption(double amount) {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("Date: %s, Amount: %.2fkg", date, amount);
    }
}
