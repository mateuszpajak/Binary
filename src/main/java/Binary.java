import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Binary {

    private Node[][] graph;
    private int dimension;
    private int assignments;
    private static final Random random = new Random();
    private LinkedList<Integer> values;

    public Binary(int dimension, float fillPercentage) {
        this.dimension = dimension;
        graph = new Node[dimension][dimension];
        values = new LinkedList<>();
        values.add(0);
        values.add(1);

        int id = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                graph[i][j] = new Node(id++);
                graph[i][j].setValue(-1);
            }
        }

        randomFill(graph, fillPercentage);
    }

    private void randomFill(Node[][] graph, float fillPercentage) {
        int fieldToFill = ((int) (Math.floor(dimension * dimension * (fillPercentage / 100)) + 1));
        for (int i = 0; i < fieldToFill; i++) {
            boolean isFilled = false;
            do {
                int x = random.nextInt((graph.length - 1) - 0 + 1) + 0;
                int y = random.nextInt((graph[0].length - 1) - 0 + 1) + 0;
                if (graph[y][x].getValue() == -1) {
                    graph[y][x].setValue(random.nextInt(1 - 0 + 1) + 0);
                    isFilled = true;
                }
            } while (!isFilled);
        }
    }

    private boolean checkConstraints(Node[][] graph) {
        return checkNumbersConstraints(graph) && checkConstraintNodes(graph) &&
                checkUniqueConstraint(graph);
    }

    private boolean checkNumbersConstraints(Node[][] graph) {
        Integer[][] numberInRows = new Integer[2][dimension];
        Integer[][] numberInColumns = new Integer[2][dimension];

        for (int i = 0; i < numberInColumns.length; i++) {
            for (int j = 0; j < numberInColumns[i].length; j++) {
                numberInColumns[i][j] = 0;
                numberInRows[i][j] = 0;
            }
        }

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j].getValue() == 0) {
                    numberInRows[0][i]++;
                    numberInColumns[0][j]++;
                } else if (graph[i][j].getValue() == 1) {
                    numberInRows[1][i]++;
                    numberInColumns[1][j]++;
                }
            }
        }

        int maxNumberOfValue = dimension / 2;
        for (int i = 0; i < numberInRows.length; i++) {
            for (int j = 0; j < numberInRows[i].length; j++) {
                if (numberInRows[i][j] > maxNumberOfValue || numberInColumns[i][j] > maxNumberOfValue)
                    return false;
            }
        }

        return true;
    }

    private boolean checkConstraintNodes(Node[][] graph) {
        int count = 1;
        for (int i = 0; i < graph.length; i++) {
            Node temp = graph[i][0];
            for (int j = 1; j < graph[i].length; j++) {
                if (graph[i][j].getValue() == temp.getValue() && graph[i][j].getValue() != -1) {
                    count++;
                } else {
                    count = 1;
                }
                if (count > 2)
                    return false;
                temp = graph[i][j];
            }
        }

        for (int i = 0; i < graph.length; i++) {
            Node temp = graph[0][i];
            for (int j = 1; j < graph[i].length; j++) {
                if (graph[j][i].getValue() == temp.getValue() && graph[j][i].getValue() != -1) {
                    count++;
                } else {
                    count = 1;
                }
                if (count > 2)
                    return false;
                temp = graph[j][i];
            }
        }
        return true;
    }

    private HashMap<String, Integer> setColumnsOrRows(Node[][] graph, boolean columns) {
        HashMap<String, Integer> result = new HashMap<>();

        for (int i = 0; i < graph.length; i++) {
            String string = "" ;
            for (int j = 0; j < graph[i].length; j++) {
                if (columns)
                    string += graph[j][i].getValue();
                else
                    string += graph[i][j].getValue();
            }
            if (!string.contains("-1")) {
                if (result.containsKey(string))
                    result.replace(string, result.get(string) + 1);
                else
                    result.put(string, 1);
            }
        }
        return result;
    }

    private boolean checkUniqueConstraint(Node[][] graph) {

        HashMap<String, Integer> rows = setColumnsOrRows(graph, false);
        HashMap<String, Integer> columns = setColumnsOrRows(graph, true);

        final Boolean[] uniqueRows = new Boolean[1];
        uniqueRows[0] = true;
        rows.forEach((key, value) -> {
            if (value > 1)
                uniqueRows[0] = false;
        });

        final Boolean[] uniqueColumns = new Boolean[1];
        uniqueColumns[0] = true;
        columns.forEach((key, value) -> {
            if (value > 1)
                uniqueColumns[0] = false;
        });

        return uniqueColumns[0] && uniqueRows[0];
    }

    private boolean isFilled(Node[][] graph) {
        boolean isFilled = true;
        boolean isEnd = true;
        int i = 0;
        do {
            if (i == graph.length) {
                isEnd = false;
            } else {
                for (int j = 0; j < graph[i].length; j++) {
                    if (graph[i][j].getValue() == -1) {
                        isFilled = false;
                        isEnd = false;
                    }
                }
            }
            i++;
        } while (isEnd);

        return isFilled;
    }

    private List<Integer> sortedValues(Node[][] graph) {
        LinkedList<Integer> result = new LinkedList<>();
        HashMap<Integer, Integer> countedValues = new HashMap<>();
        countedValues.put(0, 0);
        countedValues.put(1, 0);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j].getValue() == 0)
                    countedValues.replace(0, countedValues.get(0) + 1);
                else if (graph[i][j].getValue() == 1)
                    countedValues.replace(1, countedValues.get(1) + 1);
            }
        }

        if (countedValues.get(0) >= countedValues.get(1)) {
            result.add(0);
            result.add(1);
        } else {
            result.add(1);
            result.add(0);
        }
        return result;
    }

    public boolean backtracking() {
        return backtrackingAlgorithm(graph, findFirstNull(graph));
    }

    public boolean backtrackingHeuristic1() {
        return backtrackingHeuristic1(graph, findFirstNull(graph));
    }

    public boolean backtrackingHeuristic2() {
        return backtrackingHeuristic2(graph, findFirstNull(graph));
    }

    private boolean backtrackingHeuristic1(Node[][] graph, Node node) {
        if (isFilled(graph)) {
            if (checkConstraints(graph))
                return true;
            else
                return false;
        } else {
            if (checkConstraints(graph)) {
                List<Integer> sortedValue = sortedValues(graph);
                for (int i : sortedValue) {
                    node.setValue(i);
                    assignments++;
                    if (checkConstraints(graph) && isFilled(graph))
                        return true;
                    else if (checkConstraints(graph))
                        backtrackingAlgorithm(graph, findFirstNull(graph));
                    if (checkConstraints(graph) && isFilled(graph))
                        return true;
                }
                node.setValue(-1);
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean backtrackingHeuristic2(Node[][] graph, Node node) {
        if (isFilled(graph)) {
            if (checkConstraints(graph))
                return true;
            else
                return false;
        } else {
            if (checkConstraints(graph)) {
                for (int i : values) {
                    node.setValue(i);
                    assignments++;
                    if (isFilled(graph) && checkConstraints(graph))
                        return true;
                    else if (checkConstraints(graph))
                        backtrackingAlgorithm(graph, getRandomNotNullNode(graph));
                    if (isFilled(graph) && checkConstraints(graph))
                        return true;
                }
                node.setValue(-1);
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean backtrackingAlgorithm(Node[][] graph, Node node) {
        if (isFilled(graph)) {
            if (checkConstraints(graph))
                return true;
            else
                return false;
        } else {
            if (checkConstraints(graph)) {
                for (int i : values) {
                    node.setValue(i);
                    assignments++;
                    if (isFilled(graph) && checkConstraints(graph))
                        return true;
                    else if (checkConstraints(graph))
                        backtrackingAlgorithm(graph, findFirstNull(graph));
                    if (isFilled(graph) && checkConstraints(graph))
                        return true;
                }
                node.setValue(-1);
            } else {
                return false;
            }
        }
        return false;
    }

    private Node findFirstNull(Node[][] graph) {
        Node result = null;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j].getValue() == -1 && result == null)
                    result = graph[i][j];
            }
        }
        return result;
    }

    private Node getRandomNotNullNode(Node[][] graph) {
        Node result = null;

        do {
            int x = random.nextInt((graph.length - 1) - 0 + 1) + 0;
            int y = random.nextInt((graph[0].length - 1) - 0 + 1) + 0;
            if (graph[x][y].getValue() == -1)
                result = graph[x][y];
        } while (result == null);

        return result;
    }

    public Node[][] getGraph() {
        return graph;
    }

    public Binary setGraph(Node[][] graph) {
        this.graph = graph;
        return this;
    }

    public int getDimension() {
        return dimension;
    }

    public Binary setDimension(int dimension) {
        this.dimension = dimension;
        return this;
    }

    public int getAssignments() {
        return assignments;
    }

    public Binary setAssignments(int assignments) {
        this.assignments = assignments;
        return this;
    }

    private void printGraph(Node[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                System.out.print(graph[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        int i = 0;
        long time = 0;
        int assign = 0;
        while (i < 10) {
            Binary backtracking = new Binary(4, 60);
            long startTime = System.currentTimeMillis();
            boolean isSolution = backtracking.backtrackingHeuristic2();
            long endTime = System.currentTimeMillis();
            long processTime = endTime - startTime;
            if (isSolution) {
                time += processTime;
                assign += backtracking.getAssignments();
                i++;
            }
        }
        System.out.println("Time: " + time + "\nNumber of assignments: " + assign);
    }
}
