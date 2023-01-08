package com.mwdsp;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Instance {

    // Variables
    private int nodeCount;              // Total node count of the problem instance
    private int edgeCount;              // Total edge count of the problem instance
    private int[] weights;              // Weights of each node
    private int[] numConnections;       // Adjacent node count of each node 
    private int[] numConnectionsRO;     // Adjacent node count of each node (read-only)
    private ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>(); 

    // Constructor
    public Instance(String filename) {
        String extension = filename.substring(filename.length() - 3);

        try {
            String file = "mwdsp/data/" + filename;
            BufferedReader br = new BufferedReader(new FileReader(file));

            switch (extension) {
                case "clq":
                    parseCLQFile(br);
                    break;
                case "mtx":
                    parseMTXFile(br);
                    break;
                default:
                    parseDefault(br);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public int getNodeCount() {
        return this.nodeCount;
    }

    public int getWeight(int node) {
        return weights[node];
    }

    public ArrayList<Integer> getAdjNodesList(int node) {
        return adjList.get(node);
    }

    public int[] getNumConnections() {
        return numConnections;
    }

    public int[] getNumConnectionsRO() {
        return numConnectionsRO;
    }

    public Set<Integer> getAllNodesSet(){
        Set<Integer> allNodeSet = new HashSet<>();

        for (int i = 0; i < nodeCount; i++) {
            allNodeSet.add(i);
        }

        return allNodeSet;
    }

    public void printInstance() {
        // Print Node & Edge Count
        System.out.println("\nNode count: " + nodeCount + "\nEdge count: " + edgeCount);

        // Print Node Weights
        System.out.print("\nWeights: \n[");
        for (int i = 0; i < weights.length - 1; i++) {
            System.out.print(weights[i] + ", ");
        }
        System.out.println(weights[weights.length - 1] + "]");

        // Print Adjacency List of each node
        System.out.println("\nAdjacency List");
        int auxCont = 1; 
        for (ArrayList<Integer> arr : adjList) {
            System.out.print(auxCont + " -> ");
            for (Integer integer : arr) {
                System.out.print((integer + 1) + " ");
            }
            System.out.println();
            auxCont++;
        }

        // Print Number of Connections
        System.out.print("\n Number of connections: \n[");
        for (int i = 0; i < numConnections.length - 1; i++) {
            System.out.print(numConnections[i] + ", ");
        }
        System.out.println(numConnections[numConnections.length - 1] + "]");
    }

    /**
     * Parses a CLQ extension File.
     * @param br - Buffered Reader created from the problem instance file. 
     */
    private void parseCLQFile(BufferedReader br) {
        String line;
        String[] arr;

        try {
            while ((line = br.readLine()) != null) {
                String line_id = Character.toString(line.charAt(0));

                if (line_id.equals("c")) // Continue loop if line is a comment.
                    continue;

                arr = line.split(" ");

                int x, y;
                switch (line_id) {
                    case "p":
                        nodeCount = Integer.parseInt(arr[2]);
                        edgeCount = Integer.parseInt(arr[3]);
                        numConnections = new int[nodeCount];
                        numConnectionsRO = new int[nodeCount];
                        initializeWeights(nodeCount);
                        initializeAdjList(nodeCount);
                        break;
                    case "e":
                        x = Integer.parseInt(arr[1]) - 1; // Nodes are numbered from [1, noNodes]
                        y = Integer.parseInt(arr[2]) - 1;
                        addEdge(x, y);
                        break;
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e);
        }
    }

    /**
     * Parses a MTX extension File.
     * @param br - Buffered Reader created from the problem instance file. 
     */
    private void parseMTXFile(BufferedReader br) {
        String line;
        String[] arr;

        try {
            line = br.readLine(); // First line commentary
            line = br.readLine(); // Second line -> Nodes and edges.

            arr = line.split(" ");
            nodeCount = Integer.parseInt(arr[1]);
            edgeCount = Integer.parseInt(arr[2]);
            numConnections = new int[nodeCount];
            numConnectionsRO = new int[nodeCount];
            initializeWeights(nodeCount);
            initializeAdjList(nodeCount);

            int x, y;
            for (int i = 0; i < edgeCount; i++) {
                arr = br.readLine().split(" ");
                x = Integer.parseInt(arr[0]) - 1; // Nodes are numbered from [1, noNodes]
                y = Integer.parseInt(arr[1]) - 1;
                addEdge(x, y);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e);
        }
    }

    /**
     * Parses default extension Files. Used for T1 and T2 problem instances. 
     * @param br - Buffered Reader created from the problem instance file. 
     */
    private void parseDefault(BufferedReader br) {
        String line;

        try {
            line = br.readLine(); // First line 'NumberOfNodes:'
            line = br.readLine();
            nodeCount = Integer.parseInt(line);
            numConnections = new int[nodeCount];
            numConnectionsRO = new int[nodeCount];
            initializeAdjList(nodeCount);

            line = br.readLine(); // Node positions (not used)
            for (int i = 0; i < nodeCount + 1; i++) {
                line = br.readLine();
            }

            // Establish Weights
            weights = new int[nodeCount];
            for (int j = 0; j < nodeCount; j++) {
                weights[j] = Integer.parseInt(br.readLine());
            }

            line = br.readLine(); // Connections comment.
            String[] connections;
            edgeCount = 0;
            for (int i = 0; i < nodeCount; i++) {
                line = br.readLine();
                connections = line.split(" ");

                for (int j = 0; j < i; j++) {
                    if (Integer.parseInt(connections[j]) == 1) {
                        addEdge(i, j);
                        edgeCount++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Initialize node weights for CLQ and MTX extension files as w(i) = (i % 200) + 1.
     * @param nodeCount - Total node count of the problem instance.
     */
    private void initializeWeights(int nodeCount) {
        weights = new int[nodeCount];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = (i % 200) + 1;
        }
    }

    /**
     * Initialize adjacent node list. 
     * @param nodeCount - Total node count of the problem instance.
     */
    private void initializeAdjList(int nodeCount) {
        for (int i = 0; i < nodeCount; i++) {
            ArrayList<Integer> aux = new ArrayList<Integer>();
            this.adjList.add(aux);
        }
    }

    /**
     * Adds an edge between two nodes.
     * @param x - First node of the edge. 
     * @param y - Second node of the edge.
     */
    private void addEdge(int x, int y) {
        adjList.get(x).add(y);
        adjList.get(y).add(x);
        numConnections[x]++;
        numConnections[y]++;
        numConnectionsRO[x]++;
        numConnectionsRO[y]++;
    }
}
