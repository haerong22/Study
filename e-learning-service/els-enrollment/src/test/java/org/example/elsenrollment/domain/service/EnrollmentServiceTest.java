package org.example.elsenrollment.domain.service;

import org.example.elsenrollment.domain.entity.Enrollment;
import org.example.elsenrollment.domain.entity.Subscription;
import org.example.elsenrollment.domain.repository.EnrollmentRepository;
import org.example.elsenrollment.domain.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void test_register_course() {
        // given
        long userId = 1L;
        long courseId = 100L;
        long paymentId = 200L;

        Enrollment mockEnrollment = new Enrollment();
        mockEnrollment.setUserId(userId);
        mockEnrollment.setCourseId(courseId);
        mockEnrollment.setPaymentId(paymentId);
        mockEnrollment.setRegistrationDate(LocalDateTime.now());

        given(enrollmentRepository.save(any(Enrollment.class))).willReturn(mockEnrollment);

        // when
        Enrollment result = enrollmentService.registerCourse(userId, courseId, paymentId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getCourseId()).isEqualTo(courseId);
        assertThat(result.getPaymentId()).isEqualTo(paymentId);
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void test_manage_subscription() {
        // given
        long userId = 1L;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(30);
        long paymentId = 1L;

        Subscription mockSubscription = new Subscription();
        mockSubscription.setUserId(userId);
        mockSubscription.setStartDate(startDate);
        mockSubscription.setEndDate(endDate);
        mockSubscription.setPaymentId(paymentId);

        given(subscriptionRepository.save(any(Subscription.class))).willReturn(mockSubscription);

        // when
        Subscription result = enrollmentService.manageSubscription(userId, startDate, endDate, paymentId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getStartDate()).isEqualTo(startDate);
        assertThat(result.getEndDate()).isEqualTo(endDate);
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void test_renew_subscription() {
        // given
        long subscriptionId = 300L;
        LocalDateTime newStartDate = LocalDateTime.now();
        LocalDateTime newEndDate = LocalDateTime.now().plusDays(30);

        Subscription foundSubscription = new Subscription();
        foundSubscription.setStartDate(LocalDateTime.now().minusDays(15));
        foundSubscription.setEndDate(LocalDateTime.now().minusDays(1));

        given(subscriptionRepository.findById(subscriptionId)).willReturn(Optional.of(foundSubscription));
        given(subscriptionRepository.save(any(Subscription.class))).willReturn(foundSubscription);

        // when
        Subscription result = enrollmentService.renewSubscription(subscriptionId, newStartDate, newEndDate);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStartDate()).isEqualTo(newStartDate);
        assertThat(result.getEndDate()).isEqualTo(newEndDate);
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void test_renew_subscription_not_found() {
        // given
        long subscriptionId = 300L;
        given(subscriptionRepository.findById(subscriptionId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> enrollmentService.renewSubscription(subscriptionId, LocalDateTime.now(), LocalDateTime.now().plusDays(30)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void test_check_course_access() {
        // given
        long userId = 1L;
        long courseId = 100L;
        given(enrollmentRepository.findByUserIdAndCourseId(userId, courseId)).willReturn(Optional.of(new Enrollment()));

        // when
        boolean result = enrollmentService.checkCourseAccess(userId, courseId);

        // then
        assertThat(result).isTrue();
        verify(enrollmentRepository).findByUserIdAndCourseId(userId, courseId);
    }

    @Test
    void test_check_subscription_access() {
        // given
        long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Subscription subscription = new Subscription();
        subscription.setEndDate(now.plusDays(5));

        given(subscriptionRepository.findTopByUserIdAndEndDateAfterOrderByEndDateDesc(eq(userId), any())).willReturn(Optional.of(subscription));

        // when
        boolean result = enrollmentService.checkSubscriptionAccess(userId, now);

        // then
        assertThat(result).isTrue();
        verify(subscriptionRepository).findTopByUserIdAndEndDateAfterOrderByEndDateDesc(userId, now);
    }

    @Test
    void test_check_subscription_access_invalid() {
        // given
        long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Subscription subscription = new Subscription();
        subscription.setEndDate(now.minusDays(5));

        given(subscriptionRepository.findTopByUserIdAndEndDateAfterOrderByEndDateDesc(eq(userId), any())).willReturn(Optional.of(subscription));

        // when
        boolean result = enrollmentService.checkSubscriptionAccess(userId, now);

        // then
        assertThat(result).isFalse();
        verify(subscriptionRepository).findTopByUserIdAndEndDateAfterOrderByEndDateDesc(userId, now);
    }
}