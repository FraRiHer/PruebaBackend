package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.DrogueriaCliente;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.DrogueriaClienteRepository;
import com.example.demo.repository.DrogueriaRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired 
    DrogueriaClienteRepository drogueriaClienteRepository;

    @Autowired 
    DrogueriaRepository drogueriaRepository;


    @Transactional
    public Cliente save(Cliente cliente, long idDrogueria){
        Optional<Cliente> clientev = clienteRepository.findByCedula(cliente.getCedula());
        if(clientev.isPresent()){
            DrogueriaCliente drogueriaCliente = new DrogueriaCliente();
            drogueriaCliente.setCliente(clientev.get());
            drogueriaCliente.setDrogueria(drogueriaRepository.findById(idDrogueria).get());
            drogueriaClienteRepository.save(drogueriaCliente);

            return clientev.get();
        }else{
            
            clienteRepository.save(cliente);
            DrogueriaCliente drogueriaCliente = new DrogueriaCliente();
            drogueriaCliente.setCliente(cliente);
            drogueriaCliente.setDrogueria(drogueriaRepository.findById(idDrogueria).get());
            drogueriaClienteRepository.save(drogueriaCliente);
            return cliente;
        }
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id){
        return clienteRepository.findById(id);
    } 

    
}
