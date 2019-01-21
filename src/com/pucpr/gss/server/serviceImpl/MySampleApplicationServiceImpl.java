package com.pucpr.gss.server.serviceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pucpr.gss.client.service.MySampleApplicationService;

public class MySampleApplicationServiceImpl extends RemoteServiceServlet implements MySampleApplicationService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}