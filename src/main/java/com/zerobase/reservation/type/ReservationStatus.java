package com.zerobase.reservation.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {

    WAITING_APPROVAL, // 승인 대기
    COMPLETE,         // 예약 완료
    CANCEL,           // 예약 취소
    REJECTED,         // 예약 거절
    VISITED           // 방문 완료

}
