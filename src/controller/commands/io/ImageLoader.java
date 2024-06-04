package controller.commands.io;

import java.awt.image.BufferedImage;

import controller.commands.Command;
import model.ImageState;

/**
 * This represents the ImageLoader interface to load an image file and
 * return a state of Image model.
 */
public interface ImageLoader extends Command {

  /**
   * Return a state of Image model by reading a collection of data from a PPM file.
   * @return an ImageState model
   * @throws IllegalStateException if file is not found or reading from file failed
   */
  ImageState loadImage(String path) throws IllegalStateException;

  /**
   * Return a Buffered image from given path that stores the image file with jpg/png/ppm format.
   * @param path String path from the program directory
   * @return a BufferedImage
   */
  BufferedImage getBufferedImage(String path);
}
