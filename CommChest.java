public class CommChest
{
   private String name;
   /**
   *  effects ID#:
   *  0 --> NO EFFECT
   *  1 --> GAIN MONEY
   *  2 --> LOSE MONEY
   *  3 --> MOVE PLAYER EFFECT
   *  4 --> PLAYER KEEP
   */
   private final byte effect;
   private int amount;
   private Player owner;

   /**
    * Builds a Community Chest Card without an amount to lose or gain from
    * @param n The description of this Community Chest Card
    * @param e The effect# of this Community Chest Card
    */
   public CommChest(String n, byte e)
   {
      name = n;
      effect = e;
      amount = 0;
      owner = null;
   }

   /**
    * Builds a Community Chest Card with an amount to lose or gain from
    * @param n The description of this Community Chest Card
    * @param e The effect# of this Community Chest Card
    * @param a The amount that a player will lose or gain from
    */
   public CommChest(String n, byte e, int a)
   {
      name = n;
      effect = e;
      amount = a;
      owner = null;
   }

   /**
    * @return the description of this Community Chest Card
    */
   public String getName()
   {
      return name;
   }

   /**
    * @return the effect# of this Community Chest Card
    */
   public byte getEffectNum()
   {
      return effect;
   }

   /**
    * @return The amount of money that a player will gain or lose from this Community Chest Card
    */
   public int getAmount()
   {
      return amount;
   }

   /**
    * @return the owner of this Community Card (Mainly used for the PLAYER KEEP Effect)
    */
   public Player getOwner()
   {
      return owner;
   }

   /**
    * @param p The player that will own this Community Chest Card
    */
   public void setOwner(Player p)
   {
      owner = p;
   }
}