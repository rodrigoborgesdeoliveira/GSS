package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DashboardEvent extends GwtEvent<DashboardEventHandler> {
    public final static Type<DashboardEventHandler> TYPE = new Type<>();

    public Type<DashboardEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(DashboardEventHandler handler) {
        handler.onDashboardHandler(this);
    }
}
