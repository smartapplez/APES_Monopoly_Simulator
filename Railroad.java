public class Railroad extends Property
{
   /**
    * Constructs a Railroad Property (Property Card)
    * @param n The name of this property
    * @param o The owner of this property (none)
    * @param c The cost to afford this property (when owned by nobody)
    * @param r The cost of the rent of this property (when owned)
    */
   public Railroad(String n, Player o, short c, short r)
   {
      super(n, o, c, r);
   }
   
   /**
    * Assigns this Railroad to the player that bought it (owner)
    * And adds the number of Railroads this player has in possession to determine the rent of
    * the railroad when another player lands on this RailRoad
    * @param p The player that this property will be assigned to
    */
   public void assignOwner(Player p)
   {
      super.assignOwner(p);
      p.setNumRR((byte)(p.getNumRR() + 1));
   }
   
   /**
    * Calculates the amount of rent the player landed on this RailRoad has to pay to this owner
    * based on the amount of RailRoads this owner has
    * @param multiplier The number which will multiply this rent by
    * @return The rent this player has to pay
    */
   public short rent(short multiplier)
   {
      if(getOwner().getNumRR() == 1)
         return (short)(super.rent() * multiplier);
      if(getOwner().getNumRR() == 2)
         return (short)(super.rent() * 2 * multiplier);
      if(getOwner().getNumRR() == 3)
         return (short)(super.rent() * 4 * multiplier);
      return (short)(super.rent() * 8 * multiplier);
   }
}