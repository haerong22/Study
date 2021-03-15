package com.example.restcontroller.user.service;

import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.model.*;

import java.util.List;

public interface UserService {

    UserSummary getUserStatusCount();

    List<UserResponse> getTodayUsers();

    List<UserNoticeCount> getUserNoticeCount();

    List<UserLogCount> getUserLogCount();

    List<UserLogCount> getUserLikeBest();

    ServiceResult addInterestUser(Long id, String email);

    ServiceResult deleteInterestUser(Long id, String email);

    User login(UserLogin userLogin);
}
