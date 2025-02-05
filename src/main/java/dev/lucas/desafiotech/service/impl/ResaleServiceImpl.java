package dev.lucas.desafiotech.service.impl;

import dev.lucas.desafiotech.exception.NotFoundException;
import dev.lucas.desafiotech.exception.RecordAlreadyExistsException;
import dev.lucas.desafiotech.mappers.ResaleMapper;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.entities.ResaleEntity;
import dev.lucas.desafiotech.model.enums.OrderStatus;
import dev.lucas.desafiotech.repository.ResaleRepository;
import dev.lucas.desafiotech.service.ResaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResaleServiceImpl implements ResaleService {

    private final ResaleRepository revendaRepository;
    private final ResaleMapper resaleMapper;

    @Override
    public UUID save(Resale resale) {
        log.info("Registering new resale: {}", resale);

        if (revendaRepository.existsByCnpj(resale.cnpj())) {
            log.error("CNPJ {} is already registered!", resale.cnpj());
            throw new RecordAlreadyExistsException("Resale already registered!");
        }

        ResaleEntity resaleEntity = resaleMapper.to(resale);
        ResaleEntity save = revendaRepository.save(resaleEntity);
        log.info("Resale {} successfully registered!", resale);

        return save.getUuid();
    }

    @Override
    public List<Resale> findAll() {
        log.info("Fetching all resales.");
        return revendaRepository.findAll()
                .stream()
                .map(resaleMapper::to)
                .toList();
    }

    @Override
    public Resale findByUUID(UUID uuid) {
        log.info("Searching resale by UUID: {}", uuid);
        return revendaRepository.findByUuid(uuid)
                .map(resaleMapper::to)
                .orElseThrow(() -> {
                    log.warn("Resale not found: {}", uuid);
                    return new NotFoundException("Resale not found: " + uuid);
                });
    }

    @Override
    public List<Resale> findPendingOrdersWithoutSupplier(OrderStatus orderStatus, Integer qtdMin) {
        log.info("Fetching pending orders with status {} and minimum quantity {}", orderStatus, qtdMin);
        return revendaRepository.findResellersForOrderIssuance(orderStatus, qtdMin)
                .stream()
                .map(resaleMapper::to).toList();
    }
}
