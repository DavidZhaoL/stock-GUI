import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *When the start date is behind the end date, this alert window will pop up to alert
 */
 
public class alert extends JDialog implements ActionListener {

    JButton okButton;
    JLabel alarmText;
    Container c;

    private Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
    private int width=(int)dimension.getWidth();
    private int height=(int)dimension.getHeight();
    private JPanel jPanel;

    alert()
    {
            setTitle("alert");
            setBounds(2*width/5,2*height/5,350,150);
            alarmText=new JLabel("End Date should behind the Start Date !",SwingConstants.CENTER);
            alarmText.setFont(new Font("TimesRoman",Font.BOLD,16));

            okButton=new JButton("ok");
            okButton.addActionListener(this);
            okButton.setFont(new Font("TimesRoman",Font.BOLD,18));
            okButton.setPreferredSize(new Dimension(60,30));

            jPanel=new JPanel();
            jPanel.add(okButton,BorderLayout.CENTER);

            c=this.getContentPane();
            c.add(alarmText,BorderLayout.CENTER);
            c.add(jPanel,BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getActionCommand();
        if(source=="ok")
        {
            this.setVisible(false);
        }
    }

}
