package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.DrogueriaCliente;
import com.example.demo.repository.DrogueriaClienteRepository;

@Service
public class DrogueriaClienteService {
    @Autowired
    DrogueriaClienteRepository drogueriaClienterClienteRepository;

    public DrogueriaCliente save(DrogueriaCliente drogueriaCliente){
        return drogueriaClienterClienteRepository.save(drogueriaCliente);
    }

    public List<DrogueriaCliente> findAll(){
        return drogueriaClienterClienteRepository.findAll();
    }

    public List<DrogueriaCliente> findByDrogueria(Long drogueria_id){
        return drogueriaClienterClienteRepository.findByDrogueriaId(drogueria_id);
    }
    
}
