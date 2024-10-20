import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;


public class ActionSpaces implements BoardSpaces
{   
   private String name;
   private ActionCards temp;
   private static Queue<Chance> chanceStack;
   private static Queue<CommChest> commChestStack;
   private BufferedWriter fWriter2;

   /**
    * Builds this ActionSpace that is NOT a property space but will do some special effect to a player...
    * It can even have no effect as well
    * @param n The name of this ActionSpace
    */
   public ActionSpaces(String n)
   {
      name = n;
      temp = null;
   }

   /**
    * Builds this ActionSpace that will draw a card from a stack.
    * @param n The name of this Space
    * @param cS The stack of Chance Cards this type of space will have
    * @param ccS The stack of Community Chest Cards this type of space will have
    */
   public ActionSpaces(String n, Queue<Chance> cS, Queue<CommChest> ccS)
   {
      name = n;
      chanceStack = cS;
      commChestStack = ccS;
      temp = new ActionCards();
   }

   /**
    * Executes the space the player is currently on that is NOT a property space. This can include Chance and Community
    * Chest spaces as well as Go to Jail, Free Parking, GO, Just Visiting, and Tax Spaces.
    * @param p The player that has landed on this space
    * @param fWriter2 What will be written in the first file
    */
   public void execute(Player p, BufferedWriter fWriter2) throws IOException
   {
      this.fWriter2 = fWriter2;

      if(name.contains("Tax"))
      {
         if(name.contains("Income"))
         {
            fWriter2.write(p.getName() + " landed on Income Tax and spends $200 ;//\n");
            p.setBalance(p.getBalance() - 200);
         }
         else //This is essentially luxury tax
         {
            fWriter2.write(p.getName() + " landed on Luxury Tax and spends $100 (rip)\n");
            p.setBalance(p.getBalance() - 100);
         }
      }
      else if(name.equals("Chance"))
      {
         fWriter2.write(p.getName() + " landed on a Chance! 0.0\n");
         temp.drawChance(chanceStack);
         executeChance(p, temp);
         
      }
      else if(name.equals("Community Chest"))
      {
         fWriter2.write(p.getName() + " landed on a Community Chest Space! 0w0\n");
         temp.drawCommChest(commChestStack);
         executeCommChest(p, temp);
      }
      else if(name.equals("Jail"))
      {
         fWriter2.write(p.getName() + " landed on the Jail space and is taken directly to Jail! smhhh\n");
         p.setSpaceNum(10);
         p.setJailStatus(true);
      }
      else
      {//This could be a Free Parking Space or a Just Visiting Space
         if(name.equals("Free Parking"))
            fWriter2.write(p.getName() + " landed on Free Parking! Nothing to do here! ;))\n");
         else if(name.equals("GO"))
            fWriter2.write(p.getName() + " landed ON GO! Nothing to do here but to enjoy your free $200! ^_^\n");
         else
            fWriter2.write(p.getName() + " is Just Visiting! Good thing you're not behind the bars >:)\n");
      }
   }

   /**
    * Executes the Chance card that has been drawn from the stack (or Queue) of Chance Cards as used in the method
    * ActionSpaces.execute(). Executes by first looking for the Effect# of the card drawn, and then determining keywords
    * to execute the right instruction given to by the card.
    * @param p The player that this Chance Card will have effect on
    * @param temp The ActionCard that has the Chance Card drawn
    */
   public void executeChance(Player p, ActionCards temp) throws IOException
   {
      Chance tempCard = temp.getChanceCard();
      fWriter2.write("Chance: " + tempCard.getName() + "\n");

      if(tempCard.getEffectNum() != 0)
      {
         //This is the GAIN Money Effect
         if(tempCard.getEffectNum() == 1)
         {
            p.setBalance(p.getBalance() + tempCard.getAmount());
            fWriter2.write(p.getName() + " earned $" + tempCard.getAmount() + " from Chance card!\n");
         }
         else if(tempCard.getEffectNum() == 2) //This is the LOSE money Effect
         {
            if(tempCard.getName().contains("chairman"))
            {
               Player[] opponents = p.getOpponentsArray();

               for(Player each : opponents)
               {
                  if(!each.equals(p))
                  {
                     each.setBalance(each.getBalance() + 50);
                  }
               }
               fWriter2.write(p.getBalance() + " lost $" + (tempCard.getAmount() * (opponents.length-1)) + " in a Chance card!\n");
               p.setBalance(p.getBalance() - tempCard.getAmount() * (opponents.length-1));
            }
            else
            {
               p.setBalance(p.getBalance() - tempCard.getAmount());
               fWriter2.write(p.getName() + " lost $" + tempCard.getAmount() + " in a Chance Card!\n");
            }
         }
         else if(tempCard.getEffectNum() == 3) //This is the MOVE PLAYER Effect
         {
            BoardSpaces[] board = p.getBoard();

            if(tempCard.getName().contains("JAIL"))
            {
               p.setSpaceNum(10);
               p.setJailStatus(true);
               fWriter2.write(p.getName() + " is sent directly to Jail through Chance Card! D:<\n");
            }

            if(tempCard.getName().contains("Advance to GO!"))
            {
               p.setSpaceNum(0);
               p.setBalance(p.getBalance() + 200);
               fWriter2.write(p.getName() + " advanced to GO and collects $200! :DD\n");
            }

            if(tempCard.getName().contains("Go Back Three Spaces"))
            {
               p.setSpaceNum(p.getSpaceNum() - 3);
               fWriter2.write("\n");
               try {
                  board[p.getSpaceNum()].execute(p, fWriter2);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

            if(tempCard.getName().contains("Boardwalk"))
            {
               p.setSpaceNum(39);
               fWriter2.write("\n");
               try {
                  board[39].execute(p, fWriter2);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

            if(tempCard.getName().contains("St. Charles"))
            {
               if(p.getSpaceNum() > 11)
               {
                  fWriter2.write("On " + p.getName() + "'s trip to St. Charles, they pass GO and collect $200!! ^_^\n\n");
                  p.setBalance(p.getBalance() + 200);
               }

               p.setSpaceNum(11);
               try {
                  board[11].execute(p, fWriter2);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

            if(tempCard.getName().contains("Reading Railroad"))
            {
               if(p.getSpaceNum() > 5)
               {
                  fWriter2.write("On " + p.getName() + "'s trip to Reading Railroad, they pass GO and collect $200!! ^_^\n\n");
                  p.setBalance(p.getBalance() + 200);
               }

               p.setSpaceNum(5);
               try {
                  board[5].execute(p, fWriter2);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

            if(tempCard.getName().contains("Illinois Avenue"))
            {
               if(p.getSpaceNum() > 24)
               {
                  fWriter2.write("On " + p.getName() + "'s trip to Illinois, they pass GO and collect $200!! ^_^\n\n");
                  p.setBalance(p.getBalance() + 200);
               }

               p.setSpaceNum(24);
               try {
                  board[24].execute(p, fWriter2);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         }
         else if(tempCard.getEffectNum() == 4) //This is Moving Player to the nearest Railroad/Utility and executing a special command
         {
            BoardSpaces[] board = p.getBoard();

            if(tempCard.getName().contains("Railroad"))
            {
               p.setMultiplier((byte)2);

               if(p.getSpaceNum() >= 35 || p.getSpaceNum() < 5) //If the player is on or past Short Line Railroad
               {
                  fWriter2.write(p.getName() + " has been moved to Reading Railroad!\n");
                  if(p.getSpaceNum() >= 35 && p.getSpaceNum() <= 39)//This will determine if the player passes GO along the way
                  {
                     fWriter2.write("On " + p.getName() + "'s trip to Reading Railroad, they pass GO and collect $200!! ^_^\n");
                     p.setBalance(p.getBalance() + 200);
                  }
                  fWriter2.write("\n");
                  p.setSpaceNum(5);
                  try {
                     board[5].execute(p, fWriter2);
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               }
               else if(p.getSpaceNum() >= 5 && p.getSpaceNum() < 15) //If the player is on Reading Railroad
               {
                  fWriter2.write(p.getName() + " has been moved to Pennsylvania Railroad!\n\n");
                  p.setSpaceNum(15);
                  try {
                     board[15].execute(p, fWriter2);
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               }
               else if(p.getSpaceNum() >= 15 && p.getSpaceNum() < 25) //If the player is on Pennsylvania Railroad
               {
                  fWriter2.write(p.getName() + " has been moved to B&o Railroad!\n\n");
                  p.setSpaceNum(25);
                  try {
                     board[25].execute(p, fWriter2);
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               }
               else //If the player is on B&O Railroad
               {
                  fWriter2.write(p.getName() + " has been moved to Short Line Railroad!\n\n");
                  p.setSpaceNum(35);
                  try {
                     board[35].execute(p, fWriter2);
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               }

            }
            else //A Utility Chance Card has been drawn
            {
               p.setMultiplier((byte)10);
               int diceOne = (int)(Math.random() * 6 + 1);
               int diceTwo = (int)(Math.random() * 6 + 1);
               int sumDiceRoll = diceOne + diceTwo;
               p.setDiceRoll((byte)sumDiceRoll);

               if(p.getSpaceNum() >= 28 || p.getSpaceNum() < 12)
               {
                  fWriter2.write(p.getName() + " has been moved to Electric Company!\n");
                  if(p.getSpaceNum() <= 39)
                  {
                     fWriter2.write("On " + p.getName() + "'s trip to Electric Company, they pass GO and collect $200!! ^_^\n");
                     p.setBalance(p.getBalance() + 200);
                  }
                  fWriter2.write(p.getName() + " has rolled a total of " + sumDiceRoll + "!\n");
                  fWriter2.write("\n");
                  p.setSpaceNum(12);
                  try {
                     board[12].execute(p, fWriter2);
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               }
               else
               {
                  fWriter2.write(p.getName() + " has been moved to Water Works!\n");
                  fWriter2.write(p.getName() + " has rolled a total of " + sumDiceRoll + "!\n\n");
                  p.setSpaceNum(28);
                  try {
                     board[28].execute(p, fWriter2);
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               }

            }
            p.setMultiplier((byte)1);
         }
         else if(tempCard.getEffectNum() == 5) //This is the PLAYER KEEP Effect
         {
            fWriter2.write(p.getName() + " has received a Chance Jail Free Card! :DDDD\n\n");
            p.assignJailFreeChance(tempCard);
            tempCard.setOwner(p);
         }
      }

      //If this Card is DOESN'T have a PLAYER KEEP Effect (like a Jail Free Card), then put it back onto the stack
      if(tempCard.getEffectNum() != 5)
      {
         temp.addChanceCardToStack(tempCard);
      }
      
   }

   /**
    * Executes the Community Chest card that has been drawn from the stack (or Queue) of Chance Cards as used in the method
    * ActionSpaces.execute(). Executes by first looking for the Effect# of the card drawn, and then determining keywords
    * to execute the right instruction given to by the card.
    * @param p The player that this Community Chest Card will have effect on
    * @param temp The ActionCard that has the Community Chest Card drawn
    */
   public void executeCommChest(Player p, ActionCards temp) throws IOException
   {
      CommChest tempCard = temp.getCommChest();
      fWriter2.write("Community Chest: " + tempCard.getName() + "\n");

      if(tempCard.getEffectNum() != 0)
      {
         // This is the GAIN MONEY Effect
         if(tempCard.getEffectNum() == 1)
         {
            if(tempCard.getName().contains("birthday"))
            {
               Player[] opponents = p.getOpponentsArray();
               
               for(Player each : opponents)
               {
                  if(!each.equals(p))
                  {
                     each.setBalance(each.getBalance() - 10);
                  }
               }
               p.setBalance(p.getBalance() + tempCard.getAmount() * (opponents.length-1));
               fWriter2.write(p.getName() + " has earned a total of $" + (tempCard.getAmount() * (opponents.length-1)) + " as birthday gift from "+ opponents.length + " players!\n");
            }
            else
            {
               fWriter2.write(p.getName() + " earned $" + tempCard.getAmount() + " from Community Chest card!\n");
               p.setBalance(p.getBalance() + tempCard.getAmount());
            }
         }
         else if(tempCard.getEffectNum() == 2) // This is the LOSE MONEY Effect
         {
            fWriter2.write(p.getName() + " lost $" + tempCard.getAmount() + " in Community Chest!\n");
            p.setBalance(p.getBalance() - tempCard.getAmount());
         }
         else if(tempCard.getEffectNum() == 3) //This is the MOVE Player Effect
         {
            if(tempCard.getName().contains("Advance to GO!"))
            {
               fWriter2.write(p.getName() + " advanced to GO and collects $200! :D\n");
               p.setSpaceNum(0);
               p.setBalance(p.getBalance() + 200);
            }

            if(tempCard.getName().contains("JAIL"))
            {
               fWriter2.write(p.getName() + " is sent directly to Jail through Community Chest Card! D:<\n");
               p.setSpaceNum(10);
               p.setJailStatus(true);
            }
         }
         else if(tempCard.getEffectNum() == 4) //This is the PLAYER KEEP Effect
         {
            fWriter2.write(p.getName() + " has received a Community Chest Jail Free Card! :DDDD\n\n");
            p.assignJailFreeCommChest(tempCard);
            tempCard.setOwner(p);
         }
      }

      if(tempCard.getEffectNum() != 4)
      {
      //Make sure to put the CommChest Cards back into the Queue
         temp.addCommChestCardToStack(tempCard);
      }
   }
}