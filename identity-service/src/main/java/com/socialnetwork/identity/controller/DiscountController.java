package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.response.PageResponse;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.dto.response.DiscountResponseDTO;
import com.socialnetwork.identity.service.DiscountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/discount")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountController {
    @Autowired
    DiscountService discountRepository;

    @PostMapping(value = "/import", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<Void> importFile(@RequestBody String filePath,
                                           @RequestParam(value = "createDate") String createDate) {
        discountRepository.importDiscounts(filePath, createDate);
        return ResponseObject.<Void>builder()
                .build();
    }

    @GetMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<PageResponse<DiscountResponseDTO>> getUsers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                      @RequestParam(value = "address", required = false) String address,
                                                                      @RequestParam(value = "description", required = false) String description) {
        return ResponseObject.<PageResponse<DiscountResponseDTO>>builder()
                .result(discountRepository.getDiscounts(address, description, pageNumber, pageSize))
                .build();
    }

    @PutMapping(value = "/apply/{discountId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<Void> applyById(@PathVariable(value = "discountId") String discountId) {
        discountRepository.applyDiscount(discountId);
        return ResponseObject.<Void>builder().build();
    }
}
