package com.psu.Lionchat.api;

import com.psu.Lionchat.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdministrativeController {

    private final AdminService adminService;

    @Autowired
    public AdministrativeController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    // add mappings for requests
}
