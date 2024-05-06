package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("garageService")
@Transactional
public class GarageServiceImpl implements GarageService {

    private GarageRepository garageRepository;

    @Autowired
    public GarageServiceImpl(GarageRepository garageRepository) { this.garageRepository = garageRepository; }

    @Override
    public List<Garage> getAll() {
        return garageRepository.findAll();
    }

    @Override
    public Garage findById(Integer id) { return garageRepository.findById(id); }
}
