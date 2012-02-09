/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2011 Andreas Maschke

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
package org.jwildfire.image;

import org.jwildfire.base.Tools;

public class FastHDRTonemapper {

  public SimpleImage renderImage(SimpleHDRImage pHDRImg) {
    SimpleImage res = new SimpleImage(pHDRImg.getImageWidth(), pHDRImg.getImageHeight());
    float minLum = Float.MAX_VALUE;
    float maxLum = 0.0f;
    float rgb[] = new float[3];
    for (int i = 0; i < pHDRImg.getImageHeight(); i++) {
      for (int j = 0; j < pHDRImg.getImageWidth(); j++) {
        pHDRImg.getRGBValues(rgb, j, i);
        float lum = 0.299f * rgb[0] + 0.588f * rgb[1] + 0.113f * rgb[2];
        if (lum < minLum) {
          minLum = lum;
        }
        if (lum > maxLum) {
          maxLum = lum;
        }
      }
    }
    double lumRange = maxLum - minLum;
    if (lumRange > Tools.EPSILON) {
      for (int i = 0; i < pHDRImg.getImageHeight(); i++) {
        for (int j = 0; j < pHDRImg.getImageWidth(); j++) {
          pHDRImg.getRGBValues(rgb, j, i);
          float lum = 0.299f * rgb[0] + 0.588f * rgb[1] + 0.113f * rgb[2];
          double normedLum = ((lum - minLum) / lumRange);
          double transformedLum = 1.0 - Math.exp(-37.0 * normedLum);
          float maxComp = rgb[0];
          if (rgb[1] > maxComp) {
            maxComp = rgb[1];
          }
          if (rgb[2] > maxComp) {
            maxComp = rgb[2];
          }
          int r = Tools.roundColor(rgb[0] / maxComp * transformedLum * 256.0);
          int g = Tools.roundColor(rgb[1] / maxComp * transformedLum * 256.0);
          int b = Tools.roundColor(rgb[2] / maxComp * transformedLum * 256.0);
          res.setRGB(j, i, r, g, b);
          //          int nLum = Tools.roundColor(transformedLum * 256.0);
          //          res.setRGB(j, i, nLum, nLum, nLum);
        }
      }
    }
    return res;
  }
}
