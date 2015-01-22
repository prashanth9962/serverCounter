package org.reducedata.serverCount;

import java.util.Map;

/**
 * Created by amit on 5/22/14.
 */
public interface ServerFlushHandler {
    public void handle(Map<byte[], Long> flushedData);
}
