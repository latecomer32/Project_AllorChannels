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
public class Channel {

	private int no;
	
	private String title;
	
	private String nickName;
	
	private Timestamp date;

}
