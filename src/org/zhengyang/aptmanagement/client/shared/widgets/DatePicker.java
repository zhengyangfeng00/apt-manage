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

package org.zhengyang.aptmanagement.client.shared.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author zhengyang.feng2011@gmail.com
 * @creation May 25, 2012
 */
public class DatePicker extends Composite {

  private static DatePickerUiBinder uiBinder = GWT.create(DatePickerUiBinder.class);
  public String id;
  @UiField TextBox datePicker;
  interface DatePickerUiBinder extends UiBinder<Widget, DatePicker> {
  }
  
  public DatePicker() {
    initWidget(uiBinder.createAndBindUi(this));
    id = HTMLPanel.createUniqueId();
    datePicker.getElement().setId(id);
  }

  @Override
  protected void onAttach() {
    super.onAttach();
    bind("#" + id);
  }

  private static native void bind(String selector) /*-{
    $wnd.$(selector).datepicker();
  }-*/;
}
