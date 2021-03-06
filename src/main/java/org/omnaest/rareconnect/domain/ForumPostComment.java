/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.rareconnect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForumPostComment
{
    @JsonProperty
    private String id;

    @JsonProperty("author_id")
    private String authorId;

    @JsonProperty("editor_id")
    private String editorId;

    @JsonProperty
    private String body;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("comment_count")
    private int commentCount;

    @JsonProperty
    private Author author;

    public String getAuthorId()
    {
        return this.authorId;
    }

    public Author getAuthor()
    {
        return this.author;
    }

    public void setAuthorId(String authorId)
    {
        this.authorId = authorId;
    }

    public String getBody()
    {
        return this.body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getCreatedAt()
    {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getId()
    {
        return this.id;
    }

    public String getEditorId()
    {
        return this.editorId;
    }

    public int getCommentCount()
    {
        return this.commentCount;
    }

}
