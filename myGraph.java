import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *This class receive the relevant stock data and plot them in a lineChart
 *Users can get the stock data at the mouse position with moving mouse
 *Users can get more information by click the buttons
 */
public class myGraph {

    //Declare different stock value
    public static List openValue=new ArrayList();
    public static List closeValue=new ArrayList();
    public static List highValue=new ArrayList();
    public static List lowValue=new ArrayList();
    public static List volumn=new ArrayList();

    //These variables are changed according to which type stock value user choose
    public static List stockValue=new ArrayList();
    public static List<String>date=new ArrayList<String>();
    public static String currentTicker="";
    public static String currentType="";
    public static JButton buttonNew,buttonCLose,buttonOpen,buttonHigh,buttonLow;
    
    public static JFXPanel fxPanel;
    public static JFrame frame;


    public static void initAndShowGUI(){
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        int width=(int)dimension.getWidth();
        int height=(int)dimension.getHeight();

        // This method is invoked on the EDT thread
        frame= new JFrame("Swing and JavaFX");
        fxPanel= new JFXPanel();
        JPanel panel=new JPanel();

        //This button is to start new query
        buttonNew =new JButton("Start new");
        
        //declare buttons to make user get more information
        buttonCLose=new JButton("closing price");
        buttonOpen=new JButton("opening price");
        buttonHigh=new JButton("high price");
        buttonLow=new JButton("low price ");
        
        //Set the property
        buttonCLose.setFont(new java.awt.Font("TimesRoman", java.awt.Font.PLAIN,18));
        buttonCLose.setPreferredSize(new Dimension(130,40));
        buttonOpen.setFont(new java.awt.Font("TimesRoman", java.awt.Font.PLAIN,18));
        buttonOpen.setPreferredSize(new Dimension(130,40));
        buttonHigh.setFont(new java.awt.Font("TimesRoman", java.awt.Font.PLAIN,18));
        buttonHigh.setPreferredSize(new Dimension(130,40));
        buttonLow.setFont(new java.awt.Font("TimesRoman", java.awt.Font.PLAIN,18));
        buttonLow.setPreferredSize(new Dimension(130,40));
        
        //Set the interval between the type choose button and start new button
        JLabel blank=new JLabel("   ");

        
        buttonNew.setFont(new java.awt.Font("TimesRoman", java.awt.Font.BOLD,20));
        buttonNew.setPreferredSize(new Dimension(130,40));
        buttonNew.setBackground(new java.awt.Color(200, 206, 174));
        
        panel.add(buttonOpen);
        panel.add(buttonCLose);
        panel.add(buttonHigh);
        panel.add(buttonLow);

        panel.add(blank);
        panel.add(buttonNew);

        frame.add(fxPanel);
        frame.add(panel,BorderLayout.SOUTH);

        //add clickListener to the buttons
        setButtonListener();
        
        //set the default type is close day value
        stockValue=closeValue;
        currentType="CLOSING VALUE";

        frame.setBounds(0,0,width,11*height/12);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //call the initial fxPanel function
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }
    
     /**** Add actionListener to the buttons to display more information***************************/
    public static void setButtonListener(){
    	
    	//The start new button clicked, the clsoe the current frame
        buttonNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        frame.setVisible(false);
                    }
                });
            }
        });

        /**The open button clicked, unvisible the current fxPanel
         *pass the open price to stockValue List(which will be the data seris to draw)
         *initial a new fxPanel
         */
        buttonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        fxPanel.setVisible(false);
                        currentType="OPENING PRICE";
                        stockValue=openValue;
                        initFX(fxPanel);
                        fxPanel.setVisible(true);
                    }
                });
            }
        });

         /**The close button clicked, unvisible the current fxPanel
         *pass the close price to stockValue List(which will be the data seris to draw)
         *initial a new fxPanel
         */
        buttonCLose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        fxPanel.setVisible(false);
                        currentType="CLOSING PRICE";
                        stockValue=closeValue;
                        initFX(fxPanel);
                        fxPanel.setVisible(true);
                    }
                });
            }
        });

         /**The high button clicked, unvisible the current fxPanel
         *pass the high price to stockValue List(which will be the data seris to draw)
         *initial a new fxPanel
         */
        buttonHigh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        fxPanel.setVisible(false);
                        currentType="HIGH PRICE";
                        stockValue=highValue;
                        initFX(fxPanel);
                        fxPanel.setVisible(true);
                    }
                });
            }
        });

         /**The low button clicked, unvisible the current fxPanel
         *pass the low price to stockValue List(which will be the data seris to draw)
         *initial a new fxPanel
         */
        buttonLow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        fxPanel.setVisible(false);
                        currentType="LOW PRICE";
                        stockValue=lowValue;
                        initFX(fxPanel);
                        fxPanel.setVisible(true);
                    }
                });
            }
        });
    }
/***********************************************************************************************/
   

    //initial fxPanel
    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    /**
    *Show only one series every time, to make the data clear
    *the series datea is from stockValue List
    *So the stockValue will be update every time when the button clicked
    *each time user choose different type, the stockValue will be changed
    */
    private static Scene createScene() {
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        int width=(int)dimension.getWidth();
        int height=(int)dimension.getHeight();

        //Declare a text moving with mouse and show the relevant information
        Text  text  =  new  Text();

        //Declare a vertical line to help user find date with mouse
        Line line=new Line();

        //set the axis properties
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Value");

        //Declare the lineChart to display the stock value
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle(""+currentTicker+" "+currentType+"");

        //Disabling Symbols for the Line Chart
        lineChart.setCreateSymbols(false);

        //Input the stock data and date into a series
        XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
        series1.setName(currentType+" value");
        for (int i=0;i<stockValue.size();i++) {
            series1.getData().add(new XYChart.Data(date.get(i), stockValue.get(i)));
        }
        
        lineChart.getData().addAll(series1);
        lineChart.setPrefSize(width,4*height/5);

        //set the lineGraph as the root
        Group root=new Group(lineChart);
        root.getChildren().add(text);
        root.getChildren().add(line);

        //set the scene
        Scene scene  = new Scene(root,4*width/6,3*height/6);

        /**
        /* make a text moving with mouse and show the stock data at that point
         **/
        lineChart.setOnMouseMoved((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	
            	//get the mouse position
                Point2D mouseSceneCoords = new Point2D(event.getSceneX(), event.getSceneY());
                if(56<mouseSceneCoords.getX()&&mouseSceneCoords.getX()<(width-30)&&mouseSceneCoords.getY()<4*height/6) {
                    double x = xAxis.sceneToLocal(mouseSceneCoords).getX();

                    //get the stock data at the mouse position
                    String currentDate = xAxis.getValueForDisplay(x);
                    String currentValue = stockValue.get(date.indexOf(xAxis.getValueForDisplay(x))).toString();
                    String currentVolume=volumn.get(date.indexOf(xAxis.getValueForDisplay(x))).toString();

                    //format to the text
                    String textWithMouse = formTextOnGraph(currentTicker, currentDate, currentValue,currentVolume);

                    //set text properties
                    text.setX(MouseInfo.getPointerInfo().getLocation().getX());
                    text.setY(MouseInfo.getPointerInfo().getLocation().getY());
                    text.setFont(new Font(10));
                    text.setText(textWithMouse);
                    text.setVisible(true);

                    //set the line position
                    line.setStartX(MouseInfo.getPointerInfo().getLocation().getX());
                    line.setStartY(height/22);
                    line.setEndX(MouseInfo.getPointerInfo().getLocation().getX());
                    line.setEndY(9 * height / 13);
                }
                else {
                    text.setVisible(false);
                }
            }

            // formula the text moving with mouse in the graph
            public String formTextOnGraph(String TickerName, String date,String value, String volume) {
                String[] str;
                str=date.split("/");
                String result=""+TickerName+
                        "\nDate:20"+
                        str[2]+
                        " "+
                        str[0]+
                        " "+
                        str[1]+
                        "\nValue:"+
                        value+
                        "\nVolume: "+
                        volume+
                        "";
                return result;
            }

        }));

        return (scene);
    }



}