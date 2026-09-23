import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Integer> freq = new HashMap<>();
        boolean hasData = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("---")) break;
            if (line.isEmpty()) continue;
            int val = Integer.parseInt(line);
            freq.put(val, freq.getOrDefault(val, 0) + 1);
            hasData = true;
        }

        if (!hasData) {
            return;
        }

        int tertinggi = Integer.MIN_VALUE, terendah = Integer.MAX_VALUE;
        for (int v : freq.keySet()) {
            if (v > tertinggi) tertinggi = v;
            if (v < terendah) terendah = v;
        }

        Integer terbanyak = null, tersedikit = null;
        for (int v : freq.keySet()) {
            int f = freq.get(v);
            if (terbanyak == null) {
                terbanyak = v;
            } else {
                int fb = freq.get(terbanyak);
                if (f > fb || (f == fb && v > terbanyak)) terbanyak = v;
            }
            if (tersedikit == null) {
                tersedikit = v;
            } else {
                int fs = freq.get(tersedikit);
                if (f < fs || (f == fs && v < tersedikit)) tersedikit = v;
            }
        }

        Integer jumlahTertinggiVal = null, jumlahTerendahVal = null;
        long jumlahTertinggiHasil = 0, jumlahTerendahHasil = 0;
        for (int v : freq.keySet()) {
            long hasil = (long) v * freq.get(v);
            if (jumlahTertinggiVal == null) {
                jumlahTertinggiVal = v;
                jumlahTertinggiHasil = hasil;
            } else if (hasil > jumlahTertinggiHasil || (hasil == jumlahTertinggiHasil && v > jumlahTertinggiVal)) {
                jumlahTertinggiVal = v;
                jumlahTertinggiHasil = hasil;
            }
            if (jumlahTerendahVal == null) {
                jumlahTerendahVal = v;
                jumlahTerendahHasil = hasil;
            } else if (hasil < jumlahTerendahHasil || (hasil == jumlahTerendahHasil && v < jumlahTerendahVal)) {
                jumlahTerendahVal = v;
                jumlahTerendahHasil = hasil;
            }
        }

        System.out.println("Tertinggi: " + tertinggi);
        System.out.println("Terendah: " + terendah);
        System.out.println("Terbanyak: " + terbanyak + " (" + freq.get(terbanyak) + "x)");
        System.out.println("Tersedikit: " + tersedikit + " (" + freq.get(tersedikit) + "x)");
        System.out.println("Jumlah Tertinggi: " + jumlahTertinggiVal + " * " + freq.get(jumlahTertinggiVal) + " = " + jumlahTertinggiHasil);
        System.out.println("Jumlah Terendah: " + jumlahTerendahVal + " * " + freq.get(jumlahTerendahVal) + " = " + jumlahTerendahHasil);
    }
}
