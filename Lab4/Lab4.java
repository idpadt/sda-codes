//package temp;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/* 
// TODO - class untuk Gerbong
class Gerbong {

} 
*/

// TODO - class untuk Kereta
class Kereta {
    String name;
    int maxCapacity;
    long currentCapacity = 0;
    int conductor = -1;
    ArrayList<Integer> compartment = new ArrayList<Integer>();

    public Kereta(String name, int capacity) {
        this.maxCapacity = capacity;
        this.name = name;
    }

    public void sambung(int capacity){
        // TODO - handle sambung
        compartment.add(capacity);
        currentCapacity += capacity;
        conductor = compartment.size()-1;
      
    }

    public String pindah(String direction){
        // TODO - handle Pindah

        if(direction.equals("L") && conductor == -1
        || direction.equals("R") && conductor == compartment.size()-1){
            return "TIDAK PINDAH";
        }else{

            if(direction.equals("L")){conductor--;}
            else{conductor++;}

            if(conductor == -1){
                return "LOKOMOTIF";
            }else{
                return String.valueOf((compartment.get(conductor)));
            }
        }

        
    }

    public String lepas(){
        // TODO - handle LEPAS
        if(conductor == -1){
            return "GAGAL LEPAS";
        } else {
            conductor--;
            int temp = compartment.size() - 1;
            long removed = 0;

            while(temp>conductor){
                int lastCapacity= compartment.get(temp);
                currentCapacity -= lastCapacity;
                removed += lastCapacity;

                compartment.remove(temp);
                temp--;
            }
            return String.format("%d %d", currentCapacity, removed);
        }
    }

    public String jalan(){
        // TODO - handle jalan
        if (currentCapacity<=maxCapacity){
            return String.format("%s %d 0", name, currentCapacity);
        } else {
            long removed = 0;
            int lastCompartment = compartment.size()-1;
            while(currentCapacity>maxCapacity){
                int lastCapacity= compartment.get(lastCompartment);
                currentCapacity -= lastCapacity;
                removed += lastCapacity;

                compartment.remove(lastCompartment);
                lastCompartment--;
            }
            return String.format("%s %d %d", name, currentCapacity, removed);
        }
        
    }
}

public class Lab4{
    private static InputReader in;
    public static PrintWriter out;

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        HashMap<String, Kereta> trains = new HashMap<String, Kereta>();

        // N operations
        int N = in.nextInt();
        String cmd;

        // TODO - handle inputs
        for (int zz = 0; zz < N; zz++) {

            cmd = in.next();
            if(cmd.equals("MASUK")){
                String name = in.next();
                int maxCapacity = in.nextInt();
                // TODO
                Kereta train = new Kereta(name, maxCapacity);
                trains.put(name, train);
            }
            else if(cmd.equals("SAMBUNG")){
                String name = in.next();
                int capacity = in.nextInt();
                // TODO
                Kereta train = trains.get(name);
                train.sambung(capacity);

            } else if(cmd.equals("PINDAH")){
                String name = in.next();
                String direction = in.next();
                // TODO
                Kereta train = trains.get(name);
                out.println(train.pindah(direction));

            } else if(cmd.equals("LEPAS")){
                String name = in.next();
                // TODO
                Kereta train = trains.get(name);
                out.println(train.lepas());

            } else if(cmd.equals("JALAN")){
                String name = in.next();
                // TODO
                Kereta train = trains.get(name);
                out.println(train.jalan());
            }
        }

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