package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.*;
import com.mns.cda.locmnsback.dto.LoanStateCreateDto;
import com.mns.cda.locmnsback.dto.LoanStateDto;
import com.mns.cda.locmnsback.dto.LoanStateUpdateDto;
import com.mns.cda.locmnsback.model.LoanState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanStateService {

    private final LoanStateDao loanStateDao;

    public List<LoanStateDto> getAll() {
        return loanStateDao.findAll()
                .stream()
                .map(state -> new LoanStateDto(state.getId(), state.getName()))
                .toList();
    }

    public LoanStateDto get(int id) {
        LoanState state = loanStateDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "État de prêt introuvable"
                ));

        return new LoanStateDto(state.getId(), state.getName());
    }

    public LoanStateDto create (LoanStateCreateDto dto) {
        LoanState state = new LoanState();
        state.setName(dto.name());

        LoanState saved = loanStateDao.save(state);

        return new LoanStateDto(saved.getId(), saved.getName());
    }

    public void delete(int id) {
        if (!loanStateDao.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "État de prêt introuvable"
            );
        }

        loanStateDao.deleteById(id);
    }

    public LoanStateDto update(int id, LoanStateUpdateDto dto) {

        LoanState state = loanStateDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "État de prêt introuvable"
                ));

        state.setName(dto.name());

        LoanState saved = loanStateDao.save(state);

        return new LoanStateDto(saved.getId(), saved.getName());
    }

}

