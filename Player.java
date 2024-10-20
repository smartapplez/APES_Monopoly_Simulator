public class Player
{
   private String name; //The name of the player
   private int space; //Indicates where they are in the board (by space#)
   private int balance; //Indicates how much money they have
   private boolean inJail; //Indicates whether or not they are in jail
   private boolean bankrupt; //Indicates whether or not their balance reaches below zero
   private byte numRR; //Indicates how many railroads they currently have. This will help determine the rent of the RR
   private byte numUtil; ////Indicates how many railroads they currently have. This will help determine the rent of the utility.
   private byte diceRoll; //Shows the total value rolled on the dice. This will help determine the rent of the utility.
   private byte multiplier; //Indicates how much the rent is multiplied towards RR and utility if they ever land on Chance.
   private static Player[] opponents; //Shows the list of players in the game, this will become important when the player shares the money to everyone else and vice versa
   private static BoardSpaces[] board; //Shows the entire board, this will become important when some Action card moves a player to a particular place.
   private static CommChest jailFreeCardComm; //Points to the Get out of Jail Free Community Chest card if the player has any
   private static Chance jailFreeCardChance; //Points to the Get out of Jail Free Chance card if the player has any
   
   public Player(String n, int s, int b, boolean j, boolean brpt, Player[] o, BoardSpaces[] bS)
   {
      name = n;
      space = s;
      balance = b;
      inJail = j;
      bankrupt = brpt;
      opponents = o;
      board = bS;
      numRR = 0;
      numUtil = 0;
      diceRoll = 0;
      jailFreeCardComm = null;
      jailFreeCardChance = null;
      multiplier = 1;
   }
   
   /**
    * Sets the balance of the player
    */
   public void setBalance(int b)
   {
      balance = b;
   }
   
   /**
    * Moves a player to a particular place on the board
    */
   public void setSpaceNum(int s)
   {
      space = s;
   }
   
   /**
    * Sets whether they are going to jail or no longer being in jail
    */
   public void setJailStatus(boolean j)
   {
      inJail = j;
   }
   
   /**
    * Sets the number of RailRoads they currently have
    */
   public void setNumRR(byte rr)
   {
      numRR = rr;
   }
   
   /**
      Sets the number of Utilities they currently have
   */
   public void setNumUtil(byte u)
   {
      numUtil = u;
   }
   
   /**
      Sets the total value of the player rolled on the dice.
   */
   public void setDiceRoll(byte d)
   {
      diceRoll = d;
   }
   
   /**
    * Sets the rent multiplier for this player for Railroads and Utilities
    * Mainly used in Chance cards
    */
   public void setMultiplier(byte m)
   {
      multiplier = m;
   }
   
   /**
    * Passes the address of the list of players so this object can have access to it
    * in case game-wide money distribution/donation is necessary in ActionCards
    */
   public void setOpponents(Player[] o)
   {
      opponents = o;
   }
   
   /**
    * Returns the list of players in the game
    */
   public Player[] getOpponentsArray()
   {
      return opponents;
   }
   
   /**
    * Returns the name of this player
    */
   public String getName()
   {
      return name;
   }
   
   /**
    * Returns the Space# this player is currently on in the board
    */
   public int getSpaceNum()
   {
      return space;
   }
   
   /**
   *  This returns the current balance of this player
   */
   public int getBalance()
   {
      return balance;
   }
   
   /**
   *  This returns whether or not this player is in Jail
   */
   public boolean inJail()
   {
      return inJail;
   }
   
   /**
   * This returns whether or not this player is bankrupt
   */
   public boolean isBankrupt()
   {
      return bankrupt;
   }
   
   /**
   *  Returns the number of RR this player currently has
   *
   *  This will be important as this will calculate the rent of
   *  the RR when a player lands on their property
   */
   public byte getNumRR()
   {
      return numRR;
   }
   
   /**
    * Returns the number of Utilities this player currently has
    * This will be important as this will calculate the rent of
    * the RR when a player lands on their property
    */
   public byte getNumUtil()
   {
      return numUtil;
   }
   
   /**
    * Returns the sum of the dice the player rolled
    * This will be useful when calculating the price for the rent
    * of a utility
    */
   public byte getDiceRoll()
   {
      return diceRoll;
   }
   
   /**
    * Returns the multiplier this player will owe in rent
    * to a specific property in a RR or Utility
    * This method is specifically used when a specific Chance card
    * is pulled from the stack
    */
   public byte getMultiplier()
   {
      return multiplier;
   }
   
   /**
    * Returns the board this player is on.
    * This will be useful when moving this player to a particular place in the board
    * from Chance cards, to Go To Jail spaces
    */
   public BoardSpaces[] getBoard()
   {
      return board;
   }
   
   /**
    * Gives a Jail Free Community Chest card to the player, which they will have possession
    * of their address in case they need to use it.
    * In more technical terms: they will have the address of the card in case
    * it will need to be referred upon whenever this player is in Jail.
    */
   public void assignJailFreeCommChest(CommChest cC)
   {
      jailFreeCardComm = cC;
   }
   
   /**
    * Gives a Jail Free Chance card to the player, which they will have possession
    * of their address in case they need to use it.
    * In more technical terms: they will have the address of the card in case
    * it will need to be referred upon whenever this player is in Jail.
    */
   public void assignJailFreeChance(Chance c)
   {
      jailFreeCardChance = c;
   }
   
   /**
    * Checks if this player is in possession of any jail free cards
    * In more technical terms: it checks if this player has an address of at least
    * one type of action card that says Out of Jail.
    */
   public boolean hasJailFreeCard()
   {
      return jailFreeCardComm != null || jailFreeCardChance != null;
   }
   
   /**
    * Uses any Jail Free card the player has in possession
    */
   public void useJailFreeCard()
   {
      ActionCards temp = new ActionCards();
      
      if(jailFreeCardComm != null)
      {
         jailFreeCardComm.setOwner(null);
         temp.addCommChestCardToStack(jailFreeCardComm);
         jailFreeCardComm = null;
      }
      
      if(jailFreeCardChance != null)
      {
         jailFreeCardChance.setOwner(null);
         temp.addChanceCardToStack(jailFreeCardChance);
         jailFreeCardChance = null;

      }
      
   }
}