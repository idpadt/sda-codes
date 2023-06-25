import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.print.attribute.IntegerSyntax;

public class TP3{

    private static InputReader in;
    private static PrintWriter out;


    public static void main(String[] args) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        //---------------------------------------

        int vertexAmount = in.nextInt();
        int edgeAmount = in.nextInt();

        Graph graph = new Graph();

        for(int i = 0; i < edgeAmount; i++){
            graph.addEdge(in.nextInt(), in.nextInt(), in.nextInt());
        }
        Integer[] allVertex = graph.getAllVertex().toArray(new Integer[vertexAmount]);
        System.out.println("jum node "+ allVertex.length);


        int interpolAmount = in.nextInt();
        Integer[] interpol = new Integer[interpolAmount];
        for(int i = 0; i < interpolAmount; i++){
            int thistemp = in.nextInt(); //=0-=--0=-0==0=-0=-=0-0=-=00=0-=0-=0-=0-=0-=0-=0-=
            //out.println("lah " + i + " "+ thistemp+ " dari " + interpol.length);
            interpol[i] = thistemp;
        }
        graph.refreshInterpol(allVertex, interpol);


        MinHeap minHeap = new MinHeap();
        int queryAmount = in.nextInt();
        for(int i=0; i<queryAmount;i++){
            int query = in.nextInt();
            switch(query){
                case 1:
                    int id=in.nextInt();
                    int start=in.nextInt();
                    int end=in.nextInt();
                    minHeap.insert(new Package(id, start, end));
                    out.println("nice"); //don forgor -=-=-=-=-=-==-=-=-==--=-==--=-==-=-=-=---=-=-=-=-=-=-=-=-=-=-=
                case 2:

                case 3:
                    while(!minHeap.isEmpty()){
                        Package selectedPackage = minHeap.remove();


                    }

            }
        }
    

        //--------------------------------------

        out.close();
    }

    //
    // Packages / Binary Heap -----------------------------------
    //
    static class MinHeap {
        ArrayList<Package> heap = new ArrayList<Package>();
        //TODO: Implement Heap
        
        public void insert(Package item) {
            heap.add(item);
            int currentIndex = heap.size() - 1;
            int parentIndex = getParentIndex(currentIndex);
    
            while (parentIndex >= 0 && heap.get(currentIndex).compareTo(heap.get(parentIndex)) < 0) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
                parentIndex = getParentIndex(currentIndex);
            }
        }
    
        public Package remove() {
    
            Package minItem = heap.get(0);
            int lastIndex = heap.size() - 1;
            heap.set(0, heap.get(lastIndex));
            heap.remove(lastIndex);
    
            heapify(0);
    
            return minItem;
        }
    
        private int getParentIndex(int index) {
            return (index - 1) / 2;
        }
    
        private int getLeftChildIndex(int index) {
            return 2 * index + 1;
        }
    
        private int getRightChildIndex(int index) {
            return 2 * index + 2;
        }
    
        private void swap(int i, int j) {
            Package temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    
        private void heapify(int index) {
            int leftChildIndex = getLeftChildIndex(index);
            int rightChildIndex = getRightChildIndex(index);
            int smallestIndex = index;
    
            if (leftChildIndex < heap.size() && heap.get(leftChildIndex).compareTo(heap.get(smallestIndex)) < 0) {
                smallestIndex = leftChildIndex;
            }
    
            if (rightChildIndex < heap.size() && heap.get(rightChildIndex).compareTo(heap.get(smallestIndex)) < 0) {
                smallestIndex = rightChildIndex;
            }
    
            if (smallestIndex != index) {
                swap(index, smallestIndex);
                heapify(smallestIndex);
            }
        }

        public boolean isEmpty(){
            if(heap.size() == 0){
                return true;
            } else {
                return false;
            }
        }

    }
    
    static class Package implements Comparable<Package> {
        //TODO: Implement Package class
        int id;
        int start;
        int end;

        public Package(int id, int start, int end){
            this.id = id;
            this.start = start;
            this.end = end;
        }

        public int getId(){return this.id;}
        public int getStart(){return this.start;}
        public int getEnd(){return this.end;}

        @Override public int compareTo(Package that){

            int thatId = that.getId();
            int thatStart = that.getStart();
            int thatEnd = that.getEnd();

            if(this.id != thatId){
                if( this.id > thatId){
                    return 1;
                } else {
                    return -1;
                }
            }

            if(this.end != thatEnd){
                if( this.end > thatEnd){
                    return 1;
                } else {
                    return -1;
                }
            }

            if(this.start != thatStart){
                if( this.start > thatStart){
                    return 1;
                } else {
                    return -1;
                }
            }

            return 0;

        }

    }    

    //
    // Map / Graph ---------------------------------------------
    //

    static class Graph {

        class Edge {
            public int end;
            public int weight;

            public Edge(int end, int weight){
                this.end = end;
                this.weight = weight;
            }
        }

        public HashMap<Integer, List<Edge>> adjacencyList;
        public HashMap<Integer, HashMap<Integer, Integer>> interpolToVertex;

        public Graph(){
            adjacencyList = new HashMap<>();
        }

        public Set<Integer> getAllVertex(){
            return adjacencyList.keySet();
        }

        public void addEdge(int vertexA, int vertexB, int weight){
            Edge edgeAB = new Edge(vertexB, weight);
            Edge edgeBA = new Edge(vertexA, weight);

            adjacencyList.computeIfAbsent(vertexA, ArrayList::new).add(edgeAB);
            adjacencyList.computeIfAbsent(vertexB, ArrayList::new).add(edgeBA);
        }

        public int findShortestPath(int start, int end){
            // Dijkstra's algorithm
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
            int[] distances = new int[adjacencyList.size()];
            Arrays.fill(distances, Integer.MAX_VALUE);
            distances[start] = 0;
    
            pq.add(new Node(start, 0));
    
            while (!pq.isEmpty()) {
                Node current = pq.poll();
    
                if (current.vertex == end) {
                    return current.distance;
                }
    
                if (current.distance > distances[current.vertex]) {
                    continue;
                }
    
                List<Edge> edges = adjacencyList.get(current.vertex);
                if (edges != null) {
                    for (Edge edge : edges) {
                        int newDistance = current.distance + edge.weight;
                        if (newDistance < distances[edge.end]) {
                            distances[edge.end] = newDistance;
                            pq.add(new Node(edge.end, newDistance));
                        }
                    }
                }
            }
    
            // If there's no path from start to end
            return -1;
        }
    
        class Node {
            public int vertex;
            public int distance;
    
            public Node(int vertex, int distance) {
                this.vertex = vertex;
                this.distance = distance;
            }
        }
    
        public void refreshInterpol(Integer[] vertex, Integer[] interpol){
            for(int i=0; i < interpol.length; i++){

                HashMap<Integer, Integer> vertexList = new HashMap<Integer, Integer>();

                for(int j = 0; j < vertex.length; j++){
                    System.out.println("iii "+i);
                    System.out.println("j " + j);
                    int distance = this.findShortestPath(interpol[i], vertex[j]);
                    vertexList.put(vertex[j], distance);
                }
                interpolToVertex.put(interpol[i], vertexList);
            }
        }
    
        public int destinationToInterpol(int destination){
            return -1;
        }
    
    }

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

