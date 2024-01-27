package org.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Store {
    private Set<Laptop> laptops;
    private Range ramRange;
    private Range storageRange;

    public Store() {
        this.laptops = new HashSet<>();
    }

    public void addLaptop(Laptop laptop) {
        laptops.add(laptop);
    }

    public Set<Laptop> getLaptops() {
        return Collections.unmodifiableSet(laptops);
    }

    public Set<Laptop> filterLaptops(Map<LaptopProperties, Object> filters) {
        Set<Laptop> filteredLaptops = new HashSet<>(laptops);

        for (Laptop laptop : laptops) {
            boolean meetsCriteria = true;

            for (LaptopProperties criteria : filters.keySet()) {
                switch (criteria) {
                    case RAM:
                        Range ramRange = (Range) filters.get(criteria);
                        int laptopRam = laptop.getRam();
                        if (laptopRam < ramRange.getMin() || (ramRange.getMax() != null && laptopRam > ramRange.getMax())) {
                            meetsCriteria = false;
                        }
                        break;
                    case STORAGE:
                        Range storageRange = (Range) filters.get(criteria);
                        int laptopStorage = laptop.getStorage();
                        if (laptopStorage < storageRange.getMin() || (storageRange.getMax() != null && laptopStorage > storageRange.getMax())) {
                            meetsCriteria = false;
                        }
                        break;
                    case OS:
                        String osFilter = (String) filters.get(criteria);
                        if (!laptop.getOs().equalsIgnoreCase(osFilter)) {
                            meetsCriteria = false;
                        }
                        break;
                    case COLOR:
                        String colorFilter = (String) filters.get(criteria);
                        if (!laptop.getColor().equalsIgnoreCase(colorFilter)) {
                            meetsCriteria = false;
                        }
                        break;
                    default:
                        System.out.println("Неверный критерий фильтрации: " + criteria);
                        meetsCriteria = false;
                        break;
                }

                if (!meetsCriteria) {
                    break;
                }
            }

            if (!meetsCriteria) {
                filteredLaptops.remove(laptop);
            }
        }

        return filteredLaptops;
    }

    public Set<String> getUniqueOSValues() {
        Set<String> osValues = new HashSet<>();
        for (Laptop laptop : laptops) {
            osValues.add(laptop.getOs());
        }
        return osValues;
    }

    public Set<String> getUniqueColorValues() {
        Set<String> colorValues = new HashSet<>();
        for (Laptop laptop : laptops) {
            colorValues.add(laptop.getColor());
        }
        return colorValues;
    }

    public String getRamRange() {
        if (ramRange == null) {
            calculateRanges();
        }
        return ramRange.getMin() + "-" + ramRange.getMax();
    }

    public String getStorageRange() {
        if (storageRange == null) {
            calculateRanges();
        }
        return storageRange.getMin() + "-" + storageRange.getMax();
    }

    private void calculateRanges() {
        int minRam = Integer.MAX_VALUE;
        int maxRam = Integer.MIN_VALUE;
        int minStorage = Integer.MAX_VALUE;
        int maxStorage = Integer.MIN_VALUE;

        for (Laptop laptop : laptops) {
            int laptopRam = laptop.getRam();
            minRam = Math.min(minRam, laptopRam);
            maxRam = Math.max(maxRam, laptopRam);

            int laptopStorage = laptop.getStorage();
            minStorage = Math.min(minStorage, laptopStorage);
            maxStorage = Math.max(maxStorage, laptopStorage);
        }

        ramRange = new Range(minRam, maxRam);
        storageRange = new Range(minStorage, maxStorage);
    }
}