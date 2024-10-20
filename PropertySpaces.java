import java.io.BufferedWriter;
import java.io.IOException;

public class PropertySpaces implements BoardSpaces
{
   private final Property prop;

   /**
    * Builds this Property Space that will take in
    * @param p The Property that this space will take in
    */
   public PropertySpaces(Property p)
   {
      prop = p;
   }

   /**
    * Executes the Property space the player lands on
    * @param p The player that has landed on this space
    * @param fWriter2 What will be written in the first file
    */
   public void execute(Player p, BufferedWriter fWriter2) throws IOException
   {
           fWriter2.write(p.getName() + " landed on " + prop.getName() + "\n");

           //Checks if this property is owned by another player
           if(prop.getOwner() != null)
           {
                if(prop.getOwner() != p)
                {
                    Player owner = prop.getOwner();
                    short tempMoney;

                //Checks if this player landed on a Railroad or a Utility to determine the rent this player has to pay
                if(!(prop.getName().contains("Railroad") || (prop.getCost() == 150)))
                {
                        tempMoney = prop.rent();
                }
                else if(prop.getName().contains("Railroad"))
                {
                        Railroad temp = new Railroad(prop.getName(), owner, (short)200, (short)25);
                        tempMoney = temp.rent(p.getMultiplier());
                }
                else
                {
                        Utility temp = new Utility(prop.getName(), owner, (short)150, (short)0);
                        tempMoney = temp.rent(p, p.getMultiplier());
                }

                //The "paying the rent" process to the other player
                fWriter2.write(p.getName() + " payed $" + tempMoney + " to " + owner.getName() + " in rent!\n");
                p.setBalance(p.getBalance() - tempMoney);

                owner.setBalance(owner.getBalance() + tempMoney);
           }
           else
                fWriter2.write(p.getName() + " already owns this property lol!\n");
           }
           else //Buys the Property this player landed on if they have enough money to afford it
           {
                if(p.getBalance() >= prop.getCost())
                {
                        short tempMoney;

                        tempMoney = prop.getCost();
                        fWriter2.write(p.getName() + " has bought " + prop.getName() + " for $" + tempMoney + "!\n");
                        p.setBalance(p.getBalance() - tempMoney);
                        prop.assignOwner(p);
                }
                else
                 {
                        fWriter2.write(p.getName() + " does not have enough money to afford " + prop.getName() + "... :(\n");
                 }
           }
   }
}