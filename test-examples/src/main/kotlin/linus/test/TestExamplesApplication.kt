package linus.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestExamplesApplication

fun main(args: Array<String>) {
    runApplication<TestExamplesApplication>(*args)
}
