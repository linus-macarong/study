package linus.test

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<TestExamplesApplication>().with(TestcontainersConfiguration::class).run(*args)
}
