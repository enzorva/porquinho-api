package br.com.fiap.porquinho.service.AccountType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.porquinho.domainmodel.AccountType;
import br.com.fiap.porquinho.domainmodel.repositories.AccountTypeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService<AccountType, Long> {

    private final AccountTypeRepository accountTypeRepository;

    @Override
    public List<AccountType> findAll() {
        return new ArrayList<>(accountTypeRepository.findAll());
    }

    @Override
    public Optional<AccountType> findById(Long id) {
        return accountTypeRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return accountTypeRepository.existsById(id);
    }

}
