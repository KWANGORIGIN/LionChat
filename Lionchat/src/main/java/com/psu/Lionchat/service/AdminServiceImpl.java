package com.psu.Lionchat.service;

import com.psu.Lionchat.dao.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    private final DAO dao;

    @Autowired
    public AdminServiceImpl(DAO dao)
    {
        this.dao = dao;
    }
}
