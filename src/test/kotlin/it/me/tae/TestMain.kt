package it.me.tae

import org.apache.commons.io.FileUtils
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.converter.ResourceHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

fun main(args: Array<String>) {
    val multipartRequest = LinkedMultiValueMap<String, Any>()

    // creating an HttpEntity for the JSON part
    val jsonHeader = HttpHeaders()
    jsonHeader.contentType = MediaType.APPLICATION_JSON
    val jsonPart = HttpEntity<UploadRequest>(UploadRequest(data1 = "data1", data2 = "data2"), jsonHeader)

    // creating an HttpEntity for the binary part
    val image = FileUtils.getFile("src", "test", "resources", "photoman.png")

    val pictureHeader = HttpHeaders()
    pictureHeader.contentType = MediaType.IMAGE_PNG
    val picturePart = HttpEntity<FileSystemResource>(FileSystemResource(image), pictureHeader)

    // putting the two parts in one request
    multipartRequest.add("data", jsonPart)
    multipartRequest.add("image", picturePart)

    /*
    val data1Header = HttpHeaders()
    data1Header.contentType = MediaType.TEXT_PLAIN
    val data1Part = HttpEntity<String>("data1", data1Header)
    val data2Header = HttpHeaders()
    data2Header.contentType = MediaType.TEXT_PLAIN
    val data2Part = HttpEntity<String>("data2", data2Header)
    */

    val header = HttpHeaders()
    header.contentType = MediaType.MULTIPART_FORM_DATA

    val restTemplate = RestTemplate()
    restTemplate.messageConverters.add(ResourceHttpMessageConverter())
    restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
    val requestEntity = HttpEntity<MultiValueMap<String, Any>>(multipartRequest, header)
    val result = restTemplate.postForObject("http://localhost:8080/image", requestEntity, String::class.java)
    println(result)

}