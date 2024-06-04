package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import controller.commands.io.ImageLoader;
import controller.commands.io.ImageSaver;
import controller.commands.io.Load;
import controller.commands.io.LoadPPM;
import controller.commands.io.SaveJPG;
import controller.commands.io.SavePNG;
import controller.commands.io.SavePPM;
import controller.commands.strategy.BrightenTransform;
import controller.commands.strategy.ImageTransform;
import controller.commands.strategy.RunBlue;
import controller.commands.strategy.RunGreen;
import controller.commands.strategy.RunIntensity;
import controller.commands.strategy.RunMax;
import controller.commands.strategy.RunRed;
import controller.commands.strategy.matrixstrategy.Blur;
import controller.commands.strategy.matrixstrategy.ColorChange;
import controller.commands.strategy.matrixstrategy.GreyScaleBlue;
import controller.commands.strategy.matrixstrategy.GreyScaleGreen;
import controller.commands.strategy.matrixstrategy.GreyScaleIntensity;
import controller.commands.strategy.matrixstrategy.GreyScaleLuma;
import controller.commands.strategy.matrixstrategy.GreyScaleRed;
import controller.commands.strategy.matrixstrategy.Kernel;
import controller.commands.strategy.matrixstrategy.Sepia;
import controller.commands.strategy.matrixstrategy.Sharpen;
import model.ImageState;
import view.IView;

/**
 * This class implements the ViewListener interface with all the methods required to
 * perform image loading, rendering and saving.
 */
public class ControllerImplGUI implements ViewListener {
  private IView viewGUI;
  private final Map<String, ColorChange> command = new HashMap<>();
  private final Map<String, ImageTransform> greyscale = new HashMap<>();
  private final Map<String, Color> color = new HashMap<>();
  private Map<String, ImageState> modelMap;
  private Map<String, ImageLoader> mapLoad;
  private Map<String, ImageSaver> mapSave;

  /**
   * Construct a ViewListener by initializing the view and a map of ImageState model and id.
   * @param map a map that couples a number of valid ImageState objects with distinct String IDs
   * @param view the view that displays the program on a GUI
   */
  public ControllerImplGUI(Map<String, ImageState> map, IView view) {
    this.viewGUI = view;
    this.modelMap = map;
    this.mapLoad = new HashMap<>();
    this.mapSave = new HashMap<>();
    viewGUI.addViewListener(this);
    command.put("GreyScale-Red", new GreyScaleRed());
    command.put("GreyScale-Green", new GreyScaleGreen());
    command.put("GreyScale-Blue", new GreyScaleBlue());
    command.put("GreyScale-Luma", new GreyScaleLuma());
    command.put("GreyScale-Intensity", new GreyScaleIntensity());
    command.put("Sepia", new Sepia());
    command.put("Sharpen", new Sharpen());
    command.put("Blur", new Blur());
    mapLoad.put("png", new Load());
    mapLoad.put("jpg", new Load());
    mapLoad.put("jpeg", new Load());
    mapLoad.put("ppm", new LoadPPM());
    mapSave.put("jpg", new SaveJPG());
    mapSave.put("jpeg", new SaveJPG());
    mapSave.put("png", new SavePNG());
    mapSave.put("ppm", new SavePPM());
    greyscale.put("Red", new RunRed());
    greyscale.put("Green", new RunGreen());
    greyscale.put("Blue", new RunBlue());
    greyscale.put("Intensity", new RunIntensity());
    color.put("Red", Color.red);
    color.put("Green", Color.green);
    color.put("Blue", Color.blue);
    color.put("Intensity", Color.gray);
  }

  @Override
  public void run() {
    viewGUI.setVisible(true);
  }

  @Override
  public void handleLoadEvent() {
    String absPath = viewGUI.actLoad();
    if (!absPath.equals("")) {
      String[] info = absPath.split("  ");
      String id = info[0].substring(0, info[0].indexOf("."));
      String format = info[0].substring(info[0].indexOf(".") + 1);
      BufferedImage image = mapLoad.get(format).getBufferedImage(info[1]);
      ImageState model = mapLoad.get(format).loadImage(info[1]);
      modelMap.put(id, model);
      viewGUI.viewUpdate(image, id);
    }
  }

  @Override
  public void handleSaveEvent() {
    String absPath = viewGUI.actSave();
    if (!absPath.equals("")) {
      String[] info = absPath.split("  ");
      ImageState model = modelMap.get(info[0]);
      mapSave.get(info[2]).saveImage(model, info[1] + "." + info[2]);
    }
  }

  @Override
  public void handleRenderEvent(String cm) {
    String commandName = cm.split(" ")[1];
    ImageState source = modelMap.getOrDefault(cm.split(" ")[0], null);
    ColorChange cmd = command.getOrDefault(commandName, null);

    String newID = cm.split(" ")[0] + "-" + commandName;
    ImageState destModel;
    if (commandName.contains("Value")) {
      destModel = new RunMax().transformImage(source);
    }
    else {
      Kernel k = cmd.makeKernel(null, setKernelSize(cm));
      destModel = cmd.colorChange(k, source);
    }
    BufferedImage newBuffer = new SavePNG().getBufferedImage(destModel);
    modelMap.put(newID, destModel);
    viewGUI.viewUpdate(newBuffer, newID);
  }

  private int setKernelSize(String event) {
    if (event.contains("Sharpen")) {
      return 5;
    } else {
      return 3;
    }
  }

  @Override
  public void handleBrighten(String[] info) {
    ImageState source = modelMap.getOrDefault(info[0], null);
    String newID = info[0] + "-" + info[1] + "(" + info[2] + ")";
    int incr = Integer.parseInt(info[2]);
    ImageState destModel = new BrightenTransform(incr).transformImage(source);

    BufferedImage newBuffer = new SavePNG().getBufferedImage(destModel);
    modelMap.put(newID, destModel);
    viewGUI.viewUpdate(newBuffer, newID);
  }

  @Override
  public void handleHistogram(String event) {
    String[] info = event.split("  ");
    ImageState source = modelMap.get(info[0]);
    ImageState newSource = greyscale.get(info[1]).transformImage(source);
    int heightPaint = source.getHeight() * source.getWidth() / 2;
    Map<Integer, Integer> mapToHistogram = new HistogramImpl().makeHistogram(newSource);
    viewGUI.paintHistogram(mapToHistogram,
            color.get(info[1]), heightPaint, Integer.parseInt(info[2]));
  }
}

