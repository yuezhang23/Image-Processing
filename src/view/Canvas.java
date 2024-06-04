package view;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * This represents the canvas of the GUI window that
 * draws the BufferedImage model in response to user interactions.
 */
public interface Canvas {

  /**
   * Paint on the canvas with one rendered image on the left canvas window and
   * the original one on the right canvas window.
   * @param map the map that couples valid BufferedImage objects with distinct String IDs
   * @param id the rendered image coupled with the given id
   */
  void imagePainting(Map<String, BufferedImage> map, String id);

  /**
   * Paint only one side of the canvas with rendered image coupled with the id.
   * @param map the map that couples valid BufferedImage objects with distinct String IDs
   * @param id the rendered image coupled with the given id
   * @param side left side if 0, right side if 1.
   */
  void singlePainting(Map<String, BufferedImage> map, String id, int side);
}
