import java.util.*;

public class SlidingPuzzle {

    int dimension;
    int[][] processingArray;
    int[][] targetArray;
    boolean[] rowIsSolved;
    boolean[] columnIsSolved;
    List<Integer> cellsNotInPlace;
    List<Integer> listOfMoves;
    List<Integer> listOfSolvedCells;


    public SlidingPuzzle(int[][] puzzle) {
        processingArray = puzzle;
        dimension = puzzle.length;
        rowIsSolved = new boolean[dimension];
        columnIsSolved = new boolean[dimension];
        listOfSolvedCells = new ArrayList<>();
        setTargetArray();
    }

    public List<Integer> solve() {
        listOfMoves = new ArrayList<>();
        int cycleCounter = 0;

        while (isUnsolved()) {
            if (cycleCounter == 50) return null;
            cycleCounter++;
            System.out.println();
            System.out.println(" _________________ Cycle round # " + cycleCounter + " _________________ ");
            cellsNotInPlace = getCellsNotInPlace();
            int target = getCurrentValueNotInPlace();

            if (cellsNotInPlace.size() == 2) {
                swapCells(getCellCoordinates(cellsNotInPlace.get(1)).get(0),
                        getCellCoordinates(cellsNotInPlace.get(1)).get(0));
//                listOfMoves.addAll(cellsNotInPlace);
            }
            if (getCellCoordinates(target).equals(getCellFinalCoordinates(target))) continue;

            Node solution = iteration(getCellCoordinates(target), getCellFinalCoordinates(target), target);

            if (solution != null) {
                List<Integer> values = new ArrayList<>();
                while (solution.parent != null) {
                    values.add(solution.value);
                    solution = solution.parent;
                }
                values.add(solution.value);
                Collections.reverse(values);

                for (Integer value : values) {
//                    if (value != 0)System.out.println(value);
//                    listOfMoves.add(value);
                    swapCells(getCellCoordinates(value).get(0), getCellCoordinates(value).get(1));
                }
                swapCells(getCellCoordinates(target).get(0), getCellCoordinates(target).get(1));
//                listOfMoves.add(target);
            }
            cellsNotInPlace.remove(Integer.valueOf(target));
//            System.out.println("List of moves: " + listOfMoves + " <- list of moves");
        }

        while (listOfMoves.remove(Integer.valueOf(0))) {
        }
        System.out.println("List of moves: " + listOfMoves);
        return listOfMoves;
    }

    Node iteration(List<Integer> current, List<Integer> target, int value) {
        // TODO: 21-Feb-23 Refactor this method in final version. Probably would be better to get rid of it.
        System.out.println("Current coordinates: " + current.get(0) + " " + current.get(1));
        System.out.println("Target coordinates: " + target.get(0) + " " + target.get(1));
        System.out.println("Zero coordinates: " + getCellCoordinates(0).get(0) + " " + getCellCoordinates(0).get(1));

//        if (getCellCoordinates(0).equals(target) && Math.abs(target.get(0) - current.get(0)) <= 1
//                && Math.abs(target.get(1) - current.get(1)) <= 1) {
//            System.out.println("Inside first fork");
//            if (current.get(0).equals(target.get(0)) || current.get(1).equals(target.get(1))) {
//                swapCells(current.get(0), current.get(1));
//                System.out.println("not diagonal");
//                listOfMoves.add(value);
//                return null;
//            } else {
//                diagonalSwap(current);
//            }
//        }
        int row = current.get(0), column = current.get(1);
        if (!current.get(0).equals(target.get(0))) row = current.get(0) < target.get(0) ? current.get(0) + 1 : current.get(0) - 1;
        if (!current.get(1).equals(target.get(1))) column = current.get(1) < target.get(1) ? current.get(1) + 1 : current.get(1) - 1;
        return searchPath(getPuzzleField(processingArray[row][column], value));
    }

    Node[][] getPuzzleField(int target, int obstacle) {
        Node[][] field = new Node[dimension][dimension];
        for (int row = 0; row < dimension; row++) {
            if (Arrays.equals(targetArray[row], processingArray[row])) rowIsSolved[row] = true;
            for (int column = 0; column < dimension; column++) {
                field[row][column] = new Node(processingArray[row][column], row, column);
                if (processingArray[row][column] == 0) field[row][column].isStart = true;
                if (processingArray[row][column] == target) field[row][column].isTarget = true;
                if (processingArray[row][column] == obstacle || rowIsSolved[row]) field[row][column].isObstacle = true;
            }
        }
        return field;
    }

    List<Integer> getCellsNotInPlace() {
        List<Integer> target = new ArrayList<>();
        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                if (processingArray[row][column] != targetArray[row][column]) target.add(processingArray[row][column]);
            }
        }
        return target;
    }

    boolean isUnsolved() {
        System.out.println(Arrays.deepToString(processingArray) + " <- current list");
        System.out.println(Arrays.deepToString(targetArray) + " <- target array");
        return !Arrays.deepEquals(processingArray, targetArray);
    }

    void diagonalSwap(List<Integer> targetCell) {
        List<Integer> zeroCell = getCellCoordinates(0);
        int zeroRow = zeroCell.get(0), zeroColumn = zeroCell.get(1);
        int targetRow = targetCell.get(0), targetColumn = targetCell.get(1);
        System.out.println("Inside of diagonalSwap ====================================================");
        System.out.println(zeroCell + " " + targetCell + " coordinates for diagonal");
        // 4 cases for 4 0 <-> target configurations
        if (zeroRow < targetRow) {
            if (zeroColumn < targetColumn) {
                swapCells(zeroRow, zeroColumn + 1); //1
                swapCells(zeroRow + 1, zeroColumn + 1); //2
                swapCells(zeroRow + 1, zeroColumn); //3
                swapCells(zeroRow, zeroColumn); //1
                swapCells(zeroRow, zeroColumn + 1); //2
                swapCells(zeroRow + 1, zeroColumn + 1);
//                swapCells(zeroRow + 1, zeroColumn);
                return;
            }
            if (zeroColumn > targetColumn) {
                swapCells(zeroRow + 1, zeroColumn); //1
                swapCells(zeroRow + 1, zeroColumn - 1); //2
                swapCells(zeroRow, zeroColumn - 1); //3
                swapCells(zeroRow, zeroColumn); //1
                swapCells(zeroRow + 1, zeroColumn); //2
                swapCells(zeroRow + 1, zeroColumn - 1);
//                swapCells(zeroRow, zeroColumn - 1);
                return;
            }
        }
        if (zeroRow > targetRow) {
            if (zeroColumn < targetColumn) {
                swapCells(zeroRow - 1, zeroColumn); //1
                swapCells(zeroRow + 1, zeroColumn + 1); //2
                swapCells(zeroRow, zeroColumn + 1); //3
                swapCells(zeroRow, zeroColumn); //1
                swapCells(zeroRow - 1, zeroColumn); //2
                swapCells(zeroRow + 1, zeroColumn + 1);
//                swapCells(zeroRow, zeroColumn + 1);
                return;
            }
            if (zeroColumn > targetColumn) {
                swapCells(zeroRow, zeroColumn - 1); //1
                swapCells(zeroRow - 1, zeroColumn - 1); //2
                swapCells(zeroRow - 1, zeroColumn); //3
                swapCells(zeroRow, zeroColumn); //1
                swapCells(zeroRow, zeroColumn - 1);
                swapCells(zeroRow - 1, zeroColumn - 1);
//                swapCells(zeroRow - 1, zeroColumn);
            }
        }
    }

    private List<Integer> getCoordinates(int value, int[][] targetArray) {
        for (int row = 0; row < dimension; row++)
            for (int column = 0; column < dimension; column++)
                if (targetArray[row][column] == value) return List.of(row, column);
        return List.of(-1);
    }

    List<Integer> getCellCoordinates(int value) {
        return getCoordinates(value, processingArray);
    }

    List<Integer> getCellFinalCoordinates(int value) {
        return getCoordinates(value, targetArray);
    }

    void swapCells(int row, int column) {
        if (processingArray[row][column] != 0) listOfMoves.add(processingArray[row][column]);
        int secondRow = getCellCoordinates(0).get(0);
        int secondColumn = getCellCoordinates(0).get(1);
        int buffer = processingArray[row][column];
        processingArray[row][column] = processingArray[secondRow][secondColumn];
        processingArray[secondRow][secondColumn] = buffer;
    }

    int getCurrentValueNotInPlace() {

//        record Cell(int value, int weight) implements Comparable<Cell> {
//
//            @Override
//            public int compareTo(Cell cell) {
//                return this.weight - cell.weight;
//            }
//        }
//
//        List<Cell> cells = new ArrayList<>();
//        for (int row = 0; row < targetArray.length; row++)
//            for (int column = 0; column < targetArray[row].length; column++) {
//                if (targetArray[row][column] == 0) continue;
//                cells.add(new Cell(targetArray[row][column], row + column));
//
//            }
//        System.out.println(cells + " <- Cells");
//        while (true) {
//            cells.removeIf(cell -> !cellsNotInPlace.contains(cell.value));
////            System.out.println(cells);
//
//            Cell target = cells.stream().min(Comparator.naturalOrder()).get();
//            if (getCellCoordinates(target.value).equals(getCellFinalCoordinates(target.value))) {
//                cellsNotInPlace.remove(Integer.valueOf(target.value));
//                continue;
//            }
//            if (target.value == 0 && cellsNotInPlace.size() > 2) {
//                cellsNotInPlace.remove(Integer.valueOf(0));
//                continue;
//            }
//            System.out.println("Cells not in place: " + cellsNotInPlace);
//            System.out.println(target.value + " <- Current cell");
//
//            return target.value;
//        }

        while (true) {
            int smallestValue = cellsNotInPlace.stream().min(Comparator.naturalOrder()).orElse(-1);
            if (getCellCoordinates(smallestValue).equals(getCellFinalCoordinates(smallestValue))) {
                cellsNotInPlace.remove(Integer.valueOf(smallestValue));
                continue;
            }
            if (smallestValue == 0 && cellsNotInPlace.size() > 2) {
                cellsNotInPlace.remove(Integer.valueOf(0));
                continue;
            }
            System.out.println("Current value: " + smallestValue);
            return smallestValue;
        }
    }

    Node searchPath(Node[][] field) {
        Node start = null, target = null;
        System.out.println("A* has been launched!");

        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        for (Node[] nodes : field) {
            for (Node node : nodes) {
                if (node.isStart) start = node;
                if (node.isTarget) target = node;
            }
        }
        start.sumOfTwoDistances = start.calculateSumOfTwoDistances(start, target);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node node = openList.peek();
            if (node.value == target.value) return node;

            if (node.row - 1 >= 0) node.neighbors.add(new Node.Neighbor(field[node.row - 1][node.column]));
            if (node.row + 1 < field.length) node.neighbors.add(new Node.Neighbor(field[node.row + 1][node.column]));
            if (node.column - 1 >= 0) node.neighbors.add(new Node.Neighbor(field[node.row][node.column - 1]));
            if (node.column + 1 < field.length) node.neighbors.add(new Node.Neighbor(field[node.row][node.column + 1]));

            for (Node.Neighbor nbhr : node.neighbors) {
                Node neighbor = nbhr.node;
                int totalWeight = node.calculateSumOfTwoDistances(start, target);

                if (!openList.contains(neighbor) && !closedList.contains(neighbor)) {
                    neighbor.parent = node;
                    neighbor.startToNodeDistance = totalWeight;
                    neighbor.sumOfTwoDistances = neighbor.startToNodeDistance + neighbor.calculateDistanceToTarget(target);
                    if (!node.isObstacle && !neighbor.isObstacle) openList.add(neighbor);
                } else {
                    if (totalWeight < neighbor.startToNodeDistance) {
                        neighbor.parent = node;
                        neighbor.startToNodeDistance = totalWeight;
                        neighbor.sumOfTwoDistances = neighbor.startToNodeDistance + neighbor.calculateDistanceToTarget(target);
                        if (closedList.contains(neighbor)) {
                            closedList.remove(neighbor);
                            if (!node.isObstacle && !neighbor.isObstacle) openList.add(neighbor);
                        }
                    }
                }
            }
            openList.remove(node);
            closedList.add(node);
        }
        return null;
    }

    void setTargetArray() {

        List<Integer> list = new ArrayList<>();
        for (int[] ints : processingArray)
            for (int anInt : ints) {
                if (anInt == 0) continue;
                list.add(anInt);
            }
        List<Integer> buffer = new ArrayList<>(list.stream().sorted().toList());
        cellsNotInPlace = new ArrayList<>(buffer);
        buffer.add(0);

        targetArray = new int[dimension][dimension];
        for (int row = 0, iter = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                targetArray[row][column] = buffer.get(iter);
                iter++;
            }
        }
    }
}

class Main {
    public static void main(String[] args) {

        int[][][] fixed_tests = {
                  {{4,1,3},{2,8,0},{7,6,5}},
//                {{8,2,1},{3,7,0},{4,6,5}},
//                {{1,5,2},{7,0,8},{6,4,3}},
        };

        for (int[][] p : fixed_tests) {
            SlidingPuzzle slidingPuzzle = new SlidingPuzzle(p);
            System.out.println();
            System.out.println("========================================================================================");
            System.out.println();
            slidingPuzzle.solve();
        }
    }
}

class Node implements Comparable<Node> {

    int value, row, column;
    boolean isStart, isTarget, isObstacle;

    Node parent = null;
    List<Neighbor> neighbors;

    int startToNodeDistance; // g(x)
    int sumOfTwoDistances; // f(x)

    Node(int value, int row, int column) {
        this.row = row;
        this.column = column;
        this.value = value;

        this.neighbors = new ArrayList<>();
    }

    @Override
    public String toString() {  // TODO: 21-Feb-23 Delete this crap after final testing
        return String.valueOf(value);
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.sumOfTwoDistances, node.sumOfTwoDistances);
    }

    static class Neighbor {
        Node node;

        Neighbor(Node node) {
            this.node = node;
        }

        @Override
        public String toString() { // TODO: 21-Feb-23 Delete this crap after final testing
            return String.valueOf(this.node.value);
        }
    }

    int calculateDistanceToStart(Node start) {
        return Math.abs(this.row - start.row) + Math.abs(this.column - start.column);
    }

    int calculateDistanceToTarget(Node target) {
        return Math.abs(this.row - target.row) + Math.abs(this.column - target.column);
    }

    int calculateSumOfTwoDistances(Node start, Node target) {
        return calculateDistanceToStart(start) + calculateDistanceToTarget(target);
    }
}