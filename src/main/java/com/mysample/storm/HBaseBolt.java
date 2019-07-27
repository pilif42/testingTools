package com.mysample.storm;

import com.mysample.service.HBaseWriteManager;

/**
 * A class to mimic a Storm bolt. Used to show how Powermock can be helpful in testing.
 */
public class HBaseBolt {

    private transient HBaseWriteManager hBaseWriteManager;

    public void prepare() {
        hBaseWriteManager = new HBaseWriteManager();
    }

    public void execute() {
        hBaseWriteManager.flush();
    }
}
