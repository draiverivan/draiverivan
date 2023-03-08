import java.io.*;
import java.net.*;

public class DailyAdviceServer {

  /*
   * (remember, these Strings were word-wrapped by the code editor. Never hit
   * return in the middle of a String!)
   */
  String[] adviceList = { "Take smaller bites", "Go for the tight jeans. No they do NOT make you look fat.",
      "One word: inappropriate", "Just for today, be honest. Tell your boss what you *really* think",
      "You might want to rethink that haircut." };

  public void go() {
    try {
      /*
       * ServerSocket makes this server application ‘listen’ for client requests on
       * port 4242 on the machine this code is running on.
       */
      ServerSocket serverSock = new ServerSocket(4242);

      /*
       * The server goes into a permanent loop, waiting for (and servicing) client
       * requests
       */
      while (true) {

        /*
         * the accept method blocks (just sits there) until a request comes in, and then
         * the method returns a Socket (on some anonymous port) for communicating with
         * the client
         */
        Socket sock = serverSock.accept();

        /*
         * now we use the Socket connection to the client to make a PrintWriter and send
         * it (println()) a String advice message. Then we close the Socket because
         * we’re done with this client.
         */
        PrintWriter writer = new PrintWriter(sock.getOutputStream());
        String advice = getAdvice();
        writer.println(advice);
        writer.close();
        System.out.println(advice);
        serverSock.close();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  } // close go

  private String getAdvice() {
    int random = (int) (Math.random() * adviceList.length);
    return adviceList[random];
  }

  public static void main(String[] args) {
    DailyAdviceServer server = new DailyAdviceServer();
    server.go();
  }
}