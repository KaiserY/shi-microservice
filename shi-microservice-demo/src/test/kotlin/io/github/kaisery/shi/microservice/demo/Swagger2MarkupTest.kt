package io.github.kaisery.shi.microservice.demo

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


@RunWith(SpringRunner::class)
@SpringBootTest(
  classes = [DemoApplication::class],
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureRestDocs(outputDir = "build/asciidoc/snippets")
@AutoConfigureMockMvc
class Swagger2MarkupTest {

  @Autowired
  lateinit var mockMvc: MockMvc

  @Test
  @Throws(Exception::class)
  fun createSpringfoxSwaggerJson() {

    val outputDir = System.getProperty("io.springfox.staticdocs.outputDir")

    val mvcResult = this.mockMvc.perform(get("/v2/api-docs")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk)
      .andReturn()

    val response = mvcResult.response
    val swaggerJson = response.contentAsString

    Files.createDirectories(Paths.get(outputDir))
    Files.newBufferedWriter(
      Paths.get(outputDir, "swagger.json"),
      StandardCharsets.UTF_8).use({ writer -> writer.write(swaggerJson) }
    )
  }
}
