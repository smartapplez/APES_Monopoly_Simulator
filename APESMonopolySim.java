import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class APESMonopolySim
{
   public static Queue<Chance> chanceStack = new LinkedList<Chance>();
   public static Queue<CommChest> commChestStack = new LinkedList<CommChest>();
   public static Player[] players; //Stores every player
   public static BoardSpaces[] board; //Stores every space in the board
   public static BufferedWriter fWriter; //The output of a file displaying the balances of each player after each action
   public static BufferedWriter fWriter2; //The output of a file displaying the actions of every player as well as their balances
   public static long limitRounds; //Sets the limit of the number of rounds this program can run

   private static ArrayList<Chance> oChanceStack; //This is going to be a temporary list of Chance cards to be shuffled
   private static ArrayList<CommChest> oCommChestStack; //This is going to be a temporary list of Community Chest cards to be shuffled
    private static int numRounds = 0;
    private static Stack<Byte> roundDigits;

   public static void main(String[] args) throws IOException {
       ActionCards temp = new ActionCards();
       Scanner keyboard = new Scanner(System.in);
       boolean endGame = false; //Dictates if the game will end
       boolean pass = false; //Dictates when the user has input a desirable value and moves onto the next step
       boolean roundCounter = true;
       String fileNameOne = "";
       System.out.println("APES Monopoly Simulator for \"Carrying Capacity\" Lab:");
       System.out.println("By: David Armijo ;))\n"); //I know I'm awesome ;))

       while (!pass) {
           System.out.print("Enter name of balance output file --> ");
           String fileName = keyboard.nextLine();

           //This essentially tries to create a file that the computer system that can accept the name
           //Otherwise, it will ask the user to input a valid file name
           try {
               System.out.println("CREATING FILE...\n");

               //This checks to see if there is another file that has the same name in the folder
               //If it does, the program asks the user if they want to replace the file
               //Otherwise, the program will ask the user for another file name
               try {
                   Scanner file = new Scanner(new File(fileName + ".txt"));

                   boolean innerPass = false;
                   while (!innerPass) {
                       System.out.println("There is an existing File called: " + fileName + ".txt");
                       System.out.print("Are you sure you want to replace this file? (\"y\" to continue):: ");

                       String input = keyboard.nextLine();
                       input = input.toLowerCase();

                       if (input.equals("y")) {
                           fWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));
                           fileNameOne = fileName;
                           innerPass = true;
                           pass = true;
                       } else {
                           innerPass = true;
                       }
                   }
               } catch (IOException e) {
                   fWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));
                   fileNameOne = fileName;
                   pass = true;
               }

               System.out.println();
           } catch (IOException e) {
               System.out.println("ERROR!! INVALID FILE NAME!!\n");
           }
       }
       pass = false;

       //This is the exact same as the block of code above
       //But this time, it creates another file where the actions of the player will be recorded in a separate file
       //Due to lack of space in the console (jGrasp console)
       while (!pass) {
           System.out.print("Enter name of general game output file --> ");
           String fileName = keyboard.nextLine();

           try {
               System.out.println("CREATING FILE...\n");

               try {
                   Scanner file = new Scanner(new File(fileName + ".txt"));

                   boolean innerPass = false;
                   while (!innerPass) {
                       if (!fileNameOne.equals(fileName)) {
                           System.out.println("There is an existing File called: " + fileName + ".txt");
                           System.out.print("Are you sure you want to replace this file? (\"y\" to continue):: ");

                           String input = keyboard.nextLine();
                           input = input.toLowerCase();

                           if (input.equals("y")) {
                               fWriter2 = new BufferedWriter(new FileWriter(fileName + ".txt"));
                               innerPass = true;
                               pass = true;
                           } else {
                               innerPass = true;
                           }
                       } else {
                           //Ensures the two outputs are not put INTO the same file or it will look like a completely
                           //Organized Chaos like who would want that to happen? not me xD
                           System.out.println("You cannot have one file with two outputs written into it!\n");
                           innerPass = true;
                       }
                   }
               } catch (IOException e) {
                   fWriter2 = new BufferedWriter(new FileWriter(fileName + ".txt"));
                   pass = true;
               }

               System.out.println();
           } catch (IOException e) {
               System.out.println("ERROR!! INVALID FILE NAME!!\n");
           }
       }
       pass = false;


       System.out.println("CREATING ACTION CARDS...\n");
       oChanceStack = new ArrayList<Chance>();
       oCommChestStack = new ArrayList<CommChest>();
       createChanceCards();
       createCommChestCards();

       System.out.println("SHUFFLING CHANCE CARDS...\n");
       chanceStack = temp.shuffleChanceCards(oChanceStack);

       System.out.println("SHUFFLING COMMUNITY CHEST CARDS...\n");
       commChestStack = temp.shuffleCommChestCards(oCommChestStack);

       System.out.println("CREATING BOARD...\n");
       board = new BoardSpaces[40];
       buildBoardGame(board);

       //Asks a valid value to put the limit of rounds this Simulation will execute
       while (!pass) {
           try {
               System.out.print("Enter limit of rounds --> ");
               limitRounds = keyboard.nextLong();
               if (limitRounds > 0)
                   pass = true;
           } catch (Exception InputMismatchException) {
               System.out.println("INVALID INPUT!! TRY AGAIN!\n");
               keyboard.nextLine();
           }
       }
       pass = false;
       System.out.println();
       keyboard.nextLine();

       //Asks a valid value for the number of players that will be playing the game
       int numPlayers = 0;
       while (!pass) {
           try {
               System.out.print("Enter number of players --> ");
               numPlayers = keyboard.nextInt();
               if (numPlayers > 0)
                   pass = true;
           } catch (Exception InputMismatchException) {
               System.out.println("INVALID INPUT!! TRY AGAIN!\n");
               keyboard.nextLine();
           }
       }
       pass = false;

       System.out.println();
       keyboard.nextLine();

       //Asks for a valid value for the starting balance that each player will start with at the beginning of the game.
       int iBalance = 0;
       while (!pass) {
           try {
               System.out.print("Enter starting balance --> $");
               iBalance = keyboard.nextInt();
               if (iBalance > 0)
                   pass = true;
           } catch (Exception InputMismatchException) {
               System.out.println("INVALID INPUT!! TRY AGAIN!\n");
               keyboard.nextLine();
           }
       }

       System.out.println();
       System.out.println("CREATING PLAYER ARRAY...\n");
       players = new Player[numPlayers];

       keyboard.nextLine();

       //Asks for the name for each player that will be playing the game
       for (int i = 0; i < numPlayers; i++) {
           System.out.print("Enter Player name --> ");
           String name = keyboard.nextLine();
           players[i] = new Player(name, 0, iBalance, false, false, players, board);
           System.out.println();
       }

       pass = false;
       while(!pass){
           System.out.print("Round Counter? (y/n ONLY) --> ");
           String input = keyboard.nextLine();
           if(input.equalsIgnoreCase("y")){
               pass = true;
           }
           else if(input.equalsIgnoreCase("n")){
               pass = true;
               roundCounter = false;
           }
           else
               System.out.println("INVALID INPUT! PLEASE TRY AGAIN!\n");
       }

       //diceOne and diceTwo are separate references because it will help determine if a player gets a double and have
       //Another turn.
       int diceOne;
       int diceTwo;
       String output = "";

       //Displays the title and disclaimer because that's always important heh
       System.out.print("APES MONOPOLY SIMULATOR OUTPUT-------\n\n");
       System.out.print("Number of Players: " + numPlayers + "\n");
       System.out.print("Starting Balance: $" + iBalance + "\n\n");

       output += "DISCLAIMER::\n";
       output += "1.) Players HAVE to buy the property they land on\n";
       output += "2.) Game ENDS when a player becomes bankrupt\n";
       output += "3.) Players cannot mortgage or trade properties\n";
       output += "4.) Players cannot build houses or hotels on their property\n";
       output += "5.) Players spend $50 to get out of jail if they don't have a Jail Free Card\n";
       output += "6.) Players will not auction the property if they don't have enough to buy the property\n\n\n";

       output += "APES MONOPOLY SIMULATOR OUTPUT-------\n\n";
       output += "Number of Players: " + numPlayers + "\n";
       output += "Starting Balance: $" + iBalance + "\n\n";
       writeToFile(fWriter, output);
       fWriter2.write(output);
       output = "";

       //Displays the name for each player before starting the game
       output += "PLAYERS:\n";
       for (Player each : players) {
           output = output + each.getName() + "\n";
       }
       output += "\n\n";
       writeToFile(fWriter, output);
       fWriter2.write(output);
       System.out.print(output);

       System.out.print("\nGAME STARTING\n\n");
       output = "";

       if(roundCounter) {
           roundDigits = new Stack<Byte>();
           roundDigits.add((byte) 0);
           System.out.print("Round Executing:  ");
       }
       else
           System.out.println("EXECUTING...");
       long startTime = System.currentTimeMillis();

       while (!endGame && numRounds < limitRounds)
       {
           Player bankrupted = null;
           String name = "";
           numRounds++;
           if(roundCounter)
               updateRounds();
           output = "ROUND " + numRounds + ": ------------\n\n";
           writeToFile(fWriter, output);
           writeToFile(fWriter2, output);
           byte numSnakeEyes = 0; //Basically shows the number of times this player rolled a double

           //Will go through each player in the current list and use the current player's turn
           //To execute special actions to that specific player.
           //TL;DR: Each player will have their turn in each round and something interesting will happen in their turn.
           for (Player each : players) {
               boolean snakeEyes = false; //Basically it's when the two dice shows up as a double
               numSnakeEyes = 0;

               fWriter2.write(each.getName() + "'s turn!!\n");

               //Checks if this player is currently in jail
               //If they are, the program checks if they have a JailFree Card
               //Otherwise, they will spend $50 to get out of jail
               if (each.inJail()) {
                   fWriter2.write(each.getName() + " is in Jail!\n");
                   if (!each.hasJailFreeCard()) {
                       each.setBalance(each.getBalance() - 50);
                       each.setJailStatus(false); //Set an indicator here
                       fWriter2.write(each.getName() + " spent $50 to get out of prison...\n");

                       //Checks if the player has become bankrupt
                       if (each.getBalance() < 0) {
                           endGame = true;
                           //Add a print statement here
                           fWriter2.write("BANKRUPT! ");
                           bankrupted = each;
                           break;
                       }
                   } else {
                       fWriter2.write(each.getName() + " used a Get out of Jail Free Card!\n");
                       each.useJailFreeCard();
                   }
               }

               //Now the player will have their turn to roll the dice
               do {
                   if (numSnakeEyes == 1)
                       fWriter2.write(each.getName() + "'s 2nd turn!!\n");
                   else if(numSnakeEyes == 2)
                       fWriter2.write(each.getName() + "'s 3rd turn!!\n");

                   diceOne = (int) (Math.random() * 5 + 1); //Make sure to cast the whole output instead of the function!! (int)(Math.random())
                   diceTwo = (int) (Math.random() * 5 + 1);
                   int sumDiceRoll = diceOne + diceTwo;
                   each.setDiceRoll((byte) sumDiceRoll); //Set an indicator here

                   fWriter2.write(each.getName() + " rolled a " + diceOne + " and a " + diceTwo + "\n");
                   fWriter2.write(diceOne + " + " + diceTwo + " = " + (diceOne + diceTwo) + "\n");

                   //Checks if a player rolled a 'double'
                   if (diceOne == diceTwo) {
                       numSnakeEyes++;

                       //Checks if the player rolled a double three times in a row. If they do, sends the player directly to jail and ends the turn before they move.
                       if (numSnakeEyes >= 3) {
                           fWriter2.write(each.getName() + " rolled 3 doubles in a row and is sent directly to Jail :(\n");
                           each.setSpaceNum(10);
                           each.setJailStatus(true);
                           break;
                       } else {
                           fWriter2.write(each.getName() + " rolled a double!\n");
                           snakeEyes = true;
                       }
                   } else
                       snakeEyes = false;

                   //Moves the player x spaces forward based on the values shown on the dice
                   int currSpaceNum = each.getSpaceNum() + sumDiceRoll;
                   each.setSpaceNum(currSpaceNum);

                   //Checks if the player passes through GO
                   if (each.getSpaceNum() >= 40) {
                       currSpaceNum -= 40;
                       each.setSpaceNum(currSpaceNum);
                       each.setBalance(each.getBalance() + 200); //Print here
                       fWriter2.write(each.getName() + " passed through GO and collects $200!\n");
                   }

                   fWriter2.write("\n");

                   //Executes the space the player lands on
                   //The index of the list indicates which action will be executed for the player: Properties/other Action Spaces
                   board[currSpaceNum].execute(each, fWriter2);
                   fWriter2.write(each.getName() + "'s current Balance:: $" + each.getBalance() + "\n\n");

                   //Checks if a player's balance reaches below zero, if it does. It breaks out of the loop (turn).
                   if (each.getBalance() < 0) {
                       endGame = true;
                       bankrupted = each;
                       name = each.getName();
                       fWriter2.write("BANKRUPT! ");
                       break;
                   }
               }
               while (snakeEyes && numSnakeEyes < 3); //This checks if the player rolled a double, if yes, go again
               //This will repeat until the player no longer rolls a double or rolled a double three times.
               fWriter2.write("End of Turn!\n");
               fWriter2.write("Balances::\n");

               for (Player p : players) {
                   fWriter2.write(p.getName() + ": $" + p.getBalance() + "\n");
               }

               fWriter2.write("\n\n");

               //Checks if the endGame flag has been raised, if so. Ends the game.
               if (endGame) {
                   fWriter2.write(name + " has become bankrupt!!\n");
                   fWriter2.write("Number of rounds: " + numRounds);
                   break;
               }
           }

           for (Player p : players) {
               fWriter.write(p.getName() + ": $" + p.getBalance() + "\n");
           }
           fWriter.newLine();

           if (bankrupted != null) {
               fWriter.write(bankrupted.getName() + " got bankrupt!");
               System.out.println("\n");
               System.out.println("GAME ENDED WITH " + bankrupted.getName() + " BEING BANKRUPT!!");
           }
           else if (numRounds >= limitRounds) {
               fWriter2.write("Perpetual game! No one is bankrupt!");
               fWriter.write("Perpetual game! No one is bankrupt!");
               System.out.println("\n");
               System.out.println("COULD NOT END GAME! MAX NUM ROUNDS REACHED!\n");
           }
       }
       long endTime = System.currentTimeMillis();
       double time = (endTime - startTime) / 1000.0;
       System.out.println("Number of Rounds: " + numRounds);
       System.out.print("Time taken: " + time + " seconds");
       fWriter.close();
       fWriter2.close();
   }
   
   /**
    * Builds the entire board, space by space
    * Each space has varying types of actions when a player lands on that space
    * @param board The board that will be constructed
    */
   public static void buildBoardGame(BoardSpaces[] board)
   {
      board[0] = new ActionSpaces("GO");
      board[1] = new PropertySpaces(new Property("Mediterranean Avenue", null, (short)60, (short)2));
      board[2] = new ActionSpaces("Community Chest", chanceStack, commChestStack);//Lookout for these
      board[3] = new PropertySpaces(new Property("Baltic Avenue", null, (short)60, (short)6));
      board[4] = new ActionSpaces("Income Tax");
      board[5] = new PropertySpaces(new Railroad("Reading Railroad", null, (short)200, (short)25));
      board[6] = new PropertySpaces(new Property("Oriental Avenue", null, (short)100, (short)6));
      board[7] = new ActionSpaces("Chance", chanceStack, commChestStack);
      board[8] = new PropertySpaces(new Property("Vermont Avenue", null, (short)100, (short)6));
      board[9] = new PropertySpaces(new Property("Connecticut Avenue", null, (short)120, (short)8));
      board[10] = new ActionSpaces("Just Visiting / In Jail");
      board[11] = new PropertySpaces(new Property("St. Charles Place", null, (short)140, (short)10));
      board[12] = new PropertySpaces(new Utility("Electric Company", null, (short)150, (short)0));
      board[13] = new PropertySpaces(new Property("States Avenue", null, (short)140, (short)10));
      board[14] = new PropertySpaces(new Property("Virginia Avenue", null, (short)160, (short)12));
      board[15] = new PropertySpaces(new Railroad("Pennsylvania Railroad", null, (short)200, (short)25));
      board[16] = new PropertySpaces(new Property("St. James Place", null, (short)180, (short)14));
      board[17] = new ActionSpaces("Community Chest", chanceStack, commChestStack);
      board[18] = new PropertySpaces(new Property("Tennessee Avenue", null, (short)180, (short)14));
      board[19] = new PropertySpaces(new Property("New York Avenue", null, (short)200, (short)16));
      board[20] = new ActionSpaces("Free Parking");
      board[21] = new PropertySpaces(new Property("Kentucky Avenue", null, (short)220, (short)18));
      board[22] = new ActionSpaces("Chance", chanceStack, commChestStack);
      board[23] = new PropertySpaces(new Property("Indiana Avenue", null, (short)220, (short)18));
      board[24] = new PropertySpaces(new Property("Illinois Avenue", null, (short)240, (short)20));
      board[25] = new PropertySpaces(new Railroad("B. & O. Railroad", null, (short)200, (short)25));
      board[26] = new PropertySpaces(new Property("Atlantic Avenue", null, (short)260, (short)22));
      board[27] = new PropertySpaces(new Property("Ventor Avenue", null, (short)260, (short)22));
      board[28] = new PropertySpaces(new Utility("Water Works", null, (short)150, (short)0));
      board[29] = new PropertySpaces(new Property("Marvin Gardens", null, (short)280, (short)24));
      board[30] = new ActionSpaces("Jail");
      board[31] = new PropertySpaces(new Property("Pacific Avenue", null, (short)300, (short)26));
      board[32] = new PropertySpaces(new Property("North Carolina Avenue", null, (short)300, (short)26));
      board[33] = new ActionSpaces("Community Chest", chanceStack, commChestStack);
      board[34] = new PropertySpaces(new Property("Pennsylvania Avenue", null, (short)320, (short)28));
      board[35] = new PropertySpaces(new Railroad("Short Line Railroad", null, (short)200, (short)25));
      board[36] = new ActionSpaces("Chance", chanceStack, commChestStack);
      board[37] = new PropertySpaces(new Property("Park Place", null, (short)350, (short)35));
      board[38] = new ActionSpaces("Luxury Tax");
      board[39] = new PropertySpaces(new Property("Boardwalk", null, (short)400, (short)50));
   
   }
   
   /**
    * Creates the list of all 16 Chance Cards in the game to be shuffled later on
    */
   public static void createChanceCards()
   {
      oChanceStack.add(new Chance("Bank pays you Dividend of $50", (byte)1, 50));
      oChanceStack.add(new Chance("Your loan matures. Collect $150", (byte)1, 150));
      oChanceStack.add(new Chance("Spending Fine $15", (byte)2, 15));
      oChanceStack.add(new Chance("You have been elected chairman of the board. Pay each player $50", (byte)2, 50)); //Add Special Case to this one
      oChanceStack.add(new Chance("Advance to Boardwalk", (byte)3));
      oChanceStack.add(new Chance("Advance to St. Charles Place (If you pass GO, collect $200)", (byte)3));
      oChanceStack.add(new Chance("Take a trip to Reading Railroad (If you pass GO, collect $200)", (byte)3));
      oChanceStack.add(new Chance("Advance to Illinois Avenue, (If you pass GO, collect $200)", (byte)3));
      oChanceStack.add(new Chance("Advance to GO! (Collect $200)", (byte)3));
      oChanceStack.add(new Chance("Go Back Three Spaces", (byte)3));
      oChanceStack.add(new Chance("GO DIRECTLY TO JAIL", (byte)3));
      oChanceStack.add(new Chance("Advance to nearest Railroad (Owned --> Pay twice the rental of property)", (byte)4));
      oChanceStack.add(new Chance("Advance to nearest Railroad (Owned --> Pay twice the rental of property)", (byte)4));
      oChanceStack.add(new Chance("Advance to nearest Utility (Owned --> Roll dice again and pay 10x of the roll)", (byte)4));
      oChanceStack.add(new Chance("Get Out of Jail Free Card!", (byte)5));
      oChanceStack.add(new Chance("You are Assessed for Street Repairs", (byte)0)); // NO EFFECT
   }

   /**
   *  Creates the list of all 16 Community Chest Cards in the game to be shuffled later on
   */

   public static void createCommChestCards()
   {
      oCommChestStack.add(new CommChest("Bank Error in your favor. Collect $200.", (byte)1, 200));
      oCommChestStack.add(new CommChest("Income Tax Refund. Collect $20", (byte)1, 20));
      oCommChestStack.add(new CommChest("You inherit $100", (byte)1, 100));
      oCommChestStack.add(new CommChest("Receive $25 Consultancy Fee", (byte)1, 25));
      oCommChestStack.add(new CommChest("Holiday Fund Matures. Receive $100", (byte)1, 100));
      oCommChestStack.add(new CommChest("You have won second prize in a beauty contest. Collect $10", (byte)1, 10));
      oCommChestStack.add(new CommChest("From Sale of Stock. You get $50.", (byte)1, 50));
      oCommChestStack.add(new CommChest("Life Insurance Matures. Collect $100.", (byte)1, 100));
      oCommChestStack.add(new CommChest("Pay School Fees of $50.", (byte)2, 50));
      oCommChestStack.add(new CommChest("Doctor's Fees. Pay $50.", (byte)2, 50));
      oCommChestStack.add(new CommChest("Pay Hospital Fees pf $100.", (byte)2, 100));
      oCommChestStack.add(new CommChest("GO DIRECTLY TO JAIL", (byte)3));
      oCommChestStack.add(new CommChest("Advance to GO! (Collect $200)", (byte)3));
      oCommChestStack.add(new CommChest("Get Out of Jail Free Card!", (byte)4));
      oCommChestStack.add(new CommChest("It's your birthday. Collect $10 From Every Player", (byte)1, 10)); //Change the balances of other player's balances
      oCommChestStack.add(new CommChest("You are Assessed for Street Repairs", (byte)0)); // NO EFFECT
   }

   /**
    * Writes an output to a file
    * @param fWriter The file that this String will be written into
    * @param s The String that will be written to a file
    */
   public static void writeToFile(BufferedWriter fWriter, String s) throws IOException
   {
      fWriter.write(s);
   }

    /**
     * Updates the number of Rounds shown in the console
     */
   public static void updateRounds()
   {
       updateDigits(roundDigits.pop());
   }

    /**
     * Updates the digits TO BE SHOWN in the console. This is a recursive method!
     * @param digit The digit that will be analyzed and printed according to decimal format
     */
   public static void updateDigits(byte digit)
   {
       System.out.print("\b");

       if(digit < 9){
           digit++;
           roundDigits.push(digit);
           System.out.print(digit);
       }
       else{
           if(roundDigits.size() == 0){
               roundDigits.push((byte) 1);
               roundDigits.push((byte) 0);
               System.out.print(10);
           }
           else{
               updateDigits((byte) (roundDigits.pop()));
               digit = 0;
               roundDigits.push(digit);
               System.out.print(digit);
           }
       }
   }
}