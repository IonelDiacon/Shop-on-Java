package com.example.demo.repository;

import com.example.demo.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Image,Long> {
}
