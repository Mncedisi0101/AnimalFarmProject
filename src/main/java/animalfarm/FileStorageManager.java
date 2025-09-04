/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalfarm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorageManager {
    private static final String ANIMALS_FILE = "animals.txt";
    private static final String CONSUMPTION_FILE = "consumption.txt";
    
    public FileStorageManager() {
        // Create files if they don't exist
        try {
            new File(ANIMALS_FILE).createNewFile();
            new File(CONSUMPTION_FILE).createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating storage files: " + e.getMessage());
        }
    }
    
    // Save animals to file
    public void saveAnimals(List<Animal> animals) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ANIMALS_FILE))) {
            for (Animal animal : animals) {
                writer.println(animal.getAnimalType() + "," + 
                             animal.getId() + "," + 
                             animal.getName() + "," + 
                             animal.getFoodCostPerKg());
            }
        } catch (IOException e) {
            System.out.println("Error saving animals: " + e.getMessage());
        }
    }
    
    // Load animals from file
    public List<Animal> loadAnimals() {
        List<Animal> animals = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ANIMALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0];
                    String id = parts[1];
                    String name = parts[2];
                    double foodCost = Double.parseDouble(parts[3]);
                    
                    Animal animal = createAnimal(type, name, id, foodCost);
                    if (animal != null) {
                        loadConsumptionRecords(animal);
                        animals.add(animal);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading animals: " + e.getMessage());
        }
        
        return animals;
    }
    
    private Animal createAnimal(String type, String name, String id, double foodCost) {
        switch (type.toLowerCase()) {
            case "cow": return new Cow(name, id, foodCost);
            case "goat": return new Goat(name, id, foodCost);
            case "pig": return new Pig(name, id, foodCost);
            case "dog": return new Dog(name, id, foodCost);
            default: return null;
        }
    }
    
    // Save consumption record
    public void saveConsumptionRecord(String animalId, String date, double amount) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONSUMPTION_FILE, true))) {
            writer.println(animalId + "," + date + "," + amount);
        } catch (IOException e) {
            System.out.println("Error saving consumption record: " + e.getMessage());
        }
    }
    
    // Load consumption records for an animal
    private void loadConsumptionRecords(Animal animal) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONSUMPTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(animal.getId())) {
                    String date = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    animal.recordDailyConsumption(date, amount);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading consumption records: " + e.getMessage());
        }
    }
    
    // Get consumption records for display
    public List<DailyConsumption> getConsumptionRecords(String animalId) {
        List<DailyConsumption> records = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CONSUMPTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(animalId)) {
                    records.add(new DailyConsumption(parts[1], Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException e) {
            System.out.println("Error getting consumption records: " + e.getMessage());
        }
        
        return records;
    }
}