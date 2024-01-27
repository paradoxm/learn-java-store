package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        addLaptopsToStore(store);

        Map<LaptopProperties, Object> filters = getFiltersFromUser(store);
        Set<Laptop> filteredLaptops = store.filterLaptops(filters);

        if (filteredLaptops.isEmpty()) {
            System.out.println("По заданным критериям ноутбуки не найдены.");
        } else {
            System.out.println("Filtered Laptops:");
            for (Laptop laptop : filteredLaptops) {
                System.out.println(laptop);
            }
        }
    }

    private static void addLaptopsToStore(Store store) {
        store.addLaptop(new Laptop("Brand1", 8, 512, "Windows", "Black"));
        store.addLaptop(new Laptop("Brand2", 16, 1024, "MacOS", "Silver"));
        store.addLaptop(new Laptop("Brand3", 4, 256, "Linux", "White"));
        store.addLaptop(new Laptop("Brand4", 8, 512, "Windows", "Blue"));
        store.addLaptop(new Laptop("Brand5", 16, 1024, "MacOS", "Gray"));
        store.addLaptop(new Laptop("Brand6", 4, 256, "Linux", "Red"));
        store.addLaptop(new Laptop("Brand7", 8, 512, "Windows", "Green"));
        store.addLaptop(new Laptop("Brand8", 16, 1024, "MacOS", "Yellow"));
        store.addLaptop(new Laptop("Brand9", 4, 256, "Linux", "Purple"));
        store.addLaptop(new Laptop("Brand10", 8, 512, "Windows", "Orange"));
    }

    private static Map<LaptopProperties, Object> getFiltersFromUser(Store store) {
        Scanner scanner = new Scanner(System.in);
        Map<LaptopProperties, Object> filters = new HashMap<>();

        System.out.println("Введите номера критериев через запятую:");
        System.out.println("1 - ОЗУ (введите диапазон, например, " + store.getRamRange() + ")");
        System.out.println("2 - Объем ЖД (введите диапазон, например, " + store.getStorageRange() + ")");
        System.out.println("3 - Операционная система");
        System.out.println("4 - Цвет");

        String[] choices = scanner.nextLine().split(",");

        for (String choice : choices) {
            String trimmedChoice = choice.trim();

            switch (trimmedChoice) {
                case "1":
                    System.out.print("Введите диапазон ОЗУ (например, " + store.getRamRange() + "): ");
                    String ramRange = scanner.next();
                    String[] ramValues = ramRange.split("-");
                    int minRam = Integer.parseInt(ramValues[0]);
                    Integer maxRam = ramValues.length > 1 ? Integer.parseInt(ramValues[1]) : null;
                    filters.put(LaptopProperties.RAM, new Range(minRam, maxRam));
                    break;
                case "2":
                    System.out.print("Введите диапазон объема жесткого диска (например, " + store.getStorageRange() + "): ");
                    String storageRange = scanner.next();
                    String[] storageValues = storageRange.split("-");
                    int minStorage = Integer.parseInt(storageValues[0]);
                    Integer maxStorage = storageValues.length > 1 ? Integer.parseInt(storageValues[1]) : null;
                    filters.put(LaptopProperties.STORAGE, new Range(minStorage, maxStorage));
                    break;
                case "3":
                    Set<String> osValues = store.getUniqueOSValues();
                    System.out.print("Введите операционную систему " + osValues + ": ");
                    String os = scanner.next();
                    filters.put(LaptopProperties.OS, os);
                    break;
                case "4":
                    Set<String> colorValues = store.getUniqueColorValues();
                    System.out.print("Введите цвет " + colorValues + ": ");
                    String color = scanner.next();
                    filters.put(LaptopProperties.COLOR, color);
                    break;
                default:
                    System.out.println("Неверный выбор критерия: " + trimmedChoice);
                    break;
            }
        }

        return filters;
    }
}

