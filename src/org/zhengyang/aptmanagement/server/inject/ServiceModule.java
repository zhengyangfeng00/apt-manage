package org.zhengyang.aptmanagement.server.inject;

import org.zhengyang.aptmanagement.client.balance.BalanceService;
import org.zhengyang.aptmanagement.client.index.LoginService;
import org.zhengyang.aptmanagement.client.shared.SessionService;
import org.zhengyang.aptmanagement.server.rpc.BalanceServiceImpl;
import org.zhengyang.aptmanagement.server.rpc.LoginServiceImpl;
import org.zhengyang.aptmanagement.server.rpc.SessionServiceImpl;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(LoginService.class).to(LoginServiceImpl.class);
    bind(BalanceService.class).to(BalanceServiceImpl.class);
    bind(SessionService.class).to(SessionServiceImpl.class);
  }

}
