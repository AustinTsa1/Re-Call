import java.io.IOException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.twiml.*;

@SuppressWarnings("serial")
@WebServlet("/voice")
public class TwilioRecord extends HttpServlet {

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "ACd87c1b6c124645a1c0c731530a700ea1";
    public static final String AUTH_TOKEN = "your_auth_token";

    public static void main(String[] args) throws URISyntaxException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Call call = Call.creator(
                new PhoneNumber("+14155551212"),
                new PhoneNumber("+15017250604"),
                new URI("http://demo.twilio.com/docs/voice.xml")
                )
                .setRecord(true)
                .create();

        System.out.println(call.getSid());
    }
	
  // Handle HTTP POST to /record
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Use <Say> to give the caller some instructions
    Say instructions = new Say.Builder("Hello. Please leave a message after the beep.").build();

    // Use <Record> to record the caller's message
    Record record = new Record.Builder().build();

    // End the call with <Hangup>
    Hangup hangup = new Hangup();

    // Create a TwiML builder object
    VoiceResponse twiml = new VoiceResponse.Builder()
        .say(instructions)
        .record(record)
        .hangup(hangup)
        .build();

    // Render TwiML as XML
    response.setContentType("text/xml");

    try {
      response.getWriter().print(twiml.toXml());
    } catch (TwiMLException e) {
      e.printStackTrace();
    }



  }
}