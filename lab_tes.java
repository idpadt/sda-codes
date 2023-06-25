
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class lab_tes{
    private static InputReader in;
    private static PrintWriter out;

    public static int findMaxLength(String[] chars) {  

        int[] values = new int[chars.length];

        for(int i = 0; i < chars.length; i++){
            values[i] = chars[i].equals("L") ? 1 : -1;
        }

        int maxLength = 0;

        for(int i = 0; i < values.length; i++){

            int base = 0;

            for(int j = i; j < values.length; j++){
                base += values[j];

                if(base == 0 && j - i + 1 > maxLength){
                    maxLength = j - i + 1;
                }
            }
        }

        return maxLength;
    }


    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Read value of N
        int N = in.nextInt();

        // Read value of H
        String[] H = new String[N];
        for (int i = 0; i < N; ++i) {
            H[i] = in.next();
        }

        // TODO: implement method findMaxLength(chars) to get the answer
        out.println(findMaxLength(H));

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