package com.example.openai.stt.service

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class SttService(
    private val openAiAudioTranscriptionModel: OpenAiAudioTranscriptionModel,
) {

    fun transcription(file: MultipartFile): String {
        val resource: Resource = file.resource
        // Set transcription options
        val options = OpenAiAudioTranscriptionOptions.builder()
            .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
            .withLanguage("ko")
            .withTemperature(0f) //.withResponseFormat(this.responseFormat)
            .build()

        // Create a transcription prompt
        val audioTranscriptionPrompt = AudioTranscriptionPrompt(resource, options)

        // Call the transcription API
        val audioTranscriptionResponse: AudioTranscriptionResponse = openAiAudioTranscriptionModel.call(audioTranscriptionPrompt)

        return audioTranscriptionResponse.result.output
    }
}