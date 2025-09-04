/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalfarm;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal {
    protected String name;
    protected String id;
    protected double foodCostPerKg; // cost per kg of food
    protected List<DailyConsumption> consumptionRecords;

    // Default constructor (required for inheritance)
    public Animal() {
        this.consumptionRecords = new ArrayList<>();
    }
    
    // Parameterized constructor
    public Animal(String name, String id, double foodCostPerKg) {
        this.name = name;
        this.id = id;
        this.foodCostPerKg = foodCostPerKg;
        this.consumptionRecords = new ArrayList<>();
    }

    // Abstract method for animal-specific details
    public abstract String getAnimalType();

    // Record daily consumption with date
    public void recordDailyConsumption(String date, double amount) {
        consumptionRecords.add(new DailyConsumption(date, amount));
    }

    // Record daily consumption for today
    public void recordDailyConsumption(double amount) {
        consumptionRecords.add(new DailyConsumption(amount));
    }

    // Calculate monthly food consumption
    public double calculateMonthlyFood() {
        double total = 0;
        for (DailyConsumption record : consumptionRecords) {
            total += record.getAmount(); // This returns double, not DailyConsumption
        }
        // Project to monthly (assuming 30 days)
        int daysRecorded = consumptionRecords.size();
        if (daysRecorded > 0) {
            double dailyAverage = total / daysRecorded;
            return dailyAverage * 30;
        }
        return 0;
    }

    // Calculate monthly food cost
    public double calculateMonthlyCost() {
        return calculateMonthlyFood() * foodCostPerKg;
    }

    // Get consumption records
    public List<DailyConsumption> getConsumptionRecords() {
        return consumptionRecords;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getFoodCostPerKg() {
        return foodCostPerKg;
    }

    public void setFoodCostPerKg(double foodCostPerKg) {
        this.foodCostPerKg = foodCostPerKg;
    }

    @Override
    public String toString() {
        return String.format("%s: %s (ID: %s) - Estimated Monthly: %.2fkg, Cost: R%.2f",
                getAnimalType(), name, id, calculateMonthlyFood(), calculateMonthlyCost());
    }
}