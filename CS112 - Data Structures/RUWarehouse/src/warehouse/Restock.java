package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	    // Uset his file to test restock

        int num = StdIn.readInt();

        Warehouse w1 = new Warehouse();

        for (int x= 0; x<num; x++){                            
            String task = StdIn.readString();
            
            if (task.equals("add")){
                int day = StdIn.readInt();
                int iD = StdIn.readInt();
                String name =StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                w1.addProduct(iD, name, stock, day, demand);
            }
            if (task.equals("restock")){
                int iD = StdIn.readInt();
                int amount = StdIn.readInt();
                w1.restockProduct(iD, amount);
            }
        }

        StdOut.println(w1);

    }
}
