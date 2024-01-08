package com.cognizant.AttendanceService.dao;

import com.cognizant.AttendanceService.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceDao extends JpaRepository<Attendance,Long> {
    @Transactional
    @Query(value ="SELECT * FROM Attendance a WHERE WEEK(a.date) = WEEK(?1) AND userid = (?2)", nativeQuery = true)
    List<Attendance> getAttendanceByDateByWeek(Date date, long userid);

    Optional<Attendance> findAttendanceByDate(Date date);
}
