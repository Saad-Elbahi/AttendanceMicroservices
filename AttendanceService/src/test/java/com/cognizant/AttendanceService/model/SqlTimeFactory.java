package com.cognizant.AttendanceService.model;

import org.meanbean.lang.Factory;

import java.sql.Time;

public class SqlTimeFactory implements Factory<Time> {
    @Override
    public Time create() {
        return Time.valueOf("09:00:00");
    }
}
