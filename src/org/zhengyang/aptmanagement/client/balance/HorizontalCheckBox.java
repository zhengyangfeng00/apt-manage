//
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// //////////////////////////////////////////////////////////////////////////////

package org.zhengyang.aptmanagement.client.balance;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 23, 2012
 */
public class HorizontalCheckBox extends Composite {

  private static HorizontalCheckBoxUiBinder uiBinder = GWT.create(HorizontalCheckBoxUiBinder.class);
  @UiField CheckBox checkbox;
  @UiField SpanElement label;
  interface HorizontalCheckBoxUiBinder extends UiBinder<Widget, HorizontalCheckBox> {
  }
  private String associateVal;
  
  public HorizontalCheckBox() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public HorizontalCheckBox(String text, boolean value) {
    this();
    setValue(value);
    setText(text);
  }
  
  public void setValue(boolean val) {
    checkbox.setValue(val);
  }
  
  public boolean getValue() {
    return checkbox.getValue();
  }
  
  public void setText(String text) {
    label.setInnerText(text);
  }
  
  public void getText() {
    label.getInnerText();
  }

  /**
   * @return the associateVal
   */
  public String getAssociateVal() {
    return associateVal;
  }

  /**
   * @param associateVal the associateVal to set
   */
  public void setAssociateVal(String associateVal) {
    this.associateVal = associateVal;
  }
}
