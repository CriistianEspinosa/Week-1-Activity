public class TemperatureAnalyzer {

    public static class TemperatureReport {
        public double maxTemp;
        public double minTemp;
        public double average;
        public int daysAboveAverage;

        public TemperatureReport(double maxTemp, double minTemp, double average, int daysAboveAverage) {
            this.maxTemp = maxTemp;
            this.minTemp = minTemp;
            this.average = average;
            this.daysAboveAverage = daysAboveAverage;
        }

        @Override
        public String toString() {
            return String.format("{ maxTemp: %.1f, minTemp: %.1f, average: %.2f, daysAboveAverage: %d }", 
                                 maxTemp, minTemp, average, daysAboveAverage);
        }
    }

    public static TemperatureReport analyzeTemperatures(Double[] temps) {
        if (temps == null || temps.length != 30) {
            throw new IllegalArgumentException("Validation Error: The record must contain exactly 30 days of data.");
        }

        double maxTemp = Double.NEGATIVE_INFINITY;
        double minTemp = Double.POSITIVE_INFINITY;
        double sum = 0.0;

        for (int i = 0; i < temps.length; i++) {
            Double currentTemp = temps[i];

            if (currentTemp == null || currentTemp < -50 || currentTemp > 60) {
                throw new IllegalArgumentException("Validation Error: Invalid temperature data at day " + (i + 1));
            }

            if (currentTemp > maxTemp) {
                maxTemp = currentTemp;
            }
            
            if (currentTemp < minTemp) {
                minTemp = currentTemp;
            }

            sum += currentTemp;
        }

        double average = sum / temps.length;
        int daysAboveAverage = 0;

        for (int i = 0; i < temps.length; i++) {
            if (temps[i] > average) {
                daysAboveAverage++;
            }
        }

        return new TemperatureReport(maxTemp, minTemp, average, daysAboveAverage);
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1: Normal Case (30 valid days) ---");
        Double[] validMonth = {
            12.0, 14.0, 15.0, 13.0, 16.0, 18.0, 20.0, 22.0, 21.0, 19.0, 
            17.0, 15.0, 14.0, 12.0, 10.0,  9.0,  8.0, 11.0, 13.0, 15.0, 
            18.0, 20.0, 23.0, 25.0, 24.0, 22.0, 19.0, 16.0, 14.0, 12.0
        };
        try {
            TemperatureReport report = analyzeTemperatures(validMonth);
            System.out.println("Result: " + report);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- Test 2: Incomplete records ---");
        Double[] incompleteMonth = { 15.0, 20.0 };
        try {
            analyzeTemperatures(incompleteMonth);
        } catch (Exception e) {
            System.out.println("Result: " + e.getMessage());
        }

        System.out.println("\n--- Test 3: Out of range data ---");
        Double[] invalidMonth = validMonth.clone();
        invalidMonth[15] = 100.0;
        try {
            analyzeTemperatures(invalidMonth);
        } catch (Exception e) {
            System.out.println("Result: " + e.getMessage());
        }
    }
}