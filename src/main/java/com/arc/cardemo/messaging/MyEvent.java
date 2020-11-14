package com.arc.cardemo.messaging;

import java.time.Instant;

public class MyEvent {
	private final String msg;
	private Instant timestamp;

    public MyEvent (String msg) {
        this.msg = msg;
        this.timestamp = Instant.now();
    }

    public String getMsg () {
        return msg + " " + timestamp.toString();
    }
}
