/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalfarm;

public class Cow extends Animal {
    private static final double DEFAULT_FOOD_COST = 0.50; // $ per kg

    // Default constructor
    public Cow() {
        super();
    }
    
    // Parameterized constructor
    public Cow(String name, String id) {
        super(name, id, DEFAULT_FOOD_COST);
    }

    public Cow(String name, String id, double foodCost) {
        super(name, id, foodCost);
    }

    @Override
    public String getAnimalType() {
        return "Cow";
    }
}