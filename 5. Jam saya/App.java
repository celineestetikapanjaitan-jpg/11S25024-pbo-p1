import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String jamAwalStr = scanner.nextLine().trim();

        String[] parts = jamAwalStr.split(":", -1);
        if (parts.length != 2) {
            System.out.println("Jam tidak valid");
            return;
        }

        int jam, menit;
        try {
            jam = Integer.parseInt(parts[0].trim());
            menit = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Jam tidak valid");
            return;
        }

        if (jam < 0 || jam > 23 || menit < 0 || menit > 59) {
            System.out.println("Jam tidak valid");
            return;
        }

        String jamAwalFormatted = String.format("%02d:%02d", jam, menit);
        int current = jam * 60 + menit;
        int totalMenit = 0;
        int perganti = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("---")) break;
            if (line.isEmpty()) continue;

            if (line.length() < 2 || (line.charAt(0) != '+' && line.charAt(0) != '-')) {
                System.out.println("Perintah tidak valid");
                continue;
            }

            int n;
            try {
                n = Integer.parseInt(line.substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Perintah tidak valid");
                continue;
            }

            int delta = line.charAt(0) == '+' ? n : -n;
            totalMenit += delta;
            current += delta;

            while (current >= 1440) {
                current -= 1440;
                perganti++;
            }
            while (current < 0) {
                current += 1440;
                perganti++;
            }
        }

        int jamAkhir = current / 60;
        int menitAkhir = current % 60;
        String jamAkhirFormatted = String.format("%02d:%02d", jamAkhir, menitAkhir);

        String totalMenitStr;
        if (totalMenit > 0) totalMenitStr = "+" + totalMenit;
        else totalMenitStr = String.valueOf(totalMenit);

        System.out.println("Jam Awal: " + jamAwalFormatted);
        System.out.println("Jam Akhir: " + jamAkhirFormatted);
        System.out.println("Total Menit: " + totalMenitStr);
        System.out.println("Pergantian Hari: " + perganti);
    }
}
