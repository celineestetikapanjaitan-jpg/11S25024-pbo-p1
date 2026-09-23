import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    // Panjang NIM yang valid: 3 digit kode prodi + 2 digit tahun masuk + 3 digit nomor urut.
    private static final int PANJANG_NIM = 8;
    private static final int PANJANG_KODE_PRODI = 3;
    private static final int PANJANG_KODE_TAHUN = 2;
    private static final String AWALAN_TAHUN = "20"; // NIM hanya menyimpan 2 digit terakhir tahun angkatan.
    private static final String NIM_TIDAK_VALID = "NIM tidak valid";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nim = bacaNim(scanner);

        if (!validasiPanjang(nim)) {
            System.out.println("NIM harus 8 karakter");
            return;
        }

        Map<String, String> prodiMap = buatPetaProdi();
        String kodeProdi = nim.substring(0, PANJANG_KODE_PRODI);

        if (!prodiMap.containsKey(kodeProdi)) {
            System.out.println("Kode tidak tersedia");
            return;
        }

        cetakInformasi(nim, kodeProdi, prodiMap);
    }

    private static String bacaNim(Scanner scanner) {
        return scanner.nextLine();
    }

    private static boolean validasiPanjang(String nim) {
        return nim.length() == PANJANG_NIM;
    }

    // Memetakan 3 digit pertama NIM ke nama program studi.
    private static Map<String, String> buatPetaProdi() {
        Map<String, String> prodiMap = new HashMap<>();
        prodiMap.put("11S", "Sarjana Informatika");
        prodiMap.put("12S", "Sarjana Sistem Informasi");
        prodiMap.put("13S", "Sarjana Teknik Elektro");
        prodiMap.put("21S", "Sarjana Manajemen Rekayasa");
        prodiMap.put("22S", "Sarjana Teknik Metalurgi");
        prodiMap.put("31S", "Sarjana Teknik Bioproses");
        prodiMap.put("32S", "Sarjana Bioteknologi");
        prodiMap.put("114", "Diploma 4 Teknologi Rekayasa Perangkat Lunak");
        prodiMap.put("113", "Diploma 3 Teknologi Informasi");
        prodiMap.put("133", "Diploma 3 Teknologi Komputer");
        return prodiMap;
    }

    private static void cetakInformasi(String nim, String kodeProdi, Map<String, String> prodiMap) {
        String prodi = prodiMap.get(kodeProdi);
        int batasTahun = PANJANG_KODE_PRODI + PANJANG_KODE_TAHUN;

        // Bagian tahun dan nomor urut wajib berupa digit; NIM seperti "11SXX005"
        // (kode prodi valid tapi sisanya bukan angka) harus ditolak, bukan bikin program crash.
        int angkatan;
        int urutan;
        try {
            angkatan = Integer.parseInt(AWALAN_TAHUN + nim.substring(PANJANG_KODE_PRODI, batasTahun));
            urutan = Integer.parseInt(nim.substring(batasTahun));
        } catch (NumberFormatException e) {
            System.out.println(NIM_TIDAK_VALID);
            return;
        }

        System.out.println("Informasi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + prodi);
        System.out.println(">> Angkatan: " + angkatan);
        System.out.println(">> Urutan: " + urutan);
    }
}
