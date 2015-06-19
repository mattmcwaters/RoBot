import javax.swing.*;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


/**
 * Created by Matt on 2015-06-17.
 */
public class menuGUI extends JFrame {

    public static JButton standardEnrol = new JButton("Standard Enrol");
    public static JButton waitlistEnrol = new JButton("Waitlist Enrol");

    public boolean standardPressed = false;
    public boolean waitlistPressed = false;

    public static JTextArea textArea = new JTextArea(50, 10);

    JPanel layout = new JPanel();
    JPanel pOne = new JPanel();
    JPanel pTwo = new JPanel();



    public menuGUI() {
        super("RoBot");
        setSize(400, 100);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        layout.setLayout(new BoxLayout(layout, BoxLayout.X_AXIS));



        pOne.add(standardEnrol);

        pTwo.add(waitlistEnrol);


        layout.add(pOne);
        layout.add(pTwo);


        standardEnrol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Standard button pressed");
                standardPressed = true;
            }
        });


       waitlistEnrol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Waitlist button pressed");
                waitlistPressed = true;
            }
        });


        add(layout);

        setVisible(true);

    }


    public String isPressed(){
        if(standardPressed){
            return "Standard";
        }
        if(waitlistPressed){
            return "Waitlist";
        }
        else{
            return "";
        }
    }


    private void showAllPanels(){
        pOne.setVisible(true);
        pTwo.setVisible(true);


    }
    private void hideAllPanels(){
        pOne.setVisible(false);
        pTwo.setVisible(false);
    }
}
