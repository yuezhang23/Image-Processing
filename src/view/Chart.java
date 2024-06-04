package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * This class implements the canvas that draws a histogram graph from a given hash map
 * of < value, frequencies >, greyscale color and a height scalar to draw the bar graph.
 */
public class Chart extends JPanel {
  private Map<Integer, Integer> map;
  private JLabel paint;

  /**
   * Construct the Chart by initializing part of field values.
   */
  public Chart() {
    this.map = new HashMap<>();
    setBackground(Color.white);
    paint = new JLabel();
    JScrollPane hPane = new JScrollPane(paint);
    hPane.setPreferredSize(new Dimension(800, 400));
    this.add(hPane);
  }

  /**
   * Plot the histogram graph with data from < value, frequencies> map,
   * user selected greyscale method and user selected height scalar from the GUI.
   * @param map1 a hash map of existing greyscale image that collects < value, frequencies> data
   * @param color color related to greyscale method
   * @param height height of the chart
   * @param heightScalar a scalar to adjust height proportionally.
   */
  public void plotChart(Map<Integer, Integer> map1, Color color, int height, int heightScalar) {
    map = map1;

    // paint took up the memory
    // paint.remove all is not working
    // make image a private field is not working

    paint.setIcon(null);
    BufferedImage image = new BufferedImage(1350, height / heightScalar, TYPE_INT_RGB);
    Graphics gc = image.createGraphics();
    if (!map.isEmpty()) {
      for (int i = 0; i < 266; i++) {
        if (map.get(i) != null) {
          String data = Integer.toString(i);
          gc.drawString(data, i * 5 + 10, 1);
          gc.setColor(color);
          gc.drawRect(i * 5 + 10, 2, 1, map.get(i) / heightScalar + 3);
          gc.fillRect(i * 5 + 10, 2, 1, map.get(i) / heightScalar + 3);
          gc.setColor(Color.white);
        }
      }
      gc.dispose();
      paint.setIcon(new ImageIcon(image));
    }
  }
}
