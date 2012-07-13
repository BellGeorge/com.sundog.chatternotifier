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
 * The Class Chatter.
 */
public class Chatter {

	/** The id. */
	private Long id;

	/** The chatter id. */
	private String chatterId;

	/** The actor. */
	private String actor;

	/** The post. */
	private String post;

	/** The comment count. */
	private Integer commentCount;

	/** The like count. */
	private Integer likeCount;

	/** The create time. */
	private Long createTime;

	/** The comments. */
	private Comment comments;

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
	 * Gets the actor.
	 * 
	 * @return the actor
	 */
	public String getActor() {
		return actor;
	}

	/**
	 * Sets the actor.
	 * 
	 * @param actor
	 *            the new actor
	 */
	public void setActor(String actor) {
		this.actor = actor;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the post.
	 * 
	 * @return the post
	 */
	public String getPost() {
		return post;
	}

	/**
	 * Sets the post.
	 * 
	 * @param post
	 *            the new post
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * Gets the comment count.
	 * 
	 * @return the comment count
	 */
	public Integer getCommentCount() {
		return commentCount;
	}

	/**
	 * Sets the comment count.
	 * 
	 * @param commentCount
	 *            the new comment count
	 */
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * Gets the like count.
	 * 
	 * @return the like count
	 */
	public Integer getLikeCount() {
		return likeCount;
	}

	/**
	 * Sets the like count.
	 * 
	 * @param likeCount
	 *            the new like count
	 */
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * Gets the creates the time.
	 * 
	 * @return the creates the time
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the creates the time.
	 * 
	 * @param createTime
	 *            the new creates the time
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the comments.
	 * 
	 * @return the comments
	 */
	public Comment getComments() {
		return comments;
	}

	/**
	 * Sets the comments.
	 * 
	 * @param comments
	 *            the new comments
	 */
	public void setComments(Comment comments) {
		this.comments = comments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Chatter [id=" + id + ", chatterId=" + chatterId + ", actor="
				+ actor + ", post=" + post + ", commentCount=" + commentCount
				+ ", likeCount=" + likeCount + ", createTime=" + createTime
				+ ", comments=" + comments + "]";
	}

}
