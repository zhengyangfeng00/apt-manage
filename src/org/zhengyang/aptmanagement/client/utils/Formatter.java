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

package org.zhengyang.aptmanagement.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 8, 2012
 */
public final class Formatter {
  private Formatter() { }
  
  public static String formatMoney(double money) {
    if (money < 0) {
      String temp = NumberFormat.getCurrencyFormat().format(money);
      return "-" + temp.substring(1, temp.length() - 1);
    } 
    return "+" + NumberFormat.getCurrencyFormat().format(money);
  }
  
  public static String dateToString(Date date) {
    if (date == null) {
      return "Not sepecified";
    }
    DateTimeFormat fmt = DateTimeFormat.getFormat("h:mm a yyyy/MM/dd");
    return fmt.format(date);
  }
}
