import java.util.*;
import java.io.IOException;

public class test
{
   public static BoardSpaces[] board;
   
   public static void main(String[]args)
   {
      board = new BoardSpaces[8];
      board[1] = new PropertySpaces(new Property("Boardwalk", null, (short)400, (short)50));
      board[2] = new PropertySpaces(new Railroad("Reading Railroad", null, (short)200, (short)25));
      board[3] = new PropertySpaces(new Railroad("Pennsylvania Railroad", null, (short)200, (short)25));
      board[4] = new PropertySpaces(new Railroad("B & O Railroad", null, (short)200, (short)25));
      board[5] = new PropertySpaces(new Railroad("Short Line Railroad", null, (short)200, (short)25));
      board[6] = new PropertySpaces(new Utility("Electric Company", null, (short)150, (short)0));
      board[7] = new PropertySpaces(new Utility("Water Works", null, (short)150, (short)0));

      
      short x;
      x = (short)-255;
      
      System.out.println(x + "");
      System.out.println();
      
      Scanner keyboard = new Scanner(System.in);
      
      /*
      //THIS IS LEGENDARY WOOOO!!!
      System.out.print("Enter name of the file --> ");
      String fileName = keyboard.nextLine();
      
      try
      {
         BufferedWriter fWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));
         
         fWriter.write("Hello, World!\n");
         fWriter.write("How are you doing?");
         
         fWriter.close();
      }
      catch(IOException e)
      {
         System.out.println("Error!");
      }*/
      
      Player one = new Player("David", 0, 1500, false, false, null, board);
      Player two = new Player("Eddy", 0, 1500, false, false, null, board);
      
      /*one.setSpaceNum(1);
       try {
           board[1].execute(one, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
      
      System.out.println();
      
      two.setSpaceNum(1);
       try {
           board[1].execute(two, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("Eddy's Balance: " + two.getBalance());
      System.out.println("Eddy's NumRR: " + two.getNumRR());
      System.out.println("Eddy's NumUtil " + two.getNumUtil());
      System.out.println();
      System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
      
      System.out.println();
      
      one.setSpaceNum(2);
       try {
           board[2].execute(one, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
   
      System.out.println();
      
      two.setSpaceNum(2);
       try {
           board[2].execute(two, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("Eddy's Balance: " + two.getBalance());
      System.out.println("Eddy's NumRR: " + two.getNumRR());
      System.out.println("Eddy's NumUtil " + two.getNumUtil());
      System.out.println();
      System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
   
      System.out.println();
      
      one.setSpaceNum(3);
       try {
           board[3].execute(one, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
   
      System.out.println();
          
      two.setSpaceNum(3);
       try {
           board[3].execute(two, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("Eddy's Balance: " + two.getBalance());
      System.out.println("Eddy's NumRR: " + two.getNumRR());
      System.out.println("Eddy's NumUtil " + two.getNumUtil());
      System.out.println();
      System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());

      System.out.println();

      one.setSpaceNum(4);
       try {
           board[4].execute(one, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
      
      System.out.println();
          
      two.setSpaceNum(4);
       try {
           board[4].execute(two, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("Eddy's Balance: " + two.getBalance());
      System.out.println("Eddy's NumRR: " + two.getNumRR());
      System.out.println("Eddy's NumUtil " + two.getNumUtil());
      System.out.println();
      System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
      
      System.out.println();
      
      one.setSpaceNum(5);
       try {
           board[5].execute(one, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
      
      System.out.println();
          
      two.setSpaceNum(5);
       try {
           board[5].execute(two, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("Eddy's Balance: " + two.getBalance());
      System.out.println("Eddy's NumRR: " + two.getNumRR());
      System.out.println("Eddy's NumUtil " + two.getNumUtil());
      System.out.println();
      System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());

      System.out.println();
      
      one.setSpaceNum(6);
       try {
           board[6].execute(one, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil: " + one.getNumUtil());
      
      System.out.println();
          
      two.setSpaceNum(6);
      two.setDiceRoll((byte)5);
       try {
           board[6].execute(two, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("Eddy's Balance: " + two.getBalance());
      System.out.println("Eddy's NumRR: " + two.getNumRR());
      System.out.println("Eddy's NumUtil " + two.getNumUtil());
      System.out.println("Eddy's diceRoll:" + two.getDiceRoll());
      System.out.println();
      System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
      
      System.out.println();
      
      one.setSpaceNum(7);
       try {
           board[7].execute(one, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());
      
      System.out.println();
          
      two.setSpaceNum(7);
      two.setDiceRoll((byte)5);
       try {
           board[7].execute(two, null);
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.out.println("Eddy's Balance: " + two.getBalance());
      System.out.println("Eddy's NumRR: " + two.getNumRR());
      System.out.println("Eddy's NumUtil " + two.getNumUtil());
      System.out.println("Eddy's diceRoll: " + two.getDiceRoll());
      System.out.println();
      System.out.println("David's Balance: " + one.getBalance());
      System.out.println("David's NumRR: " + one.getNumRR());
      System.out.println("David's NumUtil " + one.getNumUtil());

      System.out.println();
*/
      System.out.print("Hello!");
      System.out.print("\b");

   }
}