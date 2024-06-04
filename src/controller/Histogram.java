package controller;

import java.util.Map;

import model.ImageState;

/**
 * This represents the method required to implement a histogram from a greyscale rendered image.
 */
public interface Histogram {

  /**
   * Return a hash map that couples the pixel value with the frequencies appeared in the
   * given model.
   * @param model the image model used to make a histogram graph
   * @return the hash map of  Pixel < value, frequencies >
   */
  Map<Integer, Integer> makeHistogram(ImageState model);
}
