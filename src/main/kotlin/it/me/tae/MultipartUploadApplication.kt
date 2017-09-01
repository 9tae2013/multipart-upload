package it.me.tae

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@SpringBootApplication
open class MultipartUploadApplication {
    @Bean
    open fun objectMapperBuilder(): Jackson2ObjectMapperBuilder {
        val jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder()
        jackson2ObjectMapperBuilder.modulesToInstall(KotlinModule())
        return jackson2ObjectMapperBuilder
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MultipartUploadApplication::class.java, *args)
}