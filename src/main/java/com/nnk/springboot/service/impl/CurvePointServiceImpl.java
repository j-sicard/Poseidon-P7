package com.nnk.springboot.service.impl;

import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    CurvePointRepository curvePointRepository;

    public List<CurvePointModel> getAllCurvePoints(){
    return curvePointRepository.findAll();
    }

    public void saveCurvePoint(CurvePointModel curvePoint){
        curvePointRepository.save(curvePoint);
    }

    public void deleteCurvePoint(CurvePointModel curvePoint){
        curvePointRepository.delete(curvePoint);
    }

    public Optional<CurvePointModel> getCurvePoint(Integer id){
       return curvePointRepository.findById(id);
    }


}
