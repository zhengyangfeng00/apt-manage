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

package org.zhengyang.aptmanagement.server.inject;

import org.zhengyang.aptmanagement.server.rpc.BalanceServiceImpl;
import org.zhengyang.aptmanagement.server.rpc.LoginServiceImpl;
import org.zhengyang.aptmanagement.server.rpc.SessionServiceImpl;

import com.google.inject.servlet.ServletModule;

/**
 * @author zhengyang.feng2011@gmail.com
 * @creation May 24, 2012
 */
public class MyServletModule extends ServletModule {
  @Override
  protected void configureServlets() {
    serve("/index_gwt_module/loginService").with(LoginServiceImpl.class);
    serve("/index_gwt_module/balanceService", "/consumption_management/balanceService").with(BalanceServiceImpl.class);
    serve("/index_gwt_module/sessionService", "/consumption_management/sessionService").with(SessionServiceImpl.class);
  }
}
