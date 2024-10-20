public class Chance
{
   private String name;
   /**
    * Effects ID#
    * 0 --> NO EFFECT
    * 1 --> GAIN MONEY
    * 2 --> LOSE MONEY
    * 3 --> MOVE PLAYER (PERFORM .EXECUTE)
    * 4 --> MOVE PLAYER (PERFORM .EXECUTE WITH SPECIAL COMMAND)
    * 5 --> PLAYER KEEP
    */
   private final byte effect;
   private int amount;
   private Player owner;

   /**
    * Builds a Chance Card without an amount to lose or gain from
    * @param n The description of this Chance Card
    * @param e The effect# of this Chance Card
    */
   public Chance(String n, byte e)
   {
      name = n;
      effect = e;
      amount = 0;
      owner = null;
   }

   /**
    * Builds a Chance Card with an amount to lose or gain from
    * @param n The description of this Chance Card
    * @param e The effect# of this Chance Card
    * @param a The amount that a player will lose or gain from
    */
   public Chance(String n, byte e, int a)
   {
      name = n;
      effect = e;
      amount = a;
      owner = null;
   }

   /**
    * @return the description of this Chance Card
    */
   public String getName()
   {
      return name;
   }

   /**
    * @return the effect# of this Chance Card
    */
   public byte getEffectNum()
   {
      return effect;
   }

   /**
    * @return The amount of money that a player will gain or lose from this Chance Card
    */
   public int getAmount()
   {
      return amount;
   }

   /**
    * @return the owner of this Chance Card (Mainly used for the PLAYER KEEP Effect)
    */
   public Player getOwner()
   {
      return owner;
   }

   /**
    * @param p The player that will own this Chance Card
    */
   public void setOwner(Player p)
   {
      owner = p;
   }
   
}