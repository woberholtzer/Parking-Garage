import java.util.Scanner;

public class Kiosk {
  private int dayFee;
  private boolean paidFee;
  private boolean paidPass;
  private Customer tempCustomer;

  public Kiosk(int dayFee, int seasonPass) {
    this.dayFee = dayFee;
  }

// Seans Code
  public int getFee() {
    // $7
    return dayFee;
  }

  public void setFee(int dayFee) {
    this.dayFee = dayFee;
  }

  public boolean getPaidFee() {
    return paidFee;
  }

  public boolean getPaidPass() {
    return paidPass;
  }

  public Customer getCustomer() {
    return tempCustomer;
  }
  
// Rachel's Code

  public Customer start() {
    tempCustomer = new Customer();
    Scanner input = new Scanner(System.in);
    
// Start of the Runtime Messages (R):  
// Prompt 1:
    
    System.out.println("Welcome to the 9th Avenue Parking Lot! Please enter your license plate number on the keypad!)");
    String plateNum = input.nextLine();
    
    //if customer accidentally puts lowercase converts to uppercase
    while (plateNum.length() > 8 || plateNum.length() < 1){
      System.out.println("Please input a valid license plate.");
      plateNum = input.nextLine();
    }
      plateNum = plateNum.toUpperCase();
      tempCustomer.setLicensePlate(plateNum);
   
  return tempCustomer;
  }

// Prompt 2:
  
  public Customer build(Customer tempCustomer){
    Scanner input = new Scanner(System.in);
    System.out.println("Will you be needing a handicap spot for the day? Please Press 1 for 'yes', and Press 2 for 'no'");
    String isHandicap = input.nextLine();
    while (!isHandicap.equals("1") && !isHandicap.equals("2")) {
      System.out.println("Invalid input. You must press either '1' or '2'. Please try again");
      isHandicap = input.nextLine();
    }
    int intHandicap = Integer.parseInt(isHandicap);
    tempCustomer.setHandicapEligibilityStatus(intHandicap);

// Prompt 3:
    
    if(!tempCustomer.getRepeatCustomerStatus()){
      System.out.println("Would you like to become a repeat customer? Enter '1' for yes and '2' for no");
    String isRepeat = input.nextLine();
    while (!isRepeat.equals("1") && !isRepeat.equals("2")) {
      System.out.println("Invalid input. You must press either '1' or '2'. Please try again");
      isRepeat = input.nextLine();
    }
    int intRepeat = Integer.parseInt(isRepeat);
    tempCustomer.setRepeatCustomerStatus(intRepeat);
    } 
    return tempCustomer;
    }

// Prompt 4:
  
  public Customer payment(Customer tempCustomer){
    System.out.println("Parking for the day costs $7. We only accept cash, no coins. Please slide your cash in the slot. If you are becoming a repeat customer, you may insert as much as you wish, and the amount will be saved to a balance allowing for you to bypass the payment process next visit.");
    
    Scanner input =new  Scanner(System.in);
    if(tempCustomer.getRepeatCustomerStatus()){
      tempCustomer.deductFromBalance(dayFee);
      while(tempCustomer.getBalance() < 0){
        System.out.println("Please add more money into your account");
        if(input.hasNextInt()){
          int cash = input.nextInt();
        tempCustomer.addToBalance(cash);
        }
        else{
          System.out.println("Please enter a valid numerical value. No coins accepted");
        }
      }
      System.out.println("Your new balance is now $" + tempCustomer.getBalance() + "\nThe gate is now open");
    } else {
      int cash = 0;
      do{
        try{
          cash = Integer.parseInt(input.nextLine());
          if(cash < 7){
            System.out.println("Not enough money");
          }
        } catch (NumberFormatException e){
          System.out.println("Not valid input");
        }
      } while(cash < 7);
    int change = cash - 7;
    if (tempCustomer.getRepeatCustomerStatus()){
        tempCustomer.addToBalance(((double)change));
        System.out.println(change + " has been added to your balance" + "\nThe gate is now open");
      } else {
        System.out.println("Your change is $" + change + "\nThe gate is now open");

      }
    }
    //customer's information was put in a file 
    return tempCustomer;
  }

// Prompt 5:
  
  public Customer update(Customer c){
    Scanner input = new Scanner(System.in);
    System.out.println("What would you like to update? Press 1 for name, 2 for make, 3 for model, 4 for balance, 5 for repeat customer status, 6 to view your current account information");
    String user = input.nextLine();
    while(!user.equals("1") && !user.equals("2") && !user.equals("3") && !user.equals("4") && !user.equals("5") && !user.equals("6")){
      System.out.println("Please enter a valid number. Press 1 for name, 2 for make, 3 for model, 4 for balance, 5 for repeat customer status, 6 to view your current account information");
      user = input.nextLine();
    }
    int answer = Integer.parseInt(user);
    if(answer == 1){
      System.out.println("Please enter your name");
      String newName = input.nextLine();
      c.setName(newName);
    } else if(answer == 2){
      System.out.println("Please enter the make of your vehicle");
      String newMake = input.nextLine();
      c.setMake(newMake);
    } else if(answer == 3){
      System.out.println("please enter the model of your vehicle");
      String newModel = input.nextLine();
      c.setModel(newModel);
    } else if(answer == 4){
      System.out.println("You currently have $" + c.getBalance() + " in your account. Please add money to the kiosk, or enter 0 to cancel");
      double money = input.nextDouble();
      c.addToBalance(money);
      System.out.println("You now have $" + c.getBalance() + " in your account.");
    } else if (answer == 5){
      System.out.println("Would you like to stay a repeat customer? Enter '1' for yes and '2' for no");
      int choice = input.nextInt();
      while (choice != 1 && choice != 2){
        choice = input.nextInt();
      }
      c.setRepeatCustomerStatus(choice);
      if (!c.getRepeatCustomerStatus()){
        System.out.println("Your money has been removed from your balance. Returning $" + c.getBalance() + " in one moment");
      }
    } else if(answer == 6){
       System.out.println("Retreiving your account information");
        System.out.println("License Plate: "+ c.getLicensePlate()+ "\nBalance: "+ c.getBalance()+ "\nHandicap Eligible: "+ c.getHandicapEligiblityStatus()+ "\nRepeat Customer: "+ c.getRepeatCustomerStatus());
    }
    return c;
  }
}