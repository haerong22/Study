class Person {
    String name
    int age

    // override
    def getName() {
        "My name is $name"
    }

    def showDetails() {
        println "Name: $name, Age: $age"
    }
}

p = new Person(name: 'Bobby', age: 40)
p.showDetails()

println p.name

// constructor
class Car {
    String model
    int year

    Car(String model, int year) {
        this.model = model
        this.year = year
    }
}

def car = new Car("Tesla", 2024)
println "Model: ${car.model}, Year: ${car.year}"

// inheritance
class Animal {
    def speak() {
        println "Animal sound"
    }
}

class Dog extends Animal {

    @Override
    def speak() {
        println "Bark"
    }
}

def dog = new Dog()
dog.speak()

// static method
class Calculator {
    static double PI = 3.14159

    static double square(double num) {
        return num * num
    }
}

println Calculator.PI
println Calculator.square(5)

/*
    Name: Bobby, Age: 40
    My name is Bobby
    Model: Tesla, Year: 2024
    Bark
    3.14159
    25.0
*/