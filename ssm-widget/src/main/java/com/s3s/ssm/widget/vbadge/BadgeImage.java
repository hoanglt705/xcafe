package com.s3s.ssm.widget.vbadge;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import com.s3s.ssm.util.ColorEnum;

/**
 * This class provides the badge image with number which display at the top-right corner.
 * 
 * @since Feb 17, 2011
 */
public class BadgeImage extends BufferedImage {
  /** This the circle type of badge */
  public static final int CIRCLE = 1;

  /** This the round-rectangle type of badge */
  public static final int ROUND_RECT = 2;

  /** The size of badge's shadow, which is 4px per axis, i.e. 2px per side */
  public static final int ROUND_RECT_PADDING = 2;

  /** The height of the sample image for 'big' badge */
  public static final int BIG_HEIGHT = 32;

  /** The height of the sample image for 'medium' badge. Default size now. */
  public static final int MEDIUM_HEIGHT = 24;

  /** The height of the sample image for 'small' badge */
  public static final int SMALL_HEIGHT = 16;

  /**
   * The edge size of the rounded rectangle. It is the (Diameter without paddings)/2
   */
  public static final int ROUND_RECT_MEDIUM_EDGE = MEDIUM_HEIGHT / 2 - ROUND_RECT_PADDING;

  /** Default font for badge text */
  public static final Font DEFAULT_FONT = new Font("Courier New", Font.BOLD, 14);

  /** This is a text of the badge */
  private String fText;

  /** This is a font of the badge */
  private Font fFont;

  /** This is a background image */
  private BufferedImage fBackgroundImage;

  /** The background color of the image, which is the contrast of text color */
  private Color fBackgroundColor;

  /**
   * Constructor
   * 
   * @param width
   * @param height
   * @param type
   */
  public BadgeImage(int width, int height, int type) {
    super(width, height, type);
  }

  /**
   * @param width
   * @param height
   * @param type
   * @return <code>Badge</code>
   */
  public static BadgeImage createBadge(int width, int height, int type) {

    BadgeImage image = null;
    if (type == CIRCLE) {
      image = new CircleBagdeImage(width, height);
    } else {
      image = new RoundRectBadgeImage(width, height);
    }

    return image;
  }

  /**
   * Sets background color for the badge, which is the contrast of text color
   * 
   * @param color
   * @return BadgeImage
   */
  public BadgeImage setBackgroundColor(Color color) {
    fBackgroundColor = color;
    return this;
  }

  /**
   * @param text
   * @return <code>Badge</code>
   */
  public BadgeImage setText(String text) {
    fText = text;
    return this;
  }

  /**
   * @param font
   * @return <code>Badge</code>
   */
  public BadgeImage setFont(Font font) {
    fFont = font;
    return this;
  }

  /**
   * @return actual width when background is drawn
   */
  public int getActualWidth() {
    return fBackgroundImage.getWidth();
  }

  /**
   * Build function for this builder
   */
  public void build() {
    Graphics g = createGraphics();
    createImage(g);
    g.drawImage(fBackgroundImage, 0, 0, null);
    drawText(g);
    g.dispose();
  }

  /**
   * @return background image which is related to input height
   */
  protected BufferedImage loadBackgroundImage() {
    if (Color.WHITE.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.white");
    }
    if (Color.LIGHT_GRAY.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.lightGray");
    }
    if (Color.GRAY.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.gray");
    }
    if (Color.DARK_GRAY.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.darkGray");
    }
    if (Color.BLACK.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.black");
    }
    if (Color.RED.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.red");
    }
    if (Color.PINK.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.pink");
    }
    if (Color.ORANGE.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.orange");
    }
    if (Color.YELLOW.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.yellow");
    }
    if (Color.GREEN.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.green");
    }
    if (Color.MAGENTA.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.magenta");
    }
    if (Color.CYAN.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.cyan");
    }
    if (Color.BLUE.equals(fBackgroundColor)) {
      return (BufferedImage) Resources.getResources("images.badge.24.blue");
    }
    return (BufferedImage) Resources.getResources("images.badge.24");
  }

  /**
   * @return size of each rounded edge of background image
   */
  protected int calculateSizeOfEdges() {
    return ROUND_RECT_MEDIUM_EDGE;
  }

  /**
   * Create badge image which is round rectangle or circle
   * 
   * @param g
   */
  protected void createImage(Graphics g) {
    fBackgroundImage = null;
  }

  /**
   * @param g
   */
  private void drawText(Graphics g) {
    g.setColor(ColorEnum.getContrastNativeColor(fBackgroundColor));
    g.setFont(fFont);

    measureAndPaintText(g);
  }

  /**
   * @param g
   */
  protected void measureAndPaintText(Graphics g) {

  }

  /**
   * @param graphics
   * @return Width in pixels of 1 character for graphics rendering
   */
  protected int getDefaultCharacterWidth(Graphics graphics) {
    String defaultCharacterToMeasure = "8";

    return graphics.getFontMetrics().stringWidth(defaultCharacterToMeasure);
  }

  protected String getText() {
    return fText;
  }

  protected BufferedImage getBackgroundImage() {
    return fBackgroundImage;
  }

  protected void setBackgroundImage(BufferedImage backgroundImage) {
    fBackgroundImage = backgroundImage;
  }

}

/**
 * @since Feb 18, 2011
 */
class RoundRectBadgeImage extends BadgeImage {
  /**
   * Constructor
   * 
   * @param width
   * @param height
   */
  public RoundRectBadgeImage(int width, int height) {
    super(width, height, Transparency.TRANSLUCENT);
  }

  /**
   * @see BadgeImage#createImage(java.awt.Graphics)
   */
  @Override
  protected void createImage(Graphics g) {
    FontMetrics fm = g.getFontMetrics();
    int msgWidth = fm.stringWidth(getText());
    int widthAlignment = getDefaultCharacterWidth(g) - getText().length();

    BufferedImage backgroundImage = loadBackgroundImage();
    int msgX = calculateSizeOfEdges();

    BufferedImage leftSideClipped = backgroundImage.getSubimage(0, 0, msgX, backgroundImage.getHeight());
    BufferedImage centerClipped = backgroundImage.getSubimage(msgX, 0, msgWidth - widthAlignment,
            backgroundImage.getHeight());
    BufferedImage rightSideClipped = backgroundImage.getSubimage(backgroundImage.getWidth() - msgX
            - ROUND_RECT_PADDING * 2, 0, msgX + ROUND_RECT_PADDING * 2, backgroundImage.getHeight());

    int imageWidth = leftSideClipped.getWidth() + centerClipped.getWidth() + rightSideClipped.getWidth();
    setBackgroundImage(new BufferedImage(imageWidth, backgroundImage.getHeight(), Transparency.TRANSLUCENT));

    Graphics g2 = getBackgroundImage().createGraphics();
    g2.drawImage(leftSideClipped, 0, 0, null);
    g2.drawImage(centerClipped, leftSideClipped.getWidth(), 0, null);
    g2.drawImage(rightSideClipped, centerClipped.getWidth() + leftSideClipped.getWidth(), 0, null);

    g2.dispose();
  }

  /**
   * Paint the text in the badge
   * 
   * @param g
   */
  @Override
  protected void measureAndPaintText(Graphics g) {
    // Get measures needed to center the message
    FontMetrics fm = g.getFontMetrics();

    // How many pixels wide is the string
    int msgWidth = fm.stringWidth(getText());

    int msgX = getBackgroundImage().getWidth() - msgWidth - (calculateSizeOfEdges());
    int msgY = getBackgroundImage().getHeight() / 2 + (fm.getAscent() - fm.getDescent()) / 2
            - ROUND_RECT_PADDING / 2;

    g.drawString(getText(), msgX, msgY);
  }

}

/**
 * @since Feb 18, 2011
 */
class CircleBagdeImage extends BadgeImage {
  /**
   * Constructor
   * 
   * @param width
   * @param height
   */
  public CircleBagdeImage(int width, int height) {
    super(width, height, Transparency.TRANSLUCENT);
  }

  /**
   * @see BadgeImage#createImage(java.awt.Graphics)
   */
  @SuppressWarnings("unused")
  @Override
  protected void createImage(Graphics g) {
    BufferedImage backgroundImage = loadBackgroundImage();

    int msgX = calculateSizeOfEdges();

    BufferedImage leftSideClipped = backgroundImage.getSubimage(0, 0, msgX, backgroundImage.getHeight());
    BufferedImage rightSideClipped = backgroundImage.getSubimage(backgroundImage.getWidth() - msgX
            - ROUND_RECT_PADDING * 2, 0, msgX + ROUND_RECT_PADDING * 2, backgroundImage.getHeight());

    int imageWidth = leftSideClipped.getWidth() + rightSideClipped.getWidth();
    setBackgroundImage(new BufferedImage(imageWidth, backgroundImage.getHeight(), Transparency.TRANSLUCENT));

    Graphics g2 = getBackgroundImage().createGraphics();
    g2.drawImage(leftSideClipped, 0, 0, null);
    g2.drawImage(rightSideClipped, leftSideClipped.getWidth(), 0, null);

    g2.dispose();
  }

  /**
   * Paint the text in the badge
   * 
   * @param g
   */
  @Override
  protected void measureAndPaintText(Graphics g) {
    // Get measures needed to center the message
    FontMetrics fm = g.getFontMetrics();

    // How many pixels wide is the string
    int msgWidth = fm.stringWidth(getText());

    int msgX = getBackgroundImage().getWidth() - msgWidth - calculateSizeOfEdges();
    int msgY = getBackgroundImage().getHeight() / 2 + (fm.getAscent() - fm.getDescent()) / 2
            - ROUND_RECT_PADDING / 2;

    g.drawString(getText(), msgX, msgY);
  }

}
