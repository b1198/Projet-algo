import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class Labyrinthe {
    private class Noeud {
        private int[] state;
        private Noeud parent;
        private String action;

        public Noeud(int[] state, Noeud parent, String action) {
            this.state = state;
            this.parent = parent;
            this.action = action;
        }
    }

    private abstract class Frontiere {
        protected List<Noeud> frontier;

        public Frontiere() {
            this.frontier = new ArrayList<>();
        }

        public void add(Noeud node) {
            frontier.add(node);
        }

        public boolean containsState(int[] state) {
            for (Noeud node : frontier) {
                if (node.state[0] == state[0] && node.state[1] == state[1]) {
                    return true;
                }
            }
            return false;
        }

        public boolean empty() {
            return frontier.isEmpty();
        }

        public abstract Noeud remove();
    }

    private class Pile extends Frontiere {
        @Override
        public Noeud remove() {
            if (empty()) {
                throw new RuntimeException("Pile vide");
            } else {
                Noeud node = frontier.get(frontier.size() - 1);
                frontier.remove(frontier.size() - 1);
                return node;
            }
        }
    }

    private class Queue extends Frontiere {
        @Override
        public Noeud remove() {
            if (empty()) {
                throw new RuntimeException("Pile vide");
            } else {
                Noeud node = frontier.get(0);
                frontier.remove(0);
                return node;
            }
        }
    }

    private int height;
    private int width;
    private boolean[][] walls;
    private int[] start;
    private int[] goal;
    private List<String> solution;
    private List<int[]> solutionPath;
    private Set<String> explored;
    private int numExplored;

    public Labyrinthe(String filename) throws IOException {
        
        List<String> contents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
        }


        int startCount = 0;
        int goalCount = 0;
        for (String line : contents) {
            for (char c : line.toCharArray()) {
                if (c == 'S') startCount++;
                if (c == 'E') goalCount++;
            }
        }

        if (startCount != 1) {
            throw new RuntimeException("Le labyrinthe doit avoir exactement un point de départ");
        }
        if (goalCount != 1) {
            throw new RuntimeException("Le labyrinthes doit avoir exactement un point d'arrivée");
        }


        height = contents.size();
        width = 0;
        for (String line : contents) {
            width = Math.max(width, line.length());
        }


        walls = new boolean[height][width];
        start = new int[2];
        goal = new int[2];

        for (int i = 0; i < height; i++) {
            String line = contents.get(i);
            for (int j = 0; j < width; j++) {
                if (j < line.length()) {
                    char c = line.charAt(j);
                    if (c == 'S') {
                        start[0] = i;
                        start[1] = j;
                        walls[i][j] = false;
                    } else if (c == 'E') {
                        goal[0] = i;
                        goal[1] = j;
                        walls[i][j] = false;
                    } else if (c == '=') {
                        walls[i][j] = false;
                    } else {
                        walls[i][j] = true;
                    }
                } else {
                    walls[i][j] = false;
                }
            }
        }
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (walls[i][j]) {
                    System.out.print("█");
                } else if (i == start[0] && j == start[1]) {
                    System.out.print("S");
                } else if (i == goal[0] && j == goal[1]) {
                    System.out.print("E");
                } else if (solutionPath != null) {
                    boolean inPath = false;
                    for (int[] cell : solutionPath) {
                        if (cell[0] == i && cell[1] == j) {
                            inPath = true;
                            break;
                        }
                    }
                    System.out.print(inPath ? "*" : " ");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private List<Object[]> neighbors(int[] state) {
        int row = state[0];
        int col = state[1];
        Object[][] candidates = {
                {"up", new int[]{row - 1, col}},
                {"down", new int[]{row + 1, col}},
                {"left", new int[]{row, col - 1}},
                {"right", new int[]{row, col + 1}}
        };

        List<Object[]> result = new ArrayList<>();
        for (Object[] candidate : candidates) {
            String action = (String) candidate[0];
            int[] newState = (int[]) candidate[1];
            int r = newState[0];
            int c = newState[1];
            if (r >= 0 && r < height && c >= 0 && c < width && !walls[r][c]) {
                result.add(new Object[]{action, newState});
            }
        }
        return result;
    }

    public void solve() {
        
        numExplored = 0;


        Noeud start = new Noeud(this.start, null, null);
        Frontiere frontier = new Pile();
        frontier.add(start);


        explored = new HashSet<>();


        while (true) {
            
            if (frontier.empty()) {
                throw new RuntimeException("Aucune solution");
            }


            Noeud node = frontier.remove();
            numExplored++;


            if (node.state[0] == goal[0] && node.state[1] == goal[1]) {
                solution = new ArrayList<>();
                solutionPath = new ArrayList<>();
                while (node.parent != null) {
                    solution.add(0, node.action);
                    solutionPath.add(0, node.state);
                    node = node.parent;
                }
                return;
            }


            explored.add(node.state[0] + "," + node.state[1]);


            for (Object[] neighbor : neighbors(node.state)) {
                String action = (String) neighbor[0];
                int[] state = (int[]) neighbor[1];
                String stateStr = state[0] + "," + state[1];
                if (!frontier.containsState(state) && !explored.contains(stateStr)) {
                    Noeud child = new Noeud(state, node, action);
                    frontier.add(child);
                }
            }
        }
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Labyrinthe maze.txt");
            System.exit(1);
        }

        try {
            Labyrinthe m = new Labyrinthe(args[0]);
            System.out.println("Labyrinthe:");
            m.print();
            System.out.println("Resolutions...");
            m.solve();
            System.out.println("Chemins eexplorees: " + m.numExplored);
            System.out.println("Solution:");
            m.print();
        } catch (IOException e) {
            System.out.println("Erreur " + e.getMessage());
        }
    }
}