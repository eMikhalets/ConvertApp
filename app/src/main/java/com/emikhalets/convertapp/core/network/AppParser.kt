package com.emikhalets.convertapp.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

abstract class AppParser {

    protected suspend fun parseSource(url: String): Document {
        return withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }
    }

    protected suspend fun Document.getElements(query: String): Elements {
        return withContext(Dispatchers.IO) {
            this@getElements.select(query)
        }
    }
}
