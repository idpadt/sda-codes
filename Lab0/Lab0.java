
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Lab0 {
    private static InputReader in;
    private static PrintWriter out;

    static long calcMoney(int M, int[] H) {

        //buat variabel answer
        long ans = 0;

        //loop setiap elemen pada array H dan
        //menambahkannya ke variabel answer
        for(int i : H){
            ans += i;
        }

        //return answer
        return ans;

    }


    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Read value of M
        int M = in.nextInt();

        // Read value of H
        int[] H = new int[M];
        for (int i = 0; i < M; ++i) {
            H[i] = in.nextInt();
        }

        // TODO: implement method calcMoney(int, int[]) to get the answer
        out.println(calcMoney(M, H));

        // don't forget to close/flush the output
        out.close();
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