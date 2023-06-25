import java.util.*;

public class random {

    private static int maxHarvest(int[] garden, int maxCapacity){

        int yy = garden.length+1;
        int xx = maxCapacity+1;
        
        int[][] values = new int[yy][xx];

        for(int ii=0; ii < yy; ii++){
            for(int jj = 0; jj<xx; jj++){

                if(ii==0 || jj==0){
                    values[ii][jj] = 0;

                }else if( garden[ii-1] <= jj ) {
                    values[ii][jj] = Math.max( values[ii-1][jj], garden[ii-1] + values[ii-1][jj-garden[ii-1]]);

                } else {
                    values[ii][jj] = values[ii-1][jj];
                }
            }
        }

        for( int[] a: values){
            System.out.println(Arrays.toString(a));
        }

        return values[yy-1][xx-1];
    }

    //-----------------------------------------------------------------------

    private static ArrayList<int[]> harvestMemo = new ArrayList<int[]>();
    private static int maxMemo = 0;

    private static int maxHarvestMemo(int[] garden, int maxCapacity){

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

  
    public static void main(String[] args){

        int[] garden1 = new int[]{4,9,8,3,4};

        System.out.println(maxHarvest(garden1, 10));

        System.out.println(maxHarvest(garden1, 25));

        System.out.println("");

        System.out.println(maxHarvestMemo(garden1, 10));

        System.out.println(maxHarvestMemo(garden1, 18));
    }

}

class Package implements Comparable<Package> {
    private int id;

    public Package(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Package other) {
        return Integer.compare(this.id, other.id);
    }
}

class BinaryMinHeap {
    private List<Package> heap;

    public BinaryMinHeap() {
        heap = new ArrayList<>();
    }

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

    public Package extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty.");
        }

        Package minItem = heap.get(0);
        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);

        heapify(0);

        return minItem;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
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
}

class Graph {
    private Map<Integer, List<Edge>> adjacencyMap;

    public Graph() {
        adjacencyMap = new HashMap<>();
    }

    public void addEdge(int nodeA, int nodeB, int weight) {
        Edge edgeAB = new Edge(nodeA, nodeB, weight);
        Edge edgeBA = new Edge(nodeB, nodeA, weight);

        adjacencyMap.computeIfAbsent(nodeA, ArrayList::new).add(edgeAB);
        adjacencyMap.computeIfAbsent(nodeB, ArrayList::new).add(edgeBA);
    }

    public List<Integer> findShortestPath(int source) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>();

        for (int node : adjacencyMap.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
            previousNodes.put(node, null);
        }

        distances.put(source, 0);
        priorityQueue.offer(new NodeDistance(source, 0));

        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();
            int currentNode = current.getNode();
            int currentDistance = current.getDistance();

            if (currentDistance > distances.get(currentNode)) {
                continue;
            }

            List<Edge> neighbors = adjacencyMap.get(currentNode);

            if (neighbors != null) {
                for (Edge neighbor : neighbors) {
                    int neighborNode = neighbor.getDestination();
                    int neighborDistance = currentDistance + neighbor.getWeight();

                    if (neighborDistance < distances.get(neighborNode)) {
                        distances.put(neighborNode, neighborDistance);
                        previousNodes.put(neighborNode, currentNode);
                        priorityQueue.offer(new NodeDistance(neighborNode, neighborDistance));
                    }
                }
            }
        }

        List<Integer> shortestPath = new ArrayList<>();
        int currentNode = source;

        while (currentNode != null) {
            shortestPath.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }

        Collections.reverse(shortestPath);
        return shortestPath;
    }

    private static class Edge {
        private int source;
        private int destination;
        private int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        public int getSource() {
            return source;
        }

        public int getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }
    }

    private static class NodeDistance implements Comparable<NodeDistance> {
        private int node;
        private int distance;

        public NodeDistance(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        public int getNode() {
            return node;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(distance, other.distance);
        }
    }
}


