package io.datcord.event;

import com.google.common.eventbus.EventBus;
import lombok.Getter;

public class EventBusProvider {

    @Getter
    private static final EventBus eventBus = new EventBus();
}
