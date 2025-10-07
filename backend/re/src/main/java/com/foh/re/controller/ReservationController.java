package com.foh.re.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.foh.re.services.ReservationService;
import com.foh.re.vo.ReservationVO;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @MessageMapping("/reservation/create")
    @SendTo("/topic/reservation")
    public ReservationVO createReservation(ReservationVO reservationVO) {
        return reservationService.createReservation(reservationVO, null);
    }

    @MessageMapping("/reservation/update")
    @SendTo("/topic/reservation")
    public ReservationVO updateReservation(Long reservationId, ReservationVO reservationVO) {
        return reservationService.updateReservation(reservationId, reservationVO);
    }

    @MessageMapping("/reservation/delete")
    @SendTo("/topic/reservation")
    public String deleteReservation(Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return "Reservation deleted: " + reservationId;
    }
}
