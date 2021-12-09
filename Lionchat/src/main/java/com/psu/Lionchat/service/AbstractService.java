package com.psu.Lionchat.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.psu.Lionchat.dao.DAO;

public abstract class AbstractService{
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
