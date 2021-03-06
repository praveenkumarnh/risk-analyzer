package com.danielpacak.riskanalyzer.frontend.repository.api;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.Facility;

public interface FacilityRepository {

	Facility save(Facility facility);

	Facility findOne(String facilityId);

	void delete(String facilityId);

	List<Facility> findAll();

}
