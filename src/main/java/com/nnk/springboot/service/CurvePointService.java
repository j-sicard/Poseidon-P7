package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {
    public List<CurvePoint> getAllCurvePoints();
    public void saveCurvePoint(CurvePoint curvePoint);

    public void deleteCurvePoint(Integer id);

    public void getCurvePoint(Integer id);
}
