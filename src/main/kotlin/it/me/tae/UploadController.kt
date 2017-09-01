package it.me.tae

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.util.Base64Utils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
open class UploadController {
    @Autowired
    lateinit var mapper: ObjectMapper

    @GetMapping("/hello")
    open fun sayHello(): String {
        return "hello man"
    }

    @PostMapping("/image", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    open fun multipartUpload(@RequestBody uploadRequest: UploadRequest) { // test content negotiation
        throw IllegalStateException("Should not be call")
    }

    @PostMapping("/image")
    open fun multipartUpload(
            @RequestPart("data") uploadRequest: UploadRequest,
            @RequestPart("image") imagePart: MultipartFile
    ): String {
        val imageBase64 = Base64Utils.encodeToString(imagePart.bytes)
        println(imageBase64)
        println(mapper.writeValueAsString(uploadRequest))

        return imageBase64
    }
}