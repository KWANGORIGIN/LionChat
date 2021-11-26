package com.psu.Lionchat.service;

import com.psu.Lionchat.dao.DAO;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService implements ChatServiceIF{
    protected final DAO dao;

    public AbstractService(){
        this.dao = null;
    }
    @Autowired
    public AbstractService(DAO dao)
    {
        this.dao = dao;
    }

}
