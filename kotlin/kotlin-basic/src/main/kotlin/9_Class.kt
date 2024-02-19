class Coffee (
    var name: String = "", // 기본값 지정
    var price: Int = 0,
) {
    val brand: String
        get() {
            return  "스타벅스"
        }

    var quantity: Int = 0
        set(value) {
            if (value > 0) {
                field = value // 필드에 접근하여 데이터 저장
            }
        }
}

class EmptyClass

fun main() {
    val coffee = Coffee();
    coffee.name = "아이스 아메리카노"
    coffee.price = 2000
    coffee.quantity = 1

    println("${coffee.brand}: ${coffee.name} ${coffee.quantity}개 가격은 ${coffee.price}원")
}
