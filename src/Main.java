import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import controller.ControllerImplGUI;
import controller.ControllerImpl;
import controller.commands.io.LoadScript;
import model.ImageData;
import model.ImageState;
import view.ViewGUI;
import view.ViewImpl;

/**
 * This class run the main method.
 */
public class Main {

  /**
   * This is a demo main method.
   * @param args array of command arguments from the user
   */
  public static void main(String []args) {

    System.out.println("Welcome to Image Processing:");
    System.out.println("Type any hint to RUN: ");
    System.out.println("Hint for GUI Run:  Space");
    System.out.println("Hint for File Run: -file path-of-script-file ");
    System.out.println("Hint for Terminal Run:  -text");
    System.out.println("Show Hint : ");
    Scanner input = new Scanner(System.in);
    String hint0 = input.nextLine();
    if (hint0.contains("-text")) {
      new ControllerImpl(new ImageData(),
              new InputStreamReader(System.in), new ViewImpl(System.out)).processing();
    }

    else if (hint0.contains("script")) {
      Scanner sc = new Scanner(hint0.split(" ")[1]);
      new LoadScript().runCommand(sc, new ImageData());
    }
    else if (hint0.equals(" ")) {
      ViewGUI.setDefaultLookAndFeelDecorated(false);
      ViewGUI frame = new ViewGUI();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Map<String, ImageState> modelMap = new HashMap<>();
      new ControllerImplGUI(modelMap, frame).run();
    }
    else {
      System.out.println("invalid command, end of program");
    }
  }
}