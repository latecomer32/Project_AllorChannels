package com.AllorChannels.Dto;

import java.sql.Timestamp;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCategoryView {

	private int no;
	
	private String email;
	
	private String userId;
	
	private String nickName;
	
	private String password;
	
	private RoleType role;
	
	private String oauth; //kakao. google
	
	private Timestamp date;
	
	private boolean pub;
	
	private String categoryName;	
}
