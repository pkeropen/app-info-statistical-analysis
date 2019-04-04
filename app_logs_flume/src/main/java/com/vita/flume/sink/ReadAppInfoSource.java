package com.vita.flume.sink;

import org.apache.flume.Context;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.source.AbstractSource;

/**
 * 读取APP日志的自定义Flume source
 */
public class ReadAppInfoSource extends AbstractSource implements Configurable, PollableSource {

    @Override
    public Status process() throws EventDeliveryException {

        return null;
    }

    @Override
    public long getBackOffSleepIncrement() {

        return 0;
    }

    @Override
    public long getMaxBackOffSleepInterval() {

        return 0;
    }

    @Override
    public void configure(Context context) {

    }
}
