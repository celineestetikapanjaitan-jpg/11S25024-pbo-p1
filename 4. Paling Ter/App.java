import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static final String TANDA_SELESAI = "---";
    private static final String DATA_TIDAK_VALID = "Data tidak valid, baris dilewati: ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Integer> freq = bacaFrekuensi(scanner);

        if (freq.isEmpty()) {
            return;
        }

        int[] ekstrem = cariNilaiTertinggiTerendah(freq);
        int tertinggi = ekstrem[0];
        int terendah = ekstrem[1];

        int terbanyak = cariBerdasarkanFrekuensi(freq, true);
        int tersedikit = cariBerdasarkanFrekuensi(freq, false);

        long[] hasilTerbesar = cariBerdasarkanHasilKali(freq, true);
        long[] hasilTerkecil = cariBerdasarkanHasilKali(freq, false);

        System.out.println("Tertinggi: " + tertinggi);
        System.out.println("Terendah: " + terendah);
        System.out.println("Terbanyak: " + terbanyak + " (" + freq.get(terbanyak) + "x)");
        System.out.println("Tersedikit: " + tersedikit + " (" + freq.get(tersedikit) + "x)");
        System.out.println("Jumlah Tertinggi: " + hasilTerbesar[0] + " * " + freq.get((int) hasilTerbesar[0])
                + " = " + hasilTerbesar[1]);
        System.out.println("Jumlah Terendah: " + hasilTerkecil[0] + " * " + freq.get((int) hasilTerkecil[0])
                + " = " + hasilTerkecil[1]);
    }

    // Membaca angka satu per baris sampai menemukan "---", sambil menghitung frekuensi kemunculannya.
    // Baris yang bukan angka bulat dilewati (tidak menghentikan program) agar satu baris rusak
    // tidak membuat seluruh input gagal diproses.
    private static Map<Integer, Integer> bacaFrekuensi(Scanner scanner) {
        Map<Integer, Integer> freq = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals(TANDA_SELESAI)) break;
            if (line.isEmpty()) continue;

            try {
                int val = Integer.parseInt(line);
                freq.put(val, freq.getOrDefault(val, 0) + 1);
            } catch (NumberFormatException e) {
                System.out.println(DATA_TIDAK_VALID + line);
            }
        }
        return freq;
    }

    private static int[] cariNilaiTertinggiTerendah(Map<Integer, Integer> freq) {
        int tertinggi = Integer.MIN_VALUE;
        int terendah = Integer.MAX_VALUE;
        for (int v : freq.keySet()) {
            if (v > tertinggi) tertinggi = v;
            if (v < terendah) terendah = v;
        }
        return new int[]{tertinggi, terendah};
    }

    // Mencari nilai dengan frekuensi ter-banyak/ter-sedikit.
    // Tie-break: jika frekuensinya sama, nilai yang lebih besar dipilih untuk "terbanyak",
    // dan nilai yang lebih kecil dipilih untuk "tersedikit".
    private static int cariBerdasarkanFrekuensi(Map<Integer, Integer> freq, boolean cariTerbanyak) {
        Integer terpilih = null;
        for (int v : freq.keySet()) {
            if (terpilih == null) {
                terpilih = v;
                continue;
            }
            int frekuensiV = freq.get(v);
            int frekuensiTerpilih = freq.get(terpilih);
            boolean lebihUtama = cariTerbanyak
                    ? (frekuensiV > frekuensiTerpilih || (frekuensiV == frekuensiTerpilih && v > terpilih))
                    : (frekuensiV < frekuensiTerpilih || (frekuensiV == frekuensiTerpilih && v < terpilih));
            if (lebihUtama) terpilih = v;
        }
        return terpilih;
    }

    // Mencari nilai dengan hasil kali (nilai * frekuensi) ter-besar/ter-kecil.
    // Tie-break: jika hasil kalinya sama, nilai yang lebih besar dipilih untuk "tertinggi",
    // dan nilai yang lebih kecil dipilih untuk "terendah".
    private static long[] cariBerdasarkanHasilKali(Map<Integer, Integer> freq, boolean cariTerbesar) {
        Integer nilaiTerpilih = null;
        long hasilTerpilih = 0;
        for (int v : freq.keySet()) {
            long hasil = (long) v * freq.get(v);
            if (nilaiTerpilih == null) {
                nilaiTerpilih = v;
                hasilTerpilih = hasil;
                continue;
            }
            boolean lebihUtama = cariTerbesar
                    ? (hasil > hasilTerpilih || (hasil == hasilTerpilih && v > nilaiTerpilih))
                    : (hasil < hasilTerpilih || (hasil == hasilTerpilih && v < nilaiTerpilih));
            if (lebihUtama) {
                nilaiTerpilih = v;
                hasilTerpilih = hasil;
            }
        }
        return new long[]{nilaiTerpilih, hasilTerpilih};
    }
}
