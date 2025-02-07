package com.example.openai.tts.service

import org.springframework.ai.openai.OpenAiAudioSpeechModel
import org.springframework.ai.openai.OpenAiAudioSpeechOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.ai.openai.audio.speech.SpeechPrompt
import org.springframework.ai.openai.audio.speech.SpeechResponse
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import reactor.core.publisher.Flux
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets

@Service
class TTSService(
    private val openAiAudioSpeechModel: OpenAiAudioSpeechModel,
) {
    fun textToSpeech(file: MultipartFile): StreamingResponseBody {
        val content = String(file.bytes, StandardCharsets.UTF_8)

        val options = OpenAiAudioSpeechOptions.builder()
            .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
            .withSpeed(1.1f)
            .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
            .withModel(OpenAiAudioApi.TtsModel.TTS_1.value)
            .build()

        val speechPrompt = SpeechPrompt(content, options)

        // 리액티브 스트림 생성(실시간 오디오 스트리밍) - LLM(text)--->Auido(mp3)
        val responseStream: Flux<SpeechResponse> = openAiAudioSpeechModel.stream(speechPrompt)

        // StreamingResponseBody로 변환하여 클라이언트로 스트림 반환
        return StreamingResponseBody { outputStream: OutputStream ->
            responseStream.toStream().forEach { speechResponse: SpeechResponse ->
                writeToOutput(
                    outputStream,
                    speechResponse
                )
            }
        }
    }

    private fun writeToOutput(outputStream: OutputStream, speechResponse: SpeechResponse) {
        try {
            // 데이터를 출력 스트림에 작성
            outputStream.write(speechResponse.result.output) // byte[]
            outputStream.flush() // 즉시 전송
        } catch (e: IOException) {
            throw RuntimeException("Error writing audio data to output stream", e)
        }
    }


}