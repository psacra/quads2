import java.awt.Frame;

class MenuChanger extends Thread
{
  private String arr[];
  private int delay=100;
  private Frame f;
  private static int _1segundo=1000;
  
  public MenuChanger(Frame f,String a[])
  {
    arr = a;
    this.f=f;
  }
  
  public void setDelay(int delay){this.delay=delay;}
  
  public void run()
  {
    while (true)
      {
        for (int currS = 0;currS<arr.length;currS++){
           for (int currChar = 0;currChar<=arr[currS].length();currChar++) 
           {
             f.setTitle(arr[currS].substring(0,currChar));          
             try {Thread.sleep(delay);}catch (Exception e){}
           }
             try {Thread.sleep(_1segundo);}catch (Exception e){}           
        }
      }
  }
  
  
}