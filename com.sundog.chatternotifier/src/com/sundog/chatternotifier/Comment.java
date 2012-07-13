/*
 * Copyright (c) 2011, Sundog Interactive.
 * All rights reserved.
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * - Neither the name of Sundog Interactive nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission of Sundog Interactive.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.sundog.chatternotifier;

/**
 * The Class Comment.
 */
public class Comment {

	/** The chatter id. */
	private String chatterId;

	/** The comment id. */
	private String commentId;

	/** The author. */
	private String author;

	/** The body. */
	private String body;

	/**
	 * Gets the author.
	 * 
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 * 
	 * @param author
	 *            the new author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Gets the body.
	 * 
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 * 
	 * @param body
	 *            the new body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Gets the chatter id.
	 * 
	 * @return the chatter id
	 */
	public String getChatterId() {
		return chatterId;
	}

	/**
	 * Sets the chatter id.
	 * 
	 * @param chatterId
	 *            the new chatter id
	 */
	public void setChatterId(String chatterId) {
		this.chatterId = chatterId;
	}

	/**
	 * Gets the comment id.
	 * 
	 * @return the comment id
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * Sets the comment id.
	 * 
	 * @param commentId
	 *            the new comment id
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Comment [chatterId=" + chatterId + ", commentId=" + commentId
				+ ", author=" + author + ", body=" + body + "]";
	}
}
