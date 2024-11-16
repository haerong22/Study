# 재고 서비스

### refreshVersions plugin 
`settings.gradle.kts`
```kotlin
plugins {
    id("de.fayard.refreshVersions") version "0.60.5"
}
```

---

### JMeter

- JMeter 다운로드
https://jmeter.apache.org/download_jmeter.cgi

1. JMeter 다운로드 후 압축 해제
2. apache-jmeter-x.x.x 의 `bin/jmeter` 실행

-  JMeter plugin manager 다운로드
https://jmeter-plugins.org/wiki/PluginsManager

1. JAR file 다운로드
2. apache-jmeter-x.x.x 의 `lib/ext`로 복사

- Basic Graphs 다운로드
1. 상단 `Options` 메뉴에서 plugin manager 실행
2. `Available Plugins`에서 `3 Basic Graphs` 검색
3. 체크 후 하단 `Apply Changes and Restart JMeter` 실행

---

### K6
- https://k6.io/docs/get-started/installation

#### 새로운 스크립트 생성

```shell
k6 new sample.js
```

#### k6 스크립트 실행

```shell
k6 run sample.js
```

#### 결과

```
     data_received..................: 7.8 MB 257 kB/s
     data_sent......................: 70 kB  2.3 kB/s
     http_req_blocked...............: avg=7.81ms   min=2µs      med=11µs     max=534.47ms p(90)=27µs     p(95)=53.29µs 
     http_req_connecting............: avg=2.72ms   min=0s       med=0s       max=193.93ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=187.47ms min=177.59ms med=185.2ms  max=365.21ms p(90)=193.28ms p(95)=198.59ms
       { expected_response:true }...: avg=187.47ms min=177.59ms med=185.2ms  max=365.21ms p(90)=193.28ms p(95)=198.59ms
     http_req_failed................: 0.00%  0 out of 675
     http_req_receiving.............: avg=875.97µs min=21µs     med=228µs    max=28.01ms  p(90)=2.25ms   p(95)=5.67ms  
     http_req_sending...............: avg=212.78µs min=7µs      med=47µs     max=9.65ms   p(90)=121µs    p(95)=425.89µs
     http_req_tls_handshaking.......: avg=4.22ms   min=0s       med=0s       max=286.21ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=186.38ms min=177.5ms  med=184.12ms max=365.01ms p(90)=191.81ms p(95)=195.72ms
     http_reqs......................: 675    22.210641/s
     iteration_duration.............: avg=448.06ms min=427.88ms med=437.27ms max=1.01s    p(90)=446.62ms p(95)=457.54ms
     iterations.....................: 675    22.210641/s
     vus............................: 10     min=10       max=10
     vus_max........................: 10     min=10       max=10
```

- **http_reqs**: 전체 요청 수 (위에서는 125번)
- **http_req_failed**: 전체 실패 수와 퍼센티지 (위에서는 0.00%, 0번)
- **http_req_duration**: 요청을 받는데 걸린 시간. `http_req_sending` + `http_req_waiting` + `http_req_receiving`
- **iterations**: 전체 반복 수 (위에서는 125번)
- **vus**: 요청을 보낸 가상 사용자 수 (위에서는 5명)


#### 고정된 virtual user로 요청 보내기

```js
export const options = {
    // A number specifying the number of VUs to run concurrently.
    vus: 10,
    // A string specifying the total duration of the test run.
    duration: '15s',
}
```
- vus * 15(duration) * rps(초당 요청 수) = 전체 요청 수

```js
sleep(1); // 1 rps와 비슷
sleep(0.25) // 4 rps와 비슷
```

#### ramping-vus로 점진적으로 요청 늘리기

```js
export const options = {
    scenarios: {
        getStock: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                {duration: '5s', target: maxVus},
                {duration: '10s', target: maxVus},
                {duration: '5s', target: 0},
            ],
        }
    }
}
```
- (maxVus * 5 / 2 + maxVus * 10 + maxVus * 5 / 2) * rps = 전체 요청 수
- 15 * maxVus * rps = 전체 요청 수

참고: https://k6.io/docs/using-k6/metrics/reference

---

### Act

#### act 다운로드
https://nektosact.com/installation/index.html

#### act 실행

```shell
# workflow list
act -l 

# job graph
act -g 

# pr
act -P ubuntu-22.04=catthehacker/ubuntu:act-22.04 pull_request

# push
act -P ubuntu-22.04=catthehacker/ubuntu:act-22.04 push
```