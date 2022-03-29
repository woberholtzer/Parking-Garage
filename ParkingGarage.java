import java.util.*;
import java.io.*;

public class ParkingGarage {

  private int maxFloors;
  private int maxSpotsPerFloor;
  private ArrayList<ParkingSpot> vacant;
  private ArrayList<Customer> guestList;
  private ArrayList<Customer> repeatCustomers;
  private Kiosk k;
  public String filePath = "lib/Example.txt";
  public ArrayList<String> CSV = new ArrayList<String>();

  public ParkingGarage(int maxFloors, int maxSpotsPerFloor) {
    this.maxFloors = maxFloors;
    this.maxSpotsPerFloor = maxSpotsPerFloor;
    guestList = new ArrayList<Customer>();
    repeatCustomers = new ArrayList<Customer>();
    vacant = new ArrayList<ParkingSpot>();
    for (int i = 0; i < maxFloors; i++) { // floor number
      for (int j = 0; j < maxSpotsPerFloor; j++) { // spot number on that specific floor
        ParkingSpot ps = new ParkingSpot(j, i);
        if (j < 2) {
          ps.setHandicap(true);
        }
        vacant.add(ps);
        guestList.add(null);
      }
    }
    CSV = readCSV(filePath);
    
    if (CSV.size() > 0) {
      for (int i = 126; i < CSV.size(); i++) {
        String line = CSV.get(i);
        Customer c = new Customer();
        String[] tokens = characterSeperate(line);
        if (!tokens[0].equals("")) {
            c.setLicensePlate(tokens[0]);
            double balance = (Double.parseDouble(tokens[1].substring(1)));
            c.setBalance(balance);
            if (!repeatCustomers.contains(c)) {
              repeatCustomers.add(c);
            } else {
              int j = 0;
              while (!c.equals(repeatCustomers.get(j))) {
                j++;
              }
              System.out.println("Grabbing customer");
              c = repeatCustomers.get(j);
              System.out.println(c.getBalance());
              repeatCustomers.set(j, c);
            }
        }
      }
    }

    for (int k = 0; k < CSV.size() - repeatCustomers.size()-1; k = k + 5) {
      String line = CSV.get(k);
      if (!"Customer: N/A".equals(line)) {
        Customer c = new Customer();
        line = line.substring(11, line.length() - 1);
        String[] tokens = characterSeperate(line);
        c.setLicensePlate(tokens[0]);
        double balance = Double.parseDouble(tokens[3].substring(10));
        if (tokens[1].equals(" Repeat Customer: false")) {
          c.setRepeatCustomerStatus(2);
        } else {
          c.setRepeatCustomerStatus(1);
          if (!repeatCustomers.contains(c)) {
            repeatCustomers.add(c);
          } else {
            int i = 0;
            while (!c.equals(repeatCustomers.get(i))) {
              i++;
            }
            c.setBalance(balance);
            repeatCustomers.set(i, c);
          }
        }
        if (tokens[2].equals(" Handicap Eligible: true")) {
          c.setHandicapEligibilityStatus(1);
        } else {
          c.setHandicapEligibilityStatus(2);
        }
        c.setBalance(balance);
        c.setParkingSpot(vacant.get((k) / 5));
        guestList.set(((k) / 5), c);
      }

    }

    k = new Kiosk(7, 2100); // 7 = day fee, 2100 = seasonPass fee
  }

  public static ArrayList<String> readCSV(String filePath) {

    ArrayList<String> lines = new ArrayList<String>();

    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line;
      while ((line = br.readLine()) != null) {
        if (line.strip().length() > 0) {
          lines.add(line);
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading CSV file!");
      e.printStackTrace(System.err);
    }

    return lines;
  }

  public static String[] characterSeperate(String line) {
    String[] tokens = line.split(",");
    return tokens;
  }

  public boolean getIsFull() {
    boolean toReturn = true;
    int i = 0;
    while(toReturn && i < guestList.size()) {
      if (guestList.get(i) == null) {
        toReturn = false; // this is saying that if there isn't any Customer in that idency then return
        // that it is not full
      } else {
        i++;
        toReturn = true;
      }
    }
    return toReturn;
  }

  public Kiosk getKiosk() {
    return k;
  }

  public ArrayList getGuestList() {
    return guestList;
  }

  public String toString() {
    String toReturn = "";
    for (int i = 0; i < guestList.size(); i++) {
      if (guestList.get(i) == null) {
        toReturn = toReturn + "Customer: N/A" + "\n" + vacant.get(i).toString() + "\n";
      } else {
        toReturn = toReturn + "Customer: " + guestList.get(i).toString() + "\n";
      }
    }
    toReturn = toReturn + "Repeat Customers: " + "\n";
    if (repeatCustomers.size() > 0) {
      for (int k = 0; k < repeatCustomers.size(); k++) {
        toReturn = toReturn + repeatCustomers.get(k).getLicensePlate() + ", " + repeatCustomers.get(k).getBalance() + "\n";
      }
    }
    return toReturn;
  }

  public boolean removeCustomer(Customer c) {
    boolean toReturn = false;
    if (guestList.contains(c)) {
      System.out.println("User already in ParkingGarage");

      System.out.println("Do you wish to leave? Press 1 for exit, Press 2 to update, Press 3 to start over.");
      Scanner exit = new Scanner(System.in);
      String choice = exit.nextLine();
      
      while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
        System.out.println("invalid input. Please try again.");
        choice = exit.nextLine();
        } 
      if (choice.equals("1")) {
        // follow steps to take license and info out of array
        for (int i = 0; i < guestList.size(); i++) {
          if (c.equals(guestList.get(i))) {
            guestList.set(i, null);
          }
        }
        toReturn = true;
        System.out.println("You have left the parking garage, have a nice day!");
      } else if (choice.equals("2")) {
        toReturn = updateCustomer(c);
      } else if (choice.equals("3")) {
        toReturn = start();
      }
    }
    return toReturn;
  }

  public boolean addNew(Customer c) {
    boolean toReturn = false;
    
    Scanner exit = new Scanner(System.in);
    System.out.println("Is your license plate correct? " + c.getLicensePlate() + " Enter '1' for yes and '0' for no");
      String correct = exit.nextLine();
      while(!correct.equals("1") && !correct.equals("0")){
        System.out.println("Invalid input. Please try again");
        correct = exit.nextLine();
        }
      if(correct.equals("0")){
        do {
          System.out.println("Re-enter your license plate");
          String newLicensePlate = exit.nextLine();
          newLicensePlate = newLicensePlate.toUpperCase();
          c.setLicensePlate(newLicensePlate);
          System.out.println("Is your license plate correct? " + c.getLicensePlate() + " Enter '1' for yes and '0' for no");
          correct = exit.nextLine();
        } while(!correct.equals("1"));
      }
      
      
      
    System.out.println("Do you want to select your own spot? Press 1 for yes, 2 for no");
    String answer = exit.nextLine();
      while (!answer.equals("1") && !answer.equals("2")) {
        System.out.println("invalid input. Please try again.");
        answer = exit.nextLine();
        } 
        /*
      while (answer != 1 && answer != 2) {
        System.out.println("Invalid input. Please try again.");
        answer = exit.nextInt();
      }
      */
    if (guestList.contains(c)) {
      toReturn = removeCustomer(c);
    } else if(answer.equals("2")){
      if (repeatCustomers.contains(c)){
        int i = 0;
        while (!c.equals(repeatCustomers.get(i))) {
          i++;
        }
        System.out.println("Welcome back " + c.getLicensePlate());
        c = repeatCustomers.get(i);
        c.setRepeatCustomerStatus(1);
        // do stuff
        // check balance
        while (c.getBalance() < k.getFee()) {
          System.out.println("You need to add more money in your account");
          String response = exit.nextLine();
          int balance = Integer.parseInt(response);
          c.addToBalance(balance);
        }
        // if enough money, assign first available spot, depending on handicap
        // if reserved, assign that certain spot
      }
      c = k.build(c); // if it is not in the arrayList, continue to build the customer

      if (c.getHandicapEligiblityStatus()) { // if the customer needs a handicap spot
        int i = 0;
        while (guestList.get(i) != null || !vacant.get(i).getHandicap() && i < guestList.size()) { // while there is someone in a certain spot, the spot is not a handicap spot, and you don't reach the end of the arrayList, keep searching through the list until you see an eligible spot
          toReturn = false;
          i++;
          if(i == guestList.size()){
            break;
          }
        }
        if (i >= guestList.size()) {
          System.out.print("Sorry there are no available spots for you\n");
          toReturn = false;
        } else {
          c.setParkingSpot(vacant.get(i)); // set the ParkingSpot to the customer
          // vacant.remove(i); // remove that element from the vacant arrayList
          guestList.set(i, c); // change the element of the guestList from null to that specific customer
          if (c.getRepeatCustomerStatus()) {
            if (!repeatCustomers.contains(c)) {
              repeatCustomers.add(c);
            }
          }
          toReturn = true;
        }
      } else { // we know here that the customer does not need a handicap spot
        int i = 0;
        while (guestList.get(i) != null || vacant.get(i).getHandicap() && i < guestList.size()) { /* while there is someone in a certain spot, the spot is a handicap spot, and you don't reach the end of the list, keep searching until you see an eligible spot*/
          toReturn = false;
          i++;
          if(i == guestList.size()){
            break;
          }
        }
        if (i >= guestList.size()) {
          System.out.println("Sorry there are no available spots for you");
          toReturn = false;
        } else {
          c.setParkingSpot(vacant.get(i)); // set the ParkingSpot to the customer
          guestList.set(i, c); // change the element of the guestList from null to that specific customer
          if (c.getRepeatCustomerStatus()) {
            if (!repeatCustomers.contains(c)) {
              repeatCustomers.add(c);
            }
          }
          toReturn = true;
        }
      }
    } else {
      boolean valid = false;
      int floorNum = 0;
      int spotOnFloor = 0;
      c = k.build(c);
      do{
        Boolean isHandicap = c.getHandicapEligiblityStatus();
        System.out.println(display(isHandicap));
        System.out.println("Choose your floor");
        Scanner input = new Scanner(System.in);
        do{
          try{
            String floorNumString = input.nextLine();
            floorNum = Integer.parseInt(floorNumString);
            if(floorNum < 0 || floorNum > maxFloors-1){
              System.out.println("Invalid selection. Please try again");
              valid = false;
            } else {
              valid = true;
            }
          } catch (NumberFormatException e){
            System.out.println("Please enter a valid integer value for your floor");
            valid = false;
          }
        } while (!valid);
        System.out.println("Choose the spot on floor " + floorNum);
        do{
          try {
            String spotOnFloorString = input.nextLine();
            spotOnFloor = Integer.parseInt(spotOnFloorString);
            if(spotOnFloor < 0 || spotOnFloor > maxSpotsPerFloor-1){
              System.out.println("Invalid selection. Please try again");
              valid = false;
            } else {
              valid = true;
            }
          } catch (NumberFormatException e){
            System.out.println("Please enter a valid integer value for your spot on the floor");
            valid = false;
          }
        } while(!valid);
        
        if(guestList.get(floorNum*maxFloors+spotOnFloor)!=null){
          System.out.println("Sorry that spot is already taken, please try again.");
          toReturn = false;
        }
        if(c.getHandicapEligiblityStatus()){
          if(!vacant.get(floorNum*maxFloors+spotOnFloor).getHandicap()){
            System.out.println("Sorry this is a non-handicap spot and you registered for a handicap spot. Please try again");
            toReturn = false;
          } else {
            toReturn = true;
          }
        } else{
          if(vacant.get(floorNum*maxFloors+spotOnFloor).getHandicap()){
            System.out.println("Sorry this is a handicap spot and you registered for a non-handicap spot. Please try again");
            toReturn = false;
          } else {
            toReturn = true;
          }
        }
      } while (!toReturn);
      int i = floorNum*maxFloors+spotOnFloor;
      ParkingSpot spot = vacant.get(i);
      spot.setReserved(true);
      c.setParkingSpot(spot);
      guestList.set(i,c);
      toReturn = true;
    }
    if(toReturn){
      c = k.payment(c);
    }
    return toReturn;
  }

  public void spaceCount() {
    int spacesFull = 0;
    int spacesEmpty = 0;
    for (int i = 0; i < guestList.size(); i++) {
      if (guestList.get(i) != null) { // if there is something in the indencies of the ArrayList
        spacesFull++; // count the number of full spaces by 1
      } else { // if there is nothing in the indecies of the arrayList
        spacesEmpty++; // count the number of empty spaces by 1
      }
    }
    System.out.println("Amount of spaces full: " + spacesFull);
    System.out.println("Amount of spaces empty: " + spacesEmpty);
  }

  public boolean updateCustomer(Customer c) {
    boolean toReturn = false;
    if (guestList.contains(c)) {
      k.update(c);
      int i = 0;
      while (!c.equals(guestList.get(i))) {
        i++;
      }
      System.out.println("Your information has been updated!");
      guestList.set(i, c);
      if (repeatCustomers.contains(c)) {
        int j = 0;
        while (!c.equals(guestList.get(i))) {
          j++;
        }
        repeatCustomers.set(j, c);
        if(!c.getRepeatCustomerStatus()){
          System.out.println("Removing your repeat status");
          repeatCustomers.remove(c);
        }
      }
      toReturn = true;
    } else {
          System.out.println("Guest is not in the parkingGarage, would you like to check in? Press 1 to start check in, or Press 2 to exit the program.");
      Scanner input = new Scanner(System.in);
      int answer = input.nextInt();
      while (answer != 1 && answer != 2) {
        System.out.println("Invalid input. Please try again.");
        answer = input.nextInt();
      }
      if (answer == 1) {
        toReturn = start();
      } else if (answer == 2) {
        toReturn = false;
      }
    }
    return toReturn;
  }

  public boolean start() {
    boolean toReturn = false;
     Customer c = k.start();
    if (!guestList.contains(c)) {
      if(getIsFull()){
      System.out.println("Sorry the garage is full for today. Please come back again!");
      toReturn = false;
      } else {
        toReturn = addNew(c);
      }
      
    } else {
      int i = 0;
      while (!c.equals(guestList.get(i))) {
        i++;
      }
      c = guestList.get(i);
      System.out.println("Welcome User " + c.getLicensePlate()
          + " what would you like to do today? Press 1 to update, Press 2 to Check Out");
      Scanner input = new Scanner(System.in);
      String answerString = input.nextLine();
      while(!answerString.equals("1") && !answerString.equals("2")){
        System.out.println("Invalid input. Please press 1 to update or 2 to Check Out");
        answerString = input.nextLine();
      }
      int answer = Integer.parseInt(answerString);
      if (answer == 1) {
        toReturn = updateCustomer(c);
      } else if (answer == 2) {
        toReturn = removeCustomer(c);
      }
    } 
    
    
    return toReturn;
  }
  public String display(Boolean isHandicap) {
    spaceCount();
    String toReturn = "X Represents Spots that are Taken\nO Represents Spots that are Available\n";
    if(isHandicap != null){
      toReturn = toReturn + "- Represents Spots that are not available for Customer Handicap Status\n";
    }
    toReturn = toReturn + "Spot:     0    1    2    3    4\n";
    if(isHandicap == null){
      for (int i = 0; i < maxFloors; i++) {
        if(i==0){
          toReturn = toReturn + "Floor G:";
        } else {
          toReturn = toReturn + "Floor " + i + ":";
        }
        for (int j = 0; j < maxSpotsPerFloor; j++) {
          if (guestList.get((i * maxFloors) + j) != null) {
            toReturn = toReturn + " |X| ";
          } else {
            toReturn = toReturn + " |O| ";
          }
        }
        toReturn = toReturn + "\n";
      }
    } else if(!isHandicap){
        for (int i = 0; i < maxFloors; i++) {
        if(i==0){
          toReturn = toReturn + "Floor G:";
        } else {
          toReturn = toReturn + "Floor " + i + ":";
        }
        for (int j = 0; j < maxSpotsPerFloor; j++) {
          if (vacant.get((i * maxFloors) + j).getHandicap()){
            toReturn = toReturn + " |-| ";
          }
          else if (guestList.get((i * maxFloors) + j) != null) {
            toReturn = toReturn + " |X| ";
          } else {
            toReturn = toReturn + " |O| ";
          }
        }
        toReturn = toReturn + "\n";
      }
    } else {
        for (int i = 0; i < maxFloors; i++) {
          if(i==0){
            toReturn = toReturn + "Floor G:";
          } else {
            toReturn = toReturn + "Floor " + i + ":";
          }
          for (int j = 0; j < maxSpotsPerFloor; j++) {
            if (!vacant.get((i * maxFloors) + j).getHandicap()){
              toReturn = toReturn + " |-| ";
            }
            else if (guestList.get((i * maxFloors) + j) != null) {
              toReturn = toReturn + " |X| ";
            } else {
              toReturn = toReturn + " |O| ";
            }
          }
        toReturn = toReturn + "\n";
      }
    }
    return toReturn;
  }
  public boolean clearGarage(){
    boolean toReturn = false;
    for(int i = 0; i < guestList.size(); i++){
      guestList.set(i,null);
    }
    toReturn = true;
    return toReturn;
  }
  public boolean fillGarage(){
    boolean toReturn = false;
    for(int i = 0; i < guestList.size(); i++){
      int j = i + 65;
      Customer fill = new Customer();
      char licPlateChar = (char) j;
      String licPlate = "" + licPlateChar;
      fill.setLicensePlate(licPlate);
      fill.setParkingSpot(vacant.get(i));
      if(vacant.get(i).getHandicap()){
        fill.setHandicapEligibilityStatus(1);
      }
      guestList.set(i,fill);
    }
    toReturn = true;
    return toReturn;
  }
}

