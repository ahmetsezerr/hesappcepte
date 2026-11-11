package com.hesappcepte.financearea;

import com.hesappcepte.financearea.dto.CreateFinanceAreaRequest;
import com.hesappcepte.financearea.dto.FinanceAreaResponse;
import com.hesappcepte.financearea.dto.UpdateFinanceAreaRequest;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/finance-areas")
public class FinanceAreaController {
    private final FinanceAreaService financeAreaService;

    public FinanceAreaController(FinanceAreaService financeAreaService) {
        this.financeAreaService = financeAreaService;
    }

    @PostMapping
    public ResponseEntity<FinanceAreaResponse>createFinanceArea
            (@Valid @RequestBody CreateFinanceAreaRequest request, Authentication authentication)
    {
        Long userId = (Long) authentication.getPrincipal();

        FinanceAreaResponse areaResponse
                = financeAreaService.createFinanceArea(request,userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(areaResponse);

    }

    @GetMapping
    public ResponseEntity<List<FinanceAreaResponse>> getFinanceAreas
            (Authentication authentication){

        Long userId = (Long) authentication.getPrincipal();

        List<FinanceAreaResponse> areaResponse  = financeAreaService.getFinanceAreas(userId);


        return ResponseEntity.ok(areaResponse);

    }

    @GetMapping("/{financeAreaId}")
    public ResponseEntity<FinanceAreaResponse>getFinanceAreaById
            (Authentication authentication, @PathVariable Long financeAreaId){

        Long userId = (Long) authentication.getPrincipal();

        FinanceAreaResponse areaResponse  = financeAreaService.getFinanceAreaById(userId,financeAreaId);

        return ResponseEntity.ok(areaResponse);
    }

    @PatchMapping("/{financeAreaId}")
    public ResponseEntity<FinanceAreaResponse>updateFinanceArea
            ( Authentication authentication,@PathVariable Long financeAreaId, @Valid @RequestBody UpdateFinanceAreaRequest request){

        Long userId = (Long) authentication.getPrincipal();

        FinanceAreaResponse response = financeAreaService.updateFinanceArea(userId,financeAreaId,request);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/{financeAreaId}/archive")
    public ResponseEntity<FinanceAreaResponse>archiveFinanceArea
            (Authentication authentication,@PathVariable Long financeAreaId){

        Long userId = (Long) authentication.getPrincipal();

        FinanceAreaResponse response = financeAreaService.archiveFinanceArea(userId,financeAreaId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{financeAreaId}/restore")
    public ResponseEntity<FinanceAreaResponse>restoreFinanceArea
            (Authentication authentication,@PathVariable Long financeAreaId){

        Long userId = (Long) authentication.getPrincipal();

        FinanceAreaResponse response = financeAreaService.restoreFinanceArea(userId,financeAreaId);

        return ResponseEntity.ok(response);

    }
}
