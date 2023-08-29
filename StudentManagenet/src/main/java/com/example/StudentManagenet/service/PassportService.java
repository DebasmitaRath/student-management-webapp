package com.example.StudentManagenet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class PassportService 
{
	@Autowired
	private PassportRepository passportRepository;
	
	@Transactional
	public Passport save(Passport passport) {
		return passportRepository.save(passport);
	}
}
