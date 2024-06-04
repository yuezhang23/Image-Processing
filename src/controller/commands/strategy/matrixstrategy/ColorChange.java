package controller.commands.strategy.matrixstrategy;

import java.util.Scanner;

import controller.commands.Command;
import model.ImageState;

/**
 * This represents the ColorChange interface that extends the Command interface
 * with all the methods required to filter and transform on an Image model.
 */
public interface ColorChange extends Command {

  /**
   * Return a state of an image model after transform the original model
   * with a kernel matrix that works on the pixels.
   * @param matrix a kernel matrix related to pixel values
   * @param image the original image model
   * @return a new state of rendered image model
   */
  ImageState colorChange(Kernel matrix, ImageState image);

  /**
   * Return a kernel matrix that represents the render method that is applied to the image.
   * @param sc a scanner that reads data to build the kernel
   * @param size the dimension of the square matrix
   * @return a kernel object
   */
  Kernel makeKernel(Scanner sc, int size);
}
