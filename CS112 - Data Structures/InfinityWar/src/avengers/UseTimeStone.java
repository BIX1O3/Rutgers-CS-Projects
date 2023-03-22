package avengers;

import javax.naming.spi.StateFactory;
import javax.swing.text.html.StyleSheet;

/**
 * Given a startering event and an matrixacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 *    1. t (int): expected utility (EU) threshold
 *    2. v (int): number of events (vertices in the graph)
 *    3. v lines, each with 2 values: (int) event number and (int) EU value
 *    4. v lines, each with v (int) edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix for a directed
 * graph. 
 * The rows represent the "from" vertex and the columns represent the "to" vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2) from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the startering event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for total number of timelines
 *     //Call StdOut.print() for number of timelines with EU >= threshold EU 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    private static int eU;
    //private static int[] results;
    private static boolean[] checkin;

    private static int d;
    private static int[] timelines;
    private static int[][] matrix;
    private static int[] value;
    private static int counter;
    //private static boolean status;
    private static int holder;
    //private static int temp;

    private static int greaterThanEU;

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE

        String UseTimeStoneInputFile = args[0];
        String UseTimeStoneOutputFile = args[1];

        
        StdIn.setFile(UseTimeStoneInputFile);
        StdOut.setFile(UseTimeStoneOutputFile);

        eU = StdIn.readInt();
        int num = StdIn.readInt();

        value = new int[num];
        counter = 1;


        int first= StdIn.readInt();


        while(first != num-1){
            value[first] = StdIn.readInt();

            first = StdIn.readInt();

        }

        value[first] = StdIn.readInt();



        matrix = new int[num][num];

        for (int x = 0; x<matrix.length; x++){
            for (int y = 0; y<matrix[x].length; y++){
                matrix[x][y] = StdIn.readInt();
            }
        }


        
        holder = 0;
        
        checkin = new boolean[matrix.length];

        d = 1;

        greaterThanEU = 0;

        for (int x = 0; x<2; x++){
            if (x == 1){
                timelines = new int [counter];
                
                counter = 1;
                DFS(0,timelines);
            }else{
                DFS(0);
            }
        }
        timelines[0] = value[0]; 

        


        

        if (timelines[0]>eU){
            greaterThanEU++;
        }


        StdOut.println(counter);
        StdOut.print(greaterThanEU);
    }

    static void DFS(int starter)
    {
        

        
        for (int i = 0; i < matrix[starter].length; i++) {
            if (matrix[starter][i] == 1 ) {

                counter++;

            
                DFS(i);
                
            }
            
        }
    }
    

    static void DFS(int starter, int[] timelines)
    {

        for (int i = 0; i < matrix[starter].length; i++) {
            if (matrix[starter][i] == 1 ) {
                
                
                checkin[starter] = true;
                
                
                
                counter++;

                
                holder = value[i];

                

                if (starter != 0){

                    timelines[d] = holder + timelines[d];
                    if (timelines[d]>=eU){
                        greaterThanEU++;
                    }
                }
                else{
                    timelines[d] = holder + value[0];
                    if (timelines[d]>eU){
                        greaterThanEU++;
                    }
                    
                }

                
                DFS(i, timelines);

                
                
            }
            else if (i == matrix[starter].length-1 && matrix[starter][i] == 0 && checkin[starter]!=true){
                
                d++;

                timelines[d] = timelines[d-1] - holder;
            }
            
        }
    }

}
