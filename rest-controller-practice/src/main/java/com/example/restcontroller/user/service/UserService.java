package com.example.restcontroller.user.service;

import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.user.model.UserNoticeCount;
import com.example.restcontroller.user.model.UserLogCount;
import com.example.restcontroller.user.model.UserResponse;
import com.example.restcontroller.user.model.UserSummary;

import java.util.List;

public interface UserService {

    UserSummary getUserStatusCount();

    List<UserResponse> getTodayUsers();

    List<UserNoticeCount> getUserNoticeCount();

    List<UserLogCount> getUserLogCount();

    List<UserLogCount> getUserLikeBest();

    ServiceResult addInterestUser(Long id, String email);
}
