package com.unvise.bankingsystemapp.credit.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.credit.CreditService;
import com.unvise.bankingsystemapp.credit.web.dto.CreditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @GetMapping
    @JsonView({View.Details.class})
    public ResponseEntity<List<CreditDto>> getAll() {
        return new ResponseEntity<>(creditService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView({View.Details.class})
    public ResponseEntity<CreditDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(creditService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @JsonView({View.Details.class})
    public ResponseEntity<CreditDto> save(@Validated(View.New.class)
                                          @RequestBody CreditDto creditDto) {
        return new ResponseEntity<>(creditService.save(creditDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @JsonView({View.Details.class})
    public ResponseEntity<CreditDto> fullyUpdate(@PathVariable Long id,
                                                 @Validated(View.Update.class)
                                                 @RequestBody CreditDto creditDto) {
        return new ResponseEntity<>(creditService.updateById(id, creditDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @JsonView({View.Details.class})
    public ResponseEntity<CreditDto> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(creditService.deleteById(id), HttpStatus.OK);
    }

}
