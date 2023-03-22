package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	    // Use this file to test deleteProduct

        int num = StdIn.readInt();

        Warehouse w1 = new Warehouse();

        for (int x= 0; x<num; x++){                            
            String task = StdIn.readString();
            
            if (task.equals("add")){
                int day = StdIn.readInt();
                int iD = StdIn.readInt();
                String name =StdIn.readString();
                int stock = StdIn.readInt();
                int demoand = StdIn.readInt();
                w1.addProduct(iD, name, stock, day, demoand);
            }
            if (task.equals("delete")){
                int iD = StdIn.readInt();
                w1.deleteProduct(iD);
            }
        }


        StdOut.println(w1);
    }
}
