package com.example.openai.config

import com.example.openai.service.ChatCommandLineService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CommandLineAppStartupRunner(
    private val chatCommandLineService: ChatCommandLineService,
): CommandLineRunner {

    override fun run(vararg args: String?) {
        chatCommandLineService.startChat()
    }
}

/**
 * [without advisor]
 *
 * Enter your message:
 * 나는 개발자 입니다
 * Bot: 반갑습니다! 개발자시군요. 어떤 분야의 개발을 하시나요? 웹, 모바일, 게임, 데이터 과학 등 다양한 분야가 있는데, 특별히 관심 있는 분야가 있으면 말씀해 주세요. 도움이 필요하시면 언제든지 질문해 주세요!
 * 나의 직업은?
 * Bot: 죄송하지만, 당신의 직업을 알 수 있는 정보가 제공되지 않았습니다. 추가적인 정보를 주시면 도움이 될 수 있을지 모르겠습니다.
 *
 * [with advisor]
 *
 * Enter your message:
 * 나는 개발자 입니다
 * Bot: 반갑습니다! 개발자이시군요. 어떤 분야에서 개발을 하고 계신가요? 웹 개발, 모바일 앱 개발, 데이터 과학 등 다양한 분야가 있을 텐데요. 도움이 필요하시거나 궁금한 점이 있다면 언제든지 말씀해 주세요!
 * 나의 직업은?
 * Bot: 당신은 "개발자"라고 말씀하셨습니다. 개발자는 주로 소프트웨어나 애플리케이션을 설계하고 구현하며, 다양한 프로그래밍 언어와 도구를 사용하여 문제를 해결하는 직업입니다. 구체적으로 어떤 분야의 개발자인지는 추가적인 정보가 필요하지만, 일반적으로 웹, 모바일, 데이터, 게임, 시스템 소프트웨어 등 여러 분야에서 활동할 수 있습니다.
 *
 */