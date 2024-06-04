import org.junit.Before;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import controller.ControllerImplGUI;
import controller.ViewListener;
import model.ImageState;
import view.View;
import view.ViewImpl;
import static org.junit.Assert.assertEquals;


/**
 * This test uses a MockView object to test the methods in ControllerImplGUI class
 * and compares the output of viewUpdate method with that of the controller.
 */
public class TestView {
  private Map<String, ImageState> imageMap;
  private View oldView;
  private StringBuilder imageLog;
  private StringBuilder bufferLog;
  private MockView mockView;

  @Before
  public void setup() {
    Map<String, BufferedImage> bufferMap = new HashMap<>();
    imageMap = new HashMap<>();
    imageLog = new StringBuilder();
    bufferLog = new StringBuilder();
    oldView = new ViewImpl(imageLog);
    mockView = new MockView(oldView, bufferLog);
  }

  @Test
  public void testLoadEvent() {
    new ControllerImplGUI(imageMap, mockView).handleLoadEvent();

    // oldView uses appendable to print out ImageState model
    ImageState image1 = imageMap.get("mock1");
    oldView.renderImage(image1);

    // image display after loading
    String expected =
            "(0, 10, 15) (25, 45, 25) (45, 124, 56) (113, 32, 42) "
                    + "(150, 100, 255) (180, 100, 10) \n"
                    + "(25, 25, 25) (45, 124, 56) (113, 32, 42) (150, 100, 255) "
                    + "(180, 100, 10) (80, 90, 40) \n"
                    + "(45, 124, 56) (113, 32, 42) (150, 100, 255) (180, 100, 10) "
                    + "(100, 40, 50) (125, 25, 235) \n"
                    + "(113, 32, 42) (150, 100, 255) (180, 100, 10) (0, 0, 0) "
                    + "(25, 150, 25) (45, 124, 56) \n";
    assertEquals(expected, imageLog.toString());
    assertEquals(expected, bufferLog.toString());
  }

  @Test
  public void testBrightenEvent() {
    ViewListener controller = new ControllerImplGUI(imageMap, mockView);
    controller.handleLoadEvent();
    controller.handleBrighten(new String[]{"mock1", "Brighten", "50"});

    // oldView uses appendable to print out ImageState model
    ImageState image1 = imageMap.get("mock1-Brighten(50)");
    oldView.renderImage(image1);

    // image display after loading
    String expected1 =
            "(50, 60, 65) (75, 95, 75) (95, 174, 106) (163, 82, 92) "
                    + "(200, 150, 255) (230, 150, 60) \n"
                    + "(75, 75, 75) (95, 174, 106) (163, 82, 92) (200, 150, 255) "
                    + "(230, 150, 60) (130, 140, 90) \n"
                    + "(95, 174, 106) (163, 82, 92) (200, 150, 255) (230, 150, 60) "
                    + "(150, 90, 100) (175, 75, 255) \n"
                    + "(163, 82, 92) (200, 150, 255) (230, 150, 60) (50, 50, 50) "
                    + "(75, 200, 75) (95, 174, 106) \n";
    assertEquals(expected1, imageLog.toString());
    String expected2 =
            // log after loading
            "(0, 10, 15) (25, 45, 25) (45, 124, 56) (113, 32, 42) "
                    + "(150, 100, 255) (180, 100, 10) \n"
            + "(25, 25, 25) (45, 124, 56) (113, 32, 42) (150, 100, 255) "
                    + "(180, 100, 10) (80, 90, 40) \n"
            + "(45, 124, 56) (113, 32, 42) (150, 100, 255) (180, 100, 10) "
                    + "(100, 40, 50) (125, 25, 235) \n"
            + "(113, 32, 42) (150, 100, 255) (180, 100, 10) (0, 0, 0) "
                    + "(25, 150, 25) (45, 124, 56) \n"
            // log after brighten
            + "(50, 60, 65) (75, 95, 75) (95, 174, 106) (163, 82, 92) "
                    + "(200, 150, 255) (230, 150, 60) \n"
            + "(75, 75, 75) (95, 174, 106) (163, 82, 92) (200, 150, 255) "
                    + "(230, 150, 60) (130, 140, 90) \n"
            + "(95, 174, 106) (163, 82, 92) (200, 150, 255) (230, 150, 60) "
                    + "(150, 90, 100) (175, 75, 255) \n"
            + "(163, 82, 92) (200, 150, 255) (230, 150, 60) (50, 50, 50) "
                    + "(75, 200, 75) (95, 174, 106) \n";
    assertEquals(expected2, bufferLog.toString());
  }

  @Test
  public void testRenderEvent() {
    ViewListener controller = new ControllerImplGUI(imageMap, mockView);
    controller.handleLoadEvent();
    controller.handleRenderEvent("mock1 GreyScale-Red");

    // oldView uses appendable to print out ImageState model
    ImageState image1 = imageMap.get("mock1-GreyScale-Red");
    oldView.renderImage(image1);

    // image display after loading
    String expected1 =
            "(0, 0, 0) (25, 25, 25) (45, 45, 45) (113, 113, 113) "
                    + "(150, 150, 150) (180, 180, 180) \n"
                    + "(25, 25, 25) (45, 45, 45) (113, 113, 113) (150, 150, 150) "
                    + "(180, 180, 180) (80, 80, 80) \n"
                    + "(45, 45, 45) (113, 113, 113) (150, 150, 150) (180, 180, 180) "
                    + "(100, 100, 100) (125, 125, 125) \n"
                    + "(113, 113, 113) (150, 150, 150) (180, 180, 180) (0, 0, 0) "
                    + "(25, 25, 25) (45, 45, 45) \n";
    assertEquals(expected1, imageLog.toString());
    String expected2 =
            // log after loading
            "(0, 10, 15) (25, 45, 25) (45, 124, 56) (113, 32, 42) "
                    + "(150, 100, 255) (180, 100, 10) \n"
                    + "(25, 25, 25) (45, 124, 56) (113, 32, 42) (150, 100, 255) "
                    + "(180, 100, 10) (80, 90, 40) \n"
                    + "(45, 124, 56) (113, 32, 42) (150, 100, 255) (180, 100, 10) "
                    + "(100, 40, 50) (125, 25, 235) \n"
                    + "(113, 32, 42) (150, 100, 255) (180, 100, 10) (0, 0, 0) "
                    + "(25, 150, 25) (45, 124, 56) \n"
                    // log after brighten
                    + "(0, 0, 0) (25, 25, 25) (45, 45, 45) (113, 113, 113) "
                    + "(150, 150, 150) (180, 180, 180) \n"
                    + "(25, 25, 25) (45, 45, 45) (113, 113, 113) (150, 150, 150) "
                    + "(180, 180, 180) (80, 80, 80) \n"
                    + "(45, 45, 45) (113, 113, 113) (150, 150, 150) (180, 180, 180) "
                    + "(100, 100, 100) (125, 125, 125) \n"
                    + "(113, 113, 113) (150, 150, 150) (180, 180, 180) (0, 0, 0) "
                    + "(25, 25, 25) (45, 45, 45) \n";
    assertEquals(expected2, bufferLog.toString());
  }

  @Test
  public void testHandleSaveEvent() {
    ViewListener controller = new ControllerImplGUI(imageMap, mockView);
    controller.handleLoadEvent();
    controller.handleSaveEvent();

    // test file written to the hard drive
    try {
      Scanner sc = new Scanner(new FileInputStream("res/testcase.ppm"));
      StringBuilder builder = new StringBuilder();
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s).append(System.lineSeparator());
        }
      }
      String expected1 = "P3\n"
              + "6 4\n255\n0\n10\n15\n25\n45\n25\n45\n124\n56\n113\n32\n42\n150\n"
              + "100\n255\n180\n100\n10\n25\n25\n25\n45\n124\n56\n113\n32\n42\n"
              + "150\n100\n255\n180\n100\n10\n80\n90\n40\n45\n124\n56\n113\n32\n42\n"
              + "150\n100\n255\n180\n100\n10\n100\n"
              + "40\n50\n125\n25\n235\n113\n32\n42\n150\n"
              + "100\n255\n180\n100\n10\n0\n0\n0\n25\n150\n25\n45\n124\n56\n";
      assertEquals(expected1, builder.toString());
    } catch (FileNotFoundException e) {
      System.out.println("File mock1-br.ppm not found!");
    }
  }
}
