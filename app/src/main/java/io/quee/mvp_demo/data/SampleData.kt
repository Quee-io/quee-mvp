package io.quee.mvp_demo.data

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */
class SampleData(
    @param:JsonProperty var id: Int,
    @param:JsonProperty var body: String,
    @param:JsonProperty var postId: String
) {
    override fun toString(): String {
        return "id: $id, body: $body, postId: $postId"
    }
}