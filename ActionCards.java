import java.util.*;

public class ActionCards
{  
   String name;
   Chance chanceCard;
   CommChest commChestCard;
   private static Queue<Chance> chanceStack;
   private static Queue<CommChest> commChestStack;

   /**
    * Constructs ActionCards (without the name because that is never used)
    */
   public ActionCards()
   {
      name = "";
   }

   /**
    * Draws a Chance from the stack (Queue) to be put back into the stack (at the back/last of the list)
    * @param stack The current stack of Chance Cards that is currently being used
    * @return The Chance Card that has been drawn from the stack
    */
   public Chance drawChance(Queue<Chance> stack)
   {
      chanceCard = stack.remove();
      chanceStack = stack;
      return chanceCard;
   }

   /**
    * Draws a Community Chest from the stack (Queue) to be put back into the stack (at the back/last of the list)
    * @param stack The current stack of Community Chest Cards that is currently being used
    * @return The Community Chest Card that has been drawn from the stack
    */
   public CommChest drawCommChest(Queue<CommChest> stack)
   {
      commChestCard = stack.remove();
      commChestStack = stack;
      return commChestCard;
   }

   /**
    * Adds this Chance Card back to the stack/Queue (to last like a stack of cards would)
    * @param c Chance Card to be added back into the stack
    */
   public void addChanceCardToStack(Chance c)
   {
      chanceStack.add(c);
   }

   /**
    * Adds this Community Chest Card back to the stack/Queue (to last like a stack of cards would)
    * @param cC Community Chest Card to be added back into the stack
    */
   public void addCommChestCardToStack(CommChest cC)
   {
      commChestStack.add(cC);
   }

   /**
    * Shuffles the pre-made list of Chance Cards by picking a random index from the list, takes that card FROM the list,
    * then adding that card into the REAL stack/Queue of cards to be used in the game and returns that Queue.
    * @param arChance The list of Chance Cards to be shuffled
    * @return The stack of shuffled Chance Cards in a Queue
    */
   public Queue<Chance> shuffleChanceCards(ArrayList<Chance> arChance)
   {
      Queue<Chance> tempStack = new LinkedList<Chance>();
      int size = arChance.size();
      
      for(int i = 0; i < size; i++)
      {
         int randoIndex = (int) (Math.random() * arChance.size());
         
         Chance temp = arChance.remove(randoIndex);
         
         tempStack.add(temp);
      }
      return tempStack;
      
   }

   /**
    * Shuffles the pre-made list of Community Chest Cards by picking a random index from the list, takes that card FROM the list,
    * then adding that card into the REAL stack/Queue of cards to be used in the game and returns that Queue.
    * @param arCommChest The list of Community Chest Cards to be shuffled
    * @return The stack of shuffled Community Chest Cards in a Queue
    */
   public Queue<CommChest> shuffleCommChestCards(ArrayList<CommChest> arCommChest)
   {
      Queue<CommChest> tempStack = new LinkedList<CommChest>();
      int size = arCommChest.size();
      
      for(int i = 0; i < size; i++)
      {
         int randoIndex = (int)(Math.random() * arCommChest.size());
         
         CommChest temp = arCommChest.remove(randoIndex);
         
         tempStack.add(temp);
      }
      
      return tempStack;
   }

   /**
    * @return the Chance card that has been drawn from the stack of Chance Cards
    */
   public Chance getChanceCard()
   {
      return chanceCard;
   }

   /**
    * @return the Community Chest Card that has been drawn from the stack of Community Chest Cards
    */
   public CommChest getCommChest()
   {
      return commChestCard;
   }
}