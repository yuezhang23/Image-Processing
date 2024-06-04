# Image Processing

## Features

This program consists of three parts:

- **SRC**: Contains the MVC based design of the program, including the controller, model, and view.
- **TEST**: Tests for `ControllerImplGUI` and `ViewGUI`, using a `MockView` class.
- **RES**: Contains sample images used or generated in the program.

    1. Images created by the program designer and free to use in the program include:
        - mock1.ppm
        - louie.jpeg
        - Screenshot.png

    2. Images downloaded from the web include:
        - cartoon.ppm (source: [USNA](https://www.usna.edu/Users/cs/choi/ic210/project/p01/index.html))
        - treeflower.png (source: [PNGTree](https://pngtree.com/so/flower))

- **README.md**: This file.
- **USEME.md**: Instructions for using the program.

## Folder Structure

### Folder1: src

Contains the MVC based design of the program that includes the controller, model, and view. Design changes and justifications are added to the controller package and the view package. The `Main` class is also changed to implement three ways of running the program. All added contents in the packages are illustrated in the file with the "//" notation.

#### 1. controller package

Contains the following classes and interfaces:

- **ViewListener interface**: Represents all the methods required to implement a controller receives from and responds to the user of the ViewGUI.
- **ControllerImplGUI class**: Implements the `ViewListener` interface with all the methods required to perform image loading, rendering, and saving.
- **Histogram interface and HistogramImpl class**: Represents the method required to implement a histogram from a set of greyscale rendered images.
- **ImageLoader and ImageSaver**: Both classes add `getBufferedImage()`, used as a private method in the prior design.
- **makeKernel() method**: Added to the `ColorChange` interface.

#### 1.1 interface

Contains basic and command interfaces:

- **Controller**: Basic interface that runs the program.
- **Command**: Represents running a command through the controller using a collection of commands available to the user.
- **ImageLoader**: Represents the ImageLoader interface to load an image file and return a state of the Image model.
- **ImageSaver**: Represents the ImageSaver interface to keep writing data from an Image model required for a standard PPM image file.
- **ImageTransform**: Represents the ImageTransform interface that extends the Command interface with all the methods required to render an Image model by greyscale tools and brighten/darken tools.
- **ColorChange**: Represents the ColorChange interface that extends the Command interface with all the methods required to filter and transform an Image model by blur, sharpen, sepia, greyscale matrix.
- **Kernel**: Represents a collection of operations related to a matrix, such as matrix multiplication, scaling on an array by a matrix, etc.

#### 1.2 class

Contains basic and command classes:

- **ControllerImpl**: Implements the `Controller` interface by the `processing()` method.
- Classes are modified to abstract common codes into AbstractLoader and make specifications for PPM loading.
- Classes are modified to abstract common codes into AbstractSaver and specify different output formats.
- **RunBrighten, BrightenTransform**: Implement the Command interface for brightening and darkening operations.
- **AbstractImageTransform**: An abstract class for overall greyscale-related methods.
- **AbstractColorChange**: An abstract class for transformation using matrix-related methods that modifies all the pixels in the image.

#### Additional class

- **KernelImpl**: Implements the Kernel interface.

#### 2. model package

Contains interfaces and classes for the model:

- **PixelState, Pixel, ImageState, Image, ImageDataBase**: Represent operations for reading and modifying Pixel and Image models.
- **PixelImpl, ImageImpl, ImageData**: Implement the above interfaces with all the required methods.

#### 3. view package

Contains classes and interfaces for the view:

- **IView interface**: Represents all methods required to implement a Mock View for testing.
- **Canvas interface**: Represents the canvas of the GUI window that draws the BufferedImage model in response to user interactions.
- **CanvasImpl class**: Implements the Canvas interface that paints and updates the graphic of given BufferedImage during the running of the program.
- **ViewGUI class**: Implements both the ActionListener interface and the IView interface, presenting a visualized user-interactive program of image processing.
- **Chart class**: Implements the canvas that draws a histogram graph from a given hashmap of <value, frequencies> data, greyscale color, and a height scalar to help draw the bar graph.

### How to run

The `Main` accepts three command-line inputs to run the program:

1. `-file path-of-script-file`: Opens the script file, executes it, and then shuts down.
2. `-text`: Opens in an interactive text mode, allowing the user to type the script and execute it one line at a time.
3. `Pressing space key and enter`: Opens the graphical user interface.

