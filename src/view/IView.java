package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import controller.ViewListener;

/**
 * This interface represents all methods required to implement a Mock View for testing.
 */
public interface IView {

  /**
   * Add given image and id to the view object and update related field value.
   * @param image a given BufferedImage model
   * @param id a String id created together with the model
   */
  void viewUpdate(BufferedImage image, String id);

  /**
   * Add a user(class) to the ViewListener interface.
   * @param subscriber given class that implements the ViewListener interface
   */
  void addViewListener(ViewListener subscriber);

  /**
   * Return a String path that stores all the data required to save an image file successfully.
   * @return a string of file path and source file id
   */
  String actSave();

  /**
   * Return a String path that stores all the data required to load an image file successfully.
   * @return a string of file path
   */
  String actLoad();

  /**
   * Get focus on the frame of the Mock View.
   */
  void getFrameFocus();

  /**
   * Set the state of visibility of the View.
   * @param b visible if true, otherwise false
   */
  void setVisible(boolean b);

  /**
   * Ask canvas panel to draw a histogram graph by given data in the map.
   * @param mapToHistogram the map that contains all the data to draw a histogram graph
   * @param color set the bar color of the graph
   * @param height half value of the number of pixels in the image
   * @param scalar the scalar number that sets proportional height to frequency
   */
  void paintHistogram(Map<Integer, Integer> mapToHistogram, Color color, int height, int scalar);
}
