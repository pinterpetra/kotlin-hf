package com.jetbrains.handson.website

import freemarker.cache.*
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*


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
        post ("/remove") {
            blogEntries.removeLastOrNull()
            call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to blogEntries), ""))
        }
        post ("/modify") {
            call.respond(FreeMarkerContent("add.ftl", mapOf("entries" to blogEntries), ""))
        }
        post ("/add") {
            call.respond(FreeMarkerContent("add.ftl", mapOf("entries" to blogEntries), ""))
        }
        post("/submit") {
            val params = call.receiveParameters()
            val headline = params["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val all = params["all"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val seen = params["seen"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            /*val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )*/
            val newEntry = BlogEntry(headline, all, seen)
            if (newEntry.headline.isNotEmpty() &&
                newEntry.all.isNotEmpty() &&
                newEntry.seen.isNotEmpty() &&
                newEntry.all.toInt() > 0 &&
                newEntry.seen.toInt() > 0 &&
                newEntry.all.toInt() >= newEntry.seen.toInt())
                {
                    blogEntries.add(0, newEntry)
                    call.respondHtml {
                        body {
                            h1 { +"You have successfully entered your series!" }
                            p {
                                +"It's title is: "
                                b { +newEntry.headline }
                                +". It has "
                                b { +newEntry.all }
                                + " episodes and you have seen "
                                b { +newEntry.seen }
                            }
                            p { +"You track ${blogEntries.count()} series now!" }
                            a("/" ) { +"Go back to your list" }
                        }
                    }
            }
            else {call.respondHtml {
                body {
                    h1 { +"Please fill out the fields correctly!:" }
                    p {i {+"Fill out all of the fields!"}}
                    p {i {+"Give positive numbers to the last two of them!"}}
                    p {i {+"The number of the seen episodes should be above the number of all the episodes!"}}
                    p {b {+"According to your data, these would be the the wanted series' information:"}}
                    p {+"Name of the series: "
                        b { +newEntry.headline }
                    }
                    p{+"Number of episodes: "
                        b { +newEntry.all }
                    }
                    p{+ "Number of seen episodes: "
                        b { +newEntry.seen }
                    }
                    p { +"So you still track just ${blogEntries.count()-1} series now!" }
                    a("/" ) { +"Go back to your list" }
                }
            }
            }
        }
        /*delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (blogEntries.removeIf { it.id == id }) {
                call.respondText("Series removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }*/
    }
}
