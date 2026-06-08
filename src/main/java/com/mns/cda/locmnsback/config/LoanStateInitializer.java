package com.mns.cda.locmnsback.config;

import com.mns.cda.locmnsback.dao.LoanStateDao;
import com.mns.cda.locmnsback.enums.LoanStatus;
import com.mns.cda.locmnsback.model.LoanState;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanStateInitializer implements CommandLineRunner {

    private final LoanStateDao loanStateDao;

    @Override
    public void run(String... args) {

        for (LoanStatus status : LoanStatus.values()) {

            if (loanStateDao.findByName(status.name()) == null) {
                LoanState state = new LoanState();
                state.setName(status.name());
                loanStateDao.save(state);
            }
        }
    }
}
