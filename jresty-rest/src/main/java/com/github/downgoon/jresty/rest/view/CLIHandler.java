package com.github.downgoon.jresty.rest.view;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

public class CLIHandler implements ContentTypeHandler {

	@Override
	public String fromObject(Object obj, String resultCode, Writer stream)
			throws IOException {
		if (obj != null) {
            if (obj instanceof Map) {
            	StringBuffer sbCLI = new StringBuffer();
            	map2cli((Map)obj, sbCLI);
                stream.write(sbCLI.toString());
            } else {
                stream.write(obj.toString());
            }
        }
        return null;
	}
	
	protected void map2cli(Map r,StringBuffer sbCLI) {
		Iterator<Map.Entry> entries = r.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry item = entries.next();
			String k = item.getKey().toString();
			String v = item.getValue().toString();
			if(keyRequired) {
				sbCLI.append(k).append(sepKeyValue);
			}
			sbCLI.append(v);
			sbCLI.append(sepFields);
		}
		if(! this.lastSepRequired) {
			sbCLI.delete(sbCLI.length()-this.sepFields.length(), sbCLI.length());
		}
	}
	
	
	protected String contentType="text/html";
    
	protected String contentEncode = "UTF-8";
    
	protected String extension = "cli";
    
    /** 字段分隔符*/
	protected String sepFields = "|";

    /** 最后一个字段是否也需要补充分隔符号*/
	protected boolean lastSepRequired = false;
    
    /** 是否需要显示的指明KEY*/
	protected boolean keyRequired = false;
    
    /** 如果需要指明KEY时，那么 key和value之间的分隔符*/
	protected String sepKeyValue = "=";
    
    @Override
	public String getExtension() {
        return this.extension;
    }

    @Override
	public String getContentType() {
        return this.contentType;
    }

	@Override
	public String getContentEncode() {
		return this.contentEncode;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContentEncode(String contentEncode) {
		this.contentEncode = contentEncode;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	private Object attachement;
	@Override
	public Object getAttachment() {
		return attachement;
	}

	@Override
	public void setAttachment(Object attachment) {
		this.attachement = attachment;
	}

	public boolean isLastSepRequired() {
		return lastSepRequired;
	}

	public void setLastSepRequired(boolean lastSepRequired) {
		this.lastSepRequired = lastSepRequired;
	}

	public String getSepFields() {
		return sepFields;
	}

	public void setSepFields(String sepFields) {
		this.sepFields = sepFields;
	}

	public boolean isKeyRequired() {
		return keyRequired;
	}

	public void setKeyRequired(boolean keyRequired) {
		this.keyRequired = keyRequired;
	}

	public String getSepKeyValue() {
		return sepKeyValue;
	}

	public void setSepKeyValue(String sepKeyValue) {
		this.sepKeyValue = sepKeyValue;
	}

	private int cacheControlSecond = -1;
	
	@Override
	public int getCacheControlSecond() {
		return cacheControlSecond;
	}

	public void setCacheControlSecond(int cacheControlSecond) {
		this.cacheControlSecond = cacheControlSecond;
	}
	
	
}
