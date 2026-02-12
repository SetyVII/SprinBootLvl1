package com.example.grupo5.service;

import com.example.grupo5.Exceptions.NotFoundException;
import com.example.grupo5.dto.CustomerDTO;
import com.example.grupo5.entity.Customer;
import com.example.grupo5.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // TODO: if not email what happens?
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        saving(customerDTO, customer);
        return customerDTO;

    }

    public List<CustomerDTO> findAll (){
        return customerRepository.findAll().stream().map(this::toDTO).toList();
    }

    public CustomerDTO findById(int id) {
        return customerRepository.findById(id).map(this::toDTO).orElseThrow(() -> new NotFoundException("TEST: Customer not found with id; " + id));
    }
    
    public CustomerDTO update(int id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
        saving(customerDTO, customer);
        return toDTO(customer);
    }

    public CustomerDTO delete(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
        customerRepository.deleteById(id);
        return toDTO(customer);
    }

    private void saving(CustomerDTO customerDTO, Customer customer) {
        customer.setFirstName(customerDTO.firstName());
        customer.setLastName(customerDTO.lastName());
        customer.setEmail(customerDTO.email());
        // HASH PASSWORD: change on DTO
        customer.setPassword(customerDTO.password());
        customer.setPhoneNumber(customerDTO.phoneNumber());
        customer.setAddress(customerDTO.address());
        customerRepository.save(customer);
    }


    public CustomerDTO toDTO (Customer customer){
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getAddress(),
                customer.getPhoneNumber()
        );
    }
}
