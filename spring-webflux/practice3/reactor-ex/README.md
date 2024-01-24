## Reactor 연습

- java 17
- project reactor
- project reactor test

---

### Flux
- 0-N개 아이템을 가지는 데이터 스트림
- onNext, onComplete, onError

### Mono
- 0개 또는 1개 아이템을 가지는 데이터 스트림
- onNext, onComplete, onError

### Operator
- map
- filter
- take
- flatMap
- concatMap
- flatMapMany
- switchIfEmpty / defaultIfEmpty
- merge / zip
- count
- distinct
- reduce
- groupBy
- delaySequence / limitRate
- sample

### Schedulers
- immediate
- single
- parallel
- boundedElastic