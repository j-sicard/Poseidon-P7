package com.nnk.springboot.service;

import com.nnk.springboot.model.CurvePointModel;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {
    public List<CurvePointModel> getAllCurvePoints();
    public void saveCurvePoint(CurvePointModel curvePoint);

    public void deleteCurvePoint(CurvePointModel curvePoint);

    public Optional<CurvePointModel> getCurvePoint(Integer id);
}
