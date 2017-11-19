package com.s3s.ssm.widget.vbadge;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.jdesktop.swingx.JXButton;

import com.s3s.ssm.util.ImageUtils;

public class VBadge extends JXButton implements ActionListener {
  private static final long serialVersionUID = 1L;
  /** This is a smallest background image for badge */
  public static final int SMALL_SIZE = BadgeImage.SMALL_HEIGHT;
  /** This is a medium background image for badge */
  public static final int MEDIUM_SIZE = BadgeImage.MEDIUM_HEIGHT;
  /** This is a largest background image for badge */
  public static final int BIG_SIZE = BadgeImage.BIG_HEIGHT;
  /** This is an icon of component */
  private ImageIcon fIcon;
  /** Default for the badge */
  private ImageIcon fDefaultIcon;
  /** Store disable icon */
  private Icon fDisableIcon = null;
  /** Store enable */
  private boolean fEnable = true;
  /** Store paint border */
  private boolean fPaintBorder = true;
  /** Save focus paint */
  private boolean fFocusPaint = true;
  /** Store background color */
  private Color fBgColor = getBackground();
  /** This is badge text */
  private String fBadgeText;
  /** This is a badge value */
  private Number fBadgeValue = 0;
  /** This is a font for badge value */
  private Font fFont = BadgeImage.DEFAULT_FONT;
  /** The background color of the badge image */
  private Color fBadgeImageColor = null;
  /** Show the badge or not */
  private boolean fShowBadge = false;
  /** Special rollover handler for button in badge mode */
  private VBadgeRolloverListener vBadgeRolloverListener;
  /** image for the number on top */
  private BufferedImage fBadgeImage = null;
  /** Time used for the fading effect */
  private int fDuration = 0;
  /** Interface to repaint the border */
  private static final int REPAINT_INTERVAL = 10;
  /** The delta which is used to change the alpha value every {@link #REPAINT_INTERVAL} */
  private float fDelta = 1f / (fDuration / REPAINT_INTERVAL);
  /** Alpha value for the icon */
  private float fAlpha = 1f;
  /** Fading event timer for handler */
  private Timer fTimer;
  /** Store rollover enable */
  private boolean fRolloverEnabled;
  /** Store rollover listener */
  private VBadgeRolloverListener badgeRollListener = null;

  /**
   * Constructor with badge value, icon, size, and show badge mode.
   * 
   * @param showBadge
   * @param badgeValue
   * @param icon
   */
  public VBadge(boolean showBadge, Integer badgeValue, ImageIcon icon) {
    setIcon(icon);
    setShowBadge(showBadge);
    setBadgeValue(badgeValue);
    badgeRollListener = getRolloverListener();
  }

  /**
   * @see javax.swing.AbstractButton#setRolloverEnabled(boolean)
   * @param enabled
   */
  @Override
  public void setRolloverEnabled(boolean enabled) {
    fRolloverEnabled = enabled;
    if (fShowBadge) {
      super.setRolloverEnabled(false);
      addRollOverListener();
    } else {
      removeMouseListener(badgeRollListener);
      super.setRolloverEnabled(enabled);
    }
  }

  /**
     */
  private void addRollOverListener() {
    boolean contain = false;
    for (MouseListener m : getMouseListeners()) {
      contain |= (m == getRolloverListener());
    }
    if (!contain) {
      addMouseListener(vBadgeRolloverListener);
    }
  }

  /**
   * Sets the fShowBadge to the given parameter
   * 
   * @param showBadge the fShowBadge to set
   */
  public void setShowBadge(boolean showBadge) {
    fShowBadge = showBadge;
    setRolloverEnabled(fRolloverEnabled);
    if (fShowBadge) {
      // setBorderPainted(false);
      // setBackground(null);
      // setDisabledIcon(fIcon);
      // setText("");
      // setFocusPainted(false);
      // setEnabled(true);

      initBadgeImage();
      animateBadge();
    } else {
      setBorderPainted(fPaintBorder);
      setBackground(fBgColor);
      setDisabledIcon(fDisableIcon);
      setText(fBadgeText);
      setFocusPainted(fFocusPaint);
      setEnabled(fEnable);

      super.setIcon(fIcon);
    }
  }

  /**
   * @see javax.swing.JComponent#processMouseEvent(java.awt.event.MouseEvent)
   */
  @Override
  protected void processMouseEvent(MouseEvent e) {
    if (fShowBadge && !fRolloverEnabled && e.getButton() == 0) {
      return;
    }
    super.processMouseEvent(e);
  }

  /**
   * @return boolean
   */
  public boolean isBadgeShowing() {
    return fShowBadge;
  }

  /**
   * @see javax.swing.AbstractButton#setFocusPainted(boolean)
   */
  @Override
  public void setFocusPainted(boolean f) {
    if (fShowBadge) {
      super.setFocusPainted(false);
    } else {
      fFocusPaint = f;
      super.setFocusPainted(fFocusPaint);
    }
  }

  /**
   * @see javax.swing.AbstractButton#setBorderPainted(boolean) set no border paint for the badge mode, no
   *      change for the button mode
   */
  @Override
  public void setBorderPainted(boolean paintBorder) {
    if (fShowBadge) {
      super.setBorderPainted(false);
    } else {
      this.fPaintBorder = paintBorder;
      super.setBorderPainted(paintBorder);
    }
  }

  /**
   * @see javax.swing.JComponent#setBackground(java.awt.Color) <br>
   *      set background color for the badge mode and no change for the button mode
   */
  @Override
  public void setBackground(Color bg) {
    if (fShowBadge) {
      super.setBackground(null);
    } else {
      fBgColor = bg;
      super.setBackground(fBgColor);
    }
  }

  /**
   * @see javax.swing.AbstractButton#setDisabledIcon(javax.swing.Icon) <br>
   *      set disable icon to null in badge mode, no change for the button mode
   */
  @Override
  public void setDisabledIcon(Icon disableIcon) {
    if (fShowBadge) {
      super.setDisabledIcon(null);
    } else {
      this.fDisableIcon = disableIcon;
      super.setDisabledIcon(disableIcon);
    }
  }

  /**
   * Sets the enable to the given parameter
   * 
   * @param enable the enable to set
   */
  @Override
  public void setEnabled(boolean enable) {
    if (fShowBadge) {
      super.setEnabled(true);
    } else {
      this.fEnable = enable;
      super.setEnabled(enable);
    }
  }

  /**
   * @see javax.swing.AbstractButton#setText(java.lang.String) <br>
   *      set disable icon to null in badge mode
   */
  @Override
  public void setText(String text) {
    if (fShowBadge) {
      super.setText("");
    } else {
      fBadgeText = text;
      super.setText(text);
    }
  }

  /**
   * Change the icon for the current badge.
   * 
   * @param icon
   */
  @Override
  public void setIcon(Icon icon) {
    fIcon = new ImageIcon(ImageUtils.iconToImage(icon));

    if (fShowBadge) {
      animateBadge();
    } else {
      super.setIcon(icon);
    }
  }

  /**
   * init rollover listener for the badge mode
   * 
   * @return RolloverListener
   */
  private VBadgeRolloverListener getRolloverListener() {
    if (vBadgeRolloverListener == null) {
      vBadgeRolloverListener = new VBadgeRolloverListener();
    }
    return vBadgeRolloverListener;
  }

  /**
   * init badge image if show badge mode is set
   */
  private void initBadgeImage() {
    if (fBadgeImage == null) {
      fBadgeImage = BadgeImage.createBadge(1 + BadgeImage.ROUND_RECT_PADDING, 1, BadgeImage.CIRCLE);
    }
  }

  /**
   * init timer if show badge mode is set
   * 
   * @return timer
   */
  private Timer getTimer() {
    if (fTimer == null) {
      fTimer = new Timer(REPAINT_INTERVAL, this);
    }
    return fTimer;
  }

  /**
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (fShowBadge) {
      // drawIcon(g, fIcon);
      g.setFont(fFont);
      drawBadgeNumberField(g);
    }
  }

  /**
   * Draw the badge
   * 
   * @param g
   */
  private void drawBadgeNumberField(Graphics g) {
    fBadgeImageColor = (fBadgeImageColor != null) ? fBadgeImageColor : getParent().getBackground();
    String badgeString = fBadgeValue + "";
    int numOfChars = badgeString.length();
    int height = BadgeImage.ROUND_RECT_PADDING;
    if (numOfChars <= 1) {
      height += MEDIUM_SIZE;
      fBadgeImage = BadgeImage.createBadge(height + BadgeImage.ROUND_RECT_PADDING, height, BadgeImage.CIRCLE);
    } else if (numOfChars <= 5) {
      height += MEDIUM_SIZE;
      fBadgeImage = BadgeImage.createBadge(height * 2 + BadgeImage.ROUND_RECT_PADDING, height,
              BadgeImage.ROUND_RECT);
    } else {
      height += BIG_SIZE;
      fBadgeImage = BadgeImage.createBadge(height * 2 + BadgeImage.ROUND_RECT_PADDING, height,
              BadgeImage.ROUND_RECT);
    }

    ((BadgeImage) fBadgeImage).setText(badgeString).setFont(fFont).setBackgroundColor(fBadgeImageColor);
    ((BadgeImage) fBadgeImage).build();

    // right alignment along with icon
    int x = (getWidth() + fIcon.getIconWidth()) / 2 - ((BadgeImage) fBadgeImage).getActualWidth() + 20;
    Graphics2D g2d = (Graphics2D) g;
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, fAlpha));
    g2d.drawImage(fBadgeImage, x, 2, this);
  }

  /**
   * Fading listener
   */
  @SuppressWarnings("unused")
  @Override
  public void actionPerformed(ActionEvent e) {
    fAlpha += fDelta;
    Timer t = getTimer();
    if (fAlpha <= 0) {
      fAlpha = 0f;
      t.stop();
      super.setIcon(fIcon);
    } else if (fAlpha >= 1) {
      fAlpha = 1f;
      t.stop();
      super.setIcon(fIcon);
    }
    repaint();
  }

  /**
   * Changes the background color of the badge to the given color.
   * 
   * @param color
   */
  public void setBadgeColor(Color color) {
    fBadgeImageColor = color;
    repaint(); // call paintComponent to drawBadge
  }

  /**
   * @return colour
   */
  public Color getBadgeColor() {
    return fBadgeImageColor;
  }

  /**
   * @param badgeValue
   */
  public void setBadgeValue(Number badgeValue) {
    fBadgeValue = (badgeValue == null) ? 0 : badgeValue;
    repaint();
  }

  /**
   * @return badge value
   */
  public Number getBadgeValue() {
    return fBadgeValue;
  }

  /**
   * @param font
   */
  public void setFontOfBadge(Font font) {
    fFont = font;
    repaint();
  }

  /**
   * @return font
   */
  public Font getBadgeFont() {
    return fFont;
  }

  /**
   * Change the badge icon for the current badge.
   * 
   */
  private void animateBadge() {
    fAlpha = 0f;
    getTimer().start();
  }

  /**
   * Returns the speed in mili second
   * 
   * @return the speed
   */
  public int getAnimationDuration() {
    return fDuration;
  }

  /**
   * Sets the speed in mili second of animation on badge mode
   * 
   * @param duration the speed to set
   */
  public void setAnimationDuration(int duration) {
    this.fDuration = duration;
    fDelta = (duration == 0) ? 1 : 1f / (duration / REPAINT_INTERVAL);
  }

  /**
   * Handling rollover effect for Badge mode
   */
  class VBadgeRolloverListener extends MouseAdapter {

    /**  */
    private ImageIcon tmpIcon;

    /**
     * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {
      if (fRolloverEnabled && getRolloverIcon() != null && !SwingUtilities.isLeftMouseButton(e)) {
        tmpIcon = fIcon;
        fIcon = new ImageIcon(ImageUtils.iconToImage(getRolloverIcon()));
        animateBadge();
      }
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
     */
    @SuppressWarnings("unused")
    @Override
    public void mouseExited(MouseEvent e) {
      if (fRolloverEnabled && getRolloverIcon() != null) {
        fIcon = tmpIcon;
        animateBadge();
      }
    }
  }

}