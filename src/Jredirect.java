import java.util.regex.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Jredirect {
   private static String finalUrl;
   private static int i = 0;

   public static final String RESET = "\033[0m";  // Text Reset

   // Colors
   public static final String RED_BOLD = "\033[1;31m";    // RED BOLD
   public static final String GREEN_BOLD = "\033[1;32m";  // GREEN BOLD
   public static final String BLUE_BOLD = "\033[1;34m";   // BLUE BOLD
   public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE BOLD
   public static final String WHITE_BOLD = "\033[1;97m";  // WHITE BOLD

   // Method to print out the usage information.
   public static void usage(Jredirect app) {
      // Shows the usage in the following format: `Usage: java Jredirect <url>`
      System.out.println("Usage: java -jar " + app.getClass().getSimpleName() + ".jar" + " <url>");
   }

   // Method that will return the color of the status code.
   public static String getColor(int code) {
      if (code >= 200 && code < 300) {
         return GREEN_BOLD;
      } else if (code >= 300 && code < 400) {
         return BLUE_BOLD;
      } else if (code >= 400 && code < 500) {
         return RED_BOLD;
      } else if (code >= 500 && code < 600) {
         return PURPLE_BOLD;
      } else {
         return WHITE_BOLD;
      }
   }

   // Method to check whether the given URL is valid using ReGex.
   public static boolean isValidURL(String url) {
      // ReGex to check valid URL.
      String regex = "((http|https)://)(www.)?"
         + "[a-zA-Z0-9@:%._\\+~#?&//=]"
         + "{2,256}\\.[a-z]"
         + "{2,6}\\b([-a-zA-Z0-9@:%"
         + "._\\+~#?&//=]*)";

      try {
         // Compiles the given regular expression into a pattern.
         Pattern p = Pattern.compile(regex);

         // If the given input matches the ReGex, then it returns true, else false.
         Matcher m = p.matcher(url);
         return m.matches();
      } catch (RuntimeException e) {
         return false;
      }
    }

   // Method that will return all the redirected urls of the given url using the HttpURLConnection library.
   public static void getRedirectedURLs(String url) {
      try {
         // Display the loading animation.
         String line = WHITE_BOLD + "#" + i + ": " + RESET + "Tracing URL: " + url;
         System.out.print("\r" + line);

         // Make the connection to the given url.
         HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
         con.setInstanceFollowRedirects(false);
         con.connect();

         // Get the status code of the connection and it's coreesponding color.
         int code = con.getResponseCode();
         String color = getColor(code);

         // Clear the current line before printing the new one.
         System.out.print("\r");
         for (int j = 0; j <= line.length(); j++)
            System.out.print(" ");

         // Print the index + the status code + the url, instead of the spinner.
         if (! (code == HttpURLConnection.HTTP_MOVED_TEMP || code == HttpURLConnection.HTTP_MOVED_PERM || code == HttpURLConnection.HTTP_SEE_OTHER)) {
            finalUrl = url;
            System.out.println("\r" + WHITE_BOLD + "#" + i++ + ": " + color + code + RESET + " " + url);
         } else {
            String location = con.getHeaderField("Location");
            System.out.println("\r" + WHITE_BOLD + "#" + i++ + ": " + color + code + RESET + " " + url);
            if (location != null) {
               getRedirectedURLs(location);
            }
         }
      } catch (MalformedURLException e) {
         System.out.println(RED_BOLD + "Malformed URL: " + url + RESET);
      } catch (IOException e) {
         System.out.println(RED_BOLD + "IOException: " + url + RESET);
      }
   }

   // Main method.
   public static void main(String[] args) {
      Jredirect app = new Jredirect();

      if (args.length == 0) {
         usage(app);
         System.exit(0);
      } else {
         if (isValidURL(args[0])) {
            getRedirectedURLs(args[0]);
            System.out.println("\n" + --i + " Redirect(s) were found " + args[0] + " ➜ " + finalUrl);
            System.exit(0);
         } else {
            System.err.println("The given URL '" + args[0] + "' is invalid.");
            System.exit(1);
         }
      }
   }
}
