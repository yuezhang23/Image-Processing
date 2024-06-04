package controller;

/**
 * This represents all the methods required to implement a controller receives
 * from and responds to the user of the ViewGUI.
 */
public interface ViewListener {

  /**
   * React to pressing Load Button in the GUI window and create an ImageState object and
   * a Buffered Image that coupled with the same String id from the loading path.
   */
  void handleLoadEvent();

  /**
   * React to pressing Save Button in the GUI window and save the buffered image to the
   * given path with the given name and file type.
   */
  void handleSaveEvent();

  /**
   * React to selecting and pressing Render Button in the GUI window and return a rendered
   * image coupled with a specific String id read from the command.
   * @param command the command that contains source model id,
   *                render method and rendered model id.
   */
  void handleRenderEvent(String command);

  /**
   * React to selecting Brighten and pressing Render Button in the GUI window and
   * return a rendered image coupled with a specific String id read from the command.
   * @param event a string array of source model id, Brighten and increment value.
   */
  void handleBrighten(String[] event);

  /**
   * React to pressing the Histogram Button in the GUI window and return a hash table
   * that contains the data to draw a < value, frequencies > graph.
   * @param event a String that contains the source model id and greyscale method
   */
  void handleHistogram(String event);

  /**
   * Set the GUI window visible.
   */
  void run();
}
