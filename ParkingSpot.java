public class ParkingSpot{
      private int spotNumber;
      private int floorLevel;
      private boolean handicap;
      private boolean reserved;
      public ParkingSpot(int spotNumber, int floorLevel){
        this.spotNumber = spotNumber;
        this.floorLevel = floorLevel;
        handicap = false;
        reserved = false;
      }
      public ParkingSpot(){
      }
      public int getSpotNumber(){
        return spotNumber;
      }
      public int getFloorLevel(){
        return floorLevel;
      }
      public void setSpotNumber(int spotNumber){
        this.spotNumber = spotNumber;
      }
      public void setFloorLevel(int floorLevel){
        this.floorLevel = floorLevel;
      }
      public boolean getHandicap(){
        return handicap;
      }
      public void setHandicap(boolean handicap){
        this.handicap = handicap;
      }
      public boolean getReserved(){
        return reserved;
      }
      public void setReserved(boolean reserved){
        this.reserved = reserved;
      }
      public String toString(){
        if(floorLevel<1){
          return "Parking Floor: G" + "\n" + "Parking Spot: " + (spotNumber+1) + "\n" + "Handicap: " + handicap + "\n" + "User Chosen: " + reserved + "\n";
        }
        return "Parking Floor: " + floorLevel + "\n" + "Parking Spot: " + (spotNumber+1) + "\n" + "Handicap: " + handicap + "\n" + "User Chosen: " + reserved + "\n";
      }
}