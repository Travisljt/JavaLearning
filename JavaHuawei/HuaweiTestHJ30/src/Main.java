import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str1 = sc.next();
            String str2 = sc.next();
            System.out.println(mergeAndSort(str1, str2));
        }
    }

    private static String mergeAndSort(String str1, String str2) {
        String str = str1 + str2;
        List<Character> even = new ArrayList<>();
        List<Character> odd = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 0) {
                even.add(str.charAt(i));
            } else {
                odd.add(str.charAt(i));
            }
        }
        Collections.sort(even);
        Collections.sort(odd);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < even.size(); i++) {
            sb.append(even.get(i));
            if (i <= odd.size() - 1) {
                sb.append(odd.get(i));
            }
        }
        for (int i = 0; i < sb.length(); i++) {
            String s = sb.substring(i, i + 1);
            if (s.matches("[0-9a-fA-F]")) {
                StringBuilder binary = new StringBuilder(Integer.toBinaryString((Integer.parseInt(s, 16))));
                int len = binary.length();
                for (int j = 0; j < 4 - len; j++) {
                    binary.insert(0, "0");
                }
                binary.reverse();
                int n = Integer.parseInt(binary.toString(), 2);
                String hexString = Integer.toHexString(n).toUpperCase();
                sb.replace(i, i + 1, hexString);
            }
        }
        return sb.toString();
    }
}