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
public class standardGUI extends JFrame {
    public static JButton b = new JButton("Enrol");

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
    JPanel pThree = new JPanel();
    JPanel pFour = new JPanel();
    JPanel pFive = new JPanel();


    String choices[]={"F", "S"};
    String numberChoices[]={"-", "1", "2", "3", "4", "5"};


    JLabel userLabel = new JLabel("Rosi username");
    JTextField username = new JTextField();

    JLabel passLabel = new JLabel("Password");
    JTextField password = new JPasswordField();

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

    JTextField courseThree = new JTextField("Course Code");
    JComboBox cbThree = new JComboBox(choices);
    JTextField lectureThree = new JTextField("Lecture Section");
    JLabel labelThree = new JLabel("Class 3");

    JTextField courseFour = new JTextField("Course Code");
    JComboBox cbFour = new JComboBox(choices);
    JTextField lectureFour = new JTextField("Lecture Section");
    JLabel labelFour = new JLabel("Class 4");

    JTextField courseFive = new JTextField("Course Code");
    JComboBox cbFive = new JComboBox(choices);
    JTextField lectureFive = new JTextField("Lecture Section");
    JLabel labelFive = new JLabel("Class 5");

    public standardGUI() {
        super("RoBot");
        setSize(400, 300);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));

        username.setPreferredSize(new Dimension(80, 20));
        password.setPreferredSize( new Dimension( 80, 20 ) );

        userInfo.add(userLabel);
        userInfo.add(username);
        userInfo.add(passLabel);
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

        pThree.add(labelThree);
        pThree.add(courseThree);
        pThree.add(cbThree);
        pThree.add(lectureThree);

        pFour.add(labelFour);
        pFour.add(courseFour);
        pFour.add(cbFour);
        pFour.add(lectureFour);

        pFive.add(labelFive);
        pFive.add(courseFive);
        pFive.add(cbFive);
        pFive.add(lectureFive);

        layout.add(userInfo);
        layout.add(countPanel);
        layout.add(pOne);
        layout.add(pTwo);
        layout.add(pThree);
        layout.add(pFour);
        layout.add(pFive);
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
                    case 1: pTwo.setVisible(false);
                            pThree.setVisible(false);
                            pFour.setVisible(false);
                            pFive.setVisible(false);
                        break;
                    case 2:
                        pThree.setVisible(false);
                        pFour.setVisible(false);
                        pFive.setVisible(false);
                        break;
                    case 3:
                        pFour.setVisible(false);
                        pFive.setVisible(false);
                        break;
                    case 4:
                        pFive.setVisible(false);
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
        pOne.setVisible(true);
        pTwo.setVisible(true);
        pThree.setVisible(true);
        pFour.setVisible(true);
        pFive.setVisible(true);

    }

    private void hideAllPanels(){
        pOne.setVisible(false);
        pTwo.setVisible(false);
        pThree.setVisible(false);
        pFour.setVisible(false);
        pFive.setVisible(false);
    }
}
