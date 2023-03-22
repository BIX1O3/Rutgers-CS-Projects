package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.security.auth.callback.TextInputCallback;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        String inputString = StdIn.readAll();

	    /* Your code goes here */

        char[] cArray = inputString.toCharArray();

        double frequency = 0.0;
        
        double count = 0;
        ArrayList<Character> prevChar = new ArrayList<Character>();
        sortedCharFreqList = new ArrayList<CharFreq>();
        

        for (int x = 0; x<cArray.length; x++){
            if (prevChar.indexOf(cArray[x]) == -1){

                for (char c : cArray) {
                    if (c == cArray[x]){
                        count++;
                    }        
                }

                prevChar.add(cArray[x]);
                frequency = count/cArray.length;


                if( frequency!=0.0){
                    CharFreq tempNode = new CharFreq(cArray[x],frequency);
                    sortedCharFreqList.add(tempNode);
                }
            }
            count = 0;
        }

        if (sortedCharFreqList.size() == 1){
            CharFreq temp1 = new CharFreq((char)((((int)cArray[0])+1)%128), 0.00);

            sortedCharFreqList.add(temp1);
        }

        Collections.sort(sortedCharFreqList);
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {//real one

	    /* Your code goes here */
        Queue<CharFreq> source = new Queue<CharFreq>();
        Queue<TreeNode> target = new Queue<TreeNode>();



        while (!sortedCharFreqList.isEmpty()){
            source.enqueue(sortedCharFreqList.remove(0));
        }


        if (source.size() == 1){
            CharFreq tempNode = source.dequeue();
            TreeNode tempTreeNode = new TreeNode(tempNode, null, null);


            CharFreq tempT = new CharFreq('b', 0.0);

            TreeNode tempTr = new TreeNode(tempT, null, null);

            CharFreq newNode = new CharFreq(null, tempNode.getProbOcc()+tempTr.getData().getProbOcc());

            TreeNode temp = new TreeNode(newNode, tempTr, tempTreeNode);
            huffmanRoot = temp;


        }else{
            while(!(source.size()+target.size() == 1)){
                if (target.isEmpty()){
                    CharFreq temp1 = source.dequeue();
                    CharFreq temp2 = source.dequeue();

                    TreeNode left = new TreeNode(temp1, null, null);
                    TreeNode right = new TreeNode(temp2, null, null);

                    CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+right.getData().getProbOcc()); // source source
                    
                    
                    TreeNode newNode = new TreeNode(newChar, left, right);

                    target.enqueue(newNode);
                
                }
                else if (source.isEmpty()){
                    TreeNode left = target.dequeue();
                    TreeNode right = target.dequeue();

                    CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+right.getData().getProbOcc());

                    TreeNode newNode = new TreeNode(newChar, left, right);

                    target.enqueue(newNode);
                }
                else{
                    
                    if (source.peek().getProbOcc()<=target.peek().getData().getProbOcc()){
                        CharFreq temp1 = source.dequeue();
                        if (source.isEmpty()){
                            if (target.isEmpty()){

                                TreeNode left = new TreeNode(temp1, null, null);
                                
                                CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+0);
                            
                            
                                TreeNode newNode = new TreeNode(newChar, left, null);

                                target.enqueue(newNode);

                            }
                            else if(!target.isEmpty()){
                                TreeNode right = target.dequeue();

                                TreeNode left = new TreeNode(temp1, null, null);

                                CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+right.getData().getProbOcc());
                            
                            
                                TreeNode newNode = new TreeNode(newChar, left, right);

                                target.enqueue(newNode);

                            }
                        }
                        else if (source.peek().getProbOcc()<=target.peek().getData().getProbOcc()){
                            CharFreq temp2 = source.dequeue();

                            TreeNode left = new TreeNode(temp1, null, null);
                            TreeNode right = new TreeNode(temp2, null, null);

                            CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+right.getData().getProbOcc());
                            
                            
                            TreeNode newNode = new TreeNode(newChar, left, right);

                            target.enqueue(newNode);


                        }
                        else if (source.peek().getProbOcc()>target.peek().getData().getProbOcc()){
                            TreeNode right = target.dequeue();

                            TreeNode left = new TreeNode(temp1, null, null);

                            CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+right.getData().getProbOcc());
                            
                            
                            TreeNode newNode = new TreeNode(newChar, left, right);

                            target.enqueue(newNode);
                        }
                    }
                    else if (source.peek().getProbOcc()>target.peek().getData().getProbOcc()){
                        TreeNode left = target.dequeue();

                        if (target.isEmpty()){
                            if (source.isEmpty()){

                                
                                
                                CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+0);
                            
                            
                                TreeNode newNode = new TreeNode(newChar, left, null);

                                target.enqueue(newNode);

                            }
                            else if(!source.isEmpty()){
                                CharFreq temp2 = source.dequeue();

                                TreeNode right = new TreeNode(temp2, null, null);

                                CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+right.getData().getProbOcc());
                            
                            
                                TreeNode newNode = new TreeNode(newChar, left, right);

                                target.enqueue(newNode);

                            }
                        }
                        else if(source.peek().getProbOcc()<=target.peek().getData().getProbOcc()){
                            CharFreq temp2 = source.dequeue();

                            TreeNode right = new TreeNode(temp2, null, null);

                            CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+right.getData().getProbOcc());
                            
                            
                            TreeNode newNode = new TreeNode(newChar, left, right);

                            target.enqueue(newNode);
                        }
                        else if (source.peek().getProbOcc()>target.peek().getData().getProbOcc()){
                            TreeNode right = target.dequeue();

                            CharFreq newChar = new CharFreq(null, left.getData().getProbOcc()+right.getData().getProbOcc());
                            
                            
                            TreeNode newNode = new TreeNode(newChar, left, right);

                            target.enqueue(newNode);
                        }

                    }
                }
                
                if (source.isEmpty() && target.size() == 1){
                    TreeNode root = target.dequeue();

                    huffmanRoot = root;
                    break;
                }
            }
        }
    }


    public String[] recursiveTraversal(TreeNode root, String value, String[] encode){
        
        if (root.getLeft() == null && root.getRight() == null) {
            encode[(int)root.getData().getCharacter()] = value;
            return encode;
        }


        if (root.getLeft()!=null) recursiveTraversal(root.getLeft(), value + "0", encode);
        
        if (root.getRight()!=null){
            recursiveTraversal(root.getRight(), value + "1", encode);
        }



        return encode;
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

	/* Your code goes here */
        encodings = new String[128];

        makeSortedList();

        TreeNode tempHuffmanRoot = huffmanRoot;

        String value = "";

        encodings = recursiveTraversal(tempHuffmanRoot, value, encodings);
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

	    /* Your code goes here */
        String bitCode = "";
        char temp; 

                 
        while (StdIn.hasNextChar()){
            temp = StdIn.readChar();
            bitCode += encodings[(int)temp];
        }
        writeBitString(encodedFile,bitCode);


    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

	    /* Your code goes here */
        String bitFile = readBitString(encodedFile);

        char[] temp = bitFile.toCharArray(); 

        TreeNode tempRoot = huffmanRoot;
        

                 
        for (int x = 0; x<temp.length+1;x++){
            if (tempRoot.getLeft()==null && tempRoot.getRight() == null){
                StdOut.print(tempRoot.getData().getCharacter());
                tempRoot = huffmanRoot;
            }
            if(x<temp.length){
                if (temp[x] == '0'){
                    tempRoot = tempRoot.getLeft();
                }
                else if (temp[x] == '1'){
                    tempRoot = tempRoot.getRight();
                }
            }
        }


    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
