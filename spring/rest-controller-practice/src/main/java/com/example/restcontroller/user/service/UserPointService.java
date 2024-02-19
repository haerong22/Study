package com.example.restcontroller.user.service;

import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.user.model.UserPointInput;
import com.example.restcontroller.user.model.UserPointType;

public interface UserPointService {

    ServiceResult addPoint(String email, UserPointInput userPointInput);
}
