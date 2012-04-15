/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2012 Andreas Maschke

  This is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser 
  General Public License as published by the Free Software Foundation; either version 2.1 of the 
  License, or (at your option) any later version.
 
  This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License along with this software; 
  if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jwildfire.create.tina.animate;

import org.jwildfire.base.Prefs;
import org.jwildfire.create.tina.base.Flame;
import org.jwildfire.create.tina.base.Shading;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.render.FlameRenderer;
import org.jwildfire.create.tina.render.RenderInfo;
import org.jwildfire.create.tina.render.RenderedFlame;
import org.jwildfire.create.tina.transform.XFormTransformService;
import org.jwildfire.io.ImageWriter;

public class AnimationService {

  public static enum GlobalScript {
    NONE,
    ROTATE_PITCH,
    ROTATE_PITCH_YAW,
    ROTATE_ROLL,
    ROTATE_YAW
  }

  public static enum XFormScript {
    NONE,
    ROTATE_FULL,
    ROTATE_SLIGHTLY,
    ROTATE_LAST_XFORM,
  }

  public static enum LightScript {
    NONE,
    ROTATE_XY_PLANE,
  }

  public static void renderFrame(int pFrame, int pFrames, Flame pFlame1, Flame pFlame2, boolean pDoMorph, GlobalScript pGlobalScript, XFormScript pXFormScript, LightScript pLightScript, String pImagePath, int pWidth, int pHeight, int pQuality, Prefs pPrefs) throws Exception {
    String imgFilename = String.valueOf(pFrame);
    while (imgFilename.length() < 3) {
      imgFilename = "0" + imgFilename;
    }
    imgFilename = pImagePath + imgFilename + ".png";
    RenderInfo info = new RenderInfo(pWidth, pHeight);
    Flame flame;
    if (pDoMorph) {
      int morphFrames = pFrames / 2;
      int morphFrame;
      if (pFrame <= morphFrames) {
        morphFrame = pFrame;
      }
      else {
        morphFrame = morphFrames - (pFrame - morphFrames);
        if (morphFrame < 1) {
          morphFrame = 1;
        }
        else if (morphFrame > morphFrames) {
          morphFrame = morphFrames;
        }
      }
      flame = FlameMorphService.morphFlames(pFlame1, pFlame2, morphFrame, morphFrames);
    }
    else {
      flame = pFlame1;
    }
    double wScl = (double) info.getImageWidth() / (double) flame.getWidth();
    double hScl = (double) info.getImageHeight() / (double) flame.getHeight();
    flame.setPixelsPerUnit((wScl + hScl) * 0.5 * flame.getPixelsPerUnit());
    flame.setWidth(info.getImageWidth());
    flame.setHeight(info.getImageHeight());
    flame.setSampleDensity(pQuality);
    switch (pGlobalScript) {
      case ROTATE_PITCH: {
        double camPitch = 360.0 / (double) pFrames * (double) (pFrame - 1);
        flame.setCamPitch(camPitch);
      }
        break;
      case ROTATE_YAW: {
        double camYaw = 360.0 / (double) pFrames * (double) (pFrame - 1);
        flame.setCamYaw(camYaw);
      }
        break;
      case ROTATE_PITCH_YAW: {
        double camRoll = 86;
        double camPitch = 360.0 / (double) pFrames * (double) (pFrame - 1);
        double camYaw = -180 - camPitch;
        flame.setCamRoll(camRoll);
        flame.setCamPitch(camPitch);
        flame.setCamYaw(camYaw);
        flame.setCamPerspective(0.2);
      }
        break;
      case ROTATE_ROLL: {
        double camRoll = 360.0 / (double) pFrames * (double) (pFrame - 1);
        flame.setCamRoll(camRoll);
      }
        break;
    }

    switch (pXFormScript) {
      case ROTATE_FULL: {
        int idx = 0;
        for (XForm xForm : flame.getXForms()) {
          idx++;
          double angle = 360.0 / (double) pFrames * (double) (pFrame - 1);
          if (idx % 2 == 0) {
            angle = -angle;
          }
          XFormTransformService.rotate(xForm, angle);
        }
      }
        break;
      case ROTATE_SLIGHTLY: {
        int idx = 0;
        for (XForm xForm : flame.getXForms()) {
          double maxAngle = (++idx * 3.0) + 90;
          double angle = maxAngle / (double) pFrames * (double) (pFrame - 1);
          angle = maxAngle * (1.0 - Math.cos((double) (pFrame - 1) / (double) (pFrames - 1) * 2.0 * Math.PI)) * 0.5;
          if (idx % 2 == 0) {
            angle = -angle;
          }
          XFormTransformService.rotate(xForm, angle);
        }
      }
        break;
      case ROTATE_LAST_XFORM: {
        XForm xForm = flame.getXForms().get(flame.getXForms().size() - 1);
        double angle = 360.0 / (double) pFrames * (double) (pFrame - 1);
        XFormTransformService.rotate(xForm, angle);
      }
        break;
    }

    if (flame.getShadingInfo().getShading() == Shading.PSEUDO3D) {
      switch (pLightScript) {
        case ROTATE_XY_PLANE: {
          double radius = 0.4;
          for (int i = 0; i < flame.getShadingInfo().getLightCount(); i++) {
            double angle = 360.0 / (double) pFrames * (double) (pFrame - 1);
            radius *= 0.75;
            if (i % 2 == 0) {
              flame.getShadingInfo().getLightPosX()[i] -= radius * Math.sin(angle);
              flame.getShadingInfo().getLightPosY()[i] -= radius * Math.cos(angle);
            }
            else {
              flame.getShadingInfo().getLightPosX()[i] += radius * Math.sin(angle);
              flame.getShadingInfo().getLightPosY()[i] += radius * Math.cos(angle);
            }
          }
        }
          break;
      }
    }

    //          flame.setCamRoll(86 - 20 * Math.sin((imgIdx - 1) * 4.0 * Math.PI / 72.0));
    //          flame.setCamYaw(-180 + 60 * Math.sin((imgIdx - 1) * 2.0 * Math.PI / 72.0));

    FlameRenderer renderer = new FlameRenderer(flame, pPrefs);
    RenderedFlame res = renderer.renderFlame(info);
    new ImageWriter().saveImage(res.getImage(), imgFilename);
  }
}