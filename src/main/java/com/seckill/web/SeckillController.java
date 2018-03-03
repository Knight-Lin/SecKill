package com.seckill.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;

@Controller
@RequestMapping("/seckill")//url:/模块/资源/{id}/细分
public class SeckillController {
	
	@Resource
	private SeckillService seckillService;
	private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}
	
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model) {
		if(seckillId==null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId); 
		
		if(seckill==null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	@RequestMapping(value="/{seckillId}/exposer",
			method=RequestMethod.POST
			)
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result =  new SeckillResult<Exposer>(true,exposer);
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			result = new SeckillResult<Exposer>(false,e.getMessage());
			return result;
		}
		return result;
	}
	
	
	
	@RequestMapping(value="/{seckillId}/{md5}/execution",
			method=RequestMethod.POST,
			produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId,
													@CookieValue(value="killPhone",required=false)Long userPhone,
												   @PathVariable("md5")String md5){
		if(userPhone==null) {
			return new SeckillResult<SeckillExecution>(false,"未注册"); 
		}
		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch(RepeatKillException e1){
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch(SeckillCloseException e2) {
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true,execution);
			
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true,execution);
		}
		
	}
	
	
	@RequestMapping(value="/time/now",method=RequestMethod.GET,produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public SeckillResult<Long> time(){
		Date now = new Date();
		return new SeckillResult<Long>(true,now.getTime());
	}
	
}
