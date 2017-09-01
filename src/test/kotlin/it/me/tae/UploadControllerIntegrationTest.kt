package it.me.tae

import org.apache.commons.io.FileUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UploadControllerIntegrationTest {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun testUploadController() {
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

        val header = HttpHeaders()
        header.contentType = MediaType.MULTIPART_FORM_DATA

        val requestEntity = HttpEntity<MultiValueMap<String, Any>>(multipartRequest, header)
        val result = restTemplate.postForObject("/image", requestEntity, String::class.java)
        println(result)
    }
}