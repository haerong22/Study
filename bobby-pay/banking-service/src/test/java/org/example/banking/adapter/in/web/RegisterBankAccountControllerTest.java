package org.example.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.banking.domain.RegisteredBankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterBankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void registerBankAccountTest() throws Exception {
        RegisterBankAccountRequest request =
                new RegisterBankAccountRequest("1", "국민은행", "1234567890", true);

        RegisteredBankAccount bankAccount = RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId("1"),
                new RegisteredBankAccount.MembershipId("1"),
                new RegisteredBankAccount.BankName("국민은행"),
                new RegisteredBankAccount.BankAccountNumber("1234567890"),
                new RegisteredBankAccount.LinkedStatusIsValid(true)
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/banking/account/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(bankAccount)))
                ;
    }
}