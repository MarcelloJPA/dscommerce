package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    //Busca o ProdutoBD, converte para o DTO e envia para o ControladorREST(ProductController)
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow( //orElseThrow já verifica se existe produto dentro do Optinal, então nao precisa do get()
                () -> new ResourceNotFoundException("id não encontrado"));

        ProductDTO productDTO = new ProductDTO(product);

        return productDTO;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = productRepository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));

    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO){
        Product product = new Product();
        copyDtoToEntity(productDTO, product);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){
        try{
            Product product = productRepository.getReferenceById(id); //Não vai no banco, apenas pega a referencia.
            copyDtoToEntity(productDTO,product);
            product = productRepository.save(product);
            return new ProductDTO(product);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
           if(!productRepository.existsById(id)){
               throw new ResourceNotFoundException("Recurso não encontrado");
           }
             try {
                 productRepository.deleteById(id);
             }catch(DataIntegrityViolationException e){
                 throw new DatabaseException("Falha de integridade relacional");
             }

    }

    private void copyDtoToEntity(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());

    }




}
