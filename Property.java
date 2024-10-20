public class Property
{
   private final String name;
   private Player owner;
   private short cost;
   private short rent;

   /**
    * Constructs a Property (Card)
    * @param n The name of this property
    * @param o The owner of this property (none)
    * @param c The cost to afford this property (when owned by nobody)
    * @param r The cost of the rent of this property (when owned)
    */
   public Property(String n, Player o, short c, short r)
   {
      name = n;
      owner = o;
      cost = c;
      rent = r;
   }
   
   /**
    * @return The name of this property
    */
   public String getName()
   {
      return name;
   }
   
   /**
    * Assigns this property to the player that bought it
    * @param p The player that this property will be assigned to
    */
   public void assignOwner(Player p)
   {
      owner = p;
   }
   
   /**
    * @return The player who owns this property
    */
   public Player getOwner()
   {
      return owner;
   }
   
   /**
    * @return The cost to buy this owner-less property
    */
   public short getCost()
   {
      return cost;
   }
   
   /**
    * @return the rent the player has to pay to the Owner
    */
   public short rent()
   {
      return rent;
   }
}