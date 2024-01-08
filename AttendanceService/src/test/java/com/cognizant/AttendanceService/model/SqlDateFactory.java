package com.cognizant.AttendanceService.model;

import org.meanbean.lang.Factory;

import java.sql.Date;

public class SqlDateFactory implements Factory<Date> {

    @Override
    public Date create() {
        java.util.Date date = new java.util.Date();
        Date sqlDate  = new Date(date.getTime());
        return sqlDate;
    }
}
