package com.emikhalets.convertapp.core.network

import com.emikhalets.convertapp.core.common.extensions.logd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

abstract class AppParser {

    protected suspend fun parseSource(url: String): Document {
        logd("parseSource: $url")
        return withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }
    }

    protected suspend fun Document.getElements(query: String): Elements {
        logd("getElements: $query")
        return withContext(Dispatchers.IO) {
            this@getElements.select(query)
        }
    }
}
