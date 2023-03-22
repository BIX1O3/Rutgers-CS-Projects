package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	    // Use this file to test all methods

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
            if (task.equals("delete")){
                int iD = StdIn.readInt();
                w1.deleteProduct(iD);
            }
            if (task.equals("purchase")){
                int day = StdIn.readInt();
                int iD = StdIn.readInt();
                int amount = StdIn.readInt();
                w1.purchaseProduct(iD, day, amount);
            }
        }

        StdOut.println(w1);


    }
}
