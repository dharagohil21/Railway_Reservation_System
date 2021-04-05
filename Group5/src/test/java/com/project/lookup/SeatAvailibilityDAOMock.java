package com.project.lookup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.project.lookup.IBookedTickets;
import com.project.lookup.ISeatAvailibilityDAO;
import com.project.setup.ITrain;

public class SeatAvailibilityDAOMock implements ISeatAvailibilityDAO {

	@Override
	public List<IBookedTickets> getListOfTicketsFromSeatNo(ITrain train, Date date, int seatNo) {
//		CalculationAbstractFactory calculationAbstractFactory = CalculationAbstractFactory.instance();
//		CalculationAbstractFactoryTest calculationAbstractFactoryTest = CalculationAbstractFactoryTest.instance();
		LookupAbstractFactory lookupAbstractFactory = LookupAbstractFactory.instance();
		LookupAbstractFactoryTest lookupAbstractFactoryTest = LookupAbstractFactoryTest.instance();
		IBookedTickets  bookedTicketsWithSeatNumberOne =  lookupAbstractFactory.createBookedTickets();
		IBookedTickets bookedTicketsWithSeatNumberTwo = lookupAbstractFactory.createBookedTickets();
		BookedTicketsMock bookedTicketsMock = lookupAbstractFactoryTest.createBookedTicketsMock();
		bookedTicketsWithSeatNumberOne = bookedTicketsMock.createBookedTicketsMockForSeatNumberOne(bookedTicketsWithSeatNumberOne);
		bookedTicketsWithSeatNumberTwo = bookedTicketsMock.createBookedTicketsMockForSeatNumberTwo(bookedTicketsWithSeatNumberTwo);
		List<IBookedTickets> bookedTickets = new ArrayList<IBookedTickets>();
		bookedTickets.add(bookedTicketsWithSeatNumberOne);
		bookedTickets.add(bookedTicketsWithSeatNumberTwo);
		return bookedTickets;
	}

	@Override
	public List<Integer> getReservationId(ITrain train, Date date) {
		List<Integer> reservationId = new ArrayList<Integer>();
		reservationId.add(1);
		reservationId.add(2);
		return reservationId;
	}

	@Override
	public int maximumSeatNumberOfReservationId(int reservationId) {
		return 2;
	}

	
}
