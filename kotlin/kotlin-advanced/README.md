### 제네릭
- 제네릭, 타입 파라미터
- 무공변, 공변, 반공변
- 선언 지점 변성 / 사용 지점 변성
- 제네릭 제약, 제네릭 함수
- 타입 소거, Star Projection

> tip.
> 
> - 코틀린에서는 Raw 타입 사용이 불가능
> (타입 파라미터를 사용하지 않고 인스턴스화 하는것 )
> 
> - 변성을 부여하지 않았다면 제네릭 클래스는 기본적으로 무공변
> 
> - inline, reified 키워드를 이용해 타입 소거를 일부 막을 수 있다.
> 
> - 타입 파라미터 섀도잉(클래스의 T가 함수의 T에 의해 shadowing)
> 
> - 제네릭 클래스 상속시 같은 타입 파라미터를 전달 하거나 특정 타입을 전달
> 
> - 타입에 별명을 붙일 수 있다.(typealias)

---

### 지연과 위임
- lateinit, lazy()
- by lazy 원리, 위임 프로퍼티
- 표준 위임 객체
  - notNull()
  - observable()
  - vetoable()
  - map
- ReadOnlyProperty
- ReadWriteProperty
- DelegateProvider
- 위임 클래스

> tip
> - lateinit 을 primitive type 에 사용할 수 없다. (Int, Long)
> - lateinit : 초기화를 지연시킨 변수, 초기화 로직이 여러 곳에 위치할 수 있다. 초기화 없이 호출하면 예외 발생
> - lazy : 초기화를 get 호출 전으로 지연시킨 변수, 초기화 로직은 변수 선언과 동시에 한 곳에만 위치 할 수 있다.
> - primitive type 에는 lateinit을 사용할 수 없지만, notNull()은 사용할 수 있다.
> - observable() 은 setter 가 호출될 때 onChange() 함수 호출
> - vetoable() 은 setter 가 호출될 때 onChange() 함수 호출 true 이면 변경 적용 false 이면 변경 X
> - 위임 객체 map 은 getter 호출 시 Map 에서 찾아 응답한다.
> - sequence 는 원소 하나씩 중간연산 실행 후 다음 원소로 넘어간다. 최종 연산이 없을 경우 실행 안함(지연 연산)