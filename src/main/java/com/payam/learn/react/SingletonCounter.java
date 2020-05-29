package com.payam.learn.react;

import java.util.concurrent.atomic.LongAdder;

public enum SingletonCounter {
    INSTANCE;
    LongAdder value = new LongAdder();


}
