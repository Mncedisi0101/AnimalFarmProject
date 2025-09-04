/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package animalfarm;

import java.util.List;
import java.util.Scanner;

public class AnimalFarm {
    private List<Animal> animals;
    private Scanner scanner;
    private FileStorageManager storageManager;

    public AnimalFarm() {
        scanner = new Scanner(System.in);
        storageManager = new FileStorageManager();
        animals = storageManager.loadAnimals();
        System.out.println("Loaded " + animals.size() + " animals from storage.");
    }
    
    public void addAnimal(Animal animal) {
        animals.add(animal);
        storageManager.saveAnimals(animals); // Save after adding
    }

    public Animal findAnimalById(String id) {
        for (Animal animal : animals) {
            if (animal.getId().equalsIgnoreCase(id)) {
                return animal;
            }
        }
        return null;
    }

    public void displayAllAnimals() {
        System.out.println("\n=== ANIMAL FARM INVENTORY ===");
        if (animals.isEmpty()) {
            System.out.println("No animals in the farm.");
            return;
        }
        
        for (Animal animal : animals) {
            System.out.println(animal);
        }
        System.out.println("=============================");
    }

    public void recordDailyConsumption() {
        System.out.println("\n=== RECORD DAILY CONSUMPTION ===");
        System.out.print("Enter animal ID: ");
        String id = scanner.nextLine();
        
        Animal animal = findAnimalById(id);
        if (animal == null) {
            System.out.println("Animal with ID " + id + " not found.");
            return;
        }
        
        System.out.print("Enter date (YYYY-MM-DD) or press Enter for today: ");
        String date = scanner.nextLine();
        
        System.out.print("Enter food consumed (kg): ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        //Converting the date format
        String consumptionDate = date.isEmpty() ? java.time.LocalDate.now().toString() : date;
        
        animal.recordDailyConsumption(consumptionDate, amount);
        storageManager.saveConsumptionRecord(id, consumptionDate, amount);
        
        System.out.println("Recorded " + amount + "kg for " + animal.getName() + " on " + consumptionDate);
    }

    public void displayFoodConsumptionReport() {
        System.out.println("\n=== FOOD CONSUMPTION REPORT ===");
        
        if (animals.isEmpty()) {
            System.out.println("No animals in the farm.");
            return;
        }
        
        double totalMonthlyFood = 0;
        double totalMonthlyCost = 0;

        for (Animal animal : animals) {
            double monthlyFood = animal.calculateMonthlyFood();
            double monthlyCost = animal.calculateMonthlyCost();

            System.out.printf("%s %s (ID: %s):%n", animal.getAnimalType(), animal.getName(), animal.getId());
            System.out.printf("  Monthly Estimate: %.2fkg (R%.2f)%n", monthlyFood, monthlyCost);
            
            // Show consumption records
            List<DailyConsumption> records = storageManager.getConsumptionRecords(animal.getId());
            if (!records.isEmpty()) {
                System.out.println("  Consumption Records:");
                for (DailyConsumption record : records) {
                    System.out.println("    " + record);
                }
            }
            System.out.println();

            totalMonthlyFood += monthlyFood;
            totalMonthlyCost += monthlyCost;
        }

        System.out.println("=== TOTALS ===");
        System.out.printf("Total Monthly Food Estimate: %.2fkg%n", totalMonthlyFood);
        System.out.printf("Total Monthly Cost Estimate: R%.2f%n", totalMonthlyCost);
    }

    public void displayMenu() {
        System.out.println("\n=== ANIMAL FARM MANAGEMENT SYSTEM ===");
        System.out.println("1. View all animals");
        System.out.println("2. Record daily consumption");
        System.out.println("3. Generate consumption report");
        System.out.println("4. Add new animal");
        System.out.println("5. View animal details");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }
    
    public void viewAnimalDetails() {
        System.out.println("\n=== VIEW ANIMAL DETAILS ===");
        System.out.print("Enter animal ID: ");
        String id = scanner.nextLine();
        
        Animal animal = findAnimalById(id);
        if (animal == null) {
            System.out.println("Animal with ID " + id + " not found.");
            return;
        }
        
        System.out.println("\nAnimal Details:");
        System.out.println("Type: " + animal.getAnimalType());
        System.out.println("Name: " + animal.getName());
        System.out.println("ID: " + animal.getId());
        System.out.println("Food Cost per kg: $" + animal.getFoodCostPerKg());
        System.out.printf("Monthly Food Estimate: %.2fkg%n", animal.calculateMonthlyFood());
        System.out.printf("Monthly Cost Estimate: R%.2f%n", animal.calculateMonthlyCost());
        
        List<DailyConsumption> records = storageManager.getConsumptionRecords(id);
        if (!records.isEmpty()) {
            System.out.println("\nConsumption History:");
            for (DailyConsumption record : records) {
                System.out.println("  " + record);
            }
        } else {
            System.out.println("\nNo consumption records found.");
        }
    }

    public void addNewAnimal() {
        System.out.println("\n=== ADD NEW ANIMAL ===");
        System.out.println("Select animal type:");
        System.out.println("1. Cow");
        System.out.println("2. Goat");
        System.out.println("3. Pig");
        System.out.println("4. Dog");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter animal name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter animal ID: ");
        String id = scanner.nextLine();
        
        // Check if ID already exists
        if (findAnimalById(id) != null) {
            System.out.println("Animal with ID " + id + " already exists.");
            return;
        }
        
        System.out.print("Enter food cost per kg (or press Enter for default): ");
        String costInput = scanner.nextLine();
        
        Animal animal = null;
        if (costInput.isEmpty()) {
            switch (choice) {
                case 1: animal = new Cow(name, id); break;
                case 2: animal = new Goat(name, id); break;
                case 3: animal = new Pig(name, id); break;
                case 4: animal = new Dog(name, id); break;
                default: System.out.println("Invalid choice."); return;
            }
        } else {
            double cost = Double.parseDouble(costInput);
            switch (choice) {
                case 1: animal = new Cow(name, id, cost); break;
                case 2: animal = new Goat(name, id, cost); break;
                case 3: animal = new Pig(name, id, cost); break;
                case 4: animal = new Dog(name, id, cost); break;
                default: System.out.println("Invalid choice."); return;
            }
        }
        
        addAnimal(animal);
        System.out.println("Added " + animal.getAnimalType() + " " + name + " with ID " + id);
    }

    public static void main(String[] args) {
        AnimalFarm farm = new AnimalFarm();
        
        boolean running = true;
        while (running) {
            farm.displayMenu();
            int choice = farm.scanner.nextInt();
            farm.scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    farm.displayAllAnimals();
                    break;
                case 2:
                    farm.recordDailyConsumption();
                    break;
                case 3:
                    farm.displayFoodConsumptionReport();
                    break;
                case 4:
                    farm.addNewAnimal();
                    break;
                case 5:
                    farm.viewAnimalDetails();
                    break;
                case 6:
                    running = false;
                    // Save all data before exiting
                    farm.storageManager.saveAnimals(farm.animals);
                    System.out.println("Goodbye! Data saved to files.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}