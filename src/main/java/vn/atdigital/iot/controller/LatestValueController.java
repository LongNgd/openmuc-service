package vn.atdigital.iot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.iot.common.Constants;
import vn.atdigital.iot.domain.dto.StringDetailDTO;
import vn.atdigital.iot.service.LatestValueService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/latest-value")
@RequiredArgsConstructor
public class LatestValueController extends CommonController {
    private final LatestValueService latestValueService;

    @GetMapping("/dev")
    public ResponseEntity<?> getDevValues() {
        try {
            Map<String, String> devValues = latestValueService.getDevValues();
            return toSuccessResult(devValues);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @GetMapping("/site-name")
    public ResponseEntity<?> getSiteNames() {
        try {
            String siteName = latestValueService.getSiteName();
            return toSuccessResult(siteName);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @GetMapping("/string")
    public ResponseEntity<?> getStringValues(@RequestParam String stringId) {
        try {
            StringDetailDTO stringDetailDTO = latestValueService.getStringDetails(stringId);
            return toSuccessResult(stringDetailDTO);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @PostMapping("/delete-string")
    public ResponseEntity<?> deleteStringValues(@RequestParam String stringId) {
        try {
            latestValueService.deleteString(stringId);
            return toSuccessResultNull();
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), Constants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }
}
