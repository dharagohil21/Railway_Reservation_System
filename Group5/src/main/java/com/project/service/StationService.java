package com.project.service;

import java.util.List;

import com.project.entity.Station;

public interface StationService {
	public void save(Station station);
	
	public List<Station> listOfStations();
	
	public Station getStation(Integer sId);
	
	public void deleteStation(Integer sId);
}
