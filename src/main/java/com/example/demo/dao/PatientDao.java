package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Patient;

public interface PatientDao extends JpaRepository<Patient, Long> {

	// Find patient by their email
	@Query(value = "select * from Patient p where p.email = ?1", nativeQuery = true)
	public Patient searchByEmail(String email);

}
