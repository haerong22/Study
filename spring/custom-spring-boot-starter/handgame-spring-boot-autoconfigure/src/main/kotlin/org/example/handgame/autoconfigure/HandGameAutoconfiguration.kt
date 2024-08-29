package org.example.handgame.autoconfigure

import org.example.handgame.HandGame
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean

@AutoConfiguration
@ConditionalOnClass(HandGame::class)
@ConditionalOnProperty(prefix = "my.handgame", name = ["enabled"], havingValue = "true")
class HandGameAutoconfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun handgame() = HandGame()
}