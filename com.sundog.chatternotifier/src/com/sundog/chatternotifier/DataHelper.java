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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * The Class DataHelper.
 */
public class DataHelper {

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "chatter_notifier.db";

	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 1;

	/** The Constant TABLE_DATA. */
	private static final String TABLE_CHATTER = "chatter";

	/** The Constant TABLE_DATA. */
	private static final String TABLE_COMMENTS = "comments";

	/** The context context. */
	private Context context;

	/** The database db. */
	private SQLiteDatabase db;

	/** The insert data. */
	private SQLiteStatement insertChatter;

	/** The insert data. */
	private SQLiteStatement insertComment;

	/** The Constant INSERT_CHATTER. */
	private static final String INSERT_CHATTER = "insert into " + TABLE_CHATTER
			+ "(chatterId, actor, post, commentCount, likeCount, createTime) "
			+ "values (?,?,?,?,?,?)";

	/** The Constant INSERT_COMMENT. */
	private static final String INSERT_COMMENT = "insert into "
			+ TABLE_COMMENTS + "(chatterId, commentId, actor, post) "
			+ "values (?,?,?,?)";

	/**
	 * Instantiates a new data helper.
	 * 
	 * @param context
	 *            the context
	 */
	public DataHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.insertChatter = this.db.compileStatement(INSERT_CHATTER);
		this.insertComment = this.db.compileStatement(INSERT_COMMENT);
	}

	/**
	 * Insert data.
	 * 
	 * @param comment
	 *            the comment
	 * @return the number of inserted
	 */
	public long insertComment(Comment comment) {
		this.insertComment.bindString(1, comment.getChatterId());
		this.insertComment.bindString(2, comment.getCommentId());
		this.insertComment.bindString(3, comment.getAuthor());
		this.insertComment.bindString(4, comment.getBody());
		return this.insertComment.executeInsert();
	}

	/**
	 * Insert chatter.
	 * 
	 * @param chatter
	 *            the chatter
	 * @return the long
	 */
	public long insertChatter(Chatter chatter) {
		this.insertChatter.bindString(1, chatter.getChatterId());
		this.insertChatter.bindString(2, chatter.getActor());
		this.insertChatter.bindString(3, chatter.getPost());
		this.insertChatter.bindLong(4, chatter.getCommentCount());
		this.insertChatter.bindLong(5, chatter.getLikeCount());
		this.insertChatter.bindLong(6, System.currentTimeMillis());
		return this.insertChatter.executeInsert();
	}

	/**
	 * Insert chatter list.
	 * 
	 * @param chatterList
	 *            the chatter list
	 */
	public void insertChatterList(List<Chatter> chatterList) {
		for (Chatter c : chatterList) {
			insertChatter(c);
		}
	}

	/**
	 * Insert comment list.
	 * 
	 * @param commentList
	 *            the comment list
	 */
	public void insertCommentList(List<Comment> commentList) {
		for (Comment c : commentList) {
			insertComment(c);
		}
	}

	/**
	 * Update chatter list.
	 * 
	 * @param chatterList
	 *            the chatter list
	 */
	public void updateChatterList(List<Chatter> chatterList) {
		for (Chatter c : chatterList) {
			updateChatter(c);
		}
	}

	/**
	 * Clean up data.
	 */
	public void cleanUpData() {
		Long aWeekAgo = System.currentTimeMillis() - 604800000;
		List<Chatter> chatterList = selectAllChatter(aWeekAgo);
		for (Chatter c : chatterList) {
			deleteSingleData(c.getId());
		}
	}

	/**
	 * Update data.
	 * 
	 * @param chatter
	 *            the chatter
	 */
	public void updateChatter(Chatter chatter) {
		ContentValues vals = new ContentValues();
		vals.put("chatterId", chatter.getChatterId());
		vals.put("actor", chatter.getActor());
		vals.put("post", chatter.getPost());
		vals.put("commentCount", chatter.getCommentCount());
		vals.put("likeCount", chatter.getLikeCount());
		vals.put("createTime", System.currentTimeMillis());
		this.db.update(TABLE_CHATTER, vals, "id=" + chatter.getId(), null);
	}

	/**
	 * Update data.
	 * 
	 * @param chatterList
	 *            the chatter list
	 */
	public void updateData(List<Chatter> chatterList) {
		for (Chatter chatter : chatterList) {
			ContentValues vals = new ContentValues();
			vals.put("chatterId", chatter.getChatterId());
			vals.put("actor", chatter.getActor());
			vals.put("post", chatter.getPost());
			vals.put("commentCount", chatter.getCommentCount());
			vals.put("likeCount", chatter.getLikeCount());
			vals.put("createTime", System.currentTimeMillis());
			this.db.update(TABLE_CHATTER, vals, "id=" + chatter.getId(), null);
		}
	}

	/**
	 * Delete data deletes all data.
	 */
	public void deleteAllChatter() {
		this.db.delete(TABLE_CHATTER, null, null);
		this.db.delete(TABLE_COMMENTS, null, null);
	}

	/**
	 * Delete a single record of data.
	 * 
	 * @param id
	 *            of the record
	 */
	public void deleteSingleData(long id) {
		this.db.delete(TABLE_CHATTER, "id = " + id, null);
	}

	/**
	 * Select all data.
	 * 
	 * @return the list of Items
	 */
	public List<Chatter> selectAllChatter() {
		List<Chatter> list = new ArrayList<Chatter>();
		Cursor cursor = this.db
				.rawQuery("SELECT * FROM " + TABLE_CHATTER, null);
		if (cursor.moveToFirst()) {
			do {
				Chatter c = new Chatter();
				c.setId(cursor.getLong(0));
				c.setChatterId(cursor.getString(1));
				c.setActor(cursor.getString(2));
				c.setPost(cursor.getString(3));
				c.setCommentCount(cursor.getInt(4));
				c.setLikeCount(cursor.getInt(5));
				c.setCreateTime(cursor.getLong(6));
				list.add(c);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	/**
	 * Select all data.
	 * 
	 * @param time
	 *            the time
	 * @return the list of Items
	 */
	public List<Chatter> selectAllChatter(Long time) {
		List<Chatter> list = new ArrayList<Chatter>();
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + TABLE_CHATTER
				+ " WHERE createTime < " + time, null);
		if (cursor.moveToFirst()) {
			do {
				Chatter c = new Chatter();
				c.setId(cursor.getLong(0));
				c.setChatterId(cursor.getString(1));
				c.setActor(cursor.getString(2));
				c.setPost(cursor.getString(3));
				c.setCommentCount(cursor.getInt(4));
				c.setLikeCount(cursor.getInt(5));
				c.setCreateTime(cursor.getLong(6));
				list.add(c);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	/**
	 * Select all data.
	 * 
	 * @return the list of Items
	 */
	public Map<String, Chatter> selectAllChatterMap() {
		Map<String, Chatter> map = new HashMap<String, Chatter>();
		Cursor cursor = this.db
				.rawQuery("SELECT * FROM " + TABLE_CHATTER, null);
		if (cursor.moveToFirst()) {
			do {
				Chatter c = new Chatter();
				c.setId(cursor.getLong(0));
				c.setChatterId(cursor.getString(1));
				c.setActor(cursor.getString(2));
				c.setPost(cursor.getString(3));
				c.setCommentCount(cursor.getInt(4));
				c.setLikeCount(cursor.getInt(5));
				c.setCreateTime(cursor.getLong(6));
				map.put(c.getChatterId(), c);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return map;
	}

	/**
	 * Select all data.
	 * 
	 * @return the list of Items
	 */
	public Map<String, Comment> selectAllCommentMap() {
		Map<String, Comment> map = new HashMap<String, Comment>();
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + TABLE_COMMENTS,
				null);
		if (cursor.moveToFirst()) {
			do {
				Comment c = new Comment();
				c.setChatterId(cursor.getString(1));
				c.setCommentId(cursor.getString(2));
				c.setAuthor(cursor.getString(3));
				c.setBody(cursor.getString(4));
				map.put(c.getCommentId(), c);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return map;
	}

	/**
	 * Closes the database.
	 */
	public void close() {
		this.db.close();
	}

	/**
	 * The Class OpenHelper.
	 */
	private static class OpenHelper extends SQLiteOpenHelper {

		/**
		 * Instantiates a new open helper.
		 * 
		 * @param context
		 *            the context
		 */
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/**
		 * On create called when DB is created.
		 * 
		 * @param db
		 *            the db
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ TABLE_CHATTER
					+ "(id INTEGER PRIMARY KEY, chatterId TEXT, actor TEXT, post TEXT, commentCount INTEGER, likeCount INTEGER, createTime FLOAT) ");
			db.execSQL("CREATE TABLE "
					+ TABLE_COMMENTS
					+ "(id INTEGER PRIMARY KEY, chatterId TEXT, commentId TEXT, actor TEXT, post TEXT) ");
		}

		/**
		 * On upgrade called when DB is upgraded.
		 * 
		 * @param db
		 *            the db
		 * @param oldVersion
		 *            the old version
		 * @param newVersion
		 *            the new version
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example",
					"Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATTER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
			onCreate(db);
		}
	}
}