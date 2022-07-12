package com.unvise.bankingsystemapp.domain.credit;

import com.unvise.bankingsystemapp.domain.common.BaseService;
import com.unvise.bankingsystemapp.domain.credit.web.dto.CreditDto;
import org.springframework.stereotype.Service;

@Service
public interface CreditService extends BaseService<CreditDto, Long> {
}
