package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Patient;
import com.example.demo.service.Service;

@RestController
@RequestMapping("/patient")
public class Controller {

	@Autowired
	Service service;

	// For Adding new patient .
	@PostMapping("/add")
	public String addPatient(@RequestBody Patient patient) {

		return service.addPatient(patient);

	}

	// For getting all the list of patient.
	@GetMapping("/showall")
	public List<Patient> showAll() {

		return service.showAll();

	}

	// Delete the patient on the basis of their email
	@GetMapping("/delete")
	public String deletePatient(@RequestParam String email) {

		return service.deletePatient(email);
	}

	// Update the patient .
	@PostMapping("/update")
	public String updatePatient(@RequestBody Patient patient) {

		return service.updatePatient(patient);

	}

}
