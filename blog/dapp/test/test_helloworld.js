const helloWorld = artifacts.require("HelloWorld");

contract("HelloWorld", function(accounts){

    // HelloWorld 컨트랙트에 접근
    before(async () =>{
        this.instance = await helloWorld.deployed();
    });

    // say() 테스트
    it("should be initialized with correct value", async () => {
        const greeting = await this.instance.greeting();

        assert.equal(greeting, "Hello World!!", "Wrong initialized value!");
    });

    // setGreeting() 테스트
    it("should change the greeting", async () => {
        const val = "Hello, Ethereum!";

        // 상태를 바꾸는 함수는 계정이 필요하다
        await this.instance.setGreeting(val, {from: accounts[0]});
        const greeting = await this.instance.say();

        assert.equal(greeting, val, "dose not change the value!");
    });

});