```graphql
# @authenticate는 사용자가 인증된 경우에만 필드나 객체에 접근할 수 있도록 합니다.
directive @authenticate on FIELD_DEFINITION | OBJECT

# @authorize는 사용자가 특정 역할 및 권한을 가지고 있는지 확인합니다.
directive @authorize(role: String, permission: String) on FIELD_DEFINITION | OBJECT

type Query {
    # 사용자 정보를 ID를 통해 조회합니다.
    getUser(userId: ID!): User @authenticate @authorize(role: "user", permission: "read_user")

    # 모든 강의를 조회합니다.
    listCourses: [Course]

    # 특정 강의 정보를 course ID를 통해 조회합니다.
    getCourse(userId: ID!, courseId: ID!): Course @authenticate

    # 특정 강의의 모든 세션을 조회합니다.
    listCourseSessions(courseId: ID!): [CourseSession]

    # 특정 강의에 대한 권한을 가지고 있는지 여부를 체크합니다.
    checkCourseAccess(userId: ID!, courseId: ID!): Boolean! @authenticate @authorize(role: "user", permission: "read_purchase")

    # 특정 사용자의 모든 구독 정보를 조회합니다.
    getUserPlanSubscriptions(userId: ID!): [PlanSubscription] @authenticate @authorize(role: "user", permission: "read_enrollment")

    # 특정 사용자의 모든 등록 정보를 조회합니다.
    getUserEnrollments(userId: ID!): [Enrollment] @authenticate @authorize(role: "user", permission: "read_enrollment")

    # 특정 코스에 대한 메시지를 가져오는 쿼리
    getMessages(courseId: String!): [ChatMessage]
}

type Mutation {
    # 새로운 사용자를 생성합니다.
    createUser(name: String!, email: String!, password: String!): User

    # 기존 사용자 정보를 업데이트합니다.
    updateUser(userId: ID!, name: String, email: String): User @authenticate @authorize(role: "user", permission: "update_user")

    # 새로운 강의를 생성합니다.
    createCourse(title: String!, description: String, instructorId: ID!): Course @authenticate @authorize(role: "admin", permission: "create_course")

    # 기존 강의 정보를 업데이트합니다.
    updateCourse(id: ID!, title: String, description: String): Course @authenticate @authorize(role: "admin", permission: "update_course")

    # 강의에 새로운 세션을 추가합니다.
    addCourseSession(courseId: ID!, title: String!): CourseSession @authenticate @authorize(role: "user", permission: "update_course")

    # 강의에 대한 평가를 추가합니다.
    rateCourse(userId: ID!, courseId: ID!, rating: Int!, comment: String): CourseRating @authorize(role: "admin", permission: "update_course")

    # 재생 기록을 생성합니다.
    startRecord(userId: ID!, fileId: ID!): PlaybackRecord @authenticate @authorize(role: "user", permission: "update_record")

    # 재생 기록을 종료합니다.
    endRecord(userId: ID!, recordId: ID!): PlaybackRecord @authenticate @authorize(role: "user", permission: "update_record")

    # 이벤트를 로깅합니다.
    logEvent(recordId: ID!, userId: ID!, eventType: String!, timestamp: String!): EventLog @authenticate @authorize(role: "user", permission: "update_record")

    # 새 결제 정보를 생성합니다.
    purchaseCourse(userId: ID!, courseId: ID!, amount: Float!, paymentMethod: String!): Payment @authenticate @authorize(role: "user", permission: "update_purchase")

    # 구독 결제 정보를 생성합니다.
    purchaseSubscription(userId: ID!, amount: Float!, paymentMethod: String!): Payment @authenticate @authorize(role: "user", permission: "update_purchase")

    # 새로운 채팅 메시지를 보내는 뮤테이션
    sendMessage(courseId: String!, userId: String!, content: String!): Boolean!
}

type Subscription {
    messageReceived(courseId: String!): ChatMessage
}

type ChatMessage {
    courseId: String!
    userId: String!
    messageId: String!
    content: String!
    timestamp: String!
}

# 사용자 정보를 나타내는 타입입니다.
type User {
    id: ID!                     # 사용자 ID
    name: String!               # 사용자 이름
    email: String!              # 사용자 이메일
}

# 사용자 로그인 기록을 나타내는 타입입니다.
type UserLoginHistory {
    id: ID!                     # 로그인 기록 ID
    userId: ID!                 # 사용자 ID
    loginTime: String           # 로그인 시간
    ipAddress: String           # 로그인한 IP 주소
}

# 강의 정보를 나타내는 타입입니다.
type Course {
    id: ID!                     # 강의 ID
    title: String!              # 강의 제목
    description: String         # 강의 설명
    instructorId: ID!           # 강사 ID
    instructor: User            # 강사 정보
    courseSessions: [CourseSession]    # 강의에 속한 모든 세션
    ratings: [CourseRating]     # 강의에 대한 사용자 평가
}

# 강의 세션 정보를 나타내는 타입입니다.
type CourseSession {
    id: ID!                     # 세션 ID
    courseId: ID!               # 강의 ID
    title: String!              # 세션 제목
    # 세션에 연결된 모든 파일
    files: [CourseSessionFile] @authorize(role: "user", permission: "read_files")
}

# 강의 평가 정보를 나타내는 타입입니다.
type CourseRating {
    id: ID!                     # 평가 ID
    courseId: ID!               # 강의 ID
    course: Course              # 강의 정보
    userId: ID!                 # 사용자 ID
    user: User                  # 사용자 정보
    rating: Int!                # 평점
    comment: String             # 평가 코멘트
}

# 강의 세션 파일 정보를 나타내는 타입입니다.
type CourseSessionFile {
    fileId: ID!                 # 파일 ID
    fileName: String!           # 파일 이름
    fileType: String!           # 파일 유형
    filePath: String!           # 파일 경로
}

# 재생 기록 정보를 나타내는 타입입니다.
type PlaybackRecord {
    recordId: ID!               # 재생 기록 ID
    userId: ID!                 # 사용자 ID
    fileId: ID!                 # 파일 ID
    startTime: String!          # 시작 시간
    endTime: String             # 종료 시간
}

# 이벤트 로그 정보를 나타내는 타입입니다.
type EventLog {
    eventId: ID!                # 이벤트 ID
    recordId: ID!               # 재생 기록 ID
    userId: ID!                 # 사용자 ID
    eventType: String!          # 이벤트 유형
    timestamp: String!          # 이벤트 발생 시간
}

# 결제 타입을 나타내는 열거형입니다.
enum PaymentType {
    COURSE                      # 강의 결제
    SUBSCRIPTION                # 구독 결제
}

# 결제 정보를 나타내는 타입입니다.
type Payment {
    id: ID!                     # 결제 ID
    userId: ID!                 # 사용자 ID
    user: User!                 # 사용자 정보
    paymentType: PaymentType!   # 결제 유형
    amount: Float!              # 결제 금액
    paymentMethod: String!      # 결제 방법
    paymentDate: String!        # 결제 날짜
}

# 강의 등록 정보를 나타내는 타입입니다.
type Enrollment {
    id: ID!                     # 등록 ID
    userId: ID!                 # 사용자 ID
    user: User!                 # 사용자 정보
    courseId: ID!               # 강의 ID
    course: Course!             # 강의 정보
    paymentId: ID!              # 결제 ID
    payment: Payment!           # 결제 정보
    registrationDate: String!   # 등록 날짜
}

# 사용자 구독 정보를 나타내는 타입입니다.
type PlanSubscription {
    id: ID!                     # 구독 ID
    userId: ID!                 # 사용자 ID
    user: User!                 # 사용자 정보
    paymentId: ID!              # 결제 ID
    payment: Payment!           # 결제 정보
    startDate: String!          # 시작 날짜
    endDate: String!            # 종료 날짜
    status: String!             # 구독 상태
}

```