/**
 * Created by Matt M on 21/01/2015.
 */

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class Main {
    public static void main(String [] args) throws IOException {
        menuGUI menu = new menuGUI();
        boolean Continue = true;
        while(Continue){
            try {
                // to sleep 10 seconds
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // recommended because catching InterruptedException clears interrupt flag
                Thread.currentThread().interrupt();
                // you probably want to quit if the thread is interrupted
                return;
            }
            if(menu.isPressed().equals("Standard")){
                Continue = false;
            }
            if(menu.isPressed().equals("Waitlist")){
                Continue = false;
            }
        }


        if(menu.isPressed().equals("Standard")){
            standardGUI userInterface = new standardGUI("Standard");
            userInterface.setVisible(true);
            menu.setVisible(false);


            PrintStream printStream = new PrintStream(new customConsole(userInterface.textArea));
            PrintStream standardOut = System.out;
            PrintStream standardErr = System.err;
            System.setOut(printStream);
            System.setErr(printStream);

            List<String> courseCodeList = new ArrayList<String>();
            List<String> sectionCodeList = new ArrayList<String>();
            List<String> lectureCodeList = new ArrayList<String>();
            System.out.println("Status Information:");
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
            if(!(userInterface.waitTime == 0L)){
                System.out.print("Commencing sleep, see you in " + userInterface.waitTime + "h!");

            }

            try {
                TimeUnit.HOURS.sleep(userInterface.waitTime);
            } catch (InterruptedException e) {
                System.exit(1);
            }

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

            while(i < userInterface.getChoice()){
                try {
                    System.out.println("\nTrying to add Course Code: " + courseCodeList.get(i) + " with Section Code: " + sectionCodeList.get(i));
                    HtmlAnchor cEnrolBtn = loggedIn.getAnchorByHref("/sws/reg/main.do?main.dispatch");
                    loggedIn = cEnrolBtn.click();


                    //TimeUnit.MINUTES.sleep(1);
                    //which manage courses is this?
                    List buttons = loggedIn.getByXPath("//a");

                    HtmlAnchor manageBtn = (HtmlAnchor) buttons.get(27);
                    loggedIn = manageBtn.click();

                    //TimeUnit.MINUTES.sleep(1);
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

                    System.out.println("Successfully added course");
                    TimeUnit.MINUTES.sleep(2);
                }
                catch(Exception e){
                    System.out.println("Failed to add course");

                }
                i++;
            }
        }
        if(menu.isPressed().equals("Waitlist")){
            standardGUI userInterface = new standardGUI("Waitlist");
            userInterface.setVisible(true);
            menu.setVisible(false);


            PrintStream printStream = new PrintStream(new customConsole(userInterface.textArea));
            PrintStream standardOut = System.out;
            PrintStream standardErr = System.err;
            System.setOut(printStream);
            System.setErr(printStream);

            List<String> courseCodeList = new ArrayList<String>();
            List<String> sectionCodeList = new ArrayList<String>();
            List<String> lectureCodeList = new ArrayList<String>();
            System.out.println("Status Information:");
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


            courseCodeList = userInterface.getCourses();
            lectureCodeList = userInterface.getLecture();
            sectionCodeList = userInterface.getSessional();
            userUName = userInterface.getUser();
            userPWord = userInterface.getPass();


            LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

            int j=1;

            while(true){
                System.out.print("Logging in and accessing course page...\n");

                WebClient wc = new WebClient();

                HtmlPage rosiMain = wc.getPage("https://sws.rosi.utoronto.ca/sws/auth/login.do;jsessionid=A3ECBAED3E6597DFAA349EB3A2915A71.w2?main.dispatch");

                HtmlSubmitInput loginBtn = rosiMain.getFirstByXPath("//input[@value='Login']");
                HtmlTextInput uname = rosiMain.getFirstByXPath("//input[@id='personId']");
                HtmlPasswordInput pword = rosiMain.getFirstByXPath("//input[@id='pin']");

                uname.setValueAttribute(userUName);
                pword.setValueAttribute(userPWord);

                HtmlPage loggedIn = loginBtn.click();
                //Am logged in
                int i = 0;

                while(i < userInterface.getChoice()){
                    if(!courseCodeList.get(i).equals("DONE")){
                        try {
                            System.out.println("\nTrying to add Course Code: " + courseCodeList.get(i) + " with Section Code: " + sectionCodeList.get(i));
                            HtmlAnchor cEnrolBtn = loggedIn.getAnchorByHref("/sws/reg/main.do?main.dispatch");
                            loggedIn = cEnrolBtn.click();

                            TimeUnit.SECONDS.sleep(RandomUtils.nextInt(4, 11));
                            //TimeUnit.MINUTES.sleep(1);
                            //which manage courses is this?
                            List buttons = loggedIn.getByXPath("//a");

                            HtmlAnchor manageBtn = (HtmlAnchor) buttons.get(27);
                            if(manageBtn.asXml().contains("edit.do?editCourse")){
                                loggedIn = manageBtn.click();
                            }

                            TimeUnit.SECONDS.sleep(RandomUtils.nextInt(5, 10));
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

                            System.out.println("Successfully added course");
                            courseCodeList.set(i, "DONE");
                            sectionCodeList.set(i, "DONE");
                            lectureCodeList.set(i, "DONE");


                            TimeUnit.SECONDS.sleep(RandomUtils.nextInt(1, 10));                    }
                        catch(Exception e){
                            System.out.println("Failed to add course");

                        }

                    }
                    i++;
                }
                System.out.println("\nFinished attempt number " + j +". Will try again in "+userInterface.waitTime+" minutes");
                System.out.println("__________________________________\n");
                try {
                    TimeUnit.MINUTES.sleep(userInterface.waitTime);
                } catch (InterruptedException e) {
                    System.exit(1);
                }
                j++;
            }

        }

    }

}
