package com.AllorChannels.Dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelCategory {

	private int no;
	
	private String categoryName;
	
	private String title;
	
	private Timestamp date;

}
