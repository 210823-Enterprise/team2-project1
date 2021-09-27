package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.apache.log4j.Logger;

import java.util.Arrays;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

public class ObjectSaver extends ObjectMapper {
	private static Logger log = Logger.getLogger(ObjectSaver.class);

	// Update columns will be comma separated string that shouldn't contain id,
	// since that is how the function finds the entry to update.
	public boolean UpdateObjectInDB(final Object obj, final String update_columns, Connection conn)
			throws IllegalArgumentException {
		String u_columns = update_columns.replace(" ", "");
		String[] columns = u_columns.split(",");
		Field[] fields = obj.getClass().getDeclaredFields();
		List<Field> fieldsToUpdate = new ArrayList<Field>();
		int id = 0;
		String idName = "";
		for (Field f : fields) {
			for (String s : columns) {
				try {
					if (f.getAnnotation(Id.class).columnName().equals(s)) {
						// check if the field is an id and matches the fields being edited
						throw new IllegalArgumentException(
								"Check that update string does not contain the name of the table's primary key");
					}
					id = (int) f.get(obj);
					idName = f.getAnnotation(Id.class).columnName();

				} catch (NullPointerException e) {
					if (f.getAnnotation(Column.class).columnName().equals(s)) {
						// check if the field is a column and matches the fields being edited
						fieldsToUpdate.add(f);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					log.warn(e);
					return false;
				}
			}
		}
		if (fieldsToUpdate.isEmpty()) {
			// log something went wrong
			throw new IllegalArgumentException("Check that update string has correct column names");
		}
		String sql = "UPDATE " + "\"" + obj.getClass().getAnnotation(Entity.class).tableName() + "\"" + " SET";

		int fieldCounter = 0;
		for (Field field : fieldsToUpdate) {

			fieldCounter++;
			sql += " " + field.getAnnotation(Column.class).columnName() + " = ?";

			if (fieldsToUpdate.size() > fieldCounter)
				sql += ",";
		}
		sql += " WHERE " + idName + " = " + id + ";";
		log.info("SQL UPDATE called with query: " + sql);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long newId = 0L;
		try {
			pstmt = conn.prepareStatement(sql);
			int fieldCounter3 = 1;
			for (Field f : fieldsToUpdate) {
				try {
					// add more types when needed, for now, just testing with the two i'm likely to
					// use
					if (f.getType() == String.class) {
						if (f.get(obj) != null) {
							pstmt.setString(fieldCounter3, f.get(obj).toString());
						} else {
							pstmt.setNull(fieldCounter3, Types.VARCHAR);
						}
					} else if (f.getType() == int.class || f.getType() == Integer.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setInt(fieldCounter3, (int) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.INTEGER);
						}
					} else if (f.getType() == Long.class || f.getType() == long.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setLong(fieldCounter3, (long) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.BIGINT);
						}
					} else if (f.getType() == Double.class || f.getType() == double.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setDouble(fieldCounter3, (double) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.BIGINT);
						}
					} else if (f.getType() == Float.class || f.getType() == float.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setFloat(fieldCounter3, (float) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.BIGINT);
						}
					} else if (f.getType() == BigDecimal.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setBigDecimal(fieldCounter3, (BigDecimal) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.NUMERIC);
						}
					} else if (f.getType() == boolean.class || f.getType() == Boolean.class) {
						if (f.get(obj) != null) {
							pstmt.setBoolean(fieldCounter3, (boolean) f.get(obj));
						} else {
							pstmt.setNull(fieldCounter3, Types.TINYINT);
						}

					} else if (f.getType() == char.class || f.getType() == Character.class) {
						if (f.get(obj) != null && f.get(obj).toString() != null
								&& f.get(obj).toString().substring(0, 1) != null) {

							Character convert = f.get(obj).toString().charAt(0);

							// check to see if the character is a null character -- screw you postgres this
							// better work
							if (Character.isLetterOrDigit(f.get(obj).toString().charAt(0))) {
								pstmt.setString(fieldCounter3, convert.toString());
							} else {
								pstmt.setNull(fieldCounter3, Types.CHAR);
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.CHAR);
						}
					}  else if (f.getType() == Date.class) {
                        Date insertDate = (Date) f.get(obj);
                        if (insertDate == null) {
                            pstmt.setTimestamp(fieldCounter3, null);
                        } else {
                            pstmt.setTimestamp(fieldCounter3, new Timestamp(insertDate.getTime()));
                        }

                    }
					try {
						f.getAnnotation(Id.class).columnName();
					} catch (NullPointerException e) {
						fieldCounter3++;
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					log.warn(e);
					return false;
				} catch (SQLException e) {
					e.printStackTrace();
					log.warn(e);
					return false;
				}
			}
			// TODO log something here
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			// newId = rs.getLong("id"); //in case we want to return the id.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					log.warn(e);
					return false;
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
						log.warn(e);
						return false;
					}
				}
			}
		}

		log.info("UPDATE OBJECT IN DB SUCCESFUL");
		return true;
	}

	public boolean addObjectToDB(final Object obj, Connection conn) {
		// check if table exists
		try {
			if (!(tableExists(conn, obj.getClass().getAnnotation(Entity.class).tableName()))) {
				addTableToDB(obj, conn);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// get a list of fields in the object
		Field[] fields = obj.getClass().getDeclaredFields();
		// begin sql statement, inserting into the table
		String sql = "INSERT INTO " + "\"" + obj.getClass().getAnnotation(Entity.class).tableName().toLowerCase() + "\"" + " (";

		int fieldCounter = 0;
		for (Field f : fields) {
			fieldCounter++;
			// add field names to sql string
			try {
				f.getAnnotation(Id.class).columnName();
			} catch (NullPointerException e) {
				sql += " " + f.getAnnotation(Column.class).columnName();
				if (fields.length > fieldCounter)
					sql += ",";
			}

		}

		sql += " ) values (";

		// insert question marks
		int fieldCounter2 = 0;
		for (Field f : fields) {
			fieldCounter2++;
			try {
				f.getAnnotation(Id.class).columnName();
			} catch (NullPointerException e) {
				sql += "?";
				if (fields.length > fieldCounter2)
					sql += ",";
			}
		}
		sql += ");";
		log.info("SQL INSERT called with query: " + sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long newId = 0L;
		try {
			pstmt = conn.prepareStatement(sql);
			int fieldCounter3 = 1;
			for (Field f : fields) {
				try {
					// add more types when needed, for now, just testing with the two i'm likely to
					// use
					if (f.getType() == String.class) {
						if (f.get(obj) != null) {
							pstmt.setString(fieldCounter3, f.get(obj).toString());
						} else {
							pstmt.setNull(fieldCounter3, Types.VARCHAR);
						}
					} else if (f.getType() == int.class || f.getType() == Integer.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setInt(fieldCounter3, (int) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.INTEGER);
						}
					} else if (f.getType() == Long.class || f.getType() == long.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setLong(fieldCounter3, (long) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.BIGINT);
						}
					} else if (f.getType() == Double.class || f.getType() == double.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
									pstmt.setDouble(fieldCounter3, (double) f.get(obj));
			
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.BIGINT);
						}
					} else if (f.getType() == Float.class || f.getType() == float.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setFloat(fieldCounter3, (float) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.BIGINT);
						}
					} else if (f.getType() == BigDecimal.class) {
						if (f.get(obj) != null) {
							try {
								f.getAnnotation(Id.class).columnName();
							} catch (NullPointerException e) {
								pstmt.setBigDecimal(fieldCounter3, (BigDecimal) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.NUMERIC);
						}
					} else if (f.getType() == boolean.class || f.getType() == Boolean.class) {
						if (f.get(obj) != null) {
							pstmt.setBoolean(fieldCounter3, (boolean) f.get(obj));
						} else {
							pstmt.setNull(fieldCounter3, Types.TINYINT);
						}

					} else if (f.getType() == char.class || f.getType() == Character.class) {
						if (f.get(obj) != null && f.get(obj).toString() != null
								&& f.get(obj).toString().substring(0, 1) != null) {

							Character convert = f.get(obj).toString().charAt(0);

							// check to see if the character is a null character -- screw you postgres this
							// better work
							if (Character.isLetterOrDigit(f.get(obj).toString().charAt(0))) {
								pstmt.setString(fieldCounter3, convert.toString());
							} else {
								pstmt.setNull(fieldCounter3, Types.CHAR);
							}
						}
					} else if (f.getType() == Date.class) {
                        Date insertDate = (Date) f.get(obj);
                        if (insertDate == null) {
                            pstmt.setTimestamp(fieldCounter3, null);
                        } else {
                            pstmt.setTimestamp(fieldCounter3, new Timestamp(insertDate.getTime()));
                        }

                    }
					try {
						f.getAnnotation(Id.class).columnName();
					} catch (NullPointerException e) {
						fieldCounter3++;
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					log.warn(e);
					return false;
				} catch (SQLException e) {
					e.printStackTrace();
					log.warn(e);
					return false;
				}
			}
			// TODO log something here
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			// newId = rs.getLong("id"); //in case we want to return the id.
			// System.out.println(newId); //here for debugging

		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e);
			return false;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					log.warn(e);
					return false;
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
						log.warn(e);
						return false;
					}
				}
			}
		}
		log.info("INSERT OBJECT IN DB SUCCESFUL");
		return true;
	}

	public boolean addTableToDB(final Object obj, Connection conn) {
		Field[] fields = obj.getClass().getDeclaredFields();
		String sql = "DROP Table IF EXISTS " + obj.getClass().getAnnotation(Entity.class).tableName() + ";"
				+ " Create Table " + obj.getClass().getAnnotation(Entity.class).tableName() + " (";
		int fieldCounter = 0;
		for (Field f : fields) {
			fieldCounter++;
			try {
				sql += f.getAnnotation(Id.class).columnName() + " " + "Serial";
				if (fields.length > fieldCounter)
					sql += ",";
			} catch (NullPointerException e) {
				if (f.getType() == String.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " varchar(255)";
					if (fields.length > fieldCounter)
						sql += ",";
				} else if (f.getType() == int.class || f.getType() == Integer.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " INTEGER";
					if (fields.length > fieldCounter)
						sql += ",";
				} else if (f.getType() == long.class || f.getType() == Long.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " BIGINT";
					if (fields.length > fieldCounter)
						sql += ",";
				} else if (f.getType() == double.class || f.getType() == Double.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " BIGINT";
					if (fields.length > fieldCounter)
						sql += ",";
				} else if (f.getType() == float.class || f.getType() == Float.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " BIGINT";
					if (fields.length > fieldCounter)
						sql += ",";
				} else if (f.getType() == BigDecimal.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " NUMERIC";
					if (fields.length > fieldCounter)
						sql += ",";
				} else if (f.getType() == boolean.class || f.getType() == Boolean.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " BOOLEAN";
					if (fields.length > fieldCounter)
						sql += ",";
				} else if (f.getType() == char.class || f.getType() == Character.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " CHAR";
					if (fields.length > fieldCounter)
						sql += ",";
				} else if (f.getType() == Date.class) {
					sql += " " + f.getAnnotation(Column.class).columnName() + " TIMESTAMP";
					if (fields.length > fieldCounter)
						sql += ",";
				}
			}
		}
		sql += ");";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.warn(e);
			e.printStackTrace();
			return false;
		}
		log.info("Database Table created with query: " + sql);
		return true;
	}

	boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData meta = connection.getMetaData();
		ResultSet resultSet = meta.getTables(null, null, tableName, new String[] { "TABLE" });

		return resultSet.next();
	}
}
