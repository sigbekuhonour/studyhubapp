package com.honoursigbeku.studyhubapp

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

class CoroutineTest {
//    val testScheduler = TestCoroutineScheduler()
//    val testDispatcher = StandardTestDispatcher(testScheduler)
//    val testScope = TestScope(testDispatcher)

    suspend fun fetchData(): String {
        delay(1000L)
        return "Hello world"
    }


    suspend fun testCoroutine(): String {

        val time = measureTimeMillis {
            coroutineScope {
                val deferrds = "abcdeffffffffffffffff".map { eachLetter ->
                    async {
                        println(eachLetter)
                    }
                }
                deferrds.awaitAll()
            }
        }
        println("This took this long: $time")
        return "Hello world"
    }

    suspend fun testCoroutineInsteadWithLoops(): String {

        val time = measureTimeMillis {
            for (eachLetter in "abcdeffffffffffffffff") {
                println(eachLetter)
            }
        }
        println("This took this long: $time")
        return "Hello world"
    }

    fun main(): String {
        repeat(50_000) { // launch a lot of coroutines
            thread {
                Thread.sleep(5000L)
                print(".")
            }
        }
        return ("Hello world")
    }

    @Test
    fun testSomething() = runTest {
//        val dataCor = testCoroutine()
        val test = main()
        println("/n ")
//        val dataLoop = testCoroutineInsteadWithLoops()
        assertEquals("Hello world", test)
    }

}