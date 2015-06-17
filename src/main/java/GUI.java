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
public class GUI extends JFrame {
    public static JButton b = new JButton("Button");
    public java.util.List<String> courseCodeList = new ArrayList<String>();
    public List<String> sectionCodeList = new ArrayList<String>();
    public List<String> lectureCodeList = new ArrayList<String>();
    public boolean buttonPressed = false;
    public String user;
    public String pass;

    public static JTextArea textArea = new JTextArea(50, 10);

    JPanel layout = new JPanel();
    JPanel userInfo = new JPanel();
    JPanel countPanel = new JPanel();
    JPanel pOne = new JPanel();
    JPanel pTwo = new JPanel();


    String choices[]={"F", "S"};
    String numberChoices[]={"0", "1", "2", "3", "4", "5"};


    JLabel userLabel = new JLabel("User Info");
    JTextField username = new JTextField("Username");
    JTextField password = new JTextField("Password");

    JLabel countLabel = new JLabel("Course Count");
    JComboBox courseCount = new JComboBox(numberChoices);

    JTextField courseOne = new JTextField("Course Code");
    JComboBox cbOne = new JComboBox(choices);
    JTextField lectureOne= new JTextField("Lecture Section");
    JLabel label = new JLabel("Class 1");

    JTextField courseTwo = new JTextField("Course Code");
    JComboBox cbTwo = new JComboBox(choices);
    JTextField lectureTwo = new JTextField("Lecture Section");
    JLabel labelTwo = new JLabel("Class 2");


    public GUI() {
        super("Basic Swing App");
        setSize(400, 300);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));

        userInfo.add(userLabel);
        userInfo.add(username);
        userInfo.add(password);

        countPanel.add(countLabel);
        countPanel.add(courseCount);

        pOne.add(label);
        pOne.add(courseOne);
        pOne.add(cbOne);
        pOne.add(lectureOne);

        pTwo.add(labelTwo);
        pTwo.add(courseTwo);
        pTwo.add(cbTwo);
        pTwo.add(lectureTwo);

        layout.add(userInfo);
        layout.add(countPanel);
        layout.add(pOne);
        layout.add(pTwo);
        layout.add(b);
        layout.add(textArea);
        hideAllPanels();
        b.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        courseCodeList.add(courseOne.getText());
                                        courseCodeList.add(courseTwo.getText());
                                        sectionCodeList.add(choices[cbOne.getSelectedIndex()]);
                                        sectionCodeList.add(choices[cbTwo.getSelectedIndex()]);
                                        lectureCodeList.add(lectureOne.getText());
                                        lectureCodeList.add(lectureTwo.getText());

                                        user = username.getText();
                                        pass = password.getText();
                                        if(!buttonPressed){
                                            buttonPressed = true;
                                        }

                                    }
                                });


        courseCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int count = courseCount.getSelectedIndex();
                showAllPanels();
                switch(count){
                    case 0: hideAllPanels();
                        break;
                    case 1: pTwo.hide();
                        break;
                    case 2: System.out.println("");
                        break;
                    default:
                        break;
                }
            }
        });


        add(layout);

        setVisible(true);

    }

    public List<String> getCourses(){
        return courseCodeList;
    }

    public List<String> getSessional(){
        return sectionCodeList;
    }
    public List<String> getLecture(){
        return lectureCodeList;
    }
    public boolean isPressed(){
        return buttonPressed;
    }
    public String getUser(){
        return user;
    }
    public String getPass(){
        return pass;
    }
    private void showAllPanels(){
        pOne.show();
        pTwo.show();
    }
    private void hideAllPanels(){
        pOne.hide();
        pTwo.hide();
    }
}
