package com.forexcalculator.forex.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class LoggerSingleton {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public LoggerSingleton() {
    }
}

