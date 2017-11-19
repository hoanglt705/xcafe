package com.s3s.ssm.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @since Nov 18, 2010
 */
public class ImageUtils {

  /**
   * Convert an Icon to an Image
   * 
   * @param icon the icon
   * @return the Image
   */
  public static Image iconToImage(Icon icon) {
    if (icon instanceof ImageIcon) {
      return ((ImageIcon) icon).getImage();
    }
    int w = icon.getIconWidth();
    int h = icon.getIconHeight();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    GraphicsConfiguration gc = gd.getDefaultConfiguration();
    BufferedImage image = gc.createCompatibleImage(w, h);
    Graphics2D g = image.createGraphics();
    icon.paintIcon(null, g, 0, 0);
    g.dispose();
    return image;
  }

  /**
   * Create a buffered image from image source
   * 
   * @param image
   * @return <code>BufferedImage</code>
   */
  public static BufferedImage createBufferedImage(Image image) {

    if (image instanceof BufferedImage) {
      return (BufferedImage) image;
    }

    // This code ensures that all the pixels in the image are loaded
    Image img = new ImageIcon(image).getImage();

    // Determine if the image has transparent pixels
    boolean hasAlpha = hasAlpha(img);

    // Create a buffered image with a format that's compatible with the screen
    BufferedImage bimage = null;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    try {
      // Determine the type of transparency of the new buffered image
      int transparency = Transparency.OPAQUE;
      if (hasAlpha) {
        transparency = Transparency.BITMASK;
      }

      // Create the buffered image
      GraphicsDevice gs = ge.getDefaultScreenDevice();
      GraphicsConfiguration gc = gs.getDefaultConfiguration();
      bimage = gc.createCompatibleImage(img.getWidth(null), img.getHeight(null), transparency);
    } catch (HeadlessException e) {
    } // No screen

    if (bimage == null) {
      // Create a buffered image using the default color model
      int type = BufferedImage.TYPE_INT_RGB;
      if (hasAlpha) {
        type = BufferedImage.TYPE_INT_ARGB;
      }
      bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
    }

    // Copy image to buffered image
    Graphics g = bimage.createGraphics();

    // Paint the image onto the buffered image
    g.drawImage(image, 0, 0, null);
    g.dispose();

    return bimage;
  }

  /**
   * Check whether image has alpha or not.
   * 
   * @param image
   * @return <code>true</code> or <code>false</code>>
   */
  public static boolean hasAlpha(Image image) {
    // If buffered image, the color model is readily available
    if (image instanceof BufferedImage) {
      return ((BufferedImage) image).getColorModel().hasAlpha();
    }

    // Use a pixel grabber to retrieve the image's color model;
    // grabbing a single pixel is usually sufficient
    PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
    try {
      pg.grabPixels();
    } catch (InterruptedException e) {
    }

    return (pg.getColorModel() == null) ? false : pg.getColorModel().hasAlpha();
  }

  /**
   * @param srcImg
   * @param w
   * @param h
   * @return image with specific width and height
   */
  public static Image getScaledImage(Image srcImg, int w, int h) {

    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    return resizedImg;
  }

  /**
   * Convenience method that returns a scaled instance of the provided {@code BufferedImage}.
   * 
   * @param img the original image to be scaled
   * @param targetWidth the desired width of the scaled instance, in pixels
   * @param targetHeight the desired height of the scaled instance, in pixels
   * @param hint one of the rendering hints that corresponds to {@code RenderingHints.KEY_INTERPOLATION} (e.g.
   *          {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
   *          {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
   *          {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC} )
   * @param higherQuality if true, this method will use a multiple-step scaling technique that provides higher
   *          quality than the usual one-step technique (only useful in down-scaling cases, where
   *          {@code targetWidth} or {@code targetHeight} is smaller than the original dimensions, and
   *          generally only when the {@code BILINEAR} hint is specified)
   * @return a scaled version of the original {@code BufferedImage}
   */
  public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight,
          Object hint, boolean higherQuality) {
    int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
            : BufferedImage.TYPE_INT_ARGB;
    BufferedImage ret = img;
    int w, h;
    if (higherQuality) {
      // Use multi-step technique: start with original size, then
      // scale down in multiple passes with drawImage()
      // until the target size is reached
      w = img.getWidth();
      h = img.getHeight();
    } else {
      // Use one-step technique: scale directly from original
      // size to target size with a single drawImage() call
      w = targetWidth;
      h = targetHeight;
    }

    do {
      if (higherQuality && w > targetWidth) {
        w /= 2;
        if (w < targetWidth) {
          w = targetWidth;
        }
      }

      if (higherQuality && h > targetHeight) {
        h /= 2;
        if (h < targetHeight) {
          h = targetHeight;
        }
      }

      BufferedImage tmp = new BufferedImage(w, h, type);
      Graphics2D g2 = tmp.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
      g2.drawImage(ret, 0, 0, w, h, null);
      g2.dispose();

      ret = tmp;
    } while (w != targetWidth || h != targetHeight);

    return ret;
  }

  /**
   * @param filePath
   * @return an image from file path
   */
  public static BufferedImage getImageFromFile(String filePath) {
    Image img = null;
    try {
      img = ImageIO.read(ImageUtils.class.getResource(filePath));
    } catch (IOException ex) {
      throw new RuntimeException("Can not get image:" + ex.toString());
    }

    return createBufferedImage(img);
  }

  /**
   * Create an array of bytes from an image
   * 
   * @param image
   * @param formatName
   * @return an array of bytes
   * @throws IOException
   */
  public static byte[] createRawBytesFromImage(BufferedImage image, String formatName) throws IOException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);

    ImageIO.write(image, formatName, baos);

    baos.flush();
    byte[] resultImageAsRawBytes = baos.toByteArray();

    baos.close();

    return resultImageAsRawBytes;
  }

  /**
   * @param imageInByte
   * @return an buffered image
   */
  public static BufferedImage createImageFromRawBytes(byte[] imageInByte) {
    InputStream in = new ByteArrayInputStream(imageInByte);
    BufferedImage bImageFromConvert = null;
    try {
      bImageFromConvert = ImageIO.read(in);
    } catch (IOException ex) {
      throw new RuntimeException("Cannot get image from bytes.", ex);
    }

    return bImageFromConvert;
  }
}
