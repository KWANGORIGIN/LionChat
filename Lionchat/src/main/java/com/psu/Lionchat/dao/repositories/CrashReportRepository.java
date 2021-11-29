package com.psu.Lionchat.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.CrashReport;

/**
 * An ORM mapping of the CrashReport table and operations to run on it.
 * 
 * @author jacobkarabin
 */
public interface CrashReportRepository extends JpaRepository<CrashReport, Long> {

}
