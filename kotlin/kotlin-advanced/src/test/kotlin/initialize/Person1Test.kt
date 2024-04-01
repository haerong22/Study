package initialize

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Person1Test {
    @Test
    fun isKimTest() {
        // given
        val person1 = Person1("홍길동")

        // when
        val isKim = person1.isKim

        // then
        assertEquals(false, isKim)
    }

    @Test
    fun maskingNameTest() {
        // given
        val person1 = Person1("홍길동")

        // when
        val maskingName = person1.maskingName

        // then
        assertEquals("홍**", maskingName)
    }
}
