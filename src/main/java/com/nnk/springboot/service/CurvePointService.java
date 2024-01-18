package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {
    public List<CurvePoint> getAllCurvePoints();
    public void saveCurvePoint(CurvePoint curvePoint);

    public void deleteCurvePoint(CurvePoint curvePoint);

    public Optional<CurvePoint> getCurvePoint(Integer id);
}
