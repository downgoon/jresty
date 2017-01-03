/**
 * 
 */
package com.github.downgoon.jresty.data.http;

import java.util.List;

/**
 * @author liwei
 *
 */
public class Topic implements Comparable<Topic> {

	// e.g. newuser
	private String Name; 
	
	// e.g. 1
	private Integer ZkId;
    
    // e.g. "/ffan/kafka/ffan_service/coupon"
    private String ZkPath;
    
    // e.g. "10.77.130.12:2181"
    private String ZkAddr;

    // e.g. 2
    private Integer ReplicaNum;
    
    private List<Partition> Partitions;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getZkId() {
		return ZkId;
	}

	public void setZkId(Integer zkId) {
		ZkId = zkId;
	}

	public String getZkPath() {
		return ZkPath;
	}

	public void setZkPath(String zkPath) {
		ZkPath = zkPath;
	}

	public String getZkAddr() {
		return ZkAddr;
	}

	public void setZkAddr(String zkAddr) {
		ZkAddr = zkAddr;
	}

	public Integer getReplicaNum() {
		return ReplicaNum;
	}

	public void setReplicaNum(Integer replicaNum) {
		ReplicaNum = replicaNum;
	}

	public List<Partition> getPartitions() {
		return Partitions;
	}

	public void setPartitions(List<Partition> partitions) {
		Partitions = partitions;
	}

	@Override
	public String toString() {
		return "Topic [Name=" + Name + ", ZkId=" + ZkId + ", ZkPath=" + ZkPath + ", ZkAddr=" + ZkAddr + ", ReplicaNum="
				+ ReplicaNum + ", Partitions=" + Partitions + "]";
	}
	
	public String key() {
		return Name + "@" + ZkId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((ZkId == null) ? 0 : ZkId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (ZkId == null) {
			if (other.ZkId != null)
				return false;
		} else if (!ZkId.equals(other.ZkId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Topic o) {
		if (this.Name.equals(o.Name)) {
			return this.ZkId.compareTo(o.ZkId);
		} else {
			return this.Name.compareTo(o.Name);
		}
	}
    
	
	
}
