package controller;

import java.util.HashMap;
import java.util.Map;

import model.ImageState;

/**
 * This class implements the Histogram interface.
 */
public class HistogramImpl implements Histogram {

  @Override
  public Map<Integer, Integer> makeHistogram(ImageState model) {
    System.out.println("total pixels :" + model.getWidth() * model.getHeight());

    Map<Integer, Integer> table = new HashMap<>();
    for (int i = 0; i < model.getHeight(); i++) {
      for (int j = 0; j < model.getWidth(); j++) {
        int key = model.getRedChannel(i, j);
        table.merge(key, 1, Integer::sum);
      }
    }
    return table;
  }
}
