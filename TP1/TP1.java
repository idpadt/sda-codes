import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class TP1{

    private static InputReader in;
    private static PrintWriter out;

    private static void editBags(HashMap<String, int[]> bags, int query, String name, int amount) {

        switch(query){
            case 1:
                int[] content = new int[]{amount, 0};
                bags.put(name, content);
                break;
            case 2:
                content = bags.get(name);
                content[0] = amount;
                if (content[1]>content[0]){content[1] = content[0];}
                bags.put(name, content);
                break;
            case 3:
                content = bags.get(name);
                content[1] -= amount;
                if (content[1] < 0){content[1] = 0;}
                bags.put(name, content);
        }
    }

    static private void harvest(int[] garden, HashMap<String, int[]> bags, String name) {

        int[] content = bags.get(name);
        int max = content[0];
        int harvested = 0;

        if(max<=maxMemo){
            harvested = harvestMemo.get(max)[garden.length];
        } else{
            harvested = maxHarvest(garden, max);
        }

        content[1] = harvested;


    }

    private static ArrayList<int[]> harvestMemo = new ArrayList<int[]>();
    private static int maxMemo = 0;

    private static int maxHarvest(int[] garden, int maxCapacity){

        int newXX = maxCapacity - maxMemo+1;
        int yy = garden.length+1;

        while(newXX != 0){
            harvestMemo.add(new int[yy]);
            newXX--;
        }

        for(int ii=0; ii < yy; ii++){
            for(int jj = maxMemo; jj<maxCapacity+1; jj++){

                if(ii==0 || jj==0){
                    harvestMemo.get(jj)[ii] = 0;

                }else if( garden[ii-1] <= jj ) {
                    harvestMemo.get(jj)[ii] = Math.max( harvestMemo.get(jj)[ii-1], garden[ii-1] + harvestMemo.get(jj-garden[ii-1])[ii-1]);

                } else {
                    harvestMemo.get(jj)[ii]= harvestMemo.get(jj)[ii-1];
                }
            }
        }

        maxMemo = maxCapacity;

        return harvestMemo.get(maxCapacity)[yy-1];

    }

    static private void detail(HashMap<String, int[]> bags, String name) {
        
        int[] content = bags.get(name);

        if(content == null){
            out.printf("BAG %s NOT FOUND\n",name);
        } else {
            int maxCapacity = content[0];
            int currentCapacity = content[1];

            out.println(name +" "+ maxCapacity +" "+ currentCapacity);
        }
    }

    static private void sell(HashMap<String, int[]> bags) {

        int sold = 0;

        for(int[] content: bags.values()){
            sold += content[1];
            content[1] = 0;
        }
        out.println(sold);

    }
    
    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
  
        int N = in.nextInt();

        int[] garden = new int[N];

        for(int ii = 0; ii < N; ii++){
            garden[ii] = in.nextInt();
        }

        HashMap<String, int[]> bags = new HashMap<String, int[]>();

        N = in.nextInt();

        while(N != 0){
            int query = in.nextInt();

            switch(query){
                case 1:
                case 2:
                case 3:
                    String name = in.next();
                    int amount = in.nextInt();
                    editBags(bags, query, name, amount);
                    break;
                case 4:
                    name = in.next();
                    harvest(garden, bags, name);
                    break;
                case 5:
                    name = in.next();
                    detail(bags, name);
                    break;
                case 6:
                    sell(bags);
                    break;
            }

            N--;
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