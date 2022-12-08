package com.mwdsp;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Instance {

    // Variables
    private int noNodes;
    private int noEdges;
    private int[] weights;
    private int[] numConnections;
    private ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();

    // Constructor & Methods
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

    public int getNodes() {
        return this.noNodes;
    }

    public int getWeight(int node) {
        return weights[node];
    }

    public ArrayList<Integer> getConnectionList(int node) {
        return adjList.get(node);
    }

    public int[] getNumConnections() {
        return numConnections;
    }

    public Set<Integer> getAllNodesSet(){
        Set<Integer> allNodeSet = new HashSet<>();

        for (int i = 0; i < noNodes; i++) {
            allNodeSet.add(i);
        }

        return allNodeSet;
    }

    public void printInstance() {
        // Print number of nodes and number of edges
        System.out.println("\nNumber of nodes: " + noNodes + "\nNumber of edges: " + noEdges);

        // Print node weights
        System.out.print("\nWeights: \n[");
        for (int i = 0; i < weights.length - 1; i++) {
            System.out.print(weights[i] + ", ");
        }
        System.out.println(weights[weights.length - 1] + "]");
        // Print adjacency list
        System.out.println("\nAdjacency List");
        int auxCont = 1; // To know index in the arrayList.
        for (ArrayList<Integer> arr : adjList) {
            System.out.print(auxCont + " -> ");
            for (Integer integer : arr) {
                System.out.print((integer + 1) + " ");
            }
            System.out.println();
            auxCont++;
        }

        // Print num connections
        System.out.print("\n Num of connections: \n[");
        for (int i = 0; i < numConnections.length - 1; i++) {
            System.out.print(numConnections[i] + ", ");
        }
        System.out.println(numConnections[numConnections.length - 1] + "]");
    }

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
                        noNodes = Integer.parseInt(arr[2]);
                        noEdges = Integer.parseInt(arr[3]);
                        numConnections = new int[noNodes];
                        initializeWeights(noNodes);
                        initializeAdjList(noNodes);
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

    private void parseMTXFile(BufferedReader br) {
        String line;
        String[] arr;

        try {
            line = br.readLine(); // First line commentary
            line = br.readLine(); // Second line -> Nodes and edges.

            arr = line.split(" ");
            noNodes = Integer.parseInt(arr[1]);
            noEdges = Integer.parseInt(arr[2]);
            numConnections = new int[noNodes];
            initializeWeights(noNodes);
            initializeAdjList(noNodes);

            int x, y;
            for (int i = 0; i < noEdges; i++) {
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

    private void parseDefault(BufferedReader br) {
        String line;

        try {
            line = br.readLine(); // First line 'NumberOfNodes:'
            line = br.readLine();
            noNodes = Integer.parseInt(line);
            numConnections = new int[noNodes];
            initializeAdjList(noNodes);

            line = br.readLine(); // Node positions (not used)
            for (int i = 0; i < noNodes + 1; i++) {
                line = br.readLine();
            }

            // Establish weights
            weights = new int[noNodes];
            for (int j = 0; j < noNodes; j++) {
                weights[j] = Integer.parseInt(br.readLine());
            }

            line = br.readLine(); // Connections comment.
            String[] connections;
            noEdges = 0;
            for (int i = 0; i < noNodes; i++) {
                line = br.readLine();
                connections = line.split(" ");

                for (int j = 0; j < i; j++) {
                    if (Integer.parseInt(connections[j]) == 1) {
                        addEdge(i, j);
                        noEdges++;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void initializeWeights(int noNodes) {
        weights = new int[noNodes];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = (i % 200) + 1;
        }
    }

    private void initializeAdjList(int noNodes) {
        for (int i = 0; i < noNodes; i++) {
            ArrayList<Integer> aux = new ArrayList<Integer>();
            this.adjList.add(aux);
        }
    }

    private void addEdge(int x, int y) {
        adjList.get(x).add(y);
        adjList.get(y).add(x);
        numConnections[x]++;
        numConnections[y]++;
    }
}
