package com.jxzdoing.database.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class ManagementCenter {

    @Autowired
    @Qualifier("master")
    DataSource masterDataSource;
    @Autowired
    @Qualifier("slave1")
    DataSource slave1DataSource;
    @Autowired
    @Qualifier("slave2")
    DataSource slave2DataSource;

    int slaveIndex = 1;

    public DataSource getDefaultDataSource() {
        return masterDataSource;
    }

    public DataSource getSlaveDataSource() {
        if (slaveIndex == 1) {
            slaveIndex = 2;
            return slave1DataSource;
        }
        slaveIndex = 1;
        return slave2DataSource;
    }
}
