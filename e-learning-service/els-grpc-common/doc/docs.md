# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [enrollment_service.proto](#enrollment_service-proto)
    - [CourseAccessRequest](#org-example-elsenrollment-domain-service-CourseAccessRequest)
    - [CourseAccessResponse](#org-example-elsenrollment-domain-service-CourseAccessResponse)
    - [CourseRegistrationRequest](#org-example-elsenrollment-domain-service-CourseRegistrationRequest)
    - [CourseRegistrationResponse](#org-example-elsenrollment-domain-service-CourseRegistrationResponse)
    - [Enrollment](#org-example-elsenrollment-domain-service-Enrollment)
    - [PaymentRequest](#org-example-elsenrollment-domain-service-PaymentRequest)
    - [PaymentResponse](#org-example-elsenrollment-domain-service-PaymentResponse)
    - [PaymentsByIdRequest](#org-example-elsenrollment-domain-service-PaymentsByIdRequest)
    - [PaymentsByIdResponse](#org-example-elsenrollment-domain-service-PaymentsByIdResponse)
    - [RenewSubscriptionRequest](#org-example-elsenrollment-domain-service-RenewSubscriptionRequest)
    - [Subscription](#org-example-elsenrollment-domain-service-Subscription)
    - [SubscriptionAccessRequest](#org-example-elsenrollment-domain-service-SubscriptionAccessRequest)
    - [SubscriptionAccessResponse](#org-example-elsenrollment-domain-service-SubscriptionAccessResponse)
    - [SubscriptionRequest](#org-example-elsenrollment-domain-service-SubscriptionRequest)
    - [SubscriptionResponse](#org-example-elsenrollment-domain-service-SubscriptionResponse)
    - [UserEnrollmentsRequest](#org-example-elsenrollment-domain-service-UserEnrollmentsRequest)
    - [UserEnrollmentsResponse](#org-example-elsenrollment-domain-service-UserEnrollmentsResponse)
    - [UserPaymentsRequest](#org-example-elsenrollment-domain-service-UserPaymentsRequest)
    - [UserPaymentsResponse](#org-example-elsenrollment-domain-service-UserPaymentsResponse)
    - [UserSubscriptionsRequest](#org-example-elsenrollment-domain-service-UserSubscriptionsRequest)
    - [UserSubscriptionsResponse](#org-example-elsenrollment-domain-service-UserSubscriptionsResponse)
  
    - [EnrollmentService](#org-example-elsenrollment-domain-service-EnrollmentService)
    - [FakePaymentService](#org-example-elsenrollment-domain-service-FakePaymentService)
  
- [playback_service.proto](#playback_service-proto)
    - [EndRecordRequest](#org-example-elsplayback-domain-service-EndRecordRequest)
    - [EndRecordResponse](#org-example-elsplayback-domain-service-EndRecordResponse)
    - [EventLog](#org-example-elsplayback-domain-service-EventLog)
    - [LogEventRequest](#org-example-elsplayback-domain-service-LogEventRequest)
    - [LogEventResponse](#org-example-elsplayback-domain-service-LogEventResponse)
    - [PlaybackRecord](#org-example-elsplayback-domain-service-PlaybackRecord)
    - [StartRecordRequest](#org-example-elsplayback-domain-service-StartRecordRequest)
    - [StartRecordResponse](#org-example-elsplayback-domain-service-StartRecordResponse)
  
    - [PlaybackService](#org-example-elsplayback-domain-service-PlaybackService)
  
- [Scalar Value Types](#scalar-value-types)



<a name="enrollment_service-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## enrollment_service.proto



<a name="org-example-elsenrollment-domain-service-CourseAccessRequest"></a>

### CourseAccessRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| course_id | [int64](#int64) |  | 강의 ID |
| user_id | [int64](#int64) |  | 사용자 ID |






<a name="org-example-elsenrollment-domain-service-CourseAccessResponse"></a>

### CourseAccessResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| course_id | [int64](#int64) |  | 강의 ID |
| has_access | [bool](#bool) |  | 접근 권한 여부 |






<a name="org-example-elsenrollment-domain-service-CourseRegistrationRequest"></a>

### CourseRegistrationRequest
EnrollmentService에 대한 요청 및 응답 메시지 정의


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |
| course_id | [int64](#int64) |  | 강의 ID |
| payment_id | [int64](#int64) |  | 결제 ID |






<a name="org-example-elsenrollment-domain-service-CourseRegistrationResponse"></a>

### CourseRegistrationResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| course_id | [int64](#int64) |  | 강의 ID |
| user_id | [int64](#int64) |  | 사용자 ID |
| registration_date | [int64](#int64) |  | 등록 날짜 (Unix 타임스탬프) |






<a name="org-example-elsenrollment-domain-service-Enrollment"></a>

### Enrollment



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| enrollment_id | [int64](#int64) |  | 등록 ID |
| user_id | [int64](#int64) |  | 사용자 ID |
| course_id | [int64](#int64) |  | 강의 ID |
| registration_date | [int64](#int64) |  | 등록 날짜 (Unix 타임스탬프) |
| status | [string](#string) |  | 등록 상태 (예: Active, Completed) |
| payment_id | [int64](#int64) |  | 결제 ID |






<a name="org-example-elsenrollment-domain-service-PaymentRequest"></a>

### PaymentRequest
FakePaymentService에 대한 요청 및 응답 메시지 정의


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |
| type | [string](#string) |  | 결제 유형 (&#34;COURSE&#34; 또는 &#34;SUBSCRIPTION&#34;) |
| amount | [double](#double) |  | 결제 금액 |
| payment_method | [string](#string) |  | 결제 방법 (예: &#34;CARD&#34;) |






<a name="org-example-elsenrollment-domain-service-PaymentResponse"></a>

### PaymentResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |
| type | [string](#string) |  | 결제 유형 |
| amount | [double](#double) |  | 결제 금액 |
| payment_method | [string](#string) |  | 결제 방법 |
| payment_id | [int64](#int64) |  | 결제 ID |
| payment_date | [int64](#int64) |  | 결제 날짜 (Unix 타임스탬프) |
| payment_successful | [bool](#bool) |  | 결제 성공 여부 |






<a name="org-example-elsenrollment-domain-service-PaymentsByIdRequest"></a>

### PaymentsByIdRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| payment_id | [int64](#int64) |  | 결제 ID |






<a name="org-example-elsenrollment-domain-service-PaymentsByIdResponse"></a>

### PaymentsByIdResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| payment | [PaymentResponse](#org-example-elsenrollment-domain-service-PaymentResponse) |  | 결제 정보 |






<a name="org-example-elsenrollment-domain-service-RenewSubscriptionRequest"></a>

### RenewSubscriptionRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| subscription_id | [int64](#int64) |  | 구독 ID |
| start_date | [int64](#int64) |  | 시작 날짜 (Unix 타임스탬프) |
| end_date | [int64](#int64) |  | 종료 날짜 (Unix 타임스탬프) |






<a name="org-example-elsenrollment-domain-service-Subscription"></a>

### Subscription



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| subscription_id | [int64](#int64) |  | 구독 ID |
| user_id | [int64](#int64) |  | 사용자 ID |
| start_date | [int64](#int64) |  | 시작 날짜 (Unix 타임스탬프) |
| end_date | [int64](#int64) |  | 종료 날짜 (Unix 타임스탬프) |
| status | [string](#string) |  | 구독 상태 (예: Active, Expired) |
| payment_id | [int64](#int64) |  | 결제 ID |






<a name="org-example-elsenrollment-domain-service-SubscriptionAccessRequest"></a>

### SubscriptionAccessRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |






<a name="org-example-elsenrollment-domain-service-SubscriptionAccessResponse"></a>

### SubscriptionAccessResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| subscription_id | [int64](#int64) |  | 구독 ID |
| has_access | [bool](#bool) |  | 접근 권한 여부 |






<a name="org-example-elsenrollment-domain-service-SubscriptionRequest"></a>

### SubscriptionRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |
| start_date | [int64](#int64) |  | 시작 날짜 (Unix 타임스탬프) |
| end_date | [int64](#int64) |  | 종료 날짜 (Unix 타임스탬프) |
| payment_id | [int64](#int64) |  | 결제 ID |






<a name="org-example-elsenrollment-domain-service-SubscriptionResponse"></a>

### SubscriptionResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| subscription | [Subscription](#org-example-elsenrollment-domain-service-Subscription) |  | 구독 정보 |






<a name="org-example-elsenrollment-domain-service-UserEnrollmentsRequest"></a>

### UserEnrollmentsRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |






<a name="org-example-elsenrollment-domain-service-UserEnrollmentsResponse"></a>

### UserEnrollmentsResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| enrollments | [Enrollment](#org-example-elsenrollment-domain-service-Enrollment) | repeated | 사용자의 모든 강의 등록 내역 |






<a name="org-example-elsenrollment-domain-service-UserPaymentsRequest"></a>

### UserPaymentsRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |






<a name="org-example-elsenrollment-domain-service-UserPaymentsResponse"></a>

### UserPaymentsResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| payments | [PaymentResponse](#org-example-elsenrollment-domain-service-PaymentResponse) | repeated | 사용자의 모든 결제 내역 |






<a name="org-example-elsenrollment-domain-service-UserSubscriptionsRequest"></a>

### UserSubscriptionsRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |






<a name="org-example-elsenrollment-domain-service-UserSubscriptionsResponse"></a>

### UserSubscriptionsResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| subscriptions | [Subscription](#org-example-elsenrollment-domain-service-Subscription) | repeated | 사용자의 모든 구독 내역 |





 

 

 


<a name="org-example-elsenrollment-domain-service-EnrollmentService"></a>

### EnrollmentService
EnrollmentService는 강의 등록 및 구독 관리 서비스를 제공합니다.

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| RegisterCourse | [CourseRegistrationRequest](#org-example-elsenrollment-domain-service-CourseRegistrationRequest) | [CourseRegistrationResponse](#org-example-elsenrollment-domain-service-CourseRegistrationResponse) | RegisterCourse는 새로운 강의 등록 요청을 처리합니다. |
| ManageSubscription | [SubscriptionRequest](#org-example-elsenrollment-domain-service-SubscriptionRequest) | [SubscriptionResponse](#org-example-elsenrollment-domain-service-SubscriptionResponse) | ManageSubscription는 사용자의 새로운 구독을 등록하는 요청을 처리합니다. |
| RenewSubscription | [RenewSubscriptionRequest](#org-example-elsenrollment-domain-service-RenewSubscriptionRequest) | [SubscriptionResponse](#org-example-elsenrollment-domain-service-SubscriptionResponse) | RenewSubscription은 기존 구독을 갱신하는 요청을 처리합니다. |
| CheckCourseAccess | [CourseAccessRequest](#org-example-elsenrollment-domain-service-CourseAccessRequest) | [CourseAccessResponse](#org-example-elsenrollment-domain-service-CourseAccessResponse) | CheckCourseAccess는 특정 사용자가 특정 강의에 접근할 수 있는지 확인합니다. |
| CheckSubscriptionAccess | [SubscriptionAccessRequest](#org-example-elsenrollment-domain-service-SubscriptionAccessRequest) | [SubscriptionAccessResponse](#org-example-elsenrollment-domain-service-SubscriptionAccessResponse) | CheckSubscriptionAccess는 특정 사용자가 특정 구독에 접근할 수 있는지 확인합니다. |
| GetUserEnrollments | [UserEnrollmentsRequest](#org-example-elsenrollment-domain-service-UserEnrollmentsRequest) | [UserEnrollmentsResponse](#org-example-elsenrollment-domain-service-UserEnrollmentsResponse) | GetUserEnrollments는 특정 사용자의 모든 강의 등록 내역을 조회합니다. |
| GetUserPlanSubscriptions | [UserSubscriptionsRequest](#org-example-elsenrollment-domain-service-UserSubscriptionsRequest) | [UserSubscriptionsResponse](#org-example-elsenrollment-domain-service-UserSubscriptionsResponse) | GetUserPlanSubscriptions는 특정 사용자의 모든 구독 내역을 조회합니다. |


<a name="org-example-elsenrollment-domain-service-FakePaymentService"></a>

### FakePaymentService
FakePaymentService는 결제 관련 시뮬레이션 서비스를 제공합니다.

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreatePayment | [PaymentRequest](#org-example-elsenrollment-domain-service-PaymentRequest) | [PaymentResponse](#org-example-elsenrollment-domain-service-PaymentResponse) | CreatePayment는 결제 거래 생성을 시뮬레이션합니다. |
| ListUserPayments | [UserPaymentsRequest](#org-example-elsenrollment-domain-service-UserPaymentsRequest) | [UserPaymentsResponse](#org-example-elsenrollment-domain-service-UserPaymentsResponse) | ListUserPayments는 특정 사용자의 모든 결제 내역을 조회합니다. |
| GetPaymentsByPaymentId | [PaymentsByIdRequest](#org-example-elsenrollment-domain-service-PaymentsByIdRequest) | [PaymentsByIdResponse](#org-example-elsenrollment-domain-service-PaymentsByIdResponse) | GetPaymentsByPaymentId는 특정 결제 ID에 해당하는 결제 내역을 조회합니다. |

 



<a name="playback_service-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## playback_service.proto



<a name="org-example-elsplayback-domain-service-EndRecordRequest"></a>

### EndRecordRequest
재생 기록 종료 요청 메시지


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| record_id | [int64](#int64) |  | 재생 기록 ID |






<a name="org-example-elsplayback-domain-service-EndRecordResponse"></a>

### EndRecordResponse
재생 기록 종료 응답 메시지


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| record | [PlaybackRecord](#org-example-elsplayback-domain-service-PlaybackRecord) |  | 종료된 재생 기록 정보 |






<a name="org-example-elsplayback-domain-service-EventLog"></a>

### EventLog
이벤트 로그 정보


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| event_id | [int64](#int64) |  | 이벤트 ID |
| record_id | [int64](#int64) |  | 관련 재생 세션 ID |
| user_id | [int64](#int64) |  | 사용자 ID |
| event_type | [string](#string) |  | 이벤트 유형 (예: &#39;play&#39;, &#39;pause&#39;, &#39;stop&#39;) |
| timestamp | [int64](#int64) |  | 이벤트 발생 시간 (UNIX 타임스탬프) |






<a name="org-example-elsplayback-domain-service-LogEventRequest"></a>

### LogEventRequest
이벤트 로깅 요청 메시지


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| event | [EventLog](#org-example-elsplayback-domain-service-EventLog) |  | 로깅할 이벤트 |






<a name="org-example-elsplayback-domain-service-LogEventResponse"></a>

### LogEventResponse
이벤트 로깅 응답 메시지


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| event | [EventLog](#org-example-elsplayback-domain-service-EventLog) |  | 로깅된 이벤트 정보 |






<a name="org-example-elsplayback-domain-service-PlaybackRecord"></a>

### PlaybackRecord
재생 기록 정보


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| record_id | [int64](#int64) |  | 재생 기록 ID |
| user_id | [int64](#int64) |  | 사용자 ID |
| file_id | [int64](#int64) |  | 파일 ID |
| start_time | [int64](#int64) |  | 시작 시간 (UNIX 타임스탬프) |
| end_time | [int64](#int64) |  | 종료 시간 (UNIX 타임스탬프) |






<a name="org-example-elsplayback-domain-service-StartRecordRequest"></a>

### StartRecordRequest
재생 기록 시작 요청 메시지


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [int64](#int64) |  | 사용자 ID |
| file_id | [int64](#int64) |  | 파일 ID |






<a name="org-example-elsplayback-domain-service-StartRecordResponse"></a>

### StartRecordResponse
재생 기록 시작 응답 메시지


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| record | [PlaybackRecord](#org-example-elsplayback-domain-service-PlaybackRecord) |  | 시작된 재생 기록 정보 |





 

 

 


<a name="org-example-elsplayback-domain-service-PlaybackService"></a>

### PlaybackService
PlaybackService는 미디어 재생 기록 및 이벤트 로깅 서비스를 제공합니다.

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| StartRecord | [StartRecordRequest](#org-example-elsplayback-domain-service-StartRecordRequest) | [StartRecordResponse](#org-example-elsplayback-domain-service-StartRecordResponse) | StartRecord는 새로운 재생 기록을 시작합니다. |
| EndRecord | [EndRecordRequest](#org-example-elsplayback-domain-service-EndRecordRequest) | [EndRecordResponse](#org-example-elsplayback-domain-service-EndRecordResponse) | EndRecord는 재생 기록을 종료합니다. |
| LogEvent | [LogEventRequest](#org-example-elsplayback-domain-service-LogEventRequest) | [LogEventResponse](#org-example-elsplayback-domain-service-LogEventResponse) | LogEvent는 재생 중 발생한 이벤트를 로깅합니다. |

 



## Scalar Value Types

| .proto Type | Notes | C++ | Java | Python | Go | C# | PHP | Ruby |
| ----------- | ----- | --- | ---- | ------ | -- | -- | --- | ---- |
| <a name="double" /> double |  | double | double | float | float64 | double | float | Float |
| <a name="float" /> float |  | float | float | float | float32 | float | float | Float |
| <a name="int32" /> int32 | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint32 instead. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="int64" /> int64 | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint64 instead. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="uint32" /> uint32 | Uses variable-length encoding. | uint32 | int | int/long | uint32 | uint | integer | Bignum or Fixnum (as required) |
| <a name="uint64" /> uint64 | Uses variable-length encoding. | uint64 | long | int/long | uint64 | ulong | integer/string | Bignum or Fixnum (as required) |
| <a name="sint32" /> sint32 | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int32s. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="sint64" /> sint64 | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int64s. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="fixed32" /> fixed32 | Always four bytes. More efficient than uint32 if values are often greater than 2^28. | uint32 | int | int | uint32 | uint | integer | Bignum or Fixnum (as required) |
| <a name="fixed64" /> fixed64 | Always eight bytes. More efficient than uint64 if values are often greater than 2^56. | uint64 | long | int/long | uint64 | ulong | integer/string | Bignum |
| <a name="sfixed32" /> sfixed32 | Always four bytes. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="sfixed64" /> sfixed64 | Always eight bytes. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="bool" /> bool |  | bool | boolean | boolean | bool | bool | boolean | TrueClass/FalseClass |
| <a name="string" /> string | A string must always contain UTF-8 encoded or 7-bit ASCII text. | string | String | str/unicode | string | string | string | String (UTF-8) |
| <a name="bytes" /> bytes | May contain any arbitrary sequence of bytes. | string | ByteString | str | []byte | ByteString | string | String (ASCII-8BIT) |

