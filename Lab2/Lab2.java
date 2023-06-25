import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Lab2{
    private static InputReader in;
    private static PrintWriter out;

    private static Stack<String[]> cookiePile = new Stack<String[]>();
    private static int cookieAmount = 0;
    private static HashMap<String, Integer> eatenCount = new HashMap<String, Integer>();

    // TODO
    static int handleRestock(String Ri, int Xi) {

        String[] newCookie = new String[]{Ri, String.valueOf(Xi)};
        cookiePile.push(newCookie);
        
        cookieAmount += Xi;
        return cookieAmount;
    }

    // TODO
    static String handleMakan(int Yi) {

        String lastEaten = "";
        int temp = Yi;

        while(temp > 0){

            String[] popped = cookiePile.pop();
            String cookie = popped[0];
            int amount = Integer.valueOf(popped[1]);

            int afterEaten = amount - temp;

            if (afterEaten>=0) {

                popped[1] = String.valueOf(afterEaten);
                cookiePile.push(popped);

                addToEaten(cookie, temp);

                temp=0;
                lastEaten = cookie;

            } else {
                temp= afterEaten*(-1);
                addToEaten(cookie, amount);
            }
        }

        cookieAmount -= Yi;

        return lastEaten;
    }

    static void addToEaten(String cookie, int amount){
        
        if (Objects.isNull(eatenCount.get(cookie))){
            eatenCount.put(cookie, amount);
        } else {
            int oldAmount = eatenCount.get(cookie);
            int newAmount = oldAmount + amount;
            eatenCount.put(cookie, newAmount);
        }
    }

    // TODO
    static int handleTotal(String Ri) {

        int totalEaten;

        if (Objects.isNull(eatenCount.get(Ri))){
            totalEaten = 0;
        } else {
            totalEaten = eatenCount.get(Ri);
        }

        return totalEaten;
    }


    public static void main(String[] args) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N;

        N = in.nextInt();

        for(int tmp=0;tmp<N;tmp++) {
            String event = in.next();

            if(event.equals("RESTOCK")) {
                String Ri = in.next();
                int Xi = in.nextInt();

                out.println(handleRestock(Ri, Xi));

            } else if(event.equals("MAKAN")) {
                int Yi = in.nextInt();
                
                out.println(handleMakan(Yi));

            } else {
                String Ri = in.next();

                out.println(handleTotal(Ri));
            }
        }

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