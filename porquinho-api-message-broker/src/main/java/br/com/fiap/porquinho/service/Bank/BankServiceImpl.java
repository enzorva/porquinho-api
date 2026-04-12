package br.com.fiap.porquinho.service.Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.porquinho.domainmodel.Bank;
import br.com.fiap.porquinho.domainmodel.repositories.BankRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService<Bank, Long> {

    private final BankRepository accountTypeRepository;

    @Override
    public List<Bank> findAll() {
        return new ArrayList<>(accountTypeRepository.findAll());
    }

    @Override
    public Optional<Bank> findById(Long id) {
        return accountTypeRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return accountTypeRepository.existsById(id);
    }

}
