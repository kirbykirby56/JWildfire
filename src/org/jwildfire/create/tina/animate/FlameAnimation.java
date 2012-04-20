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

import java.util.ArrayList;
import java.util.List;

import org.jwildfire.base.QualityProfile;
import org.jwildfire.create.tina.animate.AnimationService.GlobalScript;
import org.jwildfire.create.tina.animate.AnimationService.XFormScript;
import org.jwildfire.create.tina.base.Flame;

public class FlameAnimation {
  private String soundFilename;
  private GlobalScript globalScript;
  private XFormScript xFormScript;
  private QualityProfile qualityProfile;
  private final List<Motion> motions = new ArrayList<Motion>();
  private final List<FlameAnimationPart> parts = new ArrayList<FlameAnimationPart>();

  public GlobalScript getGlobalScript() {
    return globalScript;
  }

  public void setGlobalScript(GlobalScript globalScript) {
    this.globalScript = globalScript;
  }

  public XFormScript getxFormScript() {
    return xFormScript;
  }

  public void setxFormScript(XFormScript xFormScript) {
    this.xFormScript = xFormScript;
  }

  public QualityProfile getQualityProfile() {
    return qualityProfile;
  }

  public void setQualityProfile(QualityProfile qualityProfile) {
    this.qualityProfile = qualityProfile;
  }

  public String getSoundFilename() {
    return soundFilename;
  }

  public void setSoundFilename(String soundFilename) {
    this.soundFilename = soundFilename;
  }

  public List<Motion> getMotions() {
    return motions;
  }

  public int getFrameCount() {
    int res = 0;
    for (FlameAnimationPart part : parts) {
      if (part.getFrameCount() > 0) {
        res += part.getFrameCount();
      }
    }
    return res;
  }

  public void addPart(FlameAnimationPart pPart) {
    parts.add(pPart);
  }

  public Flame getFlame(int pFrame) {
    int currFrame = 0;
    for (int i = 0; i < parts.size(); i++) {
      FlameAnimationPart part = parts.get(i);
      currFrame += part.getFrameCount();
      if (currFrame >= pFrame) {
        return part.getFlame().makeCopy();
      }
    }
    return null;
  }
}