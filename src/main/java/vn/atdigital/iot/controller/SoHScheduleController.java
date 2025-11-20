package vn.atdigital.iot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.iot.common.Constants;
import vn.atdigital.iot.domain.dto.ScheduleDTO;
import vn.atdigital.iot.service.SoHService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/soh-schedule")
public class SoHScheduleController extends CommonController {
    @Autowired
    private SoHService soHService;

    @PostMapping("/create")
    public ResponseEntity<?> createSohSchedule(@RequestParam(value = "strId") String strId,
                                            @RequestParam(value = "startTime") Instant time,
                                               @RequestParam(value = "current") Double current) {
        try {
            LocalDateTime startTime = LocalDateTime.ofInstant(time, ZoneId.of("Asia/Ho_Chi_Minh"));
            soHService.createSchedule(strId,startTime,current);
            return toSuccessResultNull();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSohSchedule(@RequestParam(value = "id") Long id,
                                            @RequestParam(value = "startTime") Instant time) {
        try {
            LocalDateTime startTime = LocalDateTime.ofInstant(time, ZoneId.of("Asia/Ho_Chi_Minh"));
            soHService.updateSchedule(id,startTime);
            return toSuccessResultNull();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<?> updateSohSchedule(@RequestParam(value = "id") Long id) {
        try {
            soHService.stopSchedule(id);
            return toSuccessResultNull();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteSohSchedule(@RequestParam(value = "id") Long id) {
        try {
            soHService.removeSchedule(id);
            return toSuccessResultNull();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @GetMapping("/get-list")
    public ResponseEntity<?> getListSohSchedule() {
        try {
            List<ScheduleDTO> listSchedule = soHService.getListSchedule();
            return toSuccessResult(listSchedule);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }
}
