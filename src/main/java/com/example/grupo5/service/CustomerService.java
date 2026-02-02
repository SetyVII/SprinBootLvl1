package com.example.grupo5.service;

import com.example.grupo5.Exceptions.NotFoundException;
import com.example.grupo5.dto.CustomerDTO;
import com.example.grupo5.entity.Customer;
import com.example.grupo5.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

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
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        // HASH PASSWORD: change on DTO
        customer.setPassword(customerDTO.getPassword());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setAddress(customerDTO.getAddress());
        customerRepository.save(customer);
    }


    public CustomerDTO toDTO (Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setId(customer.getId());
        return customerDTO;
    }
}
