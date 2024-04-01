package initialize

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Person4Test {
    private val person = Person4()

    @Test
    fun isKimTest() {
        // given
        val person = person.apply { name = "홍길동" }

        // when
        val isKim = person.isKim

        // then
        assertEquals(false, isKim)
    }

    @Test
    fun maskingNameTest() {
        // given
        val person = person.apply { name = "홍길동" }

        // when
        val maskingName = person.maskingName

        // then
        assertEquals("홍**", maskingName)
    }
}
