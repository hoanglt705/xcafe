/*
 * WindowUtils
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Utility functions for manipulating the display of windows.
 * 
 * @author Le Thanh Hoang
 * 
 */
public class WindowUtilities {
    private WindowUtilities() {
    }

    /**
     * Return the system screen size.
     * 
     * @return The dimension of the system screen size.
     */
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Return the centering point on the screen for the object with the specified dimension.
     * 
     * @param dimension
     *            the dimension of an object
     * @return The centering point on the screen for that object.
     */
    public static Point getCenteringPointOnScreen(Dimension dimension) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        if (dimension.width > screen.width) {
            dimension.width = screen.width;
        }
        if (dimension.height > screen.height) {
            dimension.height = screen.height;
        }
        return new Point((screen.width - dimension.width) / 2, (screen.height - dimension.height) / 2);
    }

    /**
     * Pack the window, center it on the screen, and set the window visible.
     * 
     * @param window
     *            the window to center and show.
     */
    public static void centerOnScreenAndSetVisible(Window window) {
        window.pack();
        centerOnScreen(window);
        window.setVisible(true);
    }

    /**
     * Take the window and center it on the screen.
     * <p>
     * This works around a bug in setLocationRelativeTo(...): it currently does not take multiple monitors into accounts
     * on all operating systems.
     * 
     * @param window
     *            the window to center
     */
    public static void centerOnScreen(Window window) {

        // This works around a bug in setLocationRelativeTo(...): it currently
        // does not take multiple monitors into accounts on all operating
        // systems.
            // Note that if this is running on a JVM prior to 1.4, then an
            // exception will be thrown and we will fall back to
            // setLocationRelativeTo(...).
            final Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

            final Dimension windowSize = window.getSize();
            final int x = screenBounds.x + ((screenBounds.width - windowSize.width) / 2);
            final int y = screenBounds.y + ((screenBounds.height - windowSize.height) / 2);
            window.setLocation(x, y);
    }

    /**
     * Pack the window, center it relative to it's parent, and set the window visible.
     * 
     * @param window
     *            the window to center and show.
     */
    public static void centerOnParentAndSetVisible(Window window) {
        window.pack();
        centerOnParent(window, window.getParent());
        window.setVisible(true);
    }

    /**
     * Center the window relative to it's parent. If the parent is null, or not showing, the window will be centered on
     * the screen
     * 
     * @param window
     *            the window to center
     * @param parent
     *            the parent
     */
    public static void centerOnParent(Window window, Component parent) {
        if (parent == null || !parent.isShowing()) {
            // call our own centerOnScreen so we work around bug in
            // setLocationRelativeTo(null)
            centerOnScreen(window);
        } else {
            window.setLocationRelativeTo(parent);
        }
    }

    /**
     * Return a <code>Dimension</code> whose size is defined not in terms of pixels, but in terms of a given percent of
     * the screen's width and height.
     * 
     * <P>
     * Use to set the preferred size of a component to a certain percentage of the screen.
     * 
     * @param percentWidth
     *            percentage width of the screen, in range <code>1..100</code>.
     * @param percentHeight
     *            percentage height of the screen, in range <code>1..100</code>.
     */
    public static final Dimension getDimensionFromPercent(int percentWidth, int percentHeight) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return calcDimensionFromPercent(screenSize, percentWidth, percentHeight);
    }

    private static Dimension calcDimensionFromPercent(Dimension dimension, int percentWidth, int percentHeight) {
        int width = dimension.width * percentWidth / 100;
        int height = dimension.height * percentHeight / 100;
        return new Dimension(width, height);
    }

    public static int getScreenWidth() {
        return  Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    public static int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    /**
     * Tell system to use native look and feel, as in previous releases. Metal (Java) LAF is the default otherwise.
     */

    public static void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    public static void setJavaLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    public static void setMotifLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
        }
    }

    /**
     * A simplified way to see a JPanel or other Container. Pops up a JFrame with specified Container as the content
     * pane.
     */

    public static JFrame openInJFrame(Container content, int width, int height, String title, Color bgColor) {
        JFrame frame = new JFrame(title);
        frame.setBackground(bgColor);
        content.setBackground(bgColor);
        frame.setSize(width, height);
        frame.setContentPane(content);
        frame.setVisible(true);
        return (frame);
    }

    /** Uses Color.white as the background color. */

    public static JFrame openInJFrame(Container content, int width, int height, String title) {
        return (openInJFrame(content, width, height, title, Color.white));
    }

    /**
     * Uses Color.white as the background color, and the name of the Container's class as the JFrame title.
     */

    public static JFrame openInJFrame(Container content, int width, int height) {
        return (openInJFrame(content, width, height, content.getClass().getName(), Color.white));
    }

    /**
     * @return Screen size minus the taskbar size.
     */
    public static Dimension getFullScreenSize() {
        Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        return new Dimension(maxBounds.width, maxBounds.height);
    }

    public static int getTaskbarHeight() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        return (dim.height - maxBounds.height);
    }
}
