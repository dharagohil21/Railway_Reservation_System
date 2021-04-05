package com.project.ticketCancellation;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.project.reservation.IPassengerInformation;
import com.project.reservation.IReservation;
import com.project.setup.ITrain;


public class CalculateAmounts implements ICalculateAmounts {
	
	public final double TWENTY_PERCENT = 0.2;
	public final double FIFTY_PERCENT = 0.5;
	public final String ADD_SECONDS = ":00";

	@Override
	public double CalculateDiscount(double amountPaid, double refundedAmount, Date trainStartDate,
			String departureTime) {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDate localDate = localDateTime.toLocalDate();
		LocalTime localTime = localDateTime.toLocalTime();

		//https://www.baeldung.com/java-date-to-localdate-and-localdatetime
		LocalDate TrainDate = Instant.ofEpochMilli(trainStartDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDateAfterAddingOneDay;
		Double amount;
		String dateStr = trainStartDate.toString();
		String trainDateTime = dateStr + " " + departureTime + ADD_SECONDS;	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
		LocalDateTime departureLocalDateTime = LocalDateTime.parse( trainDateTime , formatter);	
		LocalTime trainTime = departureLocalDateTime.toLocalTime();
		if(localDate.isEqual(TrainDate)) {
			amount = refundedAmount * TWENTY_PERCENT;
		}
		else {
			localDateAfterAddingOneDay = localDate.plusDays(1);
			if(localDateAfterAddingOneDay.isEqual(TrainDate)) {
				if(trainTime.isBefore(localTime)) {
					amount = refundedAmount * FIFTY_PERCENT;
				}
				else {
					amount = refundedAmount * TWENTY_PERCENT;
				}
			}
			else {
				amount = refundedAmount * FIFTY_PERCENT;
			}
		}
		return amount;
	}

	@Override
	public double CalculateRefundAmount(IReservation reservation, List<Integer> ids, ISearchPassengerInformationDAO searchTicketInfo) {
		int pnrNumber = reservation.getReservationId();
		double amountPaid = reservation.getAmountPaid();
		Date trainStartDate = reservation.getStartDate();
		double refundedAmount = 0.0;
		List<IPassengerInformation> passengerInformation = searchTicketInfo.SearchPassengerInfoByPNR(String.valueOf(pnrNumber));
		List<IPassengerInformation> selectedPassengerInformation = new ArrayList<>();
		double amount = 0.0;
		for(IPassengerInformation information : passengerInformation) {
			for(Integer id : ids) {
				if(id == information.getPassengerInformationId()) {
					amount+= information.getAmountPaid();
					selectedPassengerInformation.add(information);
				}
			}
		}
		refundedAmount = amountPaid - amount;
		ITrain train = searchTicketInfo.GetTrainDetails(reservation.getTrainId());
		refundedAmount = CalculateDiscount( amountPaid,  refundedAmount, trainStartDate, train.getDepartureTime());
		return refundedAmount;
	}
	
}