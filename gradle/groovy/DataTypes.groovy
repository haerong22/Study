// Primitive Data Types
byte b = 10
println "Byte: $b";
println b.class

short s = 30000
println "Short: $s"
println s.class

int i = 100000
println "Integer: $i";
println i.class

long l = 10000000000L
println "Long: $l"
println l.class

float f = 10.5F
println "Float: $f"
println f.class

double d = 20.99
println "Double: $d"
println d.class

char c = 'A'
println "Character: $c"
println c.class

boolean bool = true
println "Boolean: $bool"
println bool.class

// Reference Data Types
String str = "Hello, Groovy!"
println "String: $str"
println str.class

BigInteger bigInt = new BigInteger("12345678901234567890")
println "BigInteger: $bigInt"
println bigInt.class

BigDecimal bigDec = new BigDecimal("12345.6789")
println "BigDecimal: $bigDec"
println bigDec.class

List<Integer> list = [1, 2, 3, 4, 5]
println "List: $list"
println list.class

Map<String, Integer> map = [name: 1, age: 24]
println "Map: $map"
println map.class

Range range = 1..5
println "Range: $range"
println range.class

// Special Data Types

// Closure
def sayHello = { println "Hello, Groovy!"}
sayHello()

def add = {a1, a2 -> a1 + a2}
println add(5, 3)

def greet = {name -> println "Hello, $name!"} 
greet("Groovy")

def square = { it * it }
println square(4)

def greetWithDefault = { name = "Stranger" -> println "Hello, $name!" }
greetWithDefault()
greetWithDefault("Bobby")

def operate(a, b, operation) {
    return operation(a, b)
}

def result = operate(4, 5, { x, y -> x + y })
println result

String nullableString = null
String nullableString2 = "ABCDE"
println "Nullable String Length: ${nullableString?.length()}"
println "Nullable String Length: ${nullableString2?.length()}"

// Dynamic Typing
def dynamicVar = "I am a String"
println "Dynamic Variable: $dynamicVar (Type: ${dynamicVar.getClass().name})"

dynamicVar = 42
println "Dynamic Variable: $dynamicVar (Type: ${dynamicVar.getClass().name})"

dynamicVar = [1, 2, 3]
println "Dynamic Variable: $dynamicVar (Type: ${dynamicVar.getClass().name})"


/*

Byte: 10
class java.lang.Byte
Short: 30000
class java.lang.Short
Integer: 100000
class java.lang.Integer
Long: 10000000000
class java.lang.Long
Float: 10.5
class java.lang.Float
Double: 20.99
class java.lang.Double
Character: A
class java.lang.Character
Boolean: true
class java.lang.Boolean

String: Hello, Groovy!
class java.lang.String
BigInteger: 12345678901234567890
class java.math.BigInteger
BigDecimal: 12345.6789
class java.math.BigDecimal
List: [1, 2, 3, 4, 5]
class java.util.ArrayList
Map: [name:1, age:24]
null
Range: [1, 2, 3, 4, 5]
class groovy.lang.IntRange
Hello, Groovy!
8
Hello, Groovy!
16
Hello, Stranger!
Hello, Bobby!
9
Nullable String Length: null
Nullable String Length: 5

Dynamic Variable: I am a String (Type: java.lang.String)
Dynamic Variable: 42 (Type: java.lang.Integer)
Dynamic Variable: [1, 2, 3] (Type: java.util.ArrayList)
*/