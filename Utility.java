public class Utility extends Property
{
   /**
    * Constructs a Utility Property (Property Card)
    * @param n The name of this property
    * @param o The owner of this property (none)
    * @param c The cost to afford this property (when owned by nobody)
    * @param r The cost of the rent of this property (when owned)
    */
   public Utility(String n, Player o, short c, short r)
   {
      super(n, o, c, r);
   }
   
   /**
    * Assigns this Railroad to the player that bought it (owner)
    * And adds the number of Utilities this player has in possession to determine the rent of
    * the utilities when another player lands on this Utility
    * @param p The player that this property will be assigned to
    */
   public void assignOwner(Player p)
   {
      super.assignOwner(p);
      p.setNumUtil((byte)(p.getNumUtil() + 1));
   }
   
   /**
    * Calculates the amount of rent the player landed on this Utility has to pay to this owner
    * based on the amount of Utilities this owner has as well as the total amount of what they rolled
    * in the Dice.
    * @return the rent this player has to pay
    */
   public short rent(Player p, short multiplier)
   {
      if(multiplier == 1) {
         if (getOwner().getNumUtil() == 1)
            return (short) (p.getDiceRoll() * 4);
         return (short) (p.getDiceRoll() * 10);
      }
      else
         return (short) (p.getDiceRoll() * multiplier);
   }
}