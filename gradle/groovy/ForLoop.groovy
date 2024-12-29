// List
def fruits = ["Apple", "Banana", "Cherry"]

for(fruit in fruits) {
    println fruit
}

println ""

// Range
for(i in 1..5) {
    println "Number: $i"
}

println ""

// exclusive upper bound
for(i in 1..<5) {
    println "Number (exclusive): $i"
}

println ""

// Map
def colors = [red: "#FF0000", green: '#00FF00', blue: '#0000FF']

for(color in colors) {
    println "${color.key}: ${color.value}"
}

println ""

// using each
colors.each { key, value ->
    println "$key: $value"
}

println ""

fruits.each { fruit -> 
    println fruit
}

println ""

fruits.eachWithIndex { fruit, index -> 
    println "Fruit at index $index: $fruit"
}

/*
    Apple
    Banana
    Cherry

    Number: 1
    Number: 2
    Number: 3
    Number: 4
    Number: 5

    Number (exclusive): 1
    Number (exclusive): 2
    Number (exclusive): 3
    Number (exclusive): 4

    red: #FF0000
    green: #00FF00
    blue: #0000FF

    red: #FF0000
    green: #00FF00
    blue: #0000FF

    Apple
    Banana
    Cherry

    Fruit at index 0: Apple
    Fruit at index 1: Banana
    Fruit at index 2: Cherry
*/