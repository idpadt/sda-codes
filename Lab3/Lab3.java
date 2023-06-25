import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


class Lab3{

    private static InputReader in;
    private static PrintWriter out;

    // TODO
    static private int findMaxItem(ArrayList<Integer> Makanan, ArrayList<Integer> Minuman, ArrayList<Integer> B) {

        ArrayList<Integer> foodStart = new ArrayList<>();
        ArrayList<Integer> drinkStart = new ArrayList<>();

        for( int i = 0; i < Makanan.size(); i++){
            foodStart.add(Makanan.get(0));
        }

    }

    // TODO
    static private int findTotalTransaksi(ArrayList<Integer> Makanan, ArrayList<Integer> Minuman, ArrayList<Integer> B) {
        return -1;
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        ArrayList<Integer> Makanan = new ArrayList<>();
        ArrayList<Integer> Minuman = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();
        
        int N = in.nextInt();
        
        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            Makanan.add(tmp);
        }

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            Minuman.add(tmp);
        }

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            B.add(tmp);
        }

        int jawabanItem = findMaxItem(Makanan, Minuman, B);
        int jawabanTransaksi = findTotalTransaksi(Makanan, Minuman, B);

        out.print(jawabanItem + " " + jawabanTransaksi);

        out.flush();
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}