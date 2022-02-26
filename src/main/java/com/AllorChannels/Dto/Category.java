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
public class Category {

	private int no;
	
	private String nickName;
	
	private String categoryName;	
	
	private Timestamp date;

}
