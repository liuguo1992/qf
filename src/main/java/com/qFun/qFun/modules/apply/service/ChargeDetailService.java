package com.qFun.qFun.modules.apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qFun.qFun.common.service.CrudService;
import com.qFun.qFun.modules.apply.dao.ChargeDetailDao;
import com.qFun.qFun.modules.apply.entity.ChargeDetail;

@Service
@Transactional(readOnly = true)
public class ChargeDetailService extends CrudService<ChargeDetailDao,ChargeDetail>{
	
	@Autowired
	private ChargeDetailDao chargeDetailDao;
	
	@Transactional(readOnly = false)
	public int saveChargeDetail(ChargeDetail chargeDetail){
		return chargeDetailDao.insert(chargeDetail);
	}
	

}
