package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // 전체 데이터 조회
    @GetMapping("/api/coffees")
    public List<Coffee> index(){
        return coffeeRepository.findAll();
    }

    // 단일 데이터 조회
    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id){
        return  coffeeRepository.findById(id).orElse(null);
    }

    // 데이터 생성
    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeDto dto){
        Coffee coffeeEntity = dto.toEntity();
        return coffeeRepository.save(coffeeEntity);
    }

    // 데이터 수정
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto){
        // 수정될 데이터
        Coffee newEntity = dto.toEntity();
        // 기존 데이터 불러오기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        if(target==null || id != newEntity.getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 데이터 수정
        target.patch(newEntity);
        Coffee saved = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    // 데이터 삭제
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
