package vn.atdigital.iot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.iot.common.Constants;
import vn.atdigital.iot.domain.dto.ScheduleDTO;
import vn.atdigital.iot.service.SoHService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/openmuc/soh-schedule")
public class SoHScheduleController extends CommonController {
    @Autowired
    private SoHService soHService;

    @PostMapping("/create")
    public ResponseEntity<?> createSohSchedule(@RequestParam(value = "strId") String strId,
                                            @RequestParam(value = "startTime") LocalDateTime startTime) {
        try {
            soHService.createSchedule(strId,startTime);
            return toSuccessResultNull();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSohSchedule(@RequestParam(value = "id") Long id,
                                            @RequestParam(value = "startTime") LocalDateTime startTime) {
        try {
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
