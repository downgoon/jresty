/**
 * 
 */
package io.downgoon.jresty.data.http;

/**
 * @author liwei
 *
 */
public class Partition {

	// e.g. "10.77.135.94:10105"
	private String LeaderBrokerAddr;
	
	// e.g. 0
    private Integer PartitionId; 
    
    // e.g. 891
    private Long OffsetNewest;
    
    // e.g. 36
    private Long OffsetOldest; 
    
    // e.g. 855
    private Long LogsNum;

	public String getLeaderBrokerAddr() {
		return LeaderBrokerAddr;
	}

	public void setLeaderBrokerAddr(String leaderBrokerAddr) {
		LeaderBrokerAddr = leaderBrokerAddr;
	}

	public Integer getPartitionId() {
		return PartitionId;
	}

	public void setPartitionId(Integer partitionId) {
		PartitionId = partitionId;
	}

	public Long getOffsetNewest() {
		return OffsetNewest;
	}

	public void setOffsetNewest(Long offsetNewest) {
		OffsetNewest = offsetNewest;
	}

	public Long getOffsetOldest() {
		return OffsetOldest;
	}

	public void setOffsetOldest(Long offsetOldest) {
		OffsetOldest = offsetOldest;
	}

	public Long getLogsNum() {
		return LogsNum;
	}

	public void setLogsNum(Long logsNum) {
		LogsNum = logsNum;
	}

	@Override
	public String toString() {
		return "Partition [LeaderBrokerAddr=" + LeaderBrokerAddr + ", PartitionId=" + PartitionId + ", OffsetNewest="
				+ OffsetNewest + ", OffsetOldest=" + OffsetOldest + ", LogsNum=" + LogsNum + "]";
	}

	
    
}
