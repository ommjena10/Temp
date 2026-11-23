public class Main {
    public static void main(String[] args) {
        // test insert, update, delete,capacity Limit
        SparseMatrix m1 = new SparseMatrix(4, 4, 3);
        System.out.println("Insert (2, 2, 3.0): " + m1.set(2, 2, 3.0)); // true
        System.out.println("Insert (0, 1, 5.0): " + m1.set(0, 1, 5.0)); // true before 2,2
        System.out.println("Insert (2, 0, 8.0): " + m1.set(2, 0, 8.0)); // true  between 0,1 - 2,2
        System.out.println("Over capacity (3,3): " + m1.set(3, 3, 9.0)); // false
        System.out.println("Non-zero count: " + m1.getNonZeroCount());   // 3

        // update or delete 
        m1.set(0, 1, 10.0); // update
        System.out.println("Get (0, 1): " + m1.get(0, 1));
        System.out.println("Get (1, 1): " + m1.get(1, 1)); // 0
        m1.set(2, 0, 0.0);  // delete
        System.out.println("Count after delete: " + m1.getNonZeroCount()); // 2

        //add
        SparseMatrix m2 = new SparseMatrix(4, 4, 3);
        m2.set(0, 1, -10.0); 
        m2.set(1, 2, 4.5);   // New 
        m2.set(2, 2, 2.0);   

        SparseMatrix sum = m1.add(m2);
        System.out.println("Sum non-zero count: " + sum.getNonZeroCount()); // 2 
        System.out.println("Sum (0, 1) cancelled: " + sum.get(0, 1));       // 0
        System.out.println("Sum (1, 2): " + sum.get(1, 2));                 // 4.5
        System.out.println("Sum (2, 2): " + sum.get(2, 2));                 // 5

        //scale
        sum.scale(2.0);
        System.out.println("Scaled (2, 2) by 2: " + sum.get(2, 2)); // 10
        sum.scale(0.0);
        System.out.println("Scaled by 0 count: " + sum.getNonZeroCount()); // 0
    }
}