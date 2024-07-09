package com.surl.first.global.event.event;

public abstract class Event {
    public String getName(){
        return this.getClass().getSimpleName();
    }
}
