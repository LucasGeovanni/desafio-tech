package dev.lucas.desafiotech.service.impl;

import dev.lucas.desafiotech.exception.NotFoundException;
import dev.lucas.desafiotech.exception.RecordAlreadyExistsException;
import dev.lucas.desafiotech.mappers.ResaleMapper;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.entities.ResaleEntity;
import dev.lucas.desafiotech.model.enums.OrderStatus;
import dev.lucas.desafiotech.repository.ResaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResaleServiceImplTest {

    @Mock
    private ResaleRepository resaleRepository;

    @Spy
    private ResaleMapper resaleMapper = Mappers.getMapper(ResaleMapper.class);

    @InjectMocks
    private ResaleServiceImpl resaleService;

    private Resale resale;
    private ResaleEntity resaleEntity;
    private UUID resaleUUID;

    @BeforeEach
    void setUp() {
        resaleUUID = UUID.randomUUID();
        resale = new Resale(1L, resaleUUID, "12345678000199", "Resale Test", "Resale LTDA", "email@resale.com", null, null, null);
        resaleEntity = new ResaleEntity(1L, resaleUUID, "12345678000199", "Resale Test", "Resale LTDA", "email@resale.com", null, null, null);
    }

    @Test
    void shouldSaveNewResaleSuccessfully() {
        when(resaleRepository.existsByCnpj(resale.cnpj())).thenReturn(false);
        when(resaleRepository.save(any(ResaleEntity.class))).thenReturn(resaleEntity);

        UUID result = resaleService.save(resale);

        assertEquals(resaleUUID, result);
        verify(resaleRepository, times(1)).save(any(ResaleEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenResaleAlreadyExists() {
        when(resaleRepository.existsByCnpj(resale.cnpj())).thenReturn(true);

        assertThrows(RecordAlreadyExistsException.class, () -> resaleService.save(resale));
        verify(resaleRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllResales() {
        List<ResaleEntity> resaleEntities = List.of(resaleEntity);
        when(resaleRepository.findAll()).thenReturn(resaleEntities);

        List<Resale> result = resaleService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(resale.cnpj(), result.get(0).cnpj());
        verify(resaleRepository, times(1)).findAll();
    }

    @Test
    void shouldFindResaleByUUID() {
        when(resaleRepository.findByUuid(resaleUUID)).thenReturn(Optional.of(resaleEntity));

        Resale result = resaleService.findByUUID(resaleUUID);

        assertNotNull(result);
        assertEquals(resaleUUID, result.uuid());
        verify(resaleRepository, times(1)).findByUuid(resaleUUID);
    }

    @Test
    void shouldThrowExceptionWhenResaleNotFoundByUUID() {
        when(resaleRepository.findByUuid(resaleUUID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> resaleService.findByUUID(resaleUUID));
        verify(resaleRepository, times(1)).findByUuid(resaleUUID);
    }

    @Test
    void shouldReturnPendingOrdersWithoutSupplier() {
        List<ResaleEntity> resaleEntities = List.of(resaleEntity);
        when(resaleRepository.findResellersForOrderIssuance(OrderStatus.PENDENTE, 100)).thenReturn(resaleEntities);

        List<Resale> result = resaleService.findPendingOrdersWithoutSupplier(OrderStatus.PENDENTE, 100);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(resaleRepository, times(1)).findResellersForOrderIssuance(OrderStatus.PENDENTE, 100);
    }
}
