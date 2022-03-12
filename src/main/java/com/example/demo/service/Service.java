
package com.example.demo.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.PatientDao;
import com.example.demo.entity.Patient;

@org.springframework.stereotype.Service
public class Service {
	@Autowired
	PatientDao patientDao;

	// Add new patient checking new patient on the basis of their email
	public String addPatient(Patient patient) {
		try {
			// if the patient is already present inside database then return Email already
			// exists.
			if (patientDao.searchByEmail(patient.getEmail()) != null) {
				return "Email already exists";
			}

			// check all the validations
			String response = validations(patient);

			if (response.equalsIgnoreCase("success")) {

				patientDao.save(patient);
				return "Patient Successfully Added";
			}

			return response;

		} catch (Exception e) {
			System.out.println(e);
			return "Failed Something Went Wrong";
		}
	}

	public String validations(Patient patient) {
		// check the length of the name
		if (patient.getName().length() <= 2) {
			return "Name atleast have 3 characters ";
		}
		// check Address validation
		if (patient.getAddress().length() <= 9) {
			return "Address atleast have  characters 10";
		}

		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		Matcher matcher = pattern.matcher(patient.getEmail());
		//Check validation for email
		if (!matcher.matches()) {
			return "Email is not vaild.";
		}

		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(patient.getPassword());
		//Check validation for password
		if (!matcher.matches()) {
			return "Password is not Vaild.";
		}
		String countryCodeRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";
		pattern = Pattern.compile(countryCodeRegex);
		//check validation for phone number
		matcher = pattern.matcher(patient.getPhonenumber());
		if (!matcher.matches()) {
			return "Phone number is not vaild.";
		}
		return "success";
	}

	// Update the patient
	public String updatePatient(Patient patient) {
		try {
			// First check if the patient is present in the database or it's a new patient
			// If the patient is new then return patient doesn't exists
			if (patientDao.searchByEmail(patient.getEmail()) == null) {
				return "Patient Doesn't Exists";
			}
			// if patient is not new then i update the patient on the basis of their id .
			// check all the validations
			String response = validations(patient);

			if (response.equalsIgnoreCase("success")) {

			Patient p = patientDao.searchByEmail(patient.getEmail());
			patient.setPatientId(p.getPatientId());
			patientDao.save(patient);
			return "Patient Successfully Updated";	
			}
			return response;

		} catch (Exception e) {
			System.out.println(e);
			return "Failed";
		}
	}

	// Delete patient on the basis of their email
	public String deletePatient(String email) {
		try {
			Patient patient = patientDao.searchByEmail(email);
			// check if the email is present in the database if not then return patient
			// doesn't exists.
			if (patient == null) {
				return "Patient Doesn't Exists.";
			}
			// delete the patient on the basis of email
		 patientDao.delete(patient);
			return "Patient Successfully Deleted";

		} catch (Exception e) {
			System.out.println(e);
			return "Failed";
		}
	}

	// Show list of all the patient
	public List<Patient> showAll() {
		return patientDao.findAll();
	}

}
