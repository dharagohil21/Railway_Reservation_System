package com.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.entity.SearchTrain;
import com.project.entity.Station;
import com.project.entity.Train;
import com.project.service.DButilities;


@Component
public class SearchTrainDAOImpl implements SearchTrainDAO {


	@Autowired
	DButilities dbUtilities;
	
	@Override
	public List<Train> searchTrains(SearchTrain searchTrain) {
		System.out.println("in search train DAO impl");
		List<Train> trains = new ArrayList();
		
		Integer count = 0;
		Connection conn = dbUtilities.estConnection();	
		
		try {
			CallableStatement stmt = conn.prepareCall("{call searchTrain(? ,? , ? , ? , ?)}");
			stmt.setInt(1, Integer.parseInt(searchTrain.getSourceStation()));
			stmt.setInt(2, Integer.parseInt(searchTrain.getDestinationStation()));
			String searchStringForSourceStation = '%'+searchTrain.getSourceStation()+'%';
			String searchStringForDestinationStation = '%'+searchTrain.getDestinationStation()+'%';
			
			stmt.setString(3, searchStringForSourceStation);
			stmt.setString(4, searchStringForDestinationStation);
			stmt.setString(5, searchTrain.getTrainType());
			
			boolean hadResultsForList = stmt.execute();

			if (hadResultsForList) {
				
				ResultSet resultSet = stmt.getResultSet();
				while (resultSet.next()) {
				//	System.out.println("for train -------------------");
					Train train = new Train();
					int count2  = 0 ;
					
					List<Integer> allStations = new ArrayList();
					
					
					train.setTrainId(resultSet.getInt("trainId"));
					train.setTrainCode(resultSet.getInt("trainCode"));
					train.setTrainName(resultSet.getString("trainName"));
					train.setTrainType(resultSet.getString("trainType"));
					train.setDays(resultSet.getString("days"));
					train.setDepartureTime(resultSet.getString("departureTime"));
					train.setTotalCoaches(resultSet.getInt("totalCoaches"));
					
					
					
					train.setStartStation(resultSet.getString("startStation"));
					allStations.add(Integer.parseInt(train.getStartStation()));
				//	System.out.println("size after aading first one "+allStations.size());
					
					train.setMiddleStations(resultSet.getString("middleStations"));
					if(train.getMiddleStations() == null) {
						
					}
					else {
						String[] middleStationsList = train.getMiddleStations().split(",");
						for(String mid : middleStationsList) {
							count2++;
							allStations.add(Integer.parseInt(mid));
						//	System.out.println("count"+count2);
						}
					}
					
				//	System.out.println("size after aading middle one "+allStations.size());
					
					train.setEndStation(resultSet.getString("endStation"));
					allStations.add(Integer.parseInt(train.getEndStation()));
				//	System.out.println("size after aading thired one "+allStations.size());
					
					train.setTotalStation(allStations);
					
					trains.add(train);
					count++;
				}
			}
			System.out.println(count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbUtilities.closeConnection(conn);
		}

		// TODO Auto-generated method stub
		return trains;
	}

}
