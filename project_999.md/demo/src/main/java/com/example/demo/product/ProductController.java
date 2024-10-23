package com.example.demo.product;


import com.example.demo.models.Product;

import com.example.demo.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ProductController {
   private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Principal principal, Model model) {
        model.addAttribute("products", productsService.listProducts(title));
        model.addAttribute("user", productsService.getUserByPrincipale(principal));
        return "products";
    }

    @GetMapping("/product/{id}")

    public String productInfo(@PathVariable Long id, Model model) {
        Product product = productsService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }


    @PostMapping("/product/create")
    public String createProduct(Principal principal,@RequestParam("file1")MultipartFile file1, @RequestParam("file2")MultipartFile file2,
                                @RequestParam("file2")MultipartFile file3 , Product product) throws IOException {

        productsService.saveProducts(principal,product,file1,file2,file3);
        return "redirect:/";
    }
    @PostMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productsService.deleteProduct(id);
        return "redirect:/";
    }


}
