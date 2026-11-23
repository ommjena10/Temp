public class SparseMatrix {
    private final int totalRows;
    private final int totalCols;
    private final MatrixElement[] elements; 
    private int nonZeroCount;

    public SparseMatrix(int rows, int cols, int maxNonZeroCapacity) {
        if (rows <= 0 || cols <= 0 || maxNonZeroCapacity < 0) {
            throw new IllegalArgumentException("Invalid matrix dimensions or capacity");
        }
        this.totalRows = rows;
        this.totalCols = cols;
        this.elements = new MatrixElement[maxNonZeroCapacity];
        this.nonZeroCount = 0;
    }

    public int getNonZeroCount() {
        return nonZeroCount;
    }

    private void validateCoords(int r, int c) {
        if (r < 0 || r >= totalRows || c < 0 || c >= totalCols) {
            throw new IllegalArgumentException("Coordinates out of range: (" + r + ", " + c + ")");
        }
    }

    // binary search: returns index if found else -(inserton + 1) ifmissing 
   private int findIndex(int var1, int var2) {
      int var3 = 0;
      int var4 = this.nonZeroCount - 1;

      while(var3 <= var4) {
         int var5 = var3 + (var4 - var3) / 2;
         int var6 = this.elements[var5].compareCoords(var1, var2);
         if (var6 == 0) {
            return var5;
         }

         if (var6 < 0) {
            var3 = var5 + 1;
         } else {
            var4 = var5 - 1;
         }
      }

      return -(var3 + 1);
   }
    // inserts,updates,deletes
    public boolean set(int r, int c, double val) {
        validateCoords(r, c);
        int idx = findIndex(r, c);

        if (idx >= 0) {
            if (val == 0.0) {
                for (int i = idx; i < nonZeroCount - 1; i++) {
                    elements[i] = elements[i + 1];
                }
                elements[nonZeroCount - 1] = null;
                nonZeroCount--;
            } else {
                elements[idx].setValue(val);
            }
            return true;
        }

        if (val == 0.0) {
            return true; 
        }
        if (nonZeroCount == elements.length) {
            return false; //capacity reached
        }

        int ip = -(idx + 1);
        for (int i = nonZeroCount; i > ip; i--) {
            elements[i] = elements[i - 1];
        }
        elements[ip] = new MatrixElement(r, c, val);
        nonZeroCount++;
        return true;
    }

    
    public double get(int r, int c) {
        validateCoords(r, c);
        int idx = findIndex(r, c);
        if (idx >= 0) {
            return elements[idx].getValue();
        }else {
            return 0.0;
        }
    }

    
    public void scale(double k) {
        if (k == 0.0) {
            for (int i = 0; i < nonZeroCount; i++) {
                elements[i] = null;
            }
            nonZeroCount = 0;
            return;
        }
        for (int i = 0; i < nonZeroCount; i++) {
            elements[i].setValue(elements[i].getValue() * k);
        }
    }

    // Adds 
    public SparseMatrix add(SparseMatrix other) {
        if (other == null || this.totalRows != other.totalRows || this.totalCols != other.totalCols) {
            throw new IllegalArgumentException("Matrix dimensions must match for addition");
        }

        SparseMatrix result = new SparseMatrix(
            this.totalRows,
            this.totalCols,
            this.nonZeroCount + other.nonZeroCount
        );

        int i = 0;
        int j = 0;

        
        while (i < this.nonZeroCount && j < other.nonZeroCount) {
            MatrixElement a = this.elements[i];
            MatrixElement b = other.elements[j];
            int cmp = a.compareTo(b);

            if (cmp < 0) {
                result.elements[result.nonZeroCount++] = new MatrixElement(a.getRow(), a.getCol(), a.getValue());
                i++;
            } else if (cmp > 0) {
                result.elements[result.nonZeroCount++] = new MatrixElement(b.getRow(), b.getCol(), b.getValue());
                j++;
            } else {
                double sum = a.getValue() + b.getValue();
                if (sum != 0.0) {
                    result.elements[result.nonZeroCount++] = new MatrixElement(a.getRow(), a.getCol(), sum);
                }
                i++;
                j++;
            }
        }

        // append 
        while (i < this.nonZeroCount) {
            MatrixElement a = this.elements[i++];
            result.elements[result.nonZeroCount++] = new MatrixElement(a.getRow(), a.getCol(), a.getValue());
        }
        while (j < other.nonZeroCount) {
            MatrixElement b = other.elements[j++];
            result.elements[result.nonZeroCount++] = new MatrixElement(b.getRow(), b.getCol(), b.getValue());
        }

        return result;
    }
}