package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    CurvePointRepository curvePointRepository;

    public List<CurvePoint> getAllCurvePoints(){
    return curvePointRepository.findAll();
    }

    public void saveCurvePoint(CurvePoint curvePoint){
        curvePointRepository.save(curvePoint);
    }

    public void deleteCurvePoint(Integer id){
        curvePointRepository.deleteById(id);
    }

    public void getCurvePoint(Integer id){
        curvePointRepository.findById(id);
    }
}
