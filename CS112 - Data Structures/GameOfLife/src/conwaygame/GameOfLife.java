package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        //WRITE YOUR CODE HERE
        StdIn.setFile(file);
        int r = StdIn.readInt();
        int c = StdIn.readInt();

        grid = new boolean[r][c];

        for (int x = 0; x<r; x++){
            for (int i = 0; i<c; i++){

                grid[x][i]= StdIn.readBoolean();
            }
        } 
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        if (grid[row][col] == ALIVE){
            return true;
        }
        return DEAD;
         
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE
        int count =0;
        for (int x = 0; x<grid.length; x++){
            for (int i = 0; i<grid[x].length; i++){
                if(grid[x][i] == ALIVE){
                    count ++;
                }
            }
        }
        if (count > 0){
            return ALIVE;
        }
        return DEAD;
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE
        int count = 0;        
        
        if(grid[((row-1)+grid.length) % grid.length][((col-1)+grid[0].length)%grid[0].length]==ALIVE){
            count++;
        }
        if(grid[((row-1)+grid.length) % grid.length][((col)+grid[0].length)%grid[0].length]==ALIVE){
            count++;
        }
        if(grid[((row-1)+grid.length) % grid.length][((col+1)+grid[0].length)%grid[0].length]==ALIVE){
            count++;
        }
        if(grid[((row)+grid.length) % grid.length][((col-1)+grid[0].length)%grid[0].length]==ALIVE){
            count++;
        }
        if(grid[((row)+grid.length) % grid.length][((col+1)+grid[0].length)%grid[0].length]==ALIVE){
            count++;
        }
        if(grid[((row+1)+grid.length) % grid.length][((col-1)+grid[0].length)%grid[0].length]==ALIVE){
            count++;
        }
        if(grid[((row+1)+grid.length) % grid.length][((col)+grid[0].length)%grid[0].length]==ALIVE){
            count++;
        }
        if(grid[((row+1)+grid.length) % grid.length][((col+1)+grid[0].length)%grid[0].length]==ALIVE){
            count++;
        }
        
      
        return count;
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        // WRITE YOUR CODE HERE
        int row=grid.length;
        int col=grid[0].length;
        boolean[][] newGrid = new boolean[row][col];



        for (int x=0; x<grid.length;x++){
            for (int y=0;y<grid[x].length;y++){
                newGrid[x][y] = grid[x][y];
                if (numOfAliveNeighbors(x, y) <= 1 || numOfAliveNeighbors(x, y)>3 && grid[x][y]==ALIVE){
                    newGrid[x][y]=DEAD;
                }
                
                if ((numOfAliveNeighbors(x, y) == 2 || numOfAliveNeighbors(x, y) == 3) && grid[x][y]== ALIVE){
                    newGrid[x][y]=ALIVE;
                }
                
                if (grid[x][y]==DEAD && numOfAliveNeighbors(x, y) == 3){
                    newGrid[x][y]=ALIVE;
                }

                
            }
        }


        return newGrid;
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        grid = computeNewGrid();
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        for (int x=0;x<n;x++){
            nextGeneration();
        }
    }
    public ArrayList<Integer> alivelocations(){
        ArrayList<Integer> locations = new ArrayList<Integer>();
        
        for(int x=0; x<grid.length;x++){
            for(int i =0; i<grid[x].length;i++){
                if(getCellState(x, i) == ALIVE){
                    locations.add(x);
                    locations.add(i);
                }
            }
        }
        return locations;
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        WeightedQuickUnionUF wQUF = new WeightedQuickUnionUF(grid.length, grid[0].length);
        ArrayList<Integer> unionIDS = new ArrayList<Integer>();

        for (int x=0; x<grid.length;x++){
            for (int y=0; y<grid[0].length;y++){
                if (grid[x][y] == ALIVE ){
                    if (numOfAliveNeighbors(x,y)>0){
                        if(grid[((x-1)+grid.length) % grid.length][((y-1)+grid[0].length)%grid[0].length]==ALIVE){
                            wQUF.union(x, y, ((x-1)+grid.length) % grid.length, ((y-1)+grid[0].length)%grid[0].length);
                        }
                        if(grid[((x-1)+grid.length) % grid.length][((y)+grid[0].length)%grid[0].length]==ALIVE){
                           wQUF.union(x, y, ((x-1)+grid.length) % grid.length, ((y)+grid[0].length)%grid[0].length);
                        }
                        if(grid[((x-1)+grid.length) % grid.length][((y+1)+grid[0].length)%grid[0].length]==ALIVE){
                           wQUF.union(x, y, ((x-1)+grid.length) % grid.length, ((y+1)+grid[0].length)%grid[0].length);
                        }
                        if(grid[((x)+grid.length) % grid.length][((y-1)+grid[0].length)%grid[0].length]==ALIVE){
                           wQUF.union(x, y, ((x)+grid.length) % grid.length, ((y-1)+grid[0].length)%grid[0].length);
                        }
                        if(grid[((x)+grid.length) % grid.length][((y+1)+grid[0].length)%grid[0].length]==ALIVE){
                           wQUF.union(x, y, ((x)+grid.length) % grid.length, ((y+1)+grid[0].length)%grid[0].length);
                        }
                        if(grid[((x+1)+grid.length) % grid.length][((y-1)+grid[0].length)%grid[0].length]==ALIVE){
                           wQUF.union(x, y, ((x+1)+grid.length) % grid.length, ((y-1)+grid[0].length)%grid[0].length);
                        }
                        if(grid[((x+1)+grid.length) % grid.length][((y)+grid[0].length)%grid[0].length]==ALIVE){
                           wQUF.union(x, y, ((x+1)+grid.length) % grid.length, ((y)+grid[0].length)%grid[0].length);
                        }
                        if(grid[((x+1)+grid.length) % grid.length][((y+1)+grid[0].length)%grid[0].length]==ALIVE){
                           wQUF.union(x, y, ((x+1)+grid.length) % grid.length, ((y+1)+grid[0].length)%grid[0].length);
                        }
                    }
                }
            }
        }

        int temp=0;
        for (int x=0; x<grid.length;x++){
            for (int y=0; y<grid[x].length;y++){
                if (grid[x][y]==ALIVE){    
                    temp = wQUF.find(x,y);
                    if (unionIDS.contains(temp) != true){
                        unionIDS.add(temp);
                    } 
                }               
            }
        }

        return unionIDS.size();
    }
}
