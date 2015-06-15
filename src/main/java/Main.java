/**
 * Created by Matt M on 21/01/2015.
 */

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;


public class Main {

    public static void main(String [] args) throws IOException {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        WebClient wc = new WebClient();
        List<String> courseCodeList = new ArrayList<String>();
        List<String> sectionCodeList = new ArrayList<String>();
        List<String> lectureCodeList = new ArrayList<String>();

        Scanner userInput = new Scanner(System.in);
        int j = 1;
        boolean Continue = true;


        while(Continue) {
            System.out.print("Please enter course number " + j + "\n");
            courseCodeList.add(userInput.nextLine());
            System.out.print("Please enter section code for course number " + j + "\n");
            sectionCodeList.add(userInput.nextLine());
            System.out.print("Please enter lecture section code for course number" + j + "\n");
            lectureCodeList.add(userInput.nextLine());
            System.out.print("Would you like to add another course? (Y/N)\n");
            if ((userInput.nextLine().equals("N"))) {
                Continue = false;
            }
            j++;
        }


        HtmlPage rosiMain = wc.getPage("https://sws.rosi.utoronto.ca/sws/auth/login.do;jsessionid=A3ECBAED3E6597DFAA349EB3A2915A71.w2?main.dispatch");

        //System.out.print(rosiMain.asXml());


        HtmlSubmitInput loginBtn = rosiMain.getFirstByXPath("//input[@value='Login']");
        HtmlTextInput uname = rosiMain.getFirstByXPath("//input[@id='personId']");
        HtmlPasswordInput pword = rosiMain.getFirstByXPath("//input[@id='pin']");

        System.out.print("Please enter your Rosi username:\n");
        String userUName = userInput.nextLine();
        System.out.print("Please enter your Rosi password:\n");
        String userPWord = userInput.nextLine();
        uname.setValueAttribute(userUName);
        pword.setValueAttribute(userPWord);

        HtmlPage loggedIn = loginBtn.click();
        //System.out.print(loggedIn.asXml());
        System.out.print("Logging in and accessing course information...\n");
        //Am logged in
        int i = 0;

        while(i < courseCodeList.size()){
            System.out.print("\nTrying to add Course Code: " + courseCodeList.get(i) + " with Section Code: " + sectionCodeList.get(i));
            HtmlAnchor cEnrolBtn = loggedIn.getAnchorByHref("/sws/reg/main.do?main.dispatch");
            loggedIn = cEnrolBtn.click();

            //which manage courses is this?
            HtmlAnchor manageBtn = loggedIn.getAnchorByText("Manage Courses");

            loggedIn = manageBtn.click();


            //At course selection

            //String lecString = "//input[@value='"+ lectureCodeList.get(i)+"']";
            //System.out.print(lecString);

            HtmlTextInput courseCode = loggedIn.getFirstByXPath("//input[@id='code']");
            HtmlTextInput sectionCode = loggedIn.getFirstByXPath("//input[@id='sectionCode']");
            HtmlSubmitInput courseSubmit = loggedIn.getFirstByXPath("//input[@name='viewCourse.dispatch']");

            courseCode.setValueAttribute(courseCodeList.get(i));
            sectionCode.setValueAttribute(sectionCodeList.get(i));

            loggedIn = courseSubmit.click();
            //System.out.println(loggedIn.asText());
            try {
                if (!loggedIn.getFirstByXPath("//img[@src='/sws/images/warning.gif']").equals(null)) {
                    System.out.print("\n    Error adding class!");
                }
            }
            catch(Exception e){

            }
            HtmlRadioButtonInput lecSection = loggedIn.getFirstByXPath("//input[@value='LEC,"+lectureCodeList.get(i)+"']");
            lecSection.setChecked(true);
            loggedIn = courseSubmit.click();
            HtmlSubmitInput addSection = loggedIn.getFirstByXPath("//input[@name='modifyCourse.dispatch']");
            loggedIn = addSection.click();
            //System.out.println(loggedIn.asXml());


            //System.out.print(loggedIn.asText());


            i++;

        }





        //HtmlAnchor loginLink = rosiMain.getAnchorByHref()





    }

}
