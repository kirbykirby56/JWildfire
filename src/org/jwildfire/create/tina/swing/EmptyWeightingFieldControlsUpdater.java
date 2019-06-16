/*
  JWildfire - an image and animation processor written in Java
  Copyright (C) 1995-2019 Andreas Maschke

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
package org.jwildfire.create.tina.swing;

import org.jwildfire.create.tina.base.XForm;

public class EmptyWeightingFieldControlsUpdater extends WeightingFieldControlsUpdater {

  public EmptyWeightingFieldControlsUpdater(TinaController controller, TinaWeightingFieldControllerData controls) {
    super(controller, controls);
  }

  @Override
  public void weightingFieldColorMapFilenameBtn_clicked() {
    // EMPTY
  }

  @Override
  public void updateControls(XForm xform) {
    controls.weightingFieldTypeCmb.setSelectedItem(xform.getWeightingFieldType());
  }

  @Override
  public void enableControls(XForm xform, boolean enabled) {
    controls.weightingFieldInputCmb.setEnabled(false);
    controls.weightingFieldColorIntensityREd.setEnabled(false);
    controls.weightingFieldVariationIntensityREd.setEnabled(false);

    controls.weightingFieldColorMapFilenameLbl.setVisible(false);
    controls.weightingFieldColorMapFilenameBtn.setVisible(false);
    controls.weightingFieldColorMapFilenameInfoLbl.setVisible(false);

    controls.weightingFieldParam01REd.setVisible(false);
    controls.weightingFieldParam01Lbl.setVisible(false);
    controls.weightingFieldParam02REd.setVisible(false);
    controls.weightingFieldParam02Lbl.setVisible(false);
    controls.weightingFieldParam03REd.setVisible(false);
    controls.weightingFieldParam03Lbl.setVisible(false);
    controls.weightingFieldParam04Cmb.setVisible(false);
    controls.weightingFieldParam04Lbl.setVisible(false);
    controls.weightingFieldParam05REd.setVisible(false);
    controls.weightingFieldParam05Lbl.setVisible(false);
    controls.weightingFieldParam06REd.setVisible(false);
    controls.weightingFieldParam06Lbl.setVisible(false);
    controls.weightingFieldParam07REd.setVisible(false);
    controls.weightingFieldParam07Lbl.setVisible(false);
    controls.weightingFieldParam08Cmb.setVisible(false);
    controls.weightingFieldParam08Lbl.setVisible(false);
  }

  @Override
  public void weightingFieldParam01REd_changed() {
    // EMPTY
  }

  @Override
  public void weightingFieldParam02REd_changed() {
    // EMPTY
  }

  @Override
  public void weightingFieldParam03REd_changed() {
    // EMPTY
  }

  @Override
  public void weightingFieldParam04Cmb_changed() {
    // EMPTY
  }

  @Override
  public void weightingFieldParam05REd_changed() {
    // EMPTY
  }

  @Override
  public void weightingFieldParam06REd_changed() {
    // EMPTY
  }

  @Override
  public void weightingFieldParam07REd_changed() {
    // EMPTY
  }

  @Override
  public void weightingFieldParam08Cmb_changed() {
    // EMPTY
  }
}