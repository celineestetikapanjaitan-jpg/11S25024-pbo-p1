import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static final int JUMLAH_KOMPONEN = 6;
    private static final int BOBOT_TOTAL_WAJIB = 100;
    private static final int PERSENTASE_MAKS = 100;
    private static final String TANDA_SELESAI = "---";
    private static final String FORMAT_SALAH =
            "Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai";
    private static final String BOBOT_TIDAK_VALID = "Bobot harus berupa angka bulat";

    // Batas bawah nilai akhir untuk tiap grade (dicek dari yang tertinggi ke terendah).
    private static final double BATAS_A = 79.5;
    private static final double BATAS_AB = 72;
    private static final double BATAS_B = 64.5;
    private static final double BATAS_BC = 57;
    private static final double BATAS_C = 49.5;
    private static final double BATAS_D = 34;

    private static final String[] NAMA_KOMPONEN = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
    private static final String[] SIMBOL_KOMPONEN = {"PA", "T", "K", "P", "UTS", "UAS"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] bobotDeclared = bacaBobot(scanner);
        if (bobotDeclared == null) {
            System.out.println(BOBOT_TIDAK_VALID);
            return;
        }

        int totalBobotDeclared = jumlahkan(bobotDeclared);
        if (totalBobotDeclared != BOBOT_TOTAL_WAJIB) {
            System.out.println("Total bobot harus " + BOBOT_TOTAL_WAJIB);
            return;
        }

        Map<String, Integer> symbolIndex = buatIndeksSimbol();
        int[] totalBobotData = new int[JUMLAH_KOMPONEN];
        int[] totalPerolehanData = new int[JUMLAH_KOMPONEN];
        prosesBarisNilai(scanner, symbolIndex, totalBobotData, totalPerolehanData);

        double nilaiAkhir = cetakPerolehanPerKomponen(bobotDeclared, totalBobotData, totalPerolehanData);

        System.out.println();
        System.out.printf(Locale.US, ">> Nilai Akhir: %.2f%n", nilaiAkhir);
        System.out.println(">> Grade: " + tentukanGrade(nilaiAkhir));
    }

    // Mengembalikan null jika salah satu baris bobot bukan angka bulat, agar caller
    // bisa menampilkan pesan yang jelas alih-alih program berhenti dengan exception.
    private static int[] bacaBobot(Scanner scanner) {
        int[] bobot = new int[JUMLAH_KOMPONEN];
        for (int i = 0; i < JUMLAH_KOMPONEN; i++) {
            try {
                bobot[i] = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return bobot;
    }

    private static int jumlahkan(int[] nilai) {
        int total = 0;
        for (int v : nilai) total += v;
        return total;
    }

    private static Map<String, Integer> buatIndeksSimbol() {
        Map<String, Integer> symbolIndex = new HashMap<>();
        for (int i = 0; i < SIMBOL_KOMPONEN.length; i++) {
            symbolIndex.put(SIMBOL_KOMPONEN[i], i);
        }
        return symbolIndex;
    }

    // Membaca baris "Simbol|Bobot|Perolehan" sampai menemukan "---", lalu mengakumulasi
    // bobot dan perolehan per komponen (nilai bisa muncul lebih dari sekali per komponen).
    private static void prosesBarisNilai(Scanner scanner, Map<String, Integer> symbolIndex,
                                          int[] totalBobotData, int[] totalPerolehanData) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().equals(TANDA_SELESAI)) {
                break;
            }

            String[] parts = line.split("\\|", -1);
            if (parts.length != 3) {
                System.out.println(FORMAT_SALAH);
                continue;
            }

            String simbol = parts[0].trim();
            int bobot, perolehan;
            try {
                bobot = Integer.parseInt(parts[1].trim());
                perolehan = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.out.println(FORMAT_SALAH);
                continue;
            }

            if (!symbolIndex.containsKey(simbol)) {
                System.out.println("Simbol tidak dikenal");
                continue;
            }

            // Perolehan tidak boleh melebihi bobot atau bernilai negatif.
            perolehan = Math.min(Math.max(perolehan, 0), bobot);

            int idx = symbolIndex.get(simbol);
            totalBobotData[idx] += bobot;
            totalPerolehanData[idx] += perolehan;
        }
    }

    private static double cetakPerolehanPerKomponen(int[] bobotDeclared, int[] totalBobotData,
                                                      int[] totalPerolehanData) {
        System.out.println("Perolehan Nilai:");
        double nilaiAkhir = 0;
        for (int i = 0; i < JUMLAH_KOMPONEN; i++) {
            int persentase = totalBobotData[i] == 0
                    ? 0
                    : (totalPerolehanData[i] * PERSENTASE_MAKS) / totalBobotData[i];
            double kontribusi = (persentase / (double) PERSENTASE_MAKS) * bobotDeclared[i];
            nilaiAkhir += kontribusi;
            System.out.printf(Locale.US, ">> %s: %d/%d (%.2f/%d)%n",
                    NAMA_KOMPONEN[i], persentase, PERSENTASE_MAKS, kontribusi, bobotDeclared[i]);
        }
        return nilaiAkhir;
    }

    // Grade ditentukan dari batas tertinggi ke terendah berdasarkan nilai akhir yang sudah dibulatkan.
    private static String tentukanGrade(double nilaiAkhir) {
        double nilaiBulat = Math.round(nilaiAkhir * 100.0) / 100.0;
        if (nilaiBulat >= BATAS_A) return "A";
        if (nilaiBulat >= BATAS_AB) return "AB";
        if (nilaiBulat >= BATAS_B) return "B";
        if (nilaiBulat >= BATAS_BC) return "BC";
        if (nilaiBulat >= BATAS_C) return "C";
        if (nilaiBulat >= BATAS_D) return "D";
        return "E";
    }
}
