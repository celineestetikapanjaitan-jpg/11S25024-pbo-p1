import java.util.Scanner;

public class App {

    private static final String TIDAK_ADA = "Tidak Ada";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim());
        int[][] matrix = bacaMatrix(scanner, n);

        if (n == 1) {
            cetakKasusKecil(matrix[0][0]);
            return;
        }
        if (n == 2) {
            cetakKasusKecil(jumlahkanSemua(matrix, n));
            return;
        }

        long nilaiL = hitungNilaiL(matrix, n);
        long nilaiKebalikanL = hitungNilaiKebalikanL(matrix, n);
        long nilaiTengah = hitungNilaiTengah(matrix, n);

        long perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
        // Jika L dan kebalikannya sama besar, nilai tengah menjadi penentu (tie-breaker) dominan.
        long dominan = (perbedaan == 0) ? nilaiTengah : Math.max(nilaiL, nilaiKebalikanL);

        System.out.println("Nilai L: " + nilaiL);
        System.out.println("Nilai Kebalikan L: " + nilaiKebalikanL);
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + perbedaan);
        System.out.println("Dominan: " + dominan);
    }

    private static int[][] bacaMatrix(Scanner scanner, int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] tokens = scanner.nextLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(tokens[j]);
            }
        }
        return matrix;
    }

    // Untuk matriks 1x1 atau 2x2, tidak ada bentuk L yang bisa dibentuk;
    // seluruh nilai matriks dipakai sebagai "nilai tengah" sekaligus dominan.
    private static void cetakKasusKecil(int nilaiTengah) {
        System.out.println("Nilai L: " + TIDAK_ADA);
        System.out.println("Nilai Kebalikan L: " + TIDAK_ADA);
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + TIDAK_ADA);
        System.out.println("Dominan: " + nilaiTengah);
    }

    private static int jumlahkanSemua(int[][] matrix, int n) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                total += matrix[i][j];
            }
        }
        return total;
    }

    // Bentuk L: seluruh kolom pertama (atas ke bawah) ditambah baris terakhir
    // (dari kolom kedua sampai kolom kedua-dari-belakang, agar sudut tidak dihitung dobel).
    private static long hitungNilaiL(int[][] matrix, int n) {
        long nilaiL = 0;
        for (int i = 0; i < n; i++) {
            nilaiL += matrix[i][0];
        }
        for (int c = 1; c <= n - 2; c++) {
            nilaiL += matrix[n - 1][c];
        }
        return nilaiL;
    }

    // Bentuk kebalikan L: cerminan dari bentuk L, yaitu kolom terakhir ditambah baris pertama.
    private static long hitungNilaiKebalikanL(int[][] matrix, int n) {
        long nilaiKebalikanL = 0;
        for (int i = 0; i < n; i++) {
            nilaiKebalikanL += matrix[i][n - 1];
        }
        for (int c = 1; c <= n - 2; c++) {
            nilaiKebalikanL += matrix[0][c];
        }
        return nilaiKebalikanL;
    }

    // Untuk ukuran ganjil, nilai tengah adalah satu sel di pusat matriks.
    // Untuk ukuran genap, tidak ada satu sel pusat, sehingga 4 sel di tengah dijumlahkan.
    private static long hitungNilaiTengah(int[][] matrix, int n) {
        if (n % 2 == 1) {
            return matrix[n / 2][n / 2];
        }
        int mid1 = n / 2 - 1;
        int mid2 = n / 2;
        return (long) matrix[mid1][mid1] + matrix[mid1][mid2] + matrix[mid2][mid1] + matrix[mid2][mid2];
    }
}
