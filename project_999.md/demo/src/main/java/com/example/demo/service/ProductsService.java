package com.example.demo.service;


import com.example.demo.models.Image;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import jakarta.mail.Multipart;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductsService {
    private  final UserRepository userRepository;
    @Autowired
    private  final ProductRepository productRepository;

    public List<Product>listProducts(String title){
        if(title!=null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    public void saveProducts(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipale(principal));
        Image image1;
        Image image2;
        Image image3;
        if(file1.getSize() != 0){
            image1= toImageEntity(file1);

            product.addImageToProduct(image1);
        }
        if(file2.getSize() != 0){
            image2= toImageEntity(file2);

            product.addImageToProduct(image2);
        }
        if(file3.getSize() != 0){
            image3= toImageEntity(file3);

            product.addImageToProduct(image3);

        }
            log.info("Saving new Product.Title{}; Email: {}",product.getTitle(),product.getUser().getEmail());
        productRepository.save(product);

        Product productFromBD =productRepository.save(product);
        productFromBD.setPrevImageId(productFromBD.getImages().get(0).getId());
        productRepository.save(product);
    }

    public User getUserByPrincipale(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }



    private Image toImageEntity(MultipartFile file)throws IOException{
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    public void deleteProduct(Long id){
       productRepository.deleteById(id);
    }
    public Product getProductById(Long id) {
      return productRepository.findById(id).orElse(null);
    }
}

