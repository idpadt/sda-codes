import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Lab6{

    private static InputReader in;
    private static PrintWriter out;


    public static void main(String[] args) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int queries = in.nextInt();
        int action;

        MinHeap queue = new MinHeap();

        for (int i = 1; i <= queries; i++) {
            action = in.nextInt();

            if (action == 1) {
                Package newPackage = new Package(in.next(), in.nextInt(), in.nextInt());
                queue.insertNode(newPackage);

            } else if (action == 2) {
                int initialSize = queue.heap.size();
                int amount = in.nextInt();
                for (int j = 1; j <= amount && j <= initialSize; j++) {
                    Package delivering = queue.removeMin();
                    String output = String.format("%s %s", j, delivering.code);
                    out.println(output);
                }
            }
        }
        out.close();
    }


    static class MinHeap {
        ArrayList<Package> heap = new ArrayList<Package>();

        //TODO: Implement Heap
    
    }
    
    static class Package implements Comparable<Package> {

        //TODO: Implement Package class

    }    

    //TODO: Tambah fungsi atau class baru (jika diperlukan)


    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the
    // usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit
    // Exceeded caused by slow input-output (IO)
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

