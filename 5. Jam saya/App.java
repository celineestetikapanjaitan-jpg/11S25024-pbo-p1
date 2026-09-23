import java.util.Scanner;

public class App {

    private static final int MENIT_PER_JAM = 60;
    private static final int JAM_PER_HARI = 24;
    private static final int MENIT_PER_HARI = JAM_PER_HARI * MENIT_PER_JAM; // 1440
    private static final String TANDA_SELESAI = "---";
    private static final String JAM_TIDAK_VALID = "Jam tidak valid";
    private static final String PERINTAH_TIDAK_VALID = "Perintah tidak valid";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] jamAwal = bacaJamAwal(scanner);
        if (jamAwal == null) {
            System.out.println(JAM_TIDAK_VALID);
            return;
        }

        int jam = jamAwal[0];
        int menit = jamAwal[1];
        String jamAwalFormatted = formatJam(jam, menit);
        int current = jam * MENIT_PER_JAM + menit;

        int[] totalDanPergantian = prosesPerintah(scanner, current);
        int totalMenit = totalDanPergantian[0];
        int currentAkhir = totalDanPergantian[1];
        int perganti = totalDanPergantian[2];

        String jamAkhirFormatted = formatJam(currentAkhir / MENIT_PER_JAM, currentAkhir % MENIT_PER_JAM);
        String totalMenitStr = (totalMenit > 0 ? "+" : "") + totalMenit;

        System.out.println("Jam Awal: " + jamAwalFormatted);
        System.out.println("Jam Akhir: " + jamAkhirFormatted);
        System.out.println("Total Menit: " + totalMenitStr);
        System.out.println("Pergantian Hari: " + perganti);
    }

    // Membaca dan memvalidasi format "JJ:MM". Mengembalikan null jika format atau nilainya tidak valid.
    private static int[] bacaJamAwal(Scanner scanner) {
        String jamAwalStr = scanner.nextLine().trim();
        String[] parts = jamAwalStr.split(":", -1);
        if (parts.length != 2) {
            return null;
        }

        int jam, menit;
        try {
            jam = Integer.parseInt(parts[0].trim());
            menit = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            return null;
        }

        if (jam < 0 || jam >= JAM_PER_HARI || menit < 0 || menit >= MENIT_PER_JAM) {
            return null;
        }
        return new int[]{jam, menit};
    }

    private static String formatJam(int jam, int menit) {
        return String.format("%02d:%02d", jam, menit);
    }

    // Membaca perintah "+N" / "-N" sampai "---". Setiap kali jam "keluar" dari rentang
    // 0..1439 menit, itu berarti berganti hari, sehingga pembungkusan (wrap-around)
    // dilakukan sambil menghitung berapa kali pergantian hari terjadi.
    private static int[] prosesPerintah(Scanner scanner, int current) {
        int totalMenit = 0;
        int perganti = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals(TANDA_SELESAI)) break;
            if (line.isEmpty()) continue;

            if (line.length() < 2 || (line.charAt(0) != '+' && line.charAt(0) != '-')) {
                System.out.println(PERINTAH_TIDAK_VALID);
                continue;
            }

            int n;
            try {
                n = Integer.parseInt(line.substring(1));
            } catch (NumberFormatException e) {
                System.out.println(PERINTAH_TIDAK_VALID);
                continue;
            }

            int delta = line.charAt(0) == '+' ? n : -n;
            totalMenit += delta;
            current += delta;

            while (current >= MENIT_PER_HARI) {
                current -= MENIT_PER_HARI;
                perganti++;
            }
            while (current < 0) {
                current += MENIT_PER_HARI;
                perganti++;
            }
        }

        return new int[]{totalMenit, current, perganti};
    }
}
