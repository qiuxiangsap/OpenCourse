package com.sap.nic.maxflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.introcs.In;

public class BaseballElimination {

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("teams4.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                System.out.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    System.out.print(t + " ");
                System.out.println("}");
            }
            else {
                System.out.println(team + " is not eliminated");
            }
        }


    }
    
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in;
        in = new In(filename);
        numOfTeams = in.readInt();
        
        team_names = new ArrayList<String>();
        team_index = new HashMap<String, Integer>();
        
        w = new int[numOfTeams];
        l = new int[numOfTeams];
        r = new int[numOfTeams];
        g = new int[numOfTeams][numOfTeams];
        
        for(int i = 0; i < numOfTeams; i++) {
            String name = in.readString();
            team_names.add(name);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            
   
            team_index.put(name, i);
            
            for (int j = 0; j < numOfTeams; j++) {
                g[i][j] = in.readInt();
            }
        }
//      
//        BufferedReader bReader = null;
//        try {
//            
//            bReader = new BufferedReader(new FileReader(filename));
//            int numOfTeams = Integer.valueOf(bReader.readLine().trim());
//            
//            team_names = new ArrayList<String>();
//            team_index = new HashMap<String, Integer>();
//            w = new int[numOfTeams];
//            l = new int[numOfTeams];
//            r = new int[numOfTeams];
//            g = new int[numOfTeams][numOfTeams];
//            
//            String line;
//            int index = 0;
//            while( (line = bReader.readLine()) != null) {
//                String[] elements = line.split("(\\s)+");
//                String tName = elements[0].trim();
//                team_names.add(tName);
//                team_index.put(tName, index);
//                w[index] = Integer.valueOf(elements[1].trim());
//                l[index] = Integer.valueOf(elements[2].trim());
//                r[index] = Integer.valueOf(elements[3].trim());
//                for (int  i = 0; i  < numOfTeams; i++) {
//                    g[index][i] = Integer.valueOf(elements[i + 4]);
//                }
//                                
//                index++;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (bReader != null) {
//                try {
//                    bReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
    

    // number of teams
    public int numberOfTeams() {
        return numOfTeams;
    }
    
    // all teams
    public Iterable<String> teams() {
        return team_names;
    }
    
    // number of wins for given team
    public int wins(String team) {
        if (!team_index.containsKey(team)) {
             throw new IllegalArgumentException();
        }
        
        return w[team_index.get(team)];
    }
    
    //number of losses for given team
    public int losses(String team){
        if (!team_index.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        
        return l[team_index.get(team)];
    }
    
    // number of remaining games for given team
    public int remaining(String team) {
        if (!team_index.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        
        return r[team_index.get(team)];
    }
    
    // number of remaining games between team1 and team2
    public int against(String team1, String team2){
        if (!team_index.containsKey(team1) || !team_index.containsKey(team2)) {
            throw new IllegalArgumentException();
        }
        
        int team1Index = team_index.get(team1);
        int team2Index = team_index.get(team2);
        
        return g[team1Index][team2Index];
    }
   
    private int[] excludeNode(String team) {
        int index = team_index.get(team);
        int[] res = new int[numberOfTeams() - 1];
        
        int idx = 0;
        for (int i = 0; i < numberOfTeams(); i++) {
            if (i != index) {
                res[idx++] = i;
            }
        }
        
        return res;
    }
    
    private HashMap<Integer, Integer> getIdToTeamIndex(int[] team_ids, int start) {
        HashMap<Integer, Integer> idToTeamId = new HashMap<Integer, Integer>(); 
        for(int i = 0; i < team_ids.length; i++) {
            idToTeamId.put(start++, team_ids[i]);
        }
        
        return idToTeamId;
    }
    
    // is given team eliminated?
    public boolean isEliminated(String team) {
        if(!team_index.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        
        int node_number =  (numberOfTeams() - 1) + (numberOfTeams() - 1)  * numberOfTeams() / 2  + 2;
        
        int source = 0;
        int target = node_number - 1;
        HashMap<Integer, String> match_index = new HashMap<Integer,String>();
        
        int[] otherTeams = excludeNode(team);
        int numOfMatches = (numberOfTeams() - 1)  * numberOfTeams() / 2 ;
        int start = numOfMatches + 1;
        HashMap<Integer, Integer> id_to_team = getIdToTeamIndex(otherTeams, start);
        
        FlowNetwork network = new FlowNetwork(node_number);
       
        int firstIndex = 1;
        int maxFlow = 0;
       
        for(int  i = 0; i < otherTeams.length; i++) {
            int residual = w[team_index.get(team)] + r[team_index.get(team)] - w[id_to_team.get(start + i)];
            if (residual < 0 ) {
                return true;
            } else {
                FlowEdge thiredLayer = new FlowEdge(start + i, target, residual);
                network.addEdge(thiredLayer);
            }
      
        }
        
        HashMap<Integer, String> oneDWithTwoDim = new HashMap<Integer, String>();
        for (int i = 0; i < otherTeams.length; i++) {
            for(int j = i+1; j < otherTeams.length; j++) {
                int g_ij = g[id_to_team.get(start + i)][id_to_team.get(start + j)];
                maxFlow += g_ij;
                FlowEdge firstLayer = new FlowEdge(source, firstIndex, g_ij);
                FlowEdge secondLayer1 = new FlowEdge(firstIndex, start + i, Integer.MAX_VALUE);
                FlowEdge secondLayer2 = new FlowEdge(firstIndex, start + j, Integer.MAX_VALUE);
                network.addEdge(firstLayer);
                network.addEdge(secondLayer1);
                network.addEdge(secondLayer2);
                oneDWithTwoDim.put(firstIndex, "" + i + ":" + j);
                firstIndex ++;
            }
        }
        FordFulkerson ford = new FordFulkerson(network, source, target);
        
        for (FlowEdge edge:network.adj(0)) {
            if (edge.flow() != edge.capacity()) {
                int edgeIndex = edge.to();
                return true;
            }
        }
        

        return false;
    }
    
    //subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if(!team_index.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        
        int node_number =  (numberOfTeams() - 1) + (numberOfTeams() - 1)  * numberOfTeams() / 2  + 2;
        
        int source = 0;
        int target = node_number - 1;
        HashMap<Integer, String> match_index = new HashMap<Integer,String>();
        
        int[] otherTeams = excludeNode(team);
        int numOfMatches = (numberOfTeams() - 1)  * numberOfTeams() / 2 ;
        int start = numOfMatches + 1;
        HashMap<Integer, Integer> id_to_team = getIdToTeamIndex(otherTeams, start);
        
        FlowNetwork network = new FlowNetwork(node_number);
        Set<String> subset = new HashSet<String>();
        
        
        int firstIndex = 1;
        int maxFlow = 0;
       
        for(int  i = 0; i < otherTeams.length; i++) {
            int residual = w[team_index.get(team)] + r[team_index.get(team)] - w[id_to_team.get(start + i)];
            if (residual < 0 ) {
                subset.add(team_names.get(id_to_team.get(start + i)));
                return subset;
            } else {
                FlowEdge thiredLayer = new FlowEdge(start + i, target, residual);
                network.addEdge(thiredLayer);
            }
      
        }
        
        HashMap<Integer, String> oneDWithTwoDim = new HashMap<Integer, String>();
        for (int i = 0; i < otherTeams.length; i++) {
            for(int j = i+1; j < otherTeams.length; j++) {
                int g_ij = g[id_to_team.get(start + i)][id_to_team.get(start + j)];
                maxFlow += g_ij;
                FlowEdge firstLayer = new FlowEdge(source, firstIndex, g_ij);
                FlowEdge secondLayer1 = new FlowEdge(firstIndex, start + i, Integer.MAX_VALUE);
                FlowEdge secondLayer2 = new FlowEdge(firstIndex, start + j, Integer.MAX_VALUE);
                network.addEdge(firstLayer);
                network.addEdge(secondLayer1);
                network.addEdge(secondLayer2);
                oneDWithTwoDim.put(firstIndex, "" + i + ":" + j);
                firstIndex ++;
            }
        }
        

        
        FordFulkerson ford = new FordFulkerson(network, source, target);
        for (int i = 0; i < otherTeams.length; i++) {
            if(ford.inCut(start + i)) {
                subset.add(team_names.get(id_to_team.get(start + i)));
            }
        }
        
       
//        for (FlowEdge edge:network.adj(0)) {
//            if (edge.flow() != edge.capacity()) {
//                int edgeIndex = edge.to();
//                String[] elems = oneDWithTwoDim.get(edgeIndex).split(":");
//                subset.add( team_names.get(id_to_team.get(Integer.valueOf(elems[0]) + start)));
//                subset.add( team_names.get(id_to_team.get(Integer.valueOf(elems[1]) + start)));
//            }
//        }
        if (subset.size() < 1) {
            return null;
        }
        
        return subset;
    }
    
    private ArrayList<String> team_names;
    private int numOfTeams;
    private int[] w; // number of games win
    private int[] l; // number of games lose
    private int[] r; // number of games remaining
    private int[][] g; // number of games left to play between i and j -> g[i][j]
    
    private HashMap<String, Integer> team_index;
}
