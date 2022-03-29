public class Management{

  private int income;
  private int flow;
  private boolean repeatCustomer;
  private boolean dailyCustomer;
  private boolean currentCustomers;

  public Management(int income, int flow, int repeatCustomers, int dailyCustomers, boolean currentCustomers){
    this.income = income;
    this.flow = flow;
    this.repeatCustomer = repeatCustomer;
    // CHECK: the constructor has an S on the end of repeatCustomers, but this. does not have an s on the end
    this.dailyCustomer = dailyCustomer;
    this.currentCustomers = currentCustomers;
  }
  
  public int getIncome(){
    return income;
  }

  public int getFlow(){
    return flow;
  }

  public boolean getRepeatCustomer(){
    return repeatCustomer;
  }

  public void isRepeatCustomer(String isRepeatCustomer){
    if (isRepeatCustomer.equals("true")){
      repeatCustomer = true;
    } else {
      repeatCustomer = false;
    }
  }

  public boolean getDailyCustomer(){
    return dailyCustomer;
  }

  public void isDailyCustomer(String isDailyCustomer){
    if (isDailyCustomer.equals("true")){
      dailyCustomer = true;
    } else {
      dailyCustomer = false;
    }
  }

  public boolean getCurrentCustomers(){
    return currentCustomers;
  }

  public void isCurrentCustomer(String isCurrentCustomer){
    if (isCurrentCustomer.equals("true")){
      currentCustomers = true;
    } else {
      currentCustomers = false;
    }
  }
}
