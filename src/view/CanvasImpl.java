package view;

import java.awt.*;

import javax.swing.JScrollPane;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class implements the Canvas interface that paints and updates
 * the graphic of given BufferedImage during running of the program.
 */
public class CanvasImpl extends JPanel implements Canvas {
  private final JLabel[] imageLabel;

  /**
   * Construct the canvas functionality by set a set of two JScrollPanels that
   * draw the original image and the rendered one separately.
   */
  public CanvasImpl() {
    imageLabel = new JLabel[2];
    JScrollPane[] imageScrollPane = new JScrollPane[2];
    for (int i = 0; i < imageLabel.length; i++) {
      imageLabel[i] = new JLabel();
      imageScrollPane[i] = new JScrollPane(imageLabel[i]);
      imageScrollPane[i].setBackground(Color.gray);
      imageScrollPane[i].setPreferredSize(new Dimension(200, 500));
      this.add(imageScrollPane[i]);
    }
  }

  @Override
  public void imagePainting(Map<String, BufferedImage> map, String id) {
    imageLabel[0].setIcon(null);
    imageLabel[1].setIcon(null);
    // paint rendered image on the left
    imageLabel[0].setIcon(new ImageIcon(map.get(id)));
    // paint original image on the right
    imageLabel[1].setIcon(new ImageIcon(map.get(id.split("-")[0])));
    this.setVisible(true);
  }

  @Override
  public void singlePainting(Map<String, BufferedImage> map, String id, int side) {
    if (map.get(id) == null) {
      imageLabel[side].setIcon(new ImageIcon());
    } else {
      imageLabel[side].setIcon(new ImageIcon(map.get(id)));
    }
  }
}
