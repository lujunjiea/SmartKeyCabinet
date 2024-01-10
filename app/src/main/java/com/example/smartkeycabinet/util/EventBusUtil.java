package com.example.smartkeycabinet.util;

import org.greenrobot.eventbus.EventBus;


public class EventBusUtil {
    public static void register(Object obj) {
        if (!EventBus.getDefault().isRegistered(obj)) {
            EventBus.getDefault().register(obj);
        }
    }

    public static void unregister(Object obj) {
        if (EventBus.getDefault().isRegistered(obj)) {
            EventBus.getDefault().unregister(obj);
        }
    }

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
