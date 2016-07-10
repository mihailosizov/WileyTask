package com.wiley.support;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
    public static final Logger LOGGER = Logger.getLogger(Log.class.getName());

    static {
        PropertyConfigurator.configure(Log.class.getResource("/log4j.properties"));
    }
}
