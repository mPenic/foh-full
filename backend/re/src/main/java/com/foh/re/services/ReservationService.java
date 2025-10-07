package com.foh.re.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;
import com.foh.data.entities.usermgmt.UserEntity;
import com.foh.data.repository.usermgmt.CompanyDictionaryRepository;
import com.foh.data.repository.usermgmt.UserRepository;
import com.foh.security.CurrUserDetails;
import com.foh.re.entities.ReservationEntity;
import com.foh.re.entities.ReservationStatusDictionaryEntity;
import com.foh.re.repository.ReservationRepository;
import com.foh.re.repository.ReservationStatusDictionaryRepository;
import com.foh.re.vo.ReservationVO;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationStatusDictionaryRepository reservationStatusDictionaryRepository;
    private final UserRepository userRepository;
    private final CompanyDictionaryRepository companyDictionaryRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationStatusDictionaryRepository reservationStatusDictionaryRepository,
                              UserRepository userRepository,
                              CompanyDictionaryRepository companyDictionaryRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationStatusDictionaryRepository = reservationStatusDictionaryRepository;
        this.userRepository = userRepository;
        this.companyDictionaryRepository = companyDictionaryRepository;
    }

    @Transactional
    public ReservationVO createReservation(ReservationVO reservationVO, CurrUserDetails currentUser) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setReservationName(reservationVO.getReservationName());
        reservationEntity.setReservationDate(reservationVO.getReservationDate());

        UserEntity user = userRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        reservationEntity.setCreatedBy(user);

        ReservationStatusDictionaryEntity pendingStatus = reservationStatusDictionaryRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Default Reservation Status not found"));
        reservationEntity.setStatus(pendingStatus);

        CompanyDictionaryEntity company = companyDictionaryRepository.findById(currentUser.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        reservationEntity.setCompany(company);

        return mapToVO(reservationRepository.save(reservationEntity));
    }

    public ReservationVO updateReservation(Long reservationId, ReservationVO reservationVO) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.setReservationName(reservationVO.getReservationName());
        reservation.setReservationDate(reservationVO.getReservationDate());
        return mapToVO(reservationRepository.save(reservation));
    }

    public void deleteReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("Reservation not found");
        }
        reservationRepository.deleteById(reservationId);
    }

    public ReservationVO getReservationById(Long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        return mapToVO(reservation);
    }

    public List<ReservationVO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToVO)
                .collect(Collectors.toList());
    }

    private ReservationVO mapToVO(ReservationEntity reservation) {
        String createdByUsername = reservation.getCreatedBy() != null ? reservation.getCreatedBy().getUsername() : "Unknown";
        String statusName = reservation.getStatus() != null ? reservation.getStatus().getReservationStatusName() : "Unknown";

        return new ReservationVO(
                reservation.getReservationId(),
                reservation.getEvent() != null ? reservation.getEvent().getEventId() : null,
                reservation.getReservationName(),
                reservation.getReservationDate(),
                createdByUsername,
                statusName,
                reservation.getTable() != null ? reservation.getTable().getTableNumber() : null,
                reservation.getMoneySpent()
        );
    }
}
