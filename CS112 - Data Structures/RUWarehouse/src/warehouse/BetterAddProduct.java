package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        // Use this file to test betterAddProduct

        int num = StdIn.readInt();

        Warehouse w1 = new Warehouse();



        for (int x= 0; x<num; x++){
            int day = StdIn.readInt();
            int iD = StdIn.readInt();
            String name =StdIn.readString();
            int stock = StdIn.readInt();
            int demoand = StdIn.readInt();

            w1.betterAddProduct(iD, name, stock, day, demoand);
        }


        StdOut.println(w1);
    }
}
