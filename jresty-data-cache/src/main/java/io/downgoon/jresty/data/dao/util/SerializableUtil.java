package io.downgoon.jresty.data.dao.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

public class SerializableUtil {

	public static final int NULL = 0x00;
	public static final int NOTNULL = 0x01;

	public static byte[] serialize(Object object) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8 * 1024);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(object);
		oos.close();
		byte[] data = bos.toByteArray();
		bos.close();
		return data;
	}

	public static Object deSerialize(byte[] data) throws Exception {
		Object object = null;
		if (data != null) {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bis);
			object = ois.readObject();
		}
		return object;
	}

	public static Timestamp readTimestamp(ObjectInput in, long nullFlag) throws ClassNotFoundException, IOException {
		Timestamp stamp = null;
		long value = in.readLong();
		if (value != nullFlag) {
			stamp = new Timestamp(value);
		}
		return stamp;
	}

	public static void writeTimestamp(Timestamp stamp, ObjectOutput out, long nullFlag) throws IOException {
		if (stamp != null) {
			long value = stamp.getTime();
			out.writeLong(value);
		} else {
			out.writeLong(nullFlag);
		}
	}

	public static String readString(ObjectInput in) throws ClassNotFoundException, IOException {
		byte b = in.readByte();
		switch (b) {
		case NULL:
			return null;
		case NOTNULL:
			return in.readUTF();
		}
		throw new InvalidObjectException("null flag broken:" + b);
	}

	public static void writeString(String string, ObjectOutput out) throws IOException {
		if (string == null) {
			out.writeByte(NULL);
		} else {
			out.writeByte(NOTNULL);
			out.writeUTF(string);
		}
	}

	public static Long readLong(ObjectInput in) throws ClassNotFoundException, IOException {
		byte b = in.readByte();
		switch (b) {
		case NULL:
			return null;
		case NOTNULL:
			return in.readLong();
		}
		throw new InvalidObjectException("null flag broken:" + b);
	}

	public static void writeLong(Long num, ObjectOutput out) throws IOException {
		if (num == null) {
			out.writeByte(NULL);
		} else {
			out.writeByte(NOTNULL);
			out.writeLong(num);
		}
	}

	public static void writeInt(Integer num, ObjectOutput out) throws IOException {
		if (num == null) {
			out.writeByte(NULL);
		} else {
			out.writeByte(NOTNULL);
			out.writeInt(num);
		}
	}

	public static Integer readInt(ObjectInput in) throws ClassNotFoundException, IOException {
		byte b = in.readByte();
		switch (b) {
		case NULL:
			return null;
		case NOTNULL:
			return in.readInt();
		}
		throw new InvalidObjectException("null flag broken:" + b);
	}

	public static void writeByte(Byte num, ObjectOutput out) throws IOException {
		if (num == null) {
			out.writeByte(NULL);
		} else {
			out.writeByte(NOTNULL);
			out.writeByte(num);
		}
	}

	public static Byte readByte(ObjectInput in) throws ClassNotFoundException, IOException {
		byte b = in.readByte();
		switch (b) {
		case NULL:
			return null;
		case NOTNULL:
			return in.readByte();
		}
		throw new InvalidObjectException("null flag broken:" + b);
	}

	public static void writeObject(Object obj, ObjectOutput out) throws IOException {
		if (obj == null) {
			out.writeByte(NULL);
		} else {
			out.writeByte(NOTNULL);
			out.writeObject(obj);
		}
	}

	public static Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
		byte b = in.readByte();
		switch (b) {
		case NULL:
			return null;
		case NOTNULL:
			return in.readObject();
		}
		throw new InvalidObjectException("null flag broken:" + b);
	}

}
