package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE

        // read file names from command line
        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];

        StdIn.setFile(locateTitanInputFile);

        int numGenerators = StdIn.readInt();

        double[] functionalityValue = new double[numGenerators];

        int[][] matrix = new int[numGenerators][numGenerators];

        int first= StdIn.readInt();


        while(first != numGenerators-1){
            functionalityValue[first] = StdIn.readDouble();

            first = StdIn.readInt();

        }

        functionalityValue[first] = StdIn.readDouble();


        for (int x = 0; x<matrix.length; x++){
            for (int y = 0; y<matrix[x].length; y++){
                matrix[x][y] = StdIn.readInt();
            }
            
        } 

        
        int newValue = 0;
        for (int x = 0; x<matrix.length; x++){
            for (int y = 0; y<matrix[x].length; y++){
                newValue = (int) (matrix[x][y]/(functionalityValue[x]*functionalityValue[y]));

                matrix[x][y] = newValue;

            }
        } 


        int[] minCost = new int[numGenerators];

        boolean[] inAlready = new boolean[numGenerators];

        for (int x = 0; x<minCost.length; x++){

            if (x==0){
                minCost[x] = 0;
            }
            else{
                minCost[x] = Integer.MAX_VALUE;
            }
        }

        int currentSource = 0;

        //

        for (int x = 1; x<minCost.length-1; x++){

            int tempMin = Integer.MAX_VALUE;

            for(int h = 0; h < minCost.length; h++){
                if ((inAlready[h] != true) && (minCost[h] != Integer.MAX_VALUE)){
                    if (tempMin > minCost[h]){
                        tempMin = minCost[h];
                        currentSource = h;
                    }
                    
                }
            }

            inAlready[currentSource] = true;


            if (currentSource == 0){
                int min = Integer.MAX_VALUE;
                int minIndex = 1;
                for (int y = 1; y<matrix[0].length; y++){

                    
                    if ((matrix[0][y] > 0) && (matrix[0][y] < min)){
                        min = matrix[0][y];
                        minIndex = y;
                    }
                }

                minCost[minIndex] = min;
            
            }
            else{
                for (int y = 0; y<matrix[currentSource].length-1; y++){
                    if ((inAlready[y] != true) && (minCost[currentSource] != Integer.MAX_VALUE) && (matrix[currentSource][y]>0) && (minCost[y]>(matrix[currentSource][y] + minCost[currentSource]))){
                        minCost[y] = matrix[currentSource][y] + minCost[currentSource];


                    }
                }
            }
        }


        for (int x = 1; x<minCost.length; x++){
            if (minCost[minCost.length-1] > (minCost[x] +matrix[matrix.length-1][x]) && (matrix[matrix.length-1][x] > 0)){
                minCost[minCost.length-1] = minCost[x] + matrix[matrix.length-1][x];
            }
        }

       
        StdOut.setFile(locateTitanOutputFile);
        StdOut.print(minCost[minCost.length-1]);

    }
}
