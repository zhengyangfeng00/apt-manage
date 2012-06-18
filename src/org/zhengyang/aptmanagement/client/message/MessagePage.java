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

package org.zhengyang.aptmanagement.client.message;

import org.zhengyang.aptmanagement.client.navigate.NavigateWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 15, 2012
 */
public class MessagePage implements EntryPoint {
  private final NavigateWidget navigateWidget = GWT.create(NavigateWidget.class);
  private final LeaveMessageWidget msgWidget = GWT.create(LeaveMessageWidget.class);
  @Override
  public void onModuleLoad() {
    RootPanel.get().add(navigateWidget);
    RootPanel.get("newMessageContainer").add(msgWidget);
  }
}
