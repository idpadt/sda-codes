
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Lab1{
    private static InputReader in;
    private static PrintWriter out;

    public static int findMaxLength(String[] chars) {  

        int maxLength = 0;

        int[] firstIndex = new int[2*chars.length+1];
        Arrays.fill(firstIndex, -1);
        firstIndex[chars.length] = 0;

        int value = 0;
        for (int i = 0; i < chars.length; i++){
            value += (chars[i].equals("L") ? 1 : -1);

            int start = firstIndex[value + chars.length];

            if (start != -1 && i - start + 1 > maxLength){
                maxLength = i - start + 1;
            } else {
                firstIndex[value + chars.length] = i + 1;
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