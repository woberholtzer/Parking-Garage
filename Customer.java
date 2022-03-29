import java.util.Scanner;
import java.util.*;
public class Customer{

private String name;
private String make;
private String model;
private String paymentMethod;
private String creditCardNumber;
private String licensePlate;
private boolean repeatCustomer;
private boolean handicapEligible;
private double balance;
private String arrivalDate;
private String departDate;
private ParkingSpot ps;
private int pin;

public Customer(){
}

public Customer(String name, String make, String model, String paymentMethod, String licensePlate, boolean repeatCustomer, boolean handicapEligible, int pin){
  this.name = name;
  this.make = make;
  this.model = model;
  this.paymentMethod = paymentMethod;
  this.repeatCustomer = repeatCustomer;
  this.handicapEligible = handicapEligible;
  this.licensePlate = licensePlate;
  this.pin = pin;
}

public String getName(){
  return name;
}
public void setName(String name){
  this.name = name;
}
public String getMake(){
  return make;
}

public void setMake(String make){
  this.make = make;
}

public String model(){
  return model;
}

public void setModel(String model){
  this.model = model;
}

public String getPaymentMethod(){
  return paymentMethod;
}

public void setPaymentMethod(String paymentMethod){
  this.paymentMethod = paymentMethod;
}

public boolean getRepeatCustomerStatus(){
  return repeatCustomer;
}

public void setRepeatCustomerStatus(int repeatCustomerStatus){
  if (repeatCustomerStatus == 1){
    repeatCustomer = true;
  } else {
    repeatCustomer = false;
  }
}

public void changeRepeatCustomerStatus(){
  if (repeatCustomer = true){
    repeatCustomer = false;
  } else {
    repeatCustomer = true;
  }
}

public boolean getHandicapEligiblityStatus(){
  return handicapEligible;
}

public void setHandicapEligibilityStatus(int handicapEligibleStatus){
  if (handicapEligibleStatus == 1){
    handicapEligible = true;
  } else {
    handicapEligible = false;
  }
}

public String getLicensePlate(){
  return licensePlate;
}

public void setLicensePlate(String licensePlate){
  this.licensePlate = licensePlate;
}

public double getBalance(){
  return balance;
}

public void setBalance(double balance){
  this.balance = balance;
}
public void addToBalance(double amount){
balance += amount;
}

public void deductFromBalance(double amount){
balance -= amount;
}

public int getPin(){
  return pin;
}

public void setPin(int pin){
  this.pin = pin;
}

public String addToBalanceWithCard(){
  Scanner input = new Scanner(System.in);
  if (paymentMethod.equals("Credit Card")){
    System.out.println("Enter Credit Card Number: ");
    String creditCard = input.nextLine();
    if (creditCard.equals(creditCardNumber)){
      System.out.println("How much money do you want to add to your account? ");
      balance += input.nextDouble();    
    }
  }
  input.close();
  return "New Balance: " + balance;
}

public String addToBalanceWithCash(){
  Scanner input = new Scanner(System.in);
  System.out.println("How much money do you want to add to your account? ");
  balance += input.nextDouble();
  input.close();
  return "New Balance: " + balance;
}

public String toString(){
  String toReturn = "";
  if(ps!=null){
    toReturn = toReturn + "{" + /*name + ", " + make + " " + model + ", " + paymentMethod + ", " + */licensePlate + ", " + "Repeat Customer: " + repeatCustomer + ", Handicap Eligible: " + handicapEligible + ", Balance: " + balance + "}" + "\n" + ps.toString();
  } else {
    toReturn = toReturn + "{" + /*name + ", " + make + " " + model + ", " + paymentMethod + ", " + */licensePlate + ", " + balance + "}" + "\n";
  }
  return toReturn;
}


/*
public Calendar arrivalDate(){
  Calendar arrival = Calendar.getInstance();
  
  return arrival;
}

public Calendar departDate(){
  Calendar depart = Calendar.getInstance();
  depart.add(Calendar.DATE, 1);
  
  return depart;
}*/

public void setParkingSpot(ParkingSpot ps){
  this.ps = ps;
}
public boolean equals(Object a){ 
  // Overriding boolean to check deep equality between different objects
        // self check
        if (this == a){
            return true;
        }
        // null check
        if (a == null){
            return false;
        }
        // type check and cast
        if (getClass() != a.getClass()){
            return false;
        }
        Customer p1 = (Customer) a;
        // field comparison
        boolean toReturn = false;
        if (Objects.equals(licensePlate, p1.licensePlate)){
            toReturn = true;
        }
        return toReturn;
    }
}