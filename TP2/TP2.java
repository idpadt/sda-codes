//package TP2;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class Train {
    private String name;
    private int[] cargo;
    private int totalCargo;
    private Train headCargo;
    private Train tailCargo;
    private ArrayList<String> listOfCargo;
    private int priority;
    private Train nextTrain;

    public Train(String name, int[] cargo) {
        this.name = name;
        this.cargo = cargo;
        this.totalCargo = cargo.length;
        this.headCargo = null;
        this.tailCargo = null;
        this.listOfCargo = new ArrayList<String>();
        this.priority = -1;
        this.nextTrain = null;
    }

    public int addNextCargo(Train train){
        this.listOfCargo.add(train.getName());
        this.listOfCargo.addAll(train.getListOfCargo());
        this.totalCargo += train.getCargo().length;

        if(tailCargo == null){
            train.setHeadCargo(this);
            this.tailCargo = train;
        } else {
            this.tailCargo.addNextCargo(train);
        }
        
        return this.totalCargo;
    }

    public String removeNextCargo(Train train){
        this.totalCargo -= train.getTotalCargo();

        for(int ii = 0; ii < train.getListOfCargo().size()+1; ii++){
            this.listOfCargo.remove(this.listOfCargo.size()-1);
        }

        if(this.tailCargo.equals(train)){
            train.setHeadCargo(null);
            this.tailCargo = null;
        } else {
            this.tailCargo.removeNextCargo(train);
        }
        return this.totalCargo + " " + train.getTotalCargo();
    }

    public Train getFirstHead(){
        Train train = new Train("a", new int[0]);

        if(this.headCargo == null){
            train = this;
        }else{
            train = this.headCargo.getFirstHead();
        }

        return train;
    }


    public String getName() {return name;}

    public int[] getCargo() {return cargo;}

    public int getTotalCargo() {return totalCargo;}

    public ArrayList<String> getListOfCargo() {return this.listOfCargo;}

    public void setHeadCargo(Train headCargo) {this.headCargo = headCargo;}
    public Train getHeadCargo() {return headCargo;}

    public void setTailCargo(Train tailCargo) {this.tailCargo = tailCargo;}
    public Train getTailCargo() {return tailCargo;}

    public void setPriority(int priority) {this.priority = priority;}
    public int getPriority() {return priority;}

    public Train getNextTrain() {return nextTrain;}
    public void setNextTrain(Train nextTrain) {this.nextTrain = nextTrain;}
}


class Conductor{
    Train train;
    int position;

    public Conductor(Train train, int position){
        this.train = train;
        this.position = position-1;
    }

    public String teleport(Train train){
        this.train = train;
        this.position = 0;
        int passengers = train.getCargo()[0];
        return Integer.toString(passengers);
    }

    public String move(String direction, int paces){
        int passengers;

        if (direction.equals("LEFT")){
            if(position - paces >= 0){
                position -= paces;
            } else if(position - paces < 0 && train.getHeadCargo() == null){
                position = 0;
            } else{
                paces -= (position+1);
                train = train.getHeadCargo();
                position = train.getCargo().length-1;
                return move("LEFT", paces);
            }
        }
        else {
            int maxCargo = train.getCargo().length;

            if(position + paces <= maxCargo-1){
                position += paces;
            } else if(position + paces > maxCargo-1 && train.getTailCargo() == null){
                position = maxCargo-1;
            } else{
                paces -= (maxCargo-position);
                train = train.getTailCargo();
                position = 0;
                return move("RIGHT", paces);
            }   
        }
        passengers = train.getCargo()[position];
        return Integer.toString(passengers);
    }

    public Train getTrain(){return train;}
    public void setTrain(Train train){this.train = train;}

    public int getPosition(){return position;}
    public void setPosition(int position){this.position = position;}

}


public class TP2{
    private static InputReader in;
    public static PrintWriter out;

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int KK = in.nextInt();
        for(int ii=0; ii<KK; ii++){
            int leftChild = in.nextInt();
            int rightChild = in.nextInt();
        }

        int NN = in.nextInt();
        Train[] allTrains = new Train[NN];
        for(int ii=0; ii< NN; ii++){
            String name = in.next();
            int cargoAmount = in.nextInt();
            int[] cargo = new int[cargoAmount];
            for(int jj=0; jj<cargoAmount; jj++){
                cargo[jj] = in.nextInt();
            }
            Train train = new Train(name, cargo);
            allTrains[ii] = train;
        }

        String positionTrain = in.next();
        int positionCargo = in.nextInt();
        Train train = allTrains[findTrain(positionTrain, allTrains)];
        Conductor conductor = new Conductor(train, positionCargo);

        int QQ = in.nextInt();
        String command;
        for(int ii= 0; ii<QQ; ii++){
            command = in.next();

            if(command.equals("GABUNG")){
                Train head = allTrains[findTrain(in.next(), allTrains)];
                Train tail = allTrains[findTrain(in.next(), allTrains)];
                head.addNextCargo(tail);
                out.println(head.getTotalCargo());
            } 
            else if(command.equals("PISAH")) {
                Train tail = allTrains[findTrain(in.next(), allTrains)];
                Train head = tail.getFirstHead();
                out.println(head.removeNextCargo(tail));
            }
            else if(command.equals("TELEPORT")){
                Train newTrain = allTrains[findTrain(in.next(), allTrains)];
                out.println(conductor.teleport(newTrain));
            }
            else if(command.equals("MOVE")){
                String direction = in.next();
                int paces = in.nextInt();
                out.println(conductor.move(direction, paces));
            }

        }


        // don't forget to close/flush the output
        out.close();
    }

public static int findTrain(String name, Train[] trains){
    int index = -1;
    for(int ii=0; ii<trains.length; ii++){
        if(trains[ii].getName().equals(name)){
            index = ii;
        }
    }
    return index;
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
