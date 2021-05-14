package com.jetbrains.handson.website

import freemarker.cache.*
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.request.receiveParameters
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import org.jetbrains.annotations.NotNull


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        static("/static") {
            resources("files")
        }
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to blogEntries), ""))
        }
        post("/submit") {
            val params = call.receiveParameters()
            val headline = params["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val all = params["all"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val seen = params["seen"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val newEntry = BlogEntry(headline, all, seen)
            blogEntries.add(0, newEntry)
            call.respondHtml {
                body {
                    h1 {
                        +"You have successfully entered your series"
                    }
                    p {
                        +"It's title is: "
                        b {
                            +newEntry.headline
                        }
                    }
                    p {
                        +"You have submitted ${blogEntries.count()-1} series now!"
                    }
                    a("/" ) {
                        +"Go back"
                    }
                }
            }
        }
    }
}
