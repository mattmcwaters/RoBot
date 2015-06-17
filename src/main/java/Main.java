/**
 * Created by Matt M on 21/01/2015.
 */

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;


public class Main {

    public static void main(String [] args) throws IOException {
        GUI userInterface = new GUI();
        PrintStream printStream = new PrintStream(new customConsole(userInterface.textArea));

        PrintStream standardOut = System.out;
        PrintStream standardErr = System.err;

        System.setOut(printStream);
        System.setErr(printStream);


        List<String> courseCodeList = new ArrayList<String>();
        List<String> sectionCodeList = new ArrayList<String>();
        List<String> lectureCodeList = new ArrayList<String>();

        String userUName;
        String userPWord;

        while(!userInterface.isPressed()){
            try {
                // to sleep 10 seconds
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // recommended because catching InterruptedException clears interrupt flag
                Thread.currentThread().interrupt();
                // you probably want to quit if the thread is interrupted
                return;
            }
        }
        System.out.println("Populating arrays...");
        courseCodeList = userInterface.getCourses();
        lectureCodeList = userInterface.getLecture();
        sectionCodeList = userInterface.getSessional();
        userUName = userInterface.getUser();
        userPWord = userInterface.getPass();


        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);



        WebClient wc = new WebClient();

        HtmlPage rosiMain = wc.getPage("https://sws.rosi.utoronto.ca/sws/auth/login.do;jsessionid=A3ECBAED3E6597DFAA349EB3A2915A71.w2?main.dispatch");

        HtmlSubmitInput loginBtn = rosiMain.getFirstByXPath("//input[@value='Login']");
        HtmlTextInput uname = rosiMain.getFirstByXPath("//input[@id='personId']");
        HtmlPasswordInput pword = rosiMain.getFirstByXPath("//input[@id='pin']");

        uname.setValueAttribute(userUName);
        pword.setValueAttribute(userPWord);

        HtmlPage loggedIn = loginBtn.click();
        System.out.print("Logging in and accessing course page...\n");

        //Am logged in
        int i = 0;

        while(i < courseCodeList.size()){
            try {
                System.out.println("\nTrying to add Course Code: " + courseCodeList.get(i) + " with Section Code: " + sectionCodeList.get(i));
                HtmlAnchor cEnrolBtn = loggedIn.getAnchorByHref("/sws/reg/main.do?main.dispatch");
                loggedIn = cEnrolBtn.click();

                //which manage courses is this?
                HtmlAnchor manageBtn = loggedIn.getAnchorByText("Manage Courses");
                loggedIn = manageBtn.click();

                //At course selection
                HtmlTextInput courseCode = loggedIn.getFirstByXPath("//input[@id='code']");
                HtmlTextInput sectionCode = loggedIn.getFirstByXPath("//input[@id='sectionCode']");
                HtmlSubmitInput courseSubmit = loggedIn.getFirstByXPath("//input[@name='viewCourse.dispatch']");

                courseCode.setValueAttribute(courseCodeList.get(i));
                sectionCode.setValueAttribute(sectionCodeList.get(i));

                loggedIn = courseSubmit.click();

                try {
                    if (!loggedIn.getFirstByXPath("//img[@src='/sws/images/warning.gif']").equals(null)) {
                        System.out.println("Rosi issued a warning, class may not be available");
                    }
                } catch (Exception e) {

                }
                HtmlRadioButtonInput lecSection = loggedIn.getFirstByXPath("//input[@value='LEC," + lectureCodeList.get(i) + "']");
                lecSection.setChecked(true);
                loggedIn = courseSubmit.click();
                HtmlSubmitInput addSection = loggedIn.getFirstByXPath("//input[@name='modifyCourse.dispatch']");
                loggedIn = addSection.click();

                i++;
                System.out.println("Successfully added course");
            }
            catch(Exception e){
                System.out.println("Failed to add course");
            }
        }

    }

}
