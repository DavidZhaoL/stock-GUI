import javax.swing.*;

/**
  * Title:  STOCK VALUE VIEWER
  * Description: DISPLAY THE STOCK DATA WHICH USER CHOOSE
  * @author: LEI ZHAO
  * @version: 1.0
  * @time: 2019.01.10
  */

public class MarketGUI {

    public static void main(String[] args)
    {
        //Starting all project
        MarketFrame marketFrame=new MarketFrame();
        marketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marketFrame.setVisible(true);
    }
}
