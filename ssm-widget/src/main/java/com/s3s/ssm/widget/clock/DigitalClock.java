/*
 * Copyright (c) 2008, 2011 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 * 
 * This file is available and licensed under the following license:
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the distribution.
 * - Neither the name of Oracle Corporation nor the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.s3s.ssm.widget.clock;

import java.time.LocalTime;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class DigitalClock extends Parent {

  private Digit[] digits;

  private final Color onColor;
  private boolean ignore = false;

  private LocalTime currentTime;

  private LocalTime maxTime;

  public DigitalClock(Color onColor, Color offColor, LocalTime maxTime) {
    this.maxTime = maxTime;
    this.onColor = onColor;
    currentTime = LocalTime.of(0, 0, 0);
    initClocks(onColor, offColor);
  }

  public void reset() {
    currentTime = LocalTime.of(0, 0, 0);
  }

  public void setTime(LocalTime time) {
    this.currentTime = time;
  }

  public LocalTime getTime() {
    return currentTime;
  }

  private void initClocks(Color onColor, Color offColor) {
    // create effect for on LEDs
    Glow onEffect = new Glow(1.7f);
    onEffect.setInput(new InnerShadow());
    // create effect for on dot LEDs
    Glow onDotEffect = new Glow(1.7f);
    onDotEffect.setInput(new InnerShadow(5, Color.BLACK));
    // create effect for off LEDs
    InnerShadow offEffect = new InnerShadow();
    // create digits
    digits = new Digit[7];
    for (int i = 0; i < 6; i++) {
      Digit digit = new Digit(onColor, offColor, onEffect, offEffect);
      digit.setLayoutX(i * 80 + ((i + 1) % 2) * 20);
      digits[i] = digit;
      getChildren().add(digit);
    }
    // create dots
    Group dots = new Group(
            new Circle(80 + 54 + 20, 44, 6, onColor),
            new Circle(80 + 54 + 17, 64, 6, onColor),
            new Circle((80 * 3) + 54 + 20, 44, 6, onColor),
            new Circle((80 * 3) + 54 + 17, 64, 6, onColor));
    dots.setEffect(onDotEffect);
    getChildren().add(dots);
  }

  public void refreshClocks() {
    currentTime = currentTime.plusSeconds(1);
    if (isMax() && !isIgnore()) {
      getScene().setFill(Color.ORANGE);
      digits[0].showNumber(currentTime.getHour() / 10, onColor);
      digits[1].showNumber(currentTime.getHour() % 10, onColor);
      digits[2].showNumber(currentTime.getMinute() / 10, onColor);
      digits[3].showNumber(currentTime.getMinute() % 10, onColor);
      digits[4].showNumber(currentTime.getSecond() / 10, onColor);
      digits[5].showNumber(currentTime.getSecond() % 10, onColor);
    } else if (isIgnore()) {
      getScene().setFill(Color.WHITE);
      digits[0].showNumber(currentTime.getHour() / 10, onColor);
      digits[1].showNumber(currentTime.getHour() % 10, onColor);
      digits[2].showNumber(currentTime.getMinute() / 10, onColor);
      digits[3].showNumber(currentTime.getMinute() % 10, onColor);
      digits[4].showNumber(currentTime.getSecond() / 10, onColor);
      digits[5].showNumber(currentTime.getSecond() % 10, onColor);
    } else {
      digits[0].showNumber(currentTime.getHour() / 10, onColor);
      digits[1].showNumber(currentTime.getHour() % 10, onColor);
      digits[2].showNumber(currentTime.getMinute() / 10, onColor);
      digits[3].showNumber(currentTime.getMinute() % 10, onColor);
      digits[4].showNumber(currentTime.getSecond() / 10, onColor);
      digits[5].showNumber(currentTime.getSecond() % 10, onColor);
    }
  }

  public void play() {
    // wait till start of next second then start a timeline to call refreshClocks() every second
    Timeline delayTimeline = new Timeline();
    delayTimeline.getKeyFrames().add(
            new KeyFrame(new Duration(1000 - (System.currentTimeMillis() % 1000)),
                    new EventHandler<ActionEvent>() {
                      @SuppressWarnings("unused")
                      @Override
                      public void handle(ActionEvent event) {
                        Timeline everySecond = new Timeline();
                        everySecond.setCycleCount(Animation.INDEFINITE);
                        everySecond.getKeyFrames().add(
                                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent event) {
                                    refreshClocks();
                                  }
                                }));
                        everySecond.play();
                      }
                    })
            );
    delayTimeline.play();
  }

  private boolean isMax() {
    return currentTime.compareTo(maxTime) >= 0;
  }

  public void setIgnore() {
    this.ignore = true;
  }

  public boolean isIgnore() {
    return ignore;
  }
}