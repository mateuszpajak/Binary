import java.util.LinkedList;
import java.util.List;

public class Node {

    private int id;
    private Integer value;
    private int positionX;
    private int positionY;
    private List<Integer> possibleValues = new LinkedList<>();

    public Node() {
    }

    public Node(int id) {
        this.id = id;
    }

    public Node(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Node setId(int id) {
        this.id = id;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public Node setValue(Integer value) {
        this.value = value;
        return this;
    }

    public int getPositionX() {
        return positionX;
    }

    public Node setPositionX(int positionX) {
        this.positionX = positionX;
        return this;
    }

    public int getPositionY() {
        return positionY;
    }

    public Node setPositionY(int positionY) {
        this.positionY = positionY;
        return this;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public Node setPossibleValues(List<Integer> possibleValues) {
        this.possibleValues = possibleValues;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != node.id) return false;
        if (value != node.value) return false;
        if (positionX != node.positionX) return false;
        if (positionY != node.positionY) return false;
        return possibleValues != null ? possibleValues.equals(node.possibleValues) : node.possibleValues == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + value;
        result = 31 * result + positionX;
        result = 31 * result + positionY;
        result = 31 * result + (possibleValues != null ? possibleValues.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
