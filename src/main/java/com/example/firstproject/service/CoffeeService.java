package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // 전체 데이터 조회
    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    // 단일 데이터 조회
    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    // 데이터 생성
    public Coffee create(CoffeeDto dto) {
        Coffee coffeeEntity = dto.toEntity();
        if (coffeeEntity.getId() != null) {
            return null;
        }
        return coffeeRepository.save(coffeeEntity);
    }

    // 데이터 수정
    public Coffee update(Long id, CoffeeDto dto) {
        // 수정될 데이터
        Coffee newEntity = dto.toEntity();
        // 기존 데이터 불러오기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        if (target == null || id != newEntity.getId()) {
            return null;
        }

        // 데이터 수정
        target.patch(newEntity);
        Coffee saved = coffeeRepository.save(target);
        return saved;
    }

    // 데이터 삭제
    public Coffee delete(Long id){
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null){
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }
}
