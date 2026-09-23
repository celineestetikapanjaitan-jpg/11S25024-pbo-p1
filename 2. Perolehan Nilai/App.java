import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] names = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
        String[] symbols = {"PA", "T", "K", "P", "UTS", "UAS"};

        int[] bobotDeclared = new int[6];
        int totalBobotDeclared = 0;
        for (int i = 0; i < 6; i++) {
            bobotDeclared[i] = Integer.parseInt(scanner.nextLine().trim());
            totalBobotDeclared += bobotDeclared[i];
        }

        if (totalBobotDeclared != 100) {
            System.out.println("Total bobot harus 100");
            return;
        }

        Map<String, Integer> symbolIndex = new HashMap<>();
        for (int i = 0; i < symbols.length; i++) {
            symbolIndex.put(symbols[i], i);
        }

        int[] totalBobotData = new int[6];
        int[] totalPerolehanData = new int[6];

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().equals("---")) {
                break;
            }

            String[] parts = line.split("\\|", -1);
            if (parts.length != 3) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            String simbol = parts[0].trim();
            String bobotStr = parts[1].trim();
            String perolehanStr = parts[2].trim();

            int bobot, perolehan;
            try {
                bobot = Integer.parseInt(bobotStr);
                perolehan = Integer.parseInt(perolehanStr);
            } catch (NumberFormatException e) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            if (!symbolIndex.containsKey(simbol)) {
                System.out.println("Simbol tidak dikenal");
                continue;
            }

            if (perolehan > bobot) perolehan = bobot;
            if (perolehan < 0) perolehan = 0;

            int idx = symbolIndex.get(simbol);
            totalBobotData[idx] += bobot;
            totalPerolehanData[idx] += perolehan;
        }

        System.out.println("Perolehan Nilai:");
        double nilaiAkhir = 0;
        for (int i = 0; i < 6; i++) {
            int persentase;
            if (totalBobotData[i] == 0) {
                persentase = 0;
            } else {
                persentase = (totalPerolehanData[i] * 100) / totalBobotData[i];
            }
            double kontribusi = (persentase / 100.0) * bobotDeclared[i];
            nilaiAkhir += kontribusi;
            System.out.printf(Locale.US, ">> %s: %d/100 (%.2f/%d)%n", names[i], persentase, kontribusi, bobotDeclared[i]);
        }

        System.out.println();
        System.out.printf(Locale.US, ">> Nilai Akhir: %.2f%n", nilaiAkhir);

        double nilaiAkhirBulat = Math.round(nilaiAkhir * 100.0) / 100.0;

        String grade;
        if (nilaiAkhirBulat >= 79.5) grade = "A";
        else if (nilaiAkhirBulat >= 72) grade = "AB";
        else if (nilaiAkhirBulat >= 64.5) grade = "B";
        else if (nilaiAkhirBulat >= 57) grade = "BC";
        else if (nilaiAkhirBulat >= 49.5) grade = "C";
        else if (nilaiAkhirBulat >= 34) grade = "D";
        else grade = "E";

        System.out.println(">> Grade: " + grade);
    }
}
