package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revature.annotations.Column;
import com.revature.annotations.Id;

public class ObjectGetter extends ObjectMapper {

	private Logger log = Logger.getLogger(ObjectGetter.class);

	/*
	 * Gets a list of all objects in the database which match the included search
	 * criteria columns - comma separated list of columns to search by. conditions -
	 * comma separated list the values the columns should match to. operators -
	 * comma separated list of operators to apply to columns (AND/OR) in order that
	 * they should be applied.
	 */
	
	public List<Object> getListObjectFromDB(final Class<?> clazz, final String columns,
			 Connection cn) {
		String sql = "SELECT " + columns + " FROM " + clazz.getSimpleName();

		List<Object> objsList = new ArrayList<Object>();
		String[] arrColumns = columns.split(",");
		Field[] fields = clazz.getDeclaredFields();
		List<Field> fieldsToUpdate = new ArrayList<Field>();
		for (Field f : fields) {
			for (String s : arrColumns) {
				try {
					if(f.getAnnotation(Id.class).columnName().equals(s)) {
						fieldsToUpdate.add(f);
					}

				} catch (NullPointerException e) {
					if (f.getAnnotation(Column.class).columnName().equals(s)) {
						// check if the field is a column and matches the fields being edited
						fieldsToUpdate.add(f);
					}
				}
			}
		}
		
		try {
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Object thisObject = clazz.newInstance();
				for (Field field : fieldsToUpdate) {
					try {
						if (field.getType() == String.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getString(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}

						} else if (field.getType() == int.class || field.getType() == Integer.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == long.class || field.getType() == Long.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getLong(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == double.class || field.getType() == Double.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getDouble(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == float.class || field.getType() == Float.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getFloat(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == int.class || field.getType() == Integer.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == BigDecimal.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBigDecimal(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == char.class || field.getType() == Character.class) {
							try {
								field.setAccessible(true);
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null
										&& rs.getString(field.getAnnotation(Column.class).columnName()).length() > 0) {
									field.set(thisObject, rs.getString(field.getAnnotation(Column.class).columnName()).charAt(0));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == Date.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getTimestamp(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							} catch (NullPointerException npe) {
								npe.printStackTrace();
							}
						} else if (field.getType() == boolean.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBoolean(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						} else if (field.getType() == Boolean.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBoolean(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						} else if (field.getType() instanceof Class && ((Class<?>) field.getType()).isEnum()
								&& field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
							try {
								field.setAccessible(true);
								field.set(thisObject,
										Enum.valueOf((Class<Enum>) field.getType(), rs.getString(field.getAnnotation(Column.class).columnName())));
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				Object newObj = thisObject;
				objsList.add(newObj);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objsList;
	}
	
	public List<Object> getListObjectFromDB(final Class<?> clazz, final String columns,
			final String conditions, Connection cn) {
		String sql = "SELECT " + columns + " FROM " + clazz.getSimpleName() + " WHERE " + conditions;
		
		List<Object> objsList = new ArrayList<Object>();
		String[] arrColumns = columns.split(",");
		Field[] fields = clazz.getDeclaredFields();
		List<Field> fieldsToUpdate = new ArrayList<Field>();
		for (Field f : fields) {
			for (String s : arrColumns) {
				try {
					if(f.getAnnotation(Id.class).columnName().equals(s)) {
						fieldsToUpdate.add(f);
					}

				} catch (NullPointerException e) {
					if (f.getAnnotation(Column.class).columnName().equals(s)) {
						// check if the field is a column and matches the fields being edited
						fieldsToUpdate.add(f);
					}
				}
			}
		}
		
		try {
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Object thisObject = clazz.newInstance();
				for (Field field : fieldsToUpdate) {
					try {
						if (field.getType() == String.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getString(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}

						} else if (field.getType() == int.class || field.getType() == Integer.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == long.class || field.getType() == Long.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getLong(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == double.class || field.getType() == Double.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getDouble(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == float.class || field.getType() == Float.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getFloat(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == int.class || field.getType() == Integer.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == BigDecimal.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBigDecimal(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == char.class || field.getType() == Character.class) {
							try {
								field.setAccessible(true);
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null
										&& rs.getString(field.getAnnotation(Column.class).columnName()).length() > 0) {
									field.set(thisObject, rs.getString(field.getAnnotation(Column.class).columnName()).charAt(0));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == Date.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getTimestamp(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							} catch (NullPointerException npe) {
								npe.printStackTrace();
							}
						} else if (field.getType() == boolean.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBoolean(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						} else if (field.getType() == Boolean.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBoolean(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						} else if (field.getType() instanceof Class && ((Class<?>) field.getType()).isEnum()
								&& field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
							try {
								field.setAccessible(true);
								field.set(thisObject,
										Enum.valueOf((Class<Enum>) field.getType(), rs.getString(field.getAnnotation(Column.class).columnName())));
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				Object newObj = thisObject;
				objsList.add(newObj);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objsList;

	}

	public List<Object> getListObjectFromDB(final Class<?> clazz, Connection cn) {
		String sql = "SELECT * FROM " + clazz.getSimpleName();

		List<Object> objsList = new ArrayList<Object>();
		try {
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Object thisObject = clazz.newInstance();
				for (Field field : clazz.getFields()) {
					try {
						if (field.getType() == String.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getString(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}

						} else if (field.getType() == int.class || field.getType() == Integer.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == long.class || field.getType() == Long.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getLong(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == double.class || field.getType() == Double.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getDouble(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == float.class || field.getType() == Float.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getFloat(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == int.class || field.getType() == Integer.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == BigDecimal.class) {
							try {
								if(field.isAnnotationPresent(Id.class)) {
									field.setAccessible(true);
									field.set(thisObject, rs.getInt(field.getAnnotation(Id.class).columnName()));
								} else if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBigDecimal(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == char.class || field.getType() == Character.class) {
							try {
								field.setAccessible(true);
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null
										&& rs.getString(field.getAnnotation(Column.class).columnName()).length() > 0) {
									field.set(thisObject, rs.getString(field.getAnnotation(Column.class).columnName()).charAt(0));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (field.getType() == Date.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getTimestamp(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							} catch (NullPointerException npe) {
								npe.printStackTrace();
							}
						} else if (field.getType() == boolean.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBoolean(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						} else if (field.getType() == Boolean.class) {
							try {
								if (field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
									field.setAccessible(true);
									field.set(thisObject, rs.getBoolean(field.getAnnotation(Column.class).columnName()));
								}
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						} else if (field.getType() instanceof Class && ((Class<?>) field.getType()).isEnum()
								&& field.getAnnotation(Column.class).columnName() != null && rs.getString(field.getAnnotation(Column.class).columnName()) != null) {
							try {
								field.setAccessible(true);
								field.set(thisObject,
										Enum.valueOf((Class<Enum>) field.getType(), rs.getString(field.getAnnotation(Column.class).columnName())));
							} catch (SQLException e) {
								//e.printStackTrace();
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				Object newObj = thisObject;
				objsList.add(newObj);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objsList;
	}

}
