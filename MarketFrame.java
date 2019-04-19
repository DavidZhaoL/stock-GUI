
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class display a market data choosing viewer
 * It allows users to choose ticker name, start date and end date
 */

public class MarketFrame extends JFrame implements ActionListener {

	//get the screen width and height
    private Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
    private int width=(int)dimension.getWidth();
    private int height=(int)dimension.getHeight();

    //prepare to store the url
    private String url;

    private Container container;
    
    //declare five panels to make up the container
    private JPanel panel0,panel1,panel2,panel3,panel4;
    private JButton button;
    private JLabel title,companyName,startDate,endDate;

    
    //declare drop down box for user to choose ticker and date
    private JComboBox ticker,startYear,startMonth,startDay,endYear,endMonth,endDay;
    private String[] tickername;
    private String[] year,month,day30,day31,day28,month1,day1;

    //declare different type of stock data
    private List stockCloseValue,date,stockOpenValue,stockLowValue,stockHighValue,volumeList;

    //store the ticker that user choose
    private String currentTicker="";
    private JLabel YYYY,MM,DD,YYYY1,MM1,DD1;

    
    MarketFrame()
    {
        //initial all the component
        iniComponent();

        panel0.add(title);

        panel1.add(companyName);
        panel1.add(ticker);

        panel2.add(startDate);
        panel2.add(YYYY);
        panel2.add(startYear);
        panel2.add(MM);
        panel2.add(startMonth);
        panel2.add(DD);
        panel2.add(startDay);

        panel3.add(endDate);
        panel3.add(YYYY1);
        panel3.add(endYear);
        panel3.add(MM1);
        panel3.add(endMonth);
        panel3.add(DD1);
        panel3.add(endDay);

        panel4.add(button,BorderLayout.CENTER);

        container=this.getContentPane();
        container.setLayout(new GridLayout(5,1,1,1));
        container.add(panel0);
        container.add(panel1);
        container.add(panel2);
        container.add(panel3);
        container.add(panel4);

        //set the correct and corresponding date with month
        myDateEvent();

        setBackground(new Color(128,128,90));
        setBounds(width/4,height/4,600,400);
    }

    //initial all the component
    private void iniComponent()
    {
        year=new String[]{"2013","2014","2015","2016","2017","2018"};
        tickername=new String[]{"MSFT","AAPL"};
        month=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
        day31=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        day28=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};
        day30=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};

        YYYY=new JLabel("  YYYY");
        MM=new JLabel("   MM ");
        DD=new JLabel("   DD ");
        YYYY.setFont(new Font("TimesRoman",Font.PLAIN,16));
        MM.setFont(new Font("TimesRoman",Font.PLAIN,16));
        DD.setFont(new Font("TimesRoman",Font.PLAIN,16));

        YYYY1=new JLabel("  YYYY");
        MM1=new JLabel("   MM ");
        DD1=new JLabel("   DD ");
        YYYY1.setFont(new Font("TimesRoman",Font.PLAIN,16));
        MM1.setFont(new Font("TimesRoman",Font.PLAIN,16));
        DD1.setFont(new Font("TimesRoman",Font.PLAIN,16));

        companyName=new JLabel("Choose the ticker name:  ");
        companyName.setFont(new Font("TimesRoman",Font.BOLD,16));

        startDate=new JLabel("start date: ");
        startDate.setFont(new Font("TimesRoman",Font.BOLD,20));
        endDate=new JLabel("end date:  ");
        endDate.setFont(new Font("TimesRoman",Font.BOLD,20));
        title=new JLabel("Stock Value Viewer");
        title.setFont(new Font("TimesRoman",Font.BOLD,28));

        button=makeButton("query",this);
        button.setFont(new Font("TimesRoman",Font.BOLD,17));
        button.setPreferredSize(new Dimension(100,35));

        panel0=new JPanel();
        panel1=new JPanel();
        panel2=new JPanel();
        panel3=new JPanel();
        panel4=new JPanel();

        /*****set the drop box font and size**************************************************************/
        ticker=new JComboBox(tickername);
        ticker.setFont(new Font("TimesRoman",Font.BOLD,18));
        ticker.setPreferredSize(new Dimension(80,30));
        startYear=new JComboBox(year);
        startYear.setFont(new Font("TimesRoman",Font.PLAIN,14));
        startYear.setPreferredSize(new Dimension(80,30));
        startMonth=new JComboBox(month);
        startMonth.setFont(new Font("TimesRoman",Font.PLAIN,14));
        startMonth.setPreferredSize(new Dimension(50,30));
        startDay=new JComboBox(day31);
        startDay.setFont(new Font("TimesRoman",Font.PLAIN,14));
        startDay.setPreferredSize(new Dimension(50,30));

        endYear=new JComboBox(year);
        endYear.setFont(new Font("TimesRoman",Font.PLAIN,14));
        endYear.setPreferredSize(new Dimension(80,30));
        endMonth=new JComboBox(month);
        endMonth.setFont(new Font("TimesRoman",Font.PLAIN,14));
        endMonth.setPreferredSize(new Dimension(50,30));
        endDay=new JComboBox(day31);
        endDay.setFont(new Font("TimesRoman",Font.PLAIN,14));
        endDay.setPreferredSize(new Dimension(50,30));
        /*****set the drop box font and size****************************************************************/
    }


    //Show the correct date corresponding with month such as there are 30days in February while 31 days in January
    private void myDateEvent()
    {
            // add actionListener to startMonth to make sure it will have the corresponding date
            startMonth.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if ("4 6 9".contains(startMonth.getSelectedItem().toString()) || startMonth.getSelectedItem().toString() == "11") {
                            startDay.removeAllItems();
                            for (int i = 0; i < day30.length; i++) {
                                startDay.addItem(day30[i]);
                            }
                        } else if (startMonth.getSelectedItem().toString() == "2") {
                            startDay.removeAllItems();
                            for (int i = 0; i < day28.length; i++) {
                                startDay.addItem(day28[i]);
                            }
                        } else {
                            startDay.removeAllItems();
                            for (int i = 0; i < day31.length; i++) {
                                startDay.addItem(day31[i]);
                            }
                        }
                    }
            });

        // add actionListener to endMonth to make sure it will have the corresponding date
        endMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("4 6 9".contains(endMonth.getSelectedItem().toString())||endMonth.getSelectedItem().toString()=="11")
                {
                   endDay.removeAllItems();
                   for(int i=0;i<day30.length;i++){
                       endDay.addItem(day30[i]);
                   }
                }
                else if(endMonth.getSelectedItem().toString()=="2")
                {
                    endDay.removeAllItems();
                    for(int i=0;i<day28.length;i++){
                        endDay.addItem(day28[i]);
                    }
                }
                else{
                    endDay.removeAllItems();
                    for(int i=0;i<day31.length;i++){
                        endDay.addItem(day31[i]);
                    }
                }
            }

        });
    }

    /**
     *when the query button clicked, url will be generated,
     *then relevant stock data will be retrieved
     *and sent to myGraph class.
     *after which call the entrance of myGraph class to show the viewer 
     */
    public void actionPerformed(ActionEvent actionEvent)
    {
        Object source=actionEvent.getActionCommand();
        System.out.println(source);
        if(source=="query")
        {
            getUrl();
            if(url!="") {
                System.out.println(url);

                //get the stock value
                getData();

                //check whether there is data or not on this period
                if (this.stockLowValue.size() < 1) {
                    NoDataAlert noDataAlert=new NoDataAlert();
                    noDataAlert.setVisible(true);
                } else {
                	//pass data to myGraph class
                    myGraph.closeValue = this.stockCloseValue;
                    myGraph.date = this.date;
                    myGraph.currentTicker = this.getCurrentTicker();
                    myGraph.openValue = this.stockOpenValue;
                    myGraph.volumn = this.volumeList;
                    myGraph.highValue = this.stockHighValue;
                    myGraph.lowValue = this.stockLowValue;
                    
                    //Call the initAndShowGUI() in myGraph class to show the stock data viewer
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            myGraph.initAndShowGUI();
                        }
                    });
                }
            }

        }
        else
            System.out.println(source);
    }

    /**
    Generate the url according to tickerName,startDate and endDate,
    check the legality of input.
    get the tickerName that user choose
    */
    private void getUrl()
    {
    	//Store the startDate and endDate
        String start=startMonth.getSelectedItem()+"/"+startDay.getSelectedItem()+"/"+startYear.getSelectedItem();
        String end=endMonth.getSelectedItem()+"/"+endDay.getSelectedItem()+"/"+endYear.getSelectedItem();

        //parse the date to int in order to compare the start date with end date
        int startY=Integer.parseInt(startYear.getSelectedItem().toString());
        int startM=Integer.parseInt(startMonth.getSelectedItem().toString());
        int startD=Integer.parseInt(startDay.getSelectedItem().toString());
        int endY=Integer.parseInt(endYear.getSelectedItem().toString());
        int endM=Integer.parseInt(endMonth.getSelectedItem().toString());
        int endD=Integer.parseInt(endDay.getSelectedItem().toString());

        // to make sure endDate is behind the startDate
        if(startY>endY||(startY==endY&&startM>endM)||(startY==endY&&startM==endM&&startD>endD))
        {
            //add an alert window to tell user
            alert myAlert=new alert();
            myAlert.setVisible(true);
            url="";
        }

        //if the chosen date is valid, set the url and the chosen ticker
        else {
            url = "http://quotes.wsj.com/" + ticker.getSelectedItem() + "/historical-prices/download?MOD_VIEW=page&num_rows=1200&startDate=" + start + "&endDate=" + end + "";
            currentTicker=ticker.getSelectedItem().toString();
        }
    }

    /**
    redirect the url,
    read stock value from url
    add the data to the the corresponding type list 
    */
    private void getData() {
        try {
            //Reinitialize before get new data evey time
            stockCloseValue=new ArrayList();
            date=new ArrayList<String>();
            stockOpenValue=new ArrayList();
            volumeList=new ArrayList<>();
            stockHighValue=new ArrayList();
            stockLowValue=new ArrayList();

            HttpURLConnection con = (HttpURLConnection) (new URL(url).openConnection());
            con.setInstanceFollowRedirects(false);
            con.connect();

            //redirection
            String location = con.getHeaderField("Location");
            URL myUrl = new URL(location);

            InputStream inputStream = myUrl.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String split = ",";
            String[] str;
            double close,open,high,low;//different type data

            line=reader.readLine();// jump through the first line which is the category

            //read data line by line
            while ((line = reader.readLine())!=null) {
                str = line.split(split);
                close = Double.valueOf(str[4]);
                high=Double.valueOf(str[2]);
                open=Double.valueOf(str[1]);
                low=Double.valueOf(str[3]);

                //add data to the corresponding List
                stockOpenValue.add(0,open);
                stockCloseValue.add(0, close);

                stockHighValue.add(0,high);
                stockLowValue.add(0,low);

                volumeList.add(0,str[5]);

                date.add(0, str[0]);
            }
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    //Get current ticker
    public String getCurrentTicker() {
        return currentTicker;
    }


    //Add button and its actionListener
    public JButton makeButton(String name,ActionListener actionListener) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(actionListener);
        return myButton;
    }

}
