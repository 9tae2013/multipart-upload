package it.me.tae

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.FileUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.Base64Utils
import java.io.FileInputStream

@RunWith(SpringRunner::class)
@WebMvcTest(UploadController::class)
open class UploadControllerTest {
    @Autowired
    lateinit var mvc: MockMvc
    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    open fun testUpload() {
        val data = UploadRequest(data1 = "data1", data2 = "data2")


        val contentdata = MockMultipartFile("data", "", "application/json", mapper.writeValueAsBytes(data))
        val webcontent = FileUtils.getFile("src", "test", "resources", "photoman.png")
        val contentzip = MockMultipartFile("image", webcontent.name, MediaType.APPLICATION_OCTET_STREAM_VALUE, FileInputStream(webcontent))

        mvc.perform(
                fileUpload("/image")
                        .file(contentdata)
                        .file(contentzip)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(content().string(Base64Utils.encodeToString(FileUtils.readFileToByteArray(webcontent))))
    }
}