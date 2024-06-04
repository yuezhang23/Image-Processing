package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;
import controller.ViewListener;

/**
 * This class implements both the ActionListener interface and the IView interface
 * that together present a visualized user-interactive program of image processing.
 */
public class ViewGUI extends JFrame implements ActionListener, IView {
  private JTextArea textArea;
  private JPanel idPanel1;
  private CanvasImpl canvas;
  private Chart chart;
  private Map<String, BufferedImage> bufferMap;
  private JComboBox<String> comboboxID;
  private JComboBox<String> comboID0;
  private JComboBox<String> comboID1;
  private JComboBox<String> scaleFactor;
  private JComboBox<String> comboFormat;
  private JComboBox<String> comboHistogram;
  private JComboBox<String> comboboxTransform;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JLabel hisDisplay;
  private JLabel renderDisplay;
  private JLabel colorTransform;
  private JLabel renderID;
  private JLabel saveID;
  private List<ViewListener> subscribers = new ArrayList<>();

  /**
   * Construct all the functions on the main panel of the GUI window.
   */
  public ViewGUI() {

    // main panel
    super();
    setTitle("Image Processing");
    setSize(800, 800);
    JPanel viewPanel = new JPanel();
    viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.PAGE_AXIS));
    this.add(viewPanel);
    this.bufferMap = new HashMap<>();
    this.comboboxID = new JComboBox<>();
    this.comboID0 = new JComboBox<>();
    this.comboID1 = new JComboBox<>();
    this.comboFormat = new JComboBox<>();
    this.comboHistogram = new JComboBox<>();
    this.comboboxTransform = new JComboBox<>();
    this.scaleFactor = new JComboBox<>();

    // canvas for image view
    canvas = new CanvasImpl();
    canvas.setBorder(BorderFactory.createTitledBorder("image view"));
    canvas.setLayout(new GridLayout(1, 0, 10, 10));
    viewPanel.add(canvas);
    canvas.setVisible(false);

    // tool panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    viewPanel.add(new JScrollPane(mainPanel));

    // model ID - ID under each canvas window
    idPanel1 = new JPanel();
    idPanel1.setLayout(new GridLayout(1, 0, 10, 10));
    JPanel[] comboPanel = new JPanel[2];
    mainPanel.add(idPanel1);
    for (int i = 0; i < 2; i++) {
      comboPanel[i] = new JPanel();
      comboPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
      idPanel1.add(comboPanel[i]);
    }
    comboPanel[0].setBorder(BorderFactory.createTitledBorder("Left Image"));
    comboID0.addItem("< Select >");
    comboPanel[0].add(comboID0);
    comboPanel[1].setBorder(BorderFactory.createTitledBorder("Right Image"));
    comboID1.addItem("< Select >");
    comboPanel[1].add(comboID1);
    saveID = new JLabel("Save ID: < Select >");
    comboPanel[0].add(saveID);

    // dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    // dialog boxes-Load
    JPanel loadPanel = new JPanel();
    loadPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    dialogBoxesPanel.add(loadPanel);
    JButton fileOpenButton = new JButton(" Load ");
    fileOpenButton.setActionCommand("Load file");
    fileOpenButton.addActionListener(this);
    loadPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File will appear here");
    loadPanel.add(fileOpenDisplay);

    // dialog boxes-Save
    JPanel savePanel = new JPanel();
    savePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    dialogBoxesPanel.add(savePanel);
    JButton fileSaveButton = new JButton(" Save ");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(this);
    savePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File will appear here");
    savePanel.add(fileSaveDisplay);

    // dialog boxes-save format
    JPanel fPanel = new JPanel();
    fPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    dialogBoxesPanel.add(fPanel);
    comboFormat.setBorder(BorderFactory.createTitledBorder("Save Format"));
    comboFormat.setPreferredSize(new Dimension(150, 40));
    String[] opts = {"< Select >", "ppm", "png", "jpeg", "jpg"};
    for (String op : opts) {
      comboFormat.addItem(op);
    }
    fPanel.add(comboFormat);

    // dialog boxes - Histogram
    JPanel hisPanel = new JPanel();
    hisPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    dialogBoxesPanel.add(hisPanel);
    JButton histogramButton = new JButton(" Histogram ");
    histogramButton.setActionCommand("Draw Histogram");
    histogramButton.addActionListener(this);
    hisPanel.add(histogramButton);

    // dialog boxes - histogram selection
    JPanel hPanel = new JPanel();
    hPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    dialogBoxesPanel.add(hPanel);
    // set plot off button
    JButton offButton = new JButton(" Plot Off ");
    offButton.setActionCommand("Plot-off");
    offButton.addActionListener(this);
    hisPanel.add(offButton);

    // set height scalar
    scaleFactor.setBorder(BorderFactory.createTitledBorder("Histogram Scalar"));
    scaleFactor.setPreferredSize(new Dimension(150, 40));
    String[] optionS = {"20", "10", "5", "1", "30", "40"};
    for (String op : optionS) {
      scaleFactor.addItem(op);
    }
    hPanel.add(scaleFactor);

    // set component color to draw histogram
    comboHistogram.setBorder(BorderFactory.createTitledBorder("Component Setting"));
    comboHistogram.setPreferredSize(new Dimension(150, 40));
    String[] optionH = {"< Select >", "Red", "Green", "Blue", "Intensity"};
    for (String op : optionH) {
      comboHistogram.addItem(op);
    }
    hPanel.add(comboHistogram);
    hisDisplay = new JLabel("Histogram Plotted: ");
    hPanel.add(hisDisplay);

    // dialog boxes - histogram plot
    chart = new Chart();
    chart.setBorder(BorderFactory.createTitledBorder("Histogram Plot"));
    chart.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    dialogBoxesPanel.add(chart);
    chart.setVisible(false);

    // Render Options
    JPanel comboboxPanel = new JPanel();
    comboboxPanel.setBorder(BorderFactory.createTitledBorder("Render Options"));
    comboboxPanel.setLayout(new BoxLayout(comboboxPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(comboboxPanel);

    // Render Options - Render
    JPanel messageDialogPanel = new JPanel();
    messageDialogPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    comboboxPanel.add(messageDialogPanel);
    JButton messageButton = new JButton(" Render ");
    messageButton.setActionCommand("Render");
    messageButton.addActionListener(this);
    messageDialogPanel.add(messageButton);
    renderDisplay = new JLabel("Render information will appear here");
    messageDialogPanel.add(renderDisplay);

    // Render Options - image ID
    JPanel idPanel = new JPanel();
    idPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    comboboxPanel.add(idPanel);
    comboboxID.setBorder(BorderFactory.createTitledBorder("Render ID"));
    comboboxID.setPreferredSize(new Dimension(150, 40));
    comboboxID.addItem("< Select >");
    idPanel.add(comboboxID);
    renderID = new JLabel("< Select >");
    idPanel.add(renderID);

    // Render Options - color transform/filter
    JPanel transPanel = new JPanel();
    transPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    comboboxPanel.add(transPanel);
    comboboxTransform.setBorder(BorderFactory.createTitledBorder("Color Transform / Filtering"));
    comboboxTransform.setPreferredSize(new Dimension(200, 40));
    String[] options = {
        "< Select >", "Brighten", "Sepia", "GreyScale-Red", "GreyScale-Green",
        "GreyScale-Blue", "GreyScale-Luma", "GreyScale-Intensity",
        "GreyScale-Value", "Blur", "Sharpen" };
    for (String option : options) {
      comboboxTransform.addItem(option);
    }
    transPanel.add(comboboxTransform);
    colorTransform = new JLabel("< Select >");
    transPanel.add(colorTransform);

    // Render Options - Brighten
    JPanel brPanel = new JPanel();
    brPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    comboboxPanel.add(brPanel);
    textArea = new JTextArea(1, 12);
    textArea.setBorder(BorderFactory.createTitledBorder("Brighten Increment"));
    brPanel.add(textArea);
    textArea.setEnabled(false);

    // set Action event
    comboID0.setActionCommand("Left View");
    comboID0.addActionListener(this);
    comboID1.setActionCommand("Right View");
    comboID1.addActionListener(this);
    comboboxID.setActionCommand("Option");
    comboboxID.addActionListener(this);
    comboboxTransform.setActionCommand("Option");
    comboboxTransform.addActionListener(this);
    comboFormat.setActionCommand("Option");
    comboFormat.addActionListener(this);
    this.setFocusable(true);
  }

  @Override
  public String actLoad() {
    final JFileChooser choice = new JFileChooser(".");
    int suffix = choice.showOpenDialog(ViewGUI.this);
    File f = new File("");
    if (suffix == JFileChooser.APPROVE_OPTION) {
      f = choice.getSelectedFile();
    }
    String path = f.getName() + "  " + f.getAbsolutePath();
    return checkLoadFormat(path, f.getName());
  }
  
  private String checkLoadFormat(String path, String name) {
    List<String> opts = List.of("ppm", "png", "jpeg", "jpg");
    if (name.isEmpty()) {
      return "";
    } else if (!opts.contains(name.substring(name.indexOf(".") + 1))) {
      displayErrorMessage("supported file extension: ppm/png/jpeg/jpg");
      return "";
    } else {
      fileOpenDisplay.setText("Loaded: " + name);
      return path;
    }
  }

  @Override
  public String actSave() {
    String destID = Objects.requireNonNull(comboID0.getSelectedItem()).toString();
    String destType = Objects.requireNonNull(comboFormat.getSelectedItem()).toString();

    if (destID.equals("< Select >") || destType.equals("< Select >")) {
      displayErrorMessage("Select both Save format and Save ID");
      return "";
    }
    final JFileChooser choice = new JFileChooser(".");
    int suffix = choice.showSaveDialog(ViewGUI.this);
    File f = new File("");
    if (suffix == JFileChooser.APPROVE_OPTION) {
      f = choice.getSelectedFile();
    }
    String path = destID + "  " + f.getAbsolutePath() + "  " + destType;
    return checkSavePath(f.getName(), path, destType);
  }

  private String checkSavePath(String name, String path, String type) {
    if (name.isEmpty()) {
      return "";
    }
    else if (name.contains(".") && !name.substring(name.indexOf(".") + 1).equals(type)) {
      displayErrorMessage("format is not the same as selected");
      return "";
    }
    else {
      fileSaveDisplay.setText("Saved: " + name + "." + type);
      return path;
    }
  }

  private void displayErrorMessage(String message) {
    Objects.requireNonNull(message);
    JOptionPane.showMessageDialog(ViewGUI.this, message,
            "Warning", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void addViewListener(ViewListener subscriber) {
    Objects.requireNonNull(subscriber);
    this.subscribers.add(subscriber);
  }

  @Override
  public void getFrameFocus() {
    this.requestFocus();
  }

  @Override
  public void paintHistogram(Map<Integer, Integer> mapToHistogram,
                             Color color, int height, int scalar) {
    chart.plotChart(mapToHistogram, color, height, scalar);
    chart.setVisible(true);
  }


  @Override
  public void viewUpdate(BufferedImage image, String id) {
    Objects.requireNonNull(image);
    Objects.requireNonNull(id);
    if (!bufferMap.containsKey(id)) {
      comboboxID.addItem(id);
      comboID0.addItem(id);
      comboID1.addItem(id);
    }
    // add new image to view
    bufferMap.put(id, image);
    // set original image id
    comboID1.setSelectedItem(id.split("-")[0]);
    // set new image id
    comboID0.setSelectedItem(id);
    canvas.imagePainting(bufferMap, id);
  }

  private void emitLoadEvent() {
    for (ViewListener listener : subscribers) {
      listener.handleLoadEvent();
    }
  }

  private void emitSaveEvent() {
    for (ViewListener listener : subscribers) {
      listener.handleSaveEvent();
    }
  }

  private void emitDrawHistogram(String info) {
    if (info.contains("< Select >")) {
      displayErrorMessage("Model or Pixel component not selected");
    } else {
      for (ViewListener listener : subscribers) {
        listener.handleHistogram(info);
      }
    }
  }

  private void emitRenderEvent(String event) {
    if (event.contains("< Select >")) {
      displayErrorMessage("Render Options should all be selected");
    } else {
      for (ViewListener listener : subscribers) {
        listener.handleRenderEvent(event);
      }
    }
  }

  private void emitBrightenEvent(String event) {
    String[] info = event.split(" ");
    if (checkValidBrighten(event)) {
      for (ViewListener listener : subscribers) {
        listener.handleBrighten(info);
      }
    }
  }

  private boolean checkValidBrighten(String event) {
    boolean result = false;
    String[] info = event.split(" ");
    if (event.contains("< Select >") || info.length < 3) {
      displayErrorMessage("Brighten Options should all be selected");
    }
    else if (info.length > 3 && !info[3].equals(" ")) {
      displayErrorMessage("one value for Brighten increment");
    } else {
      try {
        int incr = Integer.parseInt(info[2]);
        if (incr < -255 || incr > 255) {
          displayErrorMessage("valid increment range (-255 ~ 255)");
        } else {
          result = true;
        }
      } catch (NumberFormatException e) {
        displayErrorMessage("brighten increment should be in integer");
      }
    }
    return result;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    switch (event.getActionCommand()) {
      case "Load file": {
        emitLoadEvent();
        this.getFrameFocus();
      }
      break;

      case "Save file": {
        emitSaveEvent();
        this.getFrameFocus();
      }
      break;

      case "Draw Histogram": {
        String info = comboID1.getSelectedItem().toString().split("-")[0]
                + "  " + comboHistogram.getSelectedItem()
                + "  " + scaleFactor.getSelectedItem();
        emitDrawHistogram(info);
        hisDisplay.setText("Histogram Plotted: "
                + comboID1.getSelectedItem().toString().split("-")[0]
                + " (Greyscale-" + comboHistogram.getSelectedItem() + ")");
        this.getFrameFocus();
      }
      break;

      case "Plot-off": {
        chart.setVisible(false);
        hisDisplay.setText("Histogram Plotted: ");
        this.getFrameFocus();
      }
      break;

      case "Render": {
        String info = comboboxID.getSelectedItem()
                + " " + comboboxTransform.getSelectedItem();
        if (comboboxTransform.getSelectedItem().equals("Brighten")) {
          info += " " + textArea.getText();
          emitBrightenEvent(info);
        } else {
          emitRenderEvent(info);
        }
        renderDisplay.setText("Done: " + info);
        this.getFrameFocus();
      }
      break;

      case "Left View": {
        String in = Objects.requireNonNull(comboID0.getSelectedItem()).toString();
        saveID.setText("Save ID: " + comboID0.getSelectedItem());
        canvas.singlePainting(bufferMap, in, 0);
        this.getFrameFocus();
      }
      break;

      case "Right View": {
        String in = Objects.requireNonNull(comboID1.getSelectedItem()).toString();
        canvas.singlePainting(bufferMap, in, 1);
        this.getFrameFocus();
      }
      break;

      case "Option": {
        renderID.setText("ID Selected: " + comboboxID.getSelectedItem());
        colorTransform.setText("Render selected: " + comboboxTransform.getSelectedItem());
        if (comboboxTransform.getSelectedItem().equals("Brighten")) {
          textArea.setEnabled(true);
        } else {
          textArea.setText("");
          textArea.setEnabled(false);
        }
        this.getFrameFocus();
      }
      break;

      default: {
        displayErrorMessage("no supported operation");
      }
      break;
    }
  }
}
