package co.edu.udes.activity.backend.demo.controllers;

import co.edu.udes.activity.backend.demo.dto.CustomerDTO;
import co.edu.udes.activity.backend.demo.models.Customer;
import co.edu.udes.activity.backend.demo.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO dto) {
        Customer customer = customerService.createCustomer(dto.getName(), dto.getEmail(), dto.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(customer, CustomerDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(customer -> ResponseEntity.ok(modelMapper.map(customer, CustomerDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        return customerService.updateCustomer(id, dto.getName(), dto.getEmail(), dto.getPhoneNumber())
                .map(customer -> ResponseEntity.ok(modelMapper.map(customer, CustomerDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        boolean deleted = customerService.deleteCustomer(id);
        return deleted ?
                ResponseEntity.ok("Cliente eliminado correctamente") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el cliente con ID: " + id);
    }
}
