package com.quantosauros.manager.service;

import java.util.List;

import com.quantosauros.manager.model.DetailInfo;

public interface DetailInfoService {

	List<DetailInfo> selectList(String instrumentCd,
			String procId, String valueType, String nonCallCd, String legType);
	
}