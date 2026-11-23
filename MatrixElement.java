public class MatrixElement {
    private int row;
    private int col;
    private double value;

    public MatrixElement(int row, int col, double value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    
    public int compareCoords(int r, int c) {
        if (this.row != r) {
            return this.row - r; 
        }
        return this.col - c;    
    }

    public int compareTo(MatrixElement other) {
        return compareCoords(other.row, other.col);
    }
}